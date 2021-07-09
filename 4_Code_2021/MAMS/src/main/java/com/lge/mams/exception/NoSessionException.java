package com.lge.mams.exception;

/**
 * Session Exception
 * 세선 정보가 없을 때 발생되는 Exception
 * 
 * @version : 1.0
 * @author : Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class NoSessionException extends Exception {

	/** UID */
	private static final long serialVersionUID = 9070613257964328322L;

	/**
	 * Constructor of NoSessionException.java class
	 */
	public NoSessionException() {
	}

	/**
	 * Constructor of NoSessionException.java class
	 */
	public NoSessionException(String msg) {
		super(msg);
	}
}