package com.lge.mams.common.util;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * Created by chokyujin on 2017. 2. 17..
 */
@Component("commonUtil")
public class CommonUtil {

    public String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public Date addHoursToJavaUtilDate(Date date, int hours) {
    	if( date == null ) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
}
