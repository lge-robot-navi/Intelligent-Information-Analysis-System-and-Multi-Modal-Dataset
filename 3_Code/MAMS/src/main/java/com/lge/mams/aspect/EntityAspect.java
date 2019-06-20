package com.lge.mams.aspect;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lge.mams.common.web.entity.BaseEntity;
import com.lge.mams.constants.Constants;

/**
 * Aspect Used to set the value of the Entity Object.
 * Aspect 를 이용하여 Entity 객체에 값을 설정한다.
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Aspect
public class EntityAspect {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	/**
	 * pointcut that target controller class
	 * 컨트롤러 클래스를 대상으로 하는 PointCut
	 * @Mehtod Name : controller
	 */
	@Pointcut("execution(public * com.lge.mams..*Controller.*(..))")
	private void controller() {}

	/**
	 * Automatically inject session value to BaseEntity.
	 * Controller 파라메터 중 BaseEntity instance 에 자동으로 Session 정보를 주입해 준다.
	 * @Mehtod Name : before
	 * @param joinPoint
	 */
	@Before("controller()")
	public void before(JoinPoint joinPoint) {
		//logger.info("######################### {} #########################" , joinPoint.getSignature().toShortString());
		for(Object obj : joinPoint.getArgs()) {
			if (obj instanceof BaseEntity) {
				HttpSession session = (HttpSession) RequestContextHolder
						.currentRequestAttributes()
						.resolveReference(RequestAttributes.REFERENCE_SESSION);
				logger.info("##################################################");
				logger.info("# EntityAspect Start                             #");
				BaseEntity entity = (BaseEntity) obj;
				logger.info("# [BEFORE] USER_SEQ={}, USER_CD={}", entity.getLoginIDInSession(), entity.getLoginCdInSession());
				entity.setLoginIDInSession((String) session.getAttribute(Constants.SESSION_USER_SEQ));
				entity.setLoginCdInSession((String) session.getAttribute(Constants.SESSION_USER_CD));
				logger.info("# [AFTER]  USER_SEQ={}, USER_CD={}", entity.getLoginIDInSession(), entity.getLoginCdInSession());
				logger.info("# EntityAspect End                               #");
				logger.info("##################################################");
			}
		}
	}
}