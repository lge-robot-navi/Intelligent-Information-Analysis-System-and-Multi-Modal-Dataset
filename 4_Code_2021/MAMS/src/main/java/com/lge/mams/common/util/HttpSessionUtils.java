package com.lge.mams.common.util;

import javax.servlet.http.HttpSession;

import com.lge.mams.management.system.entity.AdminEntity;


public class HttpSessionUtils {
	
	public static final String USER_SESSION_KEY = "sessionedUser";

	public static boolean isLoginUser(HttpSession session) {
		Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
		if(sessionedUser == null) {
			return false;
		}
		return true;
	}
}
