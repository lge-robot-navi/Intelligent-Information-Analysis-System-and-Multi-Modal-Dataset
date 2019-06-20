package com.lge.crawling.admin.common.util;

import java.util.Calendar;

/**
 * Date Support Util
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class DateSupportUtil {

	/**
	 * Start date search criteria
	 * 날짜검색 기본 시작일자
	 * @Mehtod Name : getDefaultStart
	 * @return
	 */
	public static String getDefaultStart(String param) {
		Calendar cal = DateUtil.getSystemDate();
		cal.add(Calendar.DATE, Config.getCommon().getInt(param));
		return DateUtil.format(cal, DateUtil.YYYYMMDD);
	}

	/**
	 * End date search criteria
	 * 날짜검색 기본 종료일자
	 * @Mehtod Name : getDefaultEnd
	 * @return
	 */
	public static String getDefaultEnd(String param) {
		Calendar cal = DateUtil.getSystemDate();
		cal.add(Calendar.DATE, Config.getCommon().getInt(param));
		return DateUtil.format(cal, DateUtil.YYYYMMDD);
	}

	/**
	 * Start date search criteria
	 * 날짜검색 기본 시작일자
	 * @Mehtod Name : getDefaultStart
	 * @return
	 */
	public static String getDefaultStart() {
		return DateSupportUtil.getDefaultStart("SEARCH_CONDITION.DEFAULT_START_DATE");
	}

	/**
	 * End date search criteria
	 * 날짜검색 기본 종료일자
	 * @Mehtod Name : getDefaultEnd
	 * @return
	 */
	public static String getDefaultEnd() {
		return DateSupportUtil.getDefaultEnd("SEARCH_CONDITION.DEFAULT_END_DATE");
	}
}
