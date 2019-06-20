package com.lge.mams.common.web.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Base Entity
 * 최상위 기본 엔티티
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class BaseEntity {

	private String loginIDInSession	;        // session login ID (세션 로그인 ID)
	private String loginCdInSession;		 // session login Cd (세션 관리자구분)

	private String insertDt;				 // created date (등록일시)
	private String insertId;				 // created ID (등록자 ID)
	private String updateDt;				 // modified date (수정일시)
	private String updateId;				 // modified ID (수정자 ID)

	private String searchTp;				 // search type (검색타입)
	private String searchWd;				 // search word (검색문자열)
	private String startDt;                 // date of start for search (검색시작일)
	private String endDt;                   // date of end for search (검색종료일)

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	/**
	 * @return the loginIDInSession
	 */
	public String getLoginIDInSession() {
		return loginIDInSession;
	}

	/**
	 * @param loginIDInSession the loginIDInSession to set
	 */
	public void setLoginIDInSession(String loginIDInSession) {
		this.loginIDInSession = loginIDInSession;
	}

	/**
	 * @return the insertDt
	 */
	public String getInsertDt() {
		return insertDt;
	}

	/**
	 * @param insertDt the insertDt to set
	 */
	public void setInsertDt(String insertDt) {
		this.insertDt = insertDt;
	}

	/**
	 * @return the insertId
	 */
	public String getInsertId() {
		return insertId;
	}

	/**
	 * @param insertId the insertId to set
	 */
	public void setInsertId(String insertId) {
		this.insertId = insertId;
	}

	/**
	 * @return the updateDt
	 */
	public String getUpdateDt() {
		return updateDt;
	}

	/**
	 * @param updateDt the updateDt to set
	 */
	public void setUpdateDt(String updateDt) {
		this.updateDt = updateDt;
	}

	/**
	 * @return the updateId
	 */
	public String getUpdateId() {
		return updateId;
	}

	/**
	 * @param updateId the updateId to set
	 */
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	/**
	 * @return the searchTp
	 */
	public String getSearchTp() {
		return searchTp;
	}

	/**
	 * @param searchTp the searchTp to set
	 */
	public void setSearchTp(String searchTp) {
		this.searchTp = searchTp;
	}

	/**
	 * @return the searchWd
	 */
	public String getSearchWd() {
		return searchWd;
	}

	/**
	 * @param searchWd the searchWd to set
	 */
	public void setSearchWd(String searchWd) {
		this.searchWd = searchWd;
	}

	/**
	 * @return the startDt
	 */
	public String getStartDt() {
		return startDt;
	}

	/**
	 * @param startDt the startDt to set
	 */
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}

	/**
	 * @return the endDt
	 */
	public String getEndDt() {
		return endDt;
	}

	/**
	 * @param endDt the endDt to set
	 */
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	public String getLoginCdInSession() {
		return loginCdInSession;
	}

	public void setLoginCdInSession(String loginCdInSession) {
		this.loginCdInSession = loginCdInSession;
	}
}