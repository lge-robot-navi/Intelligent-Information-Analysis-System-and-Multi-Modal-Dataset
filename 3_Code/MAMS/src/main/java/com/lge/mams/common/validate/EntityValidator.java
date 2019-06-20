package com.lge.mams.common.validate;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity Validator
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class EntityValidator {

	private static Logger logger = LoggerFactory.getLogger(EntityValidator.class);

	/**
	 * JSR-303 Validate 을 이용하여 Object 를 검증한다.<br/>
	 * 검증이 <b>"실패"</b> 할 경우 <b>true</b> 를 리턴하며<br/>
	 * <b>"성공"</b>일 경우 <b>false</b> 를 돌려준다.
	 *
	 * @Mehtod Name : isValid
	 * @param object : 검증대상 Object
	 * @param classes : 검증대상 Group, 없을 경우 Default.class
	 * @return
	 */
	public static <T> boolean isValid(T object, Class<?> ... classes) {

		if (classes == null || classes.length == 0 || classes[0] == null) {
			classes = new Class<?>[] { Default.class};
		}

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<T>> constraintViolations = validator.validate(object, classes);
		if (constraintViolations.size() > 0) {

			StringBuilder sb = new StringBuilder();
			sb.append(String.format("\r\n======== %s Invalid Error ========\r\n", object.getClass().getSimpleName()));

			Iterator<ConstraintViolation<T>> it = constraintViolations.iterator();
			while (it.hasNext()) {
				ConstraintViolation<T> error = it.next();
				sb.append("field : ").append(error.getPropertyPath()).append("\r\n")
				.append("message : ").append(error.getMessage()).append("\r\n")
				.append("invalid value : [").append(error.getInvalidValue()).append("]\r\n");
				sb.append("----------------------------------------\r\n");
			}
			logger.warn(sb.toString());

			return true;
		}

		return false;
	}
}
