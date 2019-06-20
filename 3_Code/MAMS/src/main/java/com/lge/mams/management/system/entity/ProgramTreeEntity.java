package com.lge.mams.management.system.entity;


/**
 * Program Tree Recursive Entity
 *
 * @version : 1.0
 * @author : Copyright (c) 2014 by MIRINCOM CORP. All Rights Reserved.
 */
public class ProgramTreeEntity extends ProgramEntity {

	// 어드민그룹ID
	private String adminGrpId			= "";

	// 권한 보유 여부
	private String authSel			= "";
	private String authIns			= "";
	private String authUpd			= "";
	private String authDel			= "";

	// recursive tree
	private int levelNo = 0;
	private int childCnt = 0;

	private String fullPathId = "";
	private String fullPathNm = "";

	public String getAvailable() {
		if ("Y".equals(authSel) || "Y".equals(authIns) || "Y".equals(authUpd) || "Y".equals(authDel)) {
			return this.getPgmId();
		} else {
			return "0000";
		}
	}

	public String getAdminGrpId() {
		return adminGrpId;
	}

	public void setAdminGrpId(String adminGrpId) {
		this.adminGrpId = adminGrpId;
	}

	public String getAuthSel() {
		return authSel;
	}

	public void setAuthSel(String authSel) {
		this.authSel = authSel;
	}

	public String getAuthIns() {
		return authIns;
	}

	public void setAuthIns(String authIns) {
		this.authIns = authIns;
	}

	public String getAuthUpd() {
		return authUpd;
	}

	public void setAuthUpd(String authUpd) {
		this.authUpd = authUpd;
	}

	public String getAuthDel() {
		return authDel;
	}

	public void setAuthDel(String authDel) {
		this.authDel = authDel;
	}

	public int getLevelNo() {
		return levelNo;
	}

	public void setLevelNo(int levelNo) {
		this.levelNo = levelNo;
	}

	public int getChildCnt() {
		return childCnt;
	}

	public void setChildCnt(int childCnt) {
		this.childCnt = childCnt;
	}

	public String getFullPathId() {
		return fullPathId;
	}

	public void setFullPathId(String fullPathId) {
		this.fullPathId = fullPathId;
	}

	public String getFullPathNm() {
		return fullPathNm;
	}

	public void setFullPathNm(String fullPathNm) {
		this.fullPathNm = fullPathNm;
	}
}