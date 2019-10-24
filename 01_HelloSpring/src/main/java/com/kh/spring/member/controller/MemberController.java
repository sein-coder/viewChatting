package com.kh.spring.member.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.spring.common.encrypt.MyEncrypt;
import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@SessionAttributes(value= {"loginMember","msg"})
@Controller	
public class MemberController {
	private Logger logger=LoggerFactory.getLogger(MemberController.class);
	//spring container가 알아서 해당하는 객체를 생성해서 활용해
	//범위 : springBean중에서!!!!
	@Autowired
	private MemberService service;
	@Autowired
	private BCryptPasswordEncoder pwEncoder;
	@Autowired
	private MyEncrypt enc;
	
	
	
	
	
	@RequestMapping("/member/memberEnroll.do")
	public String memberEnroll() {
		//페이지 전환용
		return "member/memberEnroll";
	}
	
	
	@RequestMapping("/member/memberView.do")
	public String memberView(Member m,Model model) {
		Member result=service.selectMemberOne(m);
		
		try {
			result.setEmail(enc.decrypt(result.getEmail()));
			result.setPhone(enc.decrypt(result.getPhone()));
			result.setAddress(enc.decrypt(result.getAddress()));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("member",result);
		return "member/memberView";
	}
	
	
	@RequestMapping("/member/memberEnrollEnd.do")
	public String memberEnrollEnd(Member m,Model model) {
		//1. 파라미터
		//1) request.getParameter();
		//2) vo객체로 받는것
		//3) Map객체로 받는것.
		//4) @RequestParam이용,변수명, name값 매칭선언!
		
		//2. 파라미터를 DB저장요청~
		//controller -> service -> dao
		
		//비밀번호를 암호화 해보자!!!
		m.setPassword(pwEncoder.encode(m.getPassword()));
		logger.debug(m.getPassword());
		
		//전화번호, 주소, 이메일까지 암호화처리 해보자
		try {
			m.setPhone(enc.encrypt(m.getPhone()));
			m.setEmail(enc.encrypt(m.getEmail()));
			m.setAddress(enc.encrypt(m.getAddress()));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		int result=service.insertMember(m);
		logger.debug(""+result);
		//msg.jsp이용하여 처리해보자.
		String msg="";
		String loc="/";
		if(result>0) {
			msg="회원가입완료!";
		}else {
			msg="회원가입실패!";
		}
		model.addAttribute("msg",msg);
		model.addAttribute("loc",loc);
		
		//가입이 끝나면??? 그냥 view로 연결하면???
		//return "redirect:/";
		//redirect방식으로 메인화면으로 이동
		return "common/msg";
	}
	
	@RequestMapping(value="/member/memberLogin.do",method=RequestMethod.POST)
	public String login(Member m, Model model) {
		//HttpSession session=req.getSession();
		logger.debug(""+m);
		Member result=service.selectMemberOne(m);
		String msg="";
		String loc="/";
		logger.debug(m.getPassword());
		logger.debug(result.getPassword());
		if(result!=null) {
			if(pwEncoder.matches(m.getPassword(), result.getPassword())) {
				msg="로그인 성공!";
				//session.setAttribute("loginMember", result);
				model.addAttribute("loginMember", result);
			}
			
		}else {
			msg="로그인 실패, 다시 시도하세요!";
		}
		model.addAttribute("msg",msg);
		model.addAttribute("loc",loc);
		
		return "common/msg";
		
	}
	
	@RequestMapping("/member/memberLogout.do")
	public String logout(HttpSession session,SessionStatus s) {
		
		if(!s.isComplete()) {
			s.setComplete();//로그아웃 SessionAttributes
			session.invalidate();
		}
		return "redirect:/";
	}
	
	
	//기본 stream방식으로 처리하기
	//@ReposeBody이용->jackson라이브러리 필요
	@RequestMapping("/member/checkId.do")
	@ResponseBody
	public String checkId(Member m,HttpServletResponse res) {
		//맵핑처리해서 반환해주기 ->어떻게
		ObjectMapper mapper=new ObjectMapper();
		//자바클래스와 json으로 보낸 자바스크립트객체를 매칭해주는 jackson에서 제공해주는 클래스
		Member result = service.selectMemberOne(m);
		String jsonStr ="";//변경을 못하는 것들도 있어서 exception이 나온것 ->try/catch문 처리해줘야 해
		//writeValueAsString: 입력받은 객체를 {키:값, 키:값...} 형식의 자바스크립트  객체로 알아서 반환
		try {
			jsonStr= mapper.writeValueAsString(result);
		}catch(JsonProcessingException e) {
			e.printStackTrace();
		}
		res.setContentType("application/json;charset=utf-8");
		return jsonStr;
		
		
	}
	
	
	
	//jsonView를 이용한 ajax처리
	//외부 라이브러리 받아와야 함->pom.xml
	//@RequestMapping("/member/checkId.do")
	/*
	 * public ModelAndView checkId(Member m) { ModelAndView mv=new ModelAndView();
	 * Member result=service.selectMemberOne(m); //result!=null아니면 -> 중복된 아이디를 찾았단 뜻
	 * ->FALSE boolean flag=result!=null?false:true;
	 * mv.addObject("flag",flag);//JSONOBJECT
	 * 
	 * //mv.addObject("member",result); 객체?? //위처럼 못하고 jackson이용한 body로 해줘야함 ,그래서
	 * 아래처럼 각각넣어줘야함
	 * //mv.addObject("userId",result.getUserId());-->nullpoint에러남=>try/catch처리 하기
	 * 
	 * //Viewname은 반드시 joinView여야함 mv.setViewName("jsonView"); return mv; }
	 */

	//기본 Stream방식으로 처리하기
	//json-lib
	//@RequestMapping("/member/checkId.do")
	//ajax는 비동기적 통신 즉. 화면전환이 일부분만 됨으로 굳이 화면으로 쏴줄 필요가 없다.
	/*
	 * public void checkId(Member m,HttpServletResponse res) { Member
	 * result=service.selectMemberOne(m); boolean flag =result!=null?false:true;
	 * res.setContentType("application/json;charset=UTF-8"); try {
	 * res.getOutputStream().print(flag); } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 */
	
}








