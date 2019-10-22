package com.kh.spring.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public class AspectTest {

	private Logger logger=LoggerFactory.getLogger(AspectTest.class);
	
	public void beforeTest(JoinPoint jp) {
		logger.debug("=====before====");
		Signature sig=jp.getSignature();
		String methodName=sig.getName();
		logger.debug("실행된 메소드 : "+methodName);
	}
	public void afterTest(JoinPoint jp) {
		logger.debug("====매소드 실행 후======");
		Signature sig=jp.getSignature();
		String className=sig.getDeclaringTypeName();
		String methodName=sig.getName();
		Object[] param =jp.getArgs();
		for(Object o:param) {
			logger.debug(""+o);//이런식으로 메소드실행시 무슨값이 들어갔는지 체크할 수 있음
		}
		logger.debug("실행된 매소드:"+className+methodName);
	}
	//메소드 하나로 around처리하기 전후둘다 함
	public Object aroundTest(ProceedingJoinPoint pjp) throws Throwable{
		//전처리
		logger.debug("시작시간:"+System.currentTimeMillis());//INSERT 메소드가 시작했을 때 실행
		StopWatch stopWatch=new StopWatch();
		stopWatch.start();
		
		//후처리로 넘어가는 기준
		//return pjp.proceed();//이렇게 쓰면 전처리만 가능,대신 나머지(이 밑으로는)모두 지워야함
		Object obj = pjp.proceed();//이이후로부터는 메소드가 끝나고 실행함
		
		stopWatch.stop();
		logger.debug("종료시작:"+System.currentTimeMillis());
		logger.debug("걸린시간:"+stopWatch.getTotalTimeMillis());
		return obj;
		//조회해올 때 시간이 오래걸린다면 이 메소드의 시간을 체크하고, 최적화할수 있다
	}
	
	
}





