package com.lge.crawling.admin.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 날짜 및 시간 관련 유틸리티
 */
public final class DatetimeUtil {

	// 년
	public static final int YEAR = Calendar.YEAR;
	// 월
	public static final int MONTH = Calendar.MONTH;
	// 일
	public static final int DATE = Calendar.DATE;
	// 시
	public static final int HOUR = Calendar.HOUR_OF_DAY;
	// 분
	public static final int MINUTE = Calendar.MINUTE;
	// 초
	public static final int SECOND = Calendar.SECOND;
	// millisecond
	public static final int MILLISECOND = Calendar.MILLISECOND;

	/**
	 * 현재 시간을 주어진 패턴으로 변환하여 반환한다.
	 * 
	 * @param pattern
	 *            패턴. java.text.SimpleDateFormat의 형식대로 사용한다.
	 * @return 현재시각.
	 */
	public static String getCurrentDatetime(String pattern) {
		return getDateString(new Date(), pattern);
	}

	/**
	 * 주어진 java.util.Date 객체를 pattern에 맞춰서 문자열로 변환한다.
	 * 
	 * @param date
	 *            날짜. java.util.Date.
	 * @param pattern
	 *            패턴. java.text.SimpleDateFormat의 형식대로 사용한다.
	 * @return pattern에 맞춰 변환된 문자열.
	 * @exception java.lang.NullPointerException
	 *                pattern이 null인 경우.
	 */
	public static String getDateString(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		if (pattern == null) {
			throw new NullPointerException("날짜 포맷이 지정되지 않았습니다");
		}

		SimpleDateFormat sdf = new SimpleDateFormat(pattern, java.util.Locale.KOREA);

		return sdf.format(date);
	}

	/**
	 * 주어진 문자열을 sourcePattern에 맞춰 parsing하여 targetPattern 형태로 변환한다.
	 * 
	 * @param strdate
	 *            날짜.
	 * @param sourcePattern
	 *            패턴. java.text.SimpleDateFormat의 형식대로 사용한다.
	 * @param targetPattern
	 *            패턴. java.text.SimpleDateFormat의 형식대로 사용한다.
	 * @return targetPattern 맞춰 변환된 문자열.
	 */
	public static String parseString(String strdate, String sourcePattern, String targetPattern) {
		return getDateString(parseString(strdate, sourcePattern), targetPattern);
	}

	/**
	 * 주어진 문자열을 sourcePattern에 맞춰 parsing하여 java.util.Date 형태로 변환한다.
	 * 
	 * @param strdate
	 *            날짜.
	 * @param sourcePattern
	 *            패턴. java.text.SimpleDateFormat의 형식대로 사용한다.
	 * @return 변환된 java.util.Date.
	 * @exception java.lang.NullPointerException
	 *                sourcePattern이 null인 경우.
	 */
	public static Date parseString(String strdate, String sourcePattern) {
		if (strdate == null) {
			return null;
		}
		if (sourcePattern == null) {
			throw new NullPointerException("날짜 포맷이 지정되지 않았습니다");
		}

		SimpleDateFormat sdf = new SimpleDateFormat(sourcePattern, java.util.Locale.KOREA);
		Date date = null;

		try {
			date = sdf.parse(strdate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * java.util.Date 개체를 java.util.Calendar 개체로 변환한다.
	 * 
	 * @param date
	 *            날짜. java.util.Date.
	 * @return java.util.Calendar.
	 */
	public static Calendar Date2Calendar(Date date) {
		if (date == null) {
			return null;
		}

		Calendar c = new GregorianCalendar(java.util.Locale.KOREA);
		c.setTime(date);

		return c;
	}

	/**
	 * 달의 가장 마지막 날짜를 리턴한다.
	 * 
	 * @param strdate
	 *            날짜.
	 * @param pattern
	 *            날짜 패턴.
	 * @return 달의 마지막 날짜.
	 */
	public static int getLastDayOfMonth(String strdate, String pattern) {
		return getLastDayOfMonth(parseString(strdate, pattern));
	}

	/**
	 * 달의 가장 마지막 날짜를 리턴한다.
	 * 
	 * @param date
	 *            날짜. java.util.Date.
	 * @return 달의 마지막 날짜.
	 */
	public static int getLastDayOfMonth(Date date) {
		if (date == null) {
			return 0;
		}

		Calendar c = Date2Calendar(date);

		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 날짜에서 특정필드(월, 일, 분 등...)에 값을 더한다.
	 * 
	 * @param strdate
	 *            날짜.
	 * @param pattern
	 *            날짜패턴.
	 * @param field
	 *            값을 더할 필드.
	 * @param amount
	 *            더할 양.
	 * @return 더해진 날짜.
	 */
	public static String add(String strdate, String pattern, int field, int amount) {
		return getDateString(add(parseString(strdate, pattern), field, amount), pattern);
	}

	/**
	 * 날짜에서 특정필드(월, 일, 분 등...)에 값을 더한다.
	 * 
	 * @param date
	 *            날짜. java.util.Date
	 * @param field
	 *            값을 더할 필드.
	 * @param amount
	 *            더할 양.
	 * @return 더해진 날짜. java.util.Date.
	 */
	public static Date add(Date date, int field, int amount) {
		if (date == null) {
			return null;
		}

		Calendar c = Date2Calendar(date);
		c.add(field, amount);

		return c.getTime();
	}

	/**
	 * 주어진 날짜에서 특정필드(월, 일, 분 등...)의 값을 가져온다.
	 * 
	 * @param strdate
	 *            날짜.
	 * @param pattern
	 *            날짜 패턴.
	 * @param field
	 *            가져올 필드.
	 * @return 해당 필드의 값.
	 */
	public static int get(String strdate, String pattern, int field) {
		return get(parseString(strdate, pattern), field);
	}

	/**
	 * 주어진 날짜에서 특정필드(월, 일, 분 등...)의 값을 가져온다.
	 * 
	 * @param date
	 *            날짜. java.util.Date.
	 * @param field
	 *            가져올 필드.
	 * @return 해당 필드의 값.
	 */
	public static int get(Date date, int field) {
		if (date == null) {
			return 0;
		}

		Calendar c = Date2Calendar(date);

		return c.get(field);
	}
	
	/**
	 * 현재 시간을 Timestamp 형식으로 가져오기
	 */
	public long getCurrentTimestamp() {
		Calendar c = Calendar.getInstance();
		long timestamp = c.getTimeInMillis();
		return timestamp;
	}
	
	/**
	 * Timestamp 형식을 Date 형으로 변경
	 */
	public static Date timestampToDate(long timestamp) {
		Date date = new Date(timestamp);
		return date;
	}
	
	/**
	 * Date 형식을 Timestamp 형으로 변경
	 */
	public static long dateToTimestamp(Date date) {
		long timestamp = date.getTime();
		return timestamp;
	}
	
	/**
	 * Timestamp 형식을 Date형 String으로 변경
	 */
	public static String timestampToDatetimeStr(long timestamp) {
		Date date = new Date(timestamp);
		return getDateString(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 시간을 더해서 리턴
	 */
	public static String getCalendarHourPlus(String dateStr, String pattern, int time) {
		Date date;
		SimpleDateFormat sdformat;
		String retStr;
		try {
			sdformat = new SimpleDateFormat(pattern);
			date = sdformat.parse(dateStr);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.HOUR, time);
			retStr = sdformat.format(cal.getTime());  
				
		} catch (ParseException e) {
			retStr = "";
		}
		return retStr;
	}
	
	/**
	 * 시간을 더해서 리턴 (Date)
	 */
	public static Date getDatetimeHourPlus(String dateStr, int time) {
		Date date;
		SimpleDateFormat sdformat;
		String strDate = dateStr + " 00:00:00.000";
		try {
			sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
			date = sdformat.parse(strDate);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.HOUR, time);
			return cal.getTime();
				
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 날짜를 더해서 리턴
	 */
	public static String getCalendarDayPlus(String dateStr, String pattern, int day) {
		Date date;
		SimpleDateFormat sdformat;
		String retStr;
		try {
			sdformat = new SimpleDateFormat(pattern);
			date = sdformat.parse(dateStr);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, day);
			retStr = sdformat.format(cal.getTime());  
				
		} catch (ParseException e) {
			retStr = "";
		}
		return retStr;
	}
	
	/**
	 * UTC를 서울시간으로 변환
	 */
	public static String getDatetimeUtcToSeoul(String dateStr) {
		Date date;
		SimpleDateFormat sdformat;
		String retStr;
		try {
			sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
			date = sdformat.parse(dateStr);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.HOUR, 9);
			retStr = sdformat.format(cal.getTime());  
				
		} catch (ParseException e) {
			retStr = "";
		}
		return retStr;
	}
	
	/**
	 * 일자를 UTC로 변환
	 */
	public static Date getDatetimeCurrentToUtc(String dateStr) {
		Date date;
		SimpleDateFormat sdformat;
		String strDate = dateStr + " 00:00:00.000";
		try {
			sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
			date = sdformat.parse(strDate);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.HOUR, -9);
			return cal.getTime();
				
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 일자를 UTC로 변환 (Start)
	 */
	public static Date getStartDatetimeCurrentToUtc(String dateStr) {
		Date date;
		SimpleDateFormat sdformat;
		String strDate = dateStr + " 00:00:00.000";
		try {
			sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
			date = sdformat.parse(strDate);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.HOUR, -9);
			return cal.getTime();
				
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 일자를 UTC로 변환 (End)
	 */
	public static Date getEndDatetimeCurrentToUtc(String dateStr) {
		Date date;
		SimpleDateFormat sdformat;
		String strDate = dateStr + " 00:00:00.000";
		try {
			sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
			date = sdformat.parse(strDate);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.HOUR, 15);
			return cal.getTime();
				
		} catch (ParseException e) {
			return null;
		}
	}
	
}
