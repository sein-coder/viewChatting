package com.kh.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

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
	
	@RequestMapping("/member/memberEnroll.do")
	public String memberEnroll() {
		//페이지 전환용
		return "member/memberEnroll";
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
}








