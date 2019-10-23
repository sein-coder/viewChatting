package com.kh.spring.board.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.page.PageFactory;


@Controller
public class BoardController {
	private Logger logger =LoggerFactory.getLogger(BoardController.class);
	
	@Autowired BoardService service;
	
	/*스스로 했던 것
	 * @RequestMapping("/board/boardList") public String selectBoard(Model
	 * model,Board board) { List<Board> list=service.selectBoard(board); return
	 * "board/board";
	 * 
	 * }
	 */
	
	//강사님 방식
	@RequestMapping("/board/boardList")
	public ModelAndView boardList(@RequestParam(value="cPage",required=false, defaultValue = "1") int cPage) {
		ModelAndView mv=new ModelAndView();
			/*이렇게 쓰지않고 위에처럼 @써서 한줄로가능! 
			 * int cPage;
			try {
				cPage="dddd";
			}*/
		int numPerPage=5;
		List<Board> list=service.selectBoardList(cPage,numPerPage);
		int totalData=service.selectBoardCount();
		
		mv.addObject("list",list);//key:value형식으로 넣어주면 됨
		mv.addObject("totalCount",totalData);//전체갯수
		mv.addObject("pageBar",PageFactory.getPageBar(totalData,cPage,numPerPage,"/spring/board/boardList"));//메소드를 이용해 팩토리 형식으로 처리할것임
		
		//팩토리메소드를 구현할때 필요한 것들:totalData,cPage,numPerPage,주소
		//자바스크립트를 이용해 할것이라서 /spring/board/boardList다 씀!
		
		mv.setViewName("board/boardList");//똑같이 resolve로 통해서 감 "views/board/boardList"로 하면 안돼!!!
		return mv;
		
	}
	@RequestMapping("/board/boardForm")
	public String boardForm() {
		return "board/boardForm";
	}
	
	
	//스프링 파일 업로드하기
	@RequestMapping("/board/boardFormEnd.do")
	public ModelAndView insertBoard(Board b,@RequestParam(value="upFile", required=false) MultipartFile[] upFile,HttpServletRequest req){
		//@RequestParam을 지정해서 파일이 아예 안올라와서 null일 경우도 처리하기 위해 value속성과 required속성을 등록한다!
		//value속성은 view에서 데이터를 받아올 input태그의 name을 써주면 되고, required속성은 null값을 허용하고 싶을 경우는 false를 null로 허용하지 않고 필수일 경우 true(default값이 true)를 준다.
		
		//title,writer,contents,file2개
		//Board b 에는 binary file받을 수 있는 건 없어,자기가 받을 것만 받아
		//나머지파일은 MultipartFile upfile로 받을 수 있게 매개변수에 씀
		//다중파일을 받으려면 MultipartFile[] 씀
		
		ModelAndView mv=new ModelAndView();
		logger.debug("board : "+b);//값이 잘 넘어오는 지 확인
		logger.debug(upFile[0].getOriginalFilename());
		logger.debug(upFile[1].getOriginalFilename());
		
		//입력된 파일 서버에 저장하는 방법
		//1.파일 저장 경로 지정
		String saveDir = req.getSession().getServletContext()
				.getRealPath("/resources/upload/board");//루트 디렉토리부터 시작해야한다.
		//context부터 시작되야 한단말!
		List<Attachment> list=new ArrayList();//다중 파일을 저장하는 객체,파일이름이 같은 것들을 저장(파일이름이 같은 것들은 list형 을 추천함) 
		
		//저장경로가 없으면 생성하고 있으면 생성하지 않는 코드를 작성
		File dir=new File(saveDir);
		if(!dir.exists()) logger.debug("폴더생성 "+dir.mkdirs());//파일을 생성하는 구문임 mkdirs():해당 경로에 속하는 모든 폴더를 생성
		
		//다중파일 서버에 저장로직
		for(MultipartFile f:upFile) {
			//파일이 실제 있는지 한번 더 확인한것
			if(!f.isEmpty()) {
				//동일한 이름이 나올 수 있으니까! 파일rename을 해주는 것!
				//파일명을 설정(renamed)
				String oriFileName=f.getOriginalFilename();//oriFileName 가져오기
				
				//파일명에서 확장자빼기
				String ext=oriFileName.substring(oriFileName.lastIndexOf("."));//실행파일을 가져오기.'.'을 찾으면확장자임을 알수 있음
				
				//rename규칙설정 -> 파일이름이 겹칠 가능성이 거~의 없음
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
				int rnd=(int)(Math.random()*1000);
				String reName=sdf.format(System.currentTimeMillis())+"_"+rnd+ext;
				//String reName=프로젝트명+sdf.format(System.currentTimeMillis())+"_"+rnd+ext;
				
				/////////////////////////////////////////////////////저장하기 위한 전초작업
				//reName된 파일명으로 저장하기
				try {
					//transferTo는 multipart가 제공
					f.transferTo(new File(saveDir+"/"+reName));
				}catch(IOException e) {
					e.printStackTrace();
				}
				//////////////////////////////////////////////////여기까지가 서버에 실제 파일 저장완료!
				
			//영속성있는 저장을 위해 	
			//DB에 저장하기 위해서는 Attachment설정
			Attachment at=new Attachment();
			//두개 같이 설정해야함 왜?
			at.setOriginalFileName(oriFileName);
			at.setRenamedFileName(reName);
			list.add(at);
			}
		}
		
		
		//트랜잭션이 rollback됐을 경우, 예외처리 해주는 구문만들기
		try {
			//DB에 입력하러 가는 로직으로 감
			service.insertBoard(b,list);
		}catch(Exception e) {
			//파일 삭제로직
			
			//에러 메시지 출력 로직구현//
			logger.debug("에러에러에러 삽입안됨!!");
			//파일 삭제
			
			/*
			 * if(!list.isEmpty()) { for(Attachment a:list) { //삭제할 경로를 del에 넣어서 파일을 삭제함
			 * File del=new File(saveDir+"/"+a.getRenamedFileName()); //이렇게 rollback되면 파일을
			 * 삭제처리시킴 del.delete();
			 * 
			 * } }
			 */
		}
		
		mv.setViewName("redirect:/board/boardList");//redirect를 통해 servlet으로 보내서 처리함(바로 view로 가는게 아님)
		return mv;
	}
	
	//상세화면 출력하기
	@RequestMapping("/board/boardView")
	public ModelAndView boardView(int boardNo) {
		//service.selectBoard(boardNo);아래처럼 써보기
		ModelAndView mv=new ModelAndView();
		mv.addObject("board",service.selectBoard(boardNo));
		mv.addObject("attach",service.selectAttach(boardNo));
		mv.setViewName("board/boardView");
		return mv;
		
	}
	@RequestMapping("/board/filedownLoad.do")
	public void fileDowLoad(String oName,String rName,HttpServletRequest req, HttpServletResponse res) {
		//HttpServletRequest:경로 찾기,HttpServletResponse:String쏴주기
		//파일 입출력을 위한 Stream을 선언
		
		//stream이 뭐야???서버에 저장되어있는 파일을 불러와서 상대방에게 보내는것
		
		BufferedInputStream bis =null;
		ServletOutputStream sos =null;
		
		//파일을 가져올 경로를 써주기
		String saveDir=req.getSession().getServletContext().getRealPath("/resources/upload/board");
		//경로에 있는 파일을 가져오기
		File downFile=new File(saveDir+"/"+rName);//페이지에서 받아온  rName
		
		//본격적으로 파일 가져오기
		try {
			FileInputStream fis=new FileInputStream(downFile);
			bis=new BufferedInputStream(fis);//검색속도를 빠르게 하기 위해 묶음으로보낸것
			//bis=new BufferedInputStream(new FileInputStream(downFile));한줄로 요약가능
			
			sos=res.getOutputStream();//브라우저 
			String resFileName="";
			//user-agent:브라우저에 관한정보,/isMSIE,Trident:마이크로소프트 브라우저를 뜻함
			boolean isMSIE=req.getHeader("user-agent").indexOf("MSIE")!=-1
					|req.getHeader("user-agent").indexOf("Trident")!=-1;
			//브라이저에 따라 달라서 그럼->indexOf설정
			
			if(isMSIE) {
				resFileName=URLEncoder.encode(oName,"UTF-8");//oName를 형식에 맞게 쪼개야 해서 이렇게 씀
				resFileName=resFileName.replaceAll("\\+", "%20");//replaceAll:인코딩한 값이 표준에 맞게 바꾼다->%20은 띄어쓰기를 의미
				//ms exploer에서는 띄었기를 \\+로 표시하기 때문에 표준에 맞게 %20으로 replaceAll메소드르 ㄹ통해 전부 변경함
			}else {
				resFileName=new String(oName.getBytes("UTF-8"), "ISO-8859-1");//한글일때 제대로 출력안되니까 UTF-8써준것
			}
			res.setContentType("application/octet-stream;charset=utf-8");//일반 파일 전송하는 것
			res.addHeader("Content-Disposition", "attachment;filename=\""+resFileName+"\"");//헤더에 담아서 보내버리기,전송할때 요청은 맨앞에 부분이 헤더!머리부분
			//Content-Disposition은 파일 name을 이걸로 하라고 정해준것
			res.setContentLength((int)downFile.length());
			// 자바는 byte가 int로 되어있어서 byte로 받아온걸 갯수만큼 열어놓은 outputstream한테 전송
			int read=0;
			while((read=bis.read())!=-1) {
				//-1이 아니면 돌게함
				sos.write(read);
				}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				//버퍼를 쓰면 닫아야함 그 처리 구문!
				sos.close();
				bis.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	}
	
	
	

}
