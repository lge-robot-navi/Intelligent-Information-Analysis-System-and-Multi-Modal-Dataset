package com.lge.mams.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static Date addDay(Date d , int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, days);
		return c.getTime();
	}
}
