package com.lge.mams.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Date Util
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class DateUtil {

	// Date Format Constants
	// 날짜형식 상수
	public static final String YYYYMM = "yyyy-MM";
	public static final String YYYYMMDD = "yyyy-MM-dd";
	public static final String YYYYMMDDHH = "yyyy-MM-dd HH";
	public static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
	public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYYMMDDHHMMSSSSS = "yyyy-MM-dd HH:mm:ss SSS";

	public static final String SERVER_DATE_FORMAT = "yyyyMMddHHmmss";

	// Time Format Constants
	// 시간형식 상수
	public static final String HHMMSS = "HH:mm:ss";

	/**
	 * main
	 * @method main
	 * @param args
	 */
	public static void main(String[] args) {

		// test convert date
		System.out.println("###################################");
		System.out.println("convertDate start!!!!");

		Date fromDate = DateUtil.parse("2014-07-18 12:11:35");
		System.out.println("from date : " + fromDate);

		Date toDate = DateUtil.parse("2014-12-18 16:12:35");
		System.out.println("to date : " + toDate);

		System.out.println("convertDate end!!!!");
		System.out.println("###################################\r\n");

		System.out.println("start");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		System.out.println(DateUtil.format(cal, "yyyy-MM-dd"));
		System.out.println(DateUtil.add(Calendar.MONDAY, -3));
		System.out.println(DateUtil.getFirstDay());
		System.out.println(DateUtil.getLastDay());
		System.out.println("end");

		System.out.println(DateUtil.getCurrentDate(YYYYMMDDHHMMSS));
		System.out.println(DateUtil.getCurrentDate(YYYYMMDDHHMMSSSSS));

	}

	/**
	 * return date to default format
	 * 날짜 기본형식을 정해진 포멧으로 돌려준다.
	 * @method getDefaultDate
	 * @return
	 */
	public static String getDefaultDate() {
		return DateUtil.getCurrentDate(YYYYMMDDHHMMSS);
	}

	/**
	 * return system time to Calendar.
	 * 시스템 시간을 Calendar 형으로 리턴한다.
	 * @method getSystemDate
	 * @return
	 */
	public static Calendar getSystemDate() {
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));
        return cal;
	}

	/**
	 * return current date
	 * 현재 일자를 돌려준다.
	 * @method getCurrentDate
	 * @return
	 */
	public static String getCurrentDate() {
        return format(getSystemDate(), YYYYMMDD);
    }

	/**
	 * return current time in the format requested.
	 * 현재 시간을 요청하신 형식으로 돌려준다.
	 * @method getCurrentDate
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format) {
        return format(getSystemDate(), format);
    }

	/**
	 * parse date
	 * 날짜를 받아 Date 형으로 파싱하여 돌려준다.
	 * @method parse
	 * @param date
	 * @return
	 */
	public static Date parse(String date) {

		date = date.replaceAll("\\W", "");

		if (date.length() == 8) {
			return DateUtil.parse(date, "yyyyMMdd");
		} else if (date.length() == 12) {
			return DateUtil.parse(date, "yyyyMMddHHmm");
		} else if (date.length() == 14) {
			return DateUtil.parse(date, "yyyyMMddHHmmss");
		} else if (date.length() == 17) {
			return DateUtil.parse(date, "yyyyMMddHHmmsssss");
		}
		return null;
	}

	/**
	 *
	 * 날짜와 날짜형식을 받아 Date 형으로 파싱하여 돌려준다.
	 * @method parse
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date parse(String date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * return formatting date
	 * 날짜를 요청한 포멧으로 변환하여 돌려준다.
	 * @method format
	 * @param time
	 * @param format
	 * @return
	 */
	public static String format(long time, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(time);
	}

	/**
	 * Return to convert a date in the format requested.
	 * 날짜를 요청한 포멧으로 변환하여 돌려준다.
	 * @method format
	 * @param cal
	 * @param format
	 * @return
	 */
	public static String format(Calendar cal, String format) {

		if (cal == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.US);
		return sdf.format(cal.getTime());
	}

	/**
	 * Return to convert a date in the format requested.
	 * 날짜를 요청한 포멧으로 변환하여 돌려준다.
	 * @method format
	 * @param s
	 * @param format
	 * @return
	 */
	public static String format(String s, String format) {

		if (s == null) {
			return null;
		}

		Calendar c = toCalendar(s);
		return format(c, format);
	}

	/**
	 * It returns the date using the default format.
	 * 기본 포멧을 이용하여 날짜를 돌려준다.
	 * @method formatYm
	 * @param cal
	 * @return
	 */
	public static String formatYm(Calendar cal) {
		return DateUtil.format(cal, YYYYMM);
	}

	/**
	 * It returns the date using the default format.
	 * 기본 포멧을 이용하여 날짜를 돌려준다.
	 * @method formatYm
	 * @param s
	 * @return
	 */
	public static String formatYm(String s) {
		return DateUtil.format(s, YYYYMM);
	}

	/**
	 * It returns the date using the default format.
	 * 기본 포멧을 이용하여 날짜를 돌려준다.
	 * @method formatYmd
	 * @param cal
	 * @return
	 */
	public static String formatYmd(Calendar cal) {
		if (cal == null) {
			return null;
		}
		return DateUtil.format(cal, YYYYMMDD);
	}

	/**
	 * It returns the date using the default format.
	 * 기본 포멧을 이용하여 날짜를 돌려준다.
	 * @method formatYmd
	 * @param s
	 * @return
	 */
	public static String formatYmd(String s) {
		if (s == null) {
			return null;
		}
		return DateUtil.format(s, YYYYMMDD);
	}

	/**
	 * It returns the date using the default format.
	 * 기본 포멧을 이용하여 날짜를 돌려준다.
	 * @method formatYmdh
	 * @param cal
	 * @return
	 */
	public static String formatYmdh(Calendar cal) {
		return DateUtil.format(cal, YYYYMMDDHH);
	}

	/**
	 * It returns the date using the default format.
	 * 기본 포멧을 이용하여 날짜를 돌려준다.
	 * @method formatYmdh
	 * @param s
	 * @return
	 */
	public static String formatYmdh(String s) {
		return DateUtil.format(s, YYYYMMDDHH);
	}

	/**
	 * It returns the date using the default format.
	 * 기본 포멧을 이용하여 날짜를 돌려준다.
	 * @method formatYmdhm
	 * @param cal
	 * @return
	 */
	public static String formatYmdhm(Calendar cal) {
		return DateUtil.format(cal, YYYYMMDDHHMM);
	}

	/**
	 * It returns the date using the default format.
	 * 기본 포멧을 이용하여 날짜를 돌려준다.
	 * @method formatYmdhm
	 * @param s
	 * @return
	 */
	public static String formatYmdhm(String s) {
		return DateUtil.format(s, YYYYMMDDHHMM);
	}

	/**
	 * It returns the date using the default format.
	 * 기본 포멧을 이용하여 날짜를 돌려준다.
	 * @method formatYmdhms
	 * @param cal
	 * @return
	 */
	public static String formatYmdhms(Calendar cal) {
		return DateUtil.format(cal, YYYYMMDDHHMMSS);
	}

	/**
	 * It returns the date using the default format.
	 * 기본 포멧을 이용하여 날짜를 돌려준다.
	 * @method formatYmdhms
	 * @param s
	 * @return
	 */
	public static String formatYmdhms(String s) {
		return DateUtil.format(s, YYYYMMDDHHMMSS);
	}

	/**
	 * It returns the time, using the default format.
	 * 기본 포멧을 이용하여 시간을 돌려준다.
	 * @method formatHms
	 * @param s
	 * @return
	 */
	public static String formatHms(String s) {
		if (s == null) {
			return null;
		}
		return DateUtil.format(s, HHMMSS);
	}

	/**
	 * It returns the date in the Calendar form.
	 * 일자를 Calendar 형식으로 돌려준다.
	 * @method toCalendar
	 * @param date
	 * @return
	 */
	public static Calendar toCalendar(String date) {

		Calendar cal = Calendar.getInstance();

		if (date.length() == 8) {
			cal.setTime(parse(date, "yyyyMMdd"));
		} else if (date.length() == 7) {
			cal.setTime(parse(date, "yyyy-MM"));
		} else if (date.length() == 10) {
			cal.setTime(parse(date, "yyyy-MM-dd"));
		} else if (date.length() == 12) {
			cal.setTime(parse(date, "yyyyMMddHHmm"));
		} else if (date.length() == 14) {
			cal.setTime(parse(date, "yyyyMMddHHmmss"));
		}

		return cal;
	}
	/**
	 * It returns the date calculated based on the current time.
	 * 현재 시간을 기준으로 계산 된 날짜를 돌려준다.
	 * @method add
	 * @param field
	 * @param amount
	 * @return
	 */
	public static String add(int field, int amount) {
		Calendar cal = getSystemDate();
		cal.add(field, amount);
		return format(cal, "yyyy-MM-dd");
	}

	/**
	 * Formatting the date calculated based on the current time to boot it returns.
	 * 현재 시간을 기준으로 계산 된 날짜를 포멧팅하여 돌려준다.
	 * @method add
	 * @param field
	 * @param amount
	 * @return
	 */
	public static String add(int field, int amount, String fmt) {
		Calendar cal = getSystemDate();
		cal.add(field, amount);
		return format(cal, fmt);
	}

	/**
	 * Returns to calculating the date received.
	 * 입력받은 일자를 연산하여 돌려준다.
	 * @method add
	 * @param date
	 * @param field
	 * @param amount
	 * @return
	 */
	public static String add(String date, int field, int amount) {
		Calendar cal = toCalendar(date);
		cal.add(field, amount);
		if (date.length() == 8)
			return format(cal, "yyyyMMdd");
		if (date.length() == 7)
			return format(cal, "yyyy-MM");
		else
			return format(cal, "yyyy-MM-dd");
    }
	/**
	 * It returns the start date of the month.
	 * 월의 시작일자를 리턴한다.
	 * @method getFirstDay
	 * @param cal
	 * @return
	 */
	public static String getFirstDay(Calendar cal) {
		int firstDay = cal.getActualMinimum(Calendar.DATE);
		cal.set(Calendar.DATE, firstDay);
		return format(cal, "yyyy-MM-dd");
	}
	/**
	 * It returns the start date of the month.
	 * 월의 시작일자를 리턴한다.
	 * @method getFirstDay
	 * @return
	 */
	public static String getFirstDay() {
		return getFirstDay(Calendar.getInstance());
	}
	/**
	 * It returns the last day of the month.
	 * 월의 마지막일자를 리턴한다.
	 * @Mehtod Name : getLastDay
	 * @param cal
	 * @return
	 */
	public static String getLastDay(Calendar cal) {
		int lastDay = cal.getActualMaximum(Calendar.DATE);
		cal.set(Calendar.DATE, lastDay);
		return format(cal, "yyyy-MM-dd");
	}
	/**
	 * It returns the last day of the month.
	 * 월의 마지막일자를 리턴한다.
	 * @method getLastDay
	 * @return
	 */
	public static String getLastDay() {
		return getLastDay(Calendar.getInstance());
	}

	/**
	 * is Within Range
	 * @method isWithinRange
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isWithinRange(String startDate, String endDate) {

		Date startDt = DateUtil.parse(startDate);
		Date endDt = DateUtil.parse(endDate);

		return isWithinRange(new Date(), startDt, endDt);
	}

	/**
	 * is Within Range
	 * @method isWithinRange
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isWithinRange(Date startDate, Date endDate) {
		return isWithinRange(new Date(), startDate, endDate);
	}

	/**
	 * is Within Range
	 * @method isWithinRange
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isWithinRange(Date testDate, Date startDate, Date endDate) {
		return testDate.after(startDate) && testDate.before(endDate);
	}
}