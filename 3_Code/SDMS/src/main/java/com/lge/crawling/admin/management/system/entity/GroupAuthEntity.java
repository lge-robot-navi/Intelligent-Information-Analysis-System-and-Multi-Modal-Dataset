package com.lge.crawling.admin.management.system.entity;

import com.lge.crawling.admin.common.web.entity.AbstractPage;

/**
 * 그룹별 권한 관리 Entity
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class GroupAuthEntity extends AbstractPage {

	private String adminGrpId;
	private String adminGrpNm;
	private String adminGrpDs;
	private String useYn;
	private String[] available;
	private String[] authSel;
	private String[] authIns;
	private String[] authUpd;
	private String[] authDel;

	/**
	 * Constructor of GroupAuthEntity.java class
	 */
	public GroupAuthEntity() {}

	/**
	 * @return the adminGrpId
	 */
	public String getAdminGrpId() {
		return adminGrpId;
	}

	/**
	 * @param adminGrpId the adminGrpId to set
	 */
	public void setAdminGrpId(String adminGrpId) {
		this.adminGrpId = adminGrpId;
	}

	/**
	 * @return the adminGrpNm
	 */
	public String getAdminGrpNm() {
		return adminGrpNm;
	}

	/**
	 * @param adminGrpNm the adminGrpNm to set
	 */
	public void setAdminGrpNm(String adminGrpNm) {
		this.adminGrpNm = adminGrpNm;
	}

	/**
	 * @return the available
	 */
	public String[] getAvailable() {
		return available;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(String[] available) {
		this.available = available;
	}

	/**
	 * @return the authSel
	 */
	public String[] getAuthSel() {
		return authSel;
	}

	/**
	 * @param authSel the authSel to set
	 */
	public void setAuthSel(String[] authSel) {
		this.authSel = authSel;
	}

	/**
	 * @return the authIns
	 */
	public String[] getAuthIns() {
		return authIns;
	}

	/**
	 * @param authIns the authIns to set
	 */
	public void setAuthIns(String[] authIns) {
		this.authIns = authIns;
	}

	/**
	 * @return the authUpd
	 */
	public String[] getAuthUpd() {
		return authUpd;
	}

	/**
	 * @param authUpd the authUpd to set
	 */
	public void setAuthUpd(String[] authUpd) {
		this.authUpd = authUpd;
	}

	/**
	 * @return the authDel
	 */
	public String[] getAuthDel() {
		return authDel;
	}

	/**
	 * @param authDel the authDel to set
	 */
	public void setAuthDel(String[] authDel) {
		this.authDel = authDel;
	}

	/**
	 * @return the adminGrpDs
	 */
	public String getAdminGrpDs() {
		return adminGrpDs;
	}

	/**
	 * @param adminGrpDs the adminGrpDs to set
	 */
	public void setAdminGrpDs(String adminGrpDs) {
		this.adminGrpDs = adminGrpDs;
	}

	/**
	 * @return the useYn
	 */
	public String getUseYn() {
		return useYn;
	}

	/**
	 * @param useYn the useYn to set
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
}