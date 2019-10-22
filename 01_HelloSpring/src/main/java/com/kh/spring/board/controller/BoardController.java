package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	public ModelAndView insertBoard(Board b,MultipartFile[] upFile,HttpServletRequest req){
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
				.getRealPath("/resources/upload/board");
		//context부터 시작되야 한단말!
		List<Attachment> list=new ArrayList();//다중 파일을 저장하는 객체
		
		//저장경로가 없으면 생성하고 있으면 생성하지 않는 코드를 작성
		File dir=new File(saveDir);
		if(!dir.exists()) logger.debug("폴더생성 "+dir.mkdirs());//파일을 생성하는 구문임 mkdirs():해당 경로에 속하는 모든 폴더를 생성
		
		//다중파일 서버에 저장로직
		for(MultipartFile f:upFile) {
			if(!f.isEmpty()) {
				//파일명을 설정(renamed)
				String oriFileName=f.getOriginalFilename();//가져오게 하고
				
				//파일명에서 확장자빼기
				String ext=oriFileName.substring(oriFileName.lastIndexOf("."));//실행파일을 가져오기.'.'을 찾으면확장자임을 알수 있음
				
				//rename규칙설정
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
				int rnd=(int)(Math.random()*1000);
				String reName=sdf.format(System.currentTimeMillis())+"_"+rnd+ext;
				//String reName=프로젝트명+sdf.format(System.currentTimeMillis())+"_"+rnd+ext;
				
				//reName된 파일명으로 저장하기
				try {
					f.transferTo(new File(saveDir+"/"+reName));
				}catch(IOException e) {
					e.printStackTrace();
				}
			//여기까지가 서버에 실제 파일 저장완료!
			//DB에 저장하기 위해서는 Attachment설정
			Attachment at=new Attachment();
			//두개 같이 설정해야함 왜?
			at.setOriginalFileName(oriFileName);
			at.setRenamedFileName(reName);
			list.add(at);
			}
		}
		service.insertBoard(b,list);
	
		
		mv.setViewName("board/boardList");
		return mv;
	}

}
