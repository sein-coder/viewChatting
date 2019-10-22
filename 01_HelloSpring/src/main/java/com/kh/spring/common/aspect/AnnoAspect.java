package com.kh.spring.common.aspect;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
@Aspect

//pojo객체는 aspect
//aspect는 pointcut,advice이 있음
public class AnnoAspect {
	Logger logger=LoggerFactory.getLogger(AnnoAspect.class);

	/*
	 * @Pointcut("execution(* com.kh.spring.memo.model.dao..insert*(..))")//insert할때
	 * 비밀번호를 눌렀는지를 확인하기 위한것 public void tttt() {} //메소드 명칭은 표현식에 합당한 이름으로 적어야함
	 */		
	
	//어노테이션에 한번에 쓰는 방법
	@Around("execution(* com.kh.spring.memo.model.dao..insert*(..))")
	public Object AnnoAround(ProceedingJoinPoint pjp) throws Throwable{
		//insert하기 전 전처리
		HttpSession session =(HttpSession)RequestContextHolder.currentRequestAttributes().resolveReference(RequestAttributes.REFERENCE_SESSION); 
		//로그인확인,RequestContextHolder의 현재요청에 대한 속성값 중에서 세션값을 불러오도록 한것
		//REFERENCE_SESSION는 SESSION, REFERENCE_REQUEST는 REQUEST
		if(session!=null&&session.getAttribute("loginMember")==null) {
			//예외처리 만들기
			throw new RuntimeException("로그인해 임마!");
		}
		return pjp.proceed();//쓰고 throw로 던져주기
		
	}
}
	
/*
 *
 * public class AnnoAspect { Logger
 * logger=LoggerFactory.getLogger(AnnoAspect.class);
 * 
 * @Pointcut("execution(* com.kh.spring.memo.model.dao..insert*(..))")//insert할때
 * 비밀번호를 눌렀는지를 확인하기 위한것 public void tttt() {} //메소드 명칭은 표현식에 합당한 이름으로 적어야함
 * 어노테이션 두가지로 나누어서 쓰는 방법
 * @Around("tttt()") public Object AnnoAround(ProceedingJoinPoint pjp) throws
 * Throwable{ //insert하기 전 전처리 HttpSession session
 * =(HttpSession)RequestContextHolder.currentRequestAttributes().
 * resolveReference(RequestAttributes.REFERENCE_SESSION);
 * //로그인확인,RequestContextHolder의 현재요청에 대한 속성값 중에서 세션값을 불러오도록 한것
 * //REFERENCE_SESSION는 SESSION, REFERENCE_REQUEST는 REQUEST
 * if(session!=null&&session.getAttribute("loginMember")==null) { //예외처리 만들기
 * throw new RuntimeException("로그인해 임마!"); } return pjp.proceed();//쓰고 throw로
 * 던져주기
 * 
 * }
 * 
 * 
 * }
 */
