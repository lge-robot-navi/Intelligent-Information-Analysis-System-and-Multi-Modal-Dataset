package com.lge.crawling.admin.aspect;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * logging by using Aspect.
 * Aspect 를 이용하여 Logging을 한다.
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Aspect
public class LoggingAspect {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	private final Integer MAX_LOG_LIST_SIZE = 50;		// The maximum number of list

	/**
	 * pointcut that target service class
	 * 구현 클래스를 대상으로 하는 PointCut<br/>
	 * (*DAOpoint, *Service)
	 * @Mehtod Name : point
	 */
	@Pointcut("execution(public * com.lge.crawling.admin..*Service.*(..))")
	private void point() {}

	/**
	 * before execute method
	 * 메소드 실행 전에 적용되는 Advice
	 * @Mehtod Name : logBefore
	 * @param joinPoint
	 */
	@Before("point()")
	public void logBefore(JoinPoint joinPoint) {

		logger.info("##################################################");

		Signature sig = joinPoint.getSignature();
		logger.info("[BEFORE] : {}.{}()", sig.getDeclaringType(), sig.getName());

		Object[] args = joinPoint.getArgs();
		for (int sz = args.length, i = 0; i < sz; i++) {
			logger.debug("ARGS[{}] : {}", i, args[i]);
		}
	}

	/**
	 * after execute method
	 * 메서드가 정상적으로 실행된 후에 적용되는 Adivce
	 * @Mehtod Name : logAfterReturning
	 * @param joinPoint
	 * @param ret
	 */
	@AfterReturning(pointcut = "point()", returning = "ret")
	public void logAfterReturning(JoinPoint joinPoint, Object ret) {

		if (ret instanceof List<?>) {

			List<?> list = (List<?>) ret;

			int sz = list.size();
			logger.info("[RETURNING] List Total size({})", sz);
			if (sz > MAX_LOG_LIST_SIZE) { // 최대갯수제한
				sz = MAX_LOG_LIST_SIZE;
			}

			for (int i = 0; i < sz; i++) {
				logger.info("List - {} : {}", i, list.get(i));
			}

		} else {

			logger.info("[RETURNING] : {}", ret);
		}
	}

	/**
	 * after throwing
	 * 메서드가 예외를 발생시킬 때 적용되는 Advice를 정의<br/>
	 * try-catch 블록에서 catch와 유사
	 * @Mehtod Name : afterThrowing
	 * @param joinPoint
	 * @param ex
	 */
//	@AfterThrowing(pointcut = "point()", throwing = "ex")
//	public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
//		Signature sig = joinPoint.getSignature();
//		logger.error("[AFTER THROWING] : {}.{}()", sig.getDeclaringType(), sig.getName());
//		logger.error("[EXCEPTION] : {}", ex);
//	}

	/**
	 * Always running advice
	 * 메서드가 정상 실행 여부에 상관없이 적용되는 Advice<br/>
	 * try-catch-finally 에서 finally와 유사
	 * @Mehtod Name : logAfter
	 * @param joinPoint
	 */
	@After("point()")
	public void logAfter(JoinPoint joinPoint) {
		Signature sig = joinPoint.getSignature();
		logger.info("[AFTER] : {}.{}()", sig.getDeclaringType(), sig.getName());
		logger.info("==================================================");
	}
}