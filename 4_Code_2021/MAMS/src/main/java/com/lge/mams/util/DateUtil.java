package com.lge.mams.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static Date addDay(Date d, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, days);
		return c.getTime();
	}
	
	public static Date setHourAndMinute(Date d, int hour, int minute) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR, hour);
		c.set(Calendar.MINUTE, minute);
		return c.getTime();
	}

	public static Date timetToDate(Integer timet) {
		java.util.Date time = new java.util.Date((long) timet * 1000);
		return time;
	}
}
