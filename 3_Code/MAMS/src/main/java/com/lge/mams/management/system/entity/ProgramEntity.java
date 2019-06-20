package com.lge.mams.management.system.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.lge.mams.common.web.entity.AbstractPage;

/**
 * Program Entity
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class ProgramEntity extends AbstractPage {

	/**
	 * 프로그램ID
	 */
	private String pgmId				= "";

	/**
	 * 프로그램명
	 */
	private String pgmNm				= "";

	/**
	 * URL 정보
	 */
	private String urlDs				= "";

	/**
	 * 아이콘
	 */
	private String iconDs				= "";

	// 기능 보유 여부
	private String ynSel				= "";
	private String ynIns				= "";
	private String ynUpd				= "";
	private String ynDel				= "";

	/**
	 * 프로그램 타입
	 */
	private String pgmTp				= "";

	/**
	 * 정렬순서
	 */
	private String rankNo				= "";

	/**
	 * 사용여부
	 */
	private String useYn				= "";

	/**
	 * 상위프로그램ID
	 */
	private String upperPgmId			= "";

	/**
	 * 상위프로그램명
	 */
	private String upperPgmNm			= "";

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getPgmId() {
		return pgmId;
	}

	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}

	public String getPgmNm() {
		return pgmNm;
	}

	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}

	public String getUrlDs() {
		return urlDs;
	}

	public void setUrlDs(String urlDs) {
		this.urlDs = urlDs;
	}

	public String getIconDs() {
		return iconDs;
	}

	public void setIconDs(String iconDs) {
		this.iconDs = iconDs;
	}

	public String getYnSel() {
		return ynSel;
	}

	public void setYnSel(String ynSel) {
		this.ynSel = ynSel;
	}

	public String getYnIns() {
		return ynIns;
	}

	public void setYnIns(String ynIns) {
		this.ynIns = ynIns;
	}

	public String getYnUpd() {
		return ynUpd;
	}

	public void setYnUpd(String ynUpd) {
		this.ynUpd = ynUpd;
	}

	public String getYnDel() {
		return ynDel;
	}

	public void setYnDel(String ynDel) {
		this.ynDel = ynDel;
	}

	public String getPgmTp() {
		return pgmTp;
	}

	public void setPgmTp(String pgmTp) {
		this.pgmTp = pgmTp;
	}

	public String getRankNo() {
		return rankNo;
	}

	public void setRankNo(String rankNo) {
		this.rankNo = rankNo;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getUpperPgmId() {
		return upperPgmId;
	}

	public void setUpperPgmId(String upperPgmId) {
		this.upperPgmId = upperPgmId;
	}

	public String getUpperPgmNm() {
		return upperPgmNm;
	}

	public void setUpperPgmNm(String upperPgmNm) {
		this.upperPgmNm = upperPgmNm;
	}
}