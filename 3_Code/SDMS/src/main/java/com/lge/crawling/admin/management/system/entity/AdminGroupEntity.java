package com.lge.crawling.admin.management.system.entity;

import com.lge.crawling.admin.common.web.entity.AbstractPage;

/**
 * 관리자 그룹 Entity
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class AdminGroupEntity extends AbstractPage {

	private String adminGrpId;
	private String adminGrpNm;
	private String adminGrpDs;
	private String useYn;

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