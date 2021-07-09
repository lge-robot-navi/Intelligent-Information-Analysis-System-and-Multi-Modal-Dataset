package com.lge.mams.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 * The logging method execution time.
 * 메소드 실행시간을 로깅한다.
 * 
 * @version : 1.0
 * @author : Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Aspect
public class StopWatchAspect {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(StopWatchAspect.class);

	/**
	 * pointcut that target service class
	 * 구현 클래스를 대상으로 하는 PointCut<br/>
	 * (*DAOpoint, *Service)
	 * 
	 * @Mehtod Name : point
	 */
	@Pointcut("execution(public * com.lge.mams..*Service.*(..))")
	private void point() {
	}

	/**
	 * output runtime
	 * 메소드 실행시간을 출력한다.
	 * 
	 * @Mehtod Name : logAround
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("point()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Signature sig = joinPoint.getSignature();
		StopWatch stopWatch = new StopWatch(sig.getDeclaringTypeName() + sig.getName());
		try {
			stopWatch.start(sig.toShortString());
			return joinPoint.proceed();
		} finally {
			stopWatch.stop();
			logger.info(stopWatch.prettyPrint());
		}
	}
}