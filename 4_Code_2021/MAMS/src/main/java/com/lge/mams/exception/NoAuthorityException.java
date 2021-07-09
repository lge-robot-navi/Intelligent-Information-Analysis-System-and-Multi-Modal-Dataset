package com.lge.mams.exception;

/**
 * No Authority Exception
 * 권한정보가 존재하지 않을 경우 Exception
 * 
 * @version : 1.0
 * @author : Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class NoAuthorityException extends Exception {

	/** UID */
	private static final long serialVersionUID = 9070613257964328322L;

	/**
	 * Constructor of NoAuthorityException.java class
	 */
	public NoAuthorityException() {
	}

	/**
	 * Constructor of NoAuthorityException.java class
	 */
	public NoAuthorityException(String msg) {
		super(msg);
	}
}