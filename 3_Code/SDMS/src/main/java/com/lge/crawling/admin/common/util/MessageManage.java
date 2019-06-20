package com.lge.crawling.admin.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;

/**
 * Message Manager
 * Message Manager 역할을한다.
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class MessageManage {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(MessageManage.class);

	private static MessageSourceAccessor messageSourceAccessor = null;

	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		MessageManage.messageSourceAccessor = messageSourceAccessor;
	}

	/**
	 * Return message for the Key
	 * Key 에 해당하는 메시지 반환
	 * @Mehtod Name : getMessage
	 * @param key
	 * @return
	 */
	public static String getMessage(String key) {
		try {
			return messageSourceAccessor.getMessage(key, LocaleContextHolder.getLocale());
		} catch (NoSuchMessageException e) {
			logger.error("NoSuchMessageException: {}", e.getMessage());
		} catch (Exception e) {
			logger.error("Exception: {}", e.getMessage());
		}

		return "";
	}

	/**
	 * Key args with messages for the return match
	 * Key 에 해당하는 메시지를 args와 매치 반환
	 * @Mehtod Name : getMessage
	 * @param key
	 * @param objs
	 * @return
	 */
	public static String getMessage(String key, Object[] objs) {
		try {
			return messageSourceAccessor.getMessage(key, objs, LocaleContextHolder.getLocale());
		} catch (NoSuchMessageException e) {
			logger.error("NoSuchMessageException: {}", e.getMessage());
		} catch (Exception e) {
			logger.error("Exception: {}", e.getMessage());
		}
		return "";
	}
}