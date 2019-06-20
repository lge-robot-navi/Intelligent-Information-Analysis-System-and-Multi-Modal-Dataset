package com.lge.mams.management.system.entity;

import java.util.Date;

import com.lge.mams.common.web.entity.AbstractPage;

/**
 * Recording Entity
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class RecordingEntity extends AbstractPage {

	private long recFileSq;			// seq
	private String recAgentId;		// agent
	private String recFileName;		// 파일명
	private String recFilePath;		// 경로
	private Date recRegistDt;		// 등록일자
	private String printRecRegistDt;		// 등록일자

	public long getRecFileSq() {
		return recFileSq;
	}
	public void setRecFileSq(long recFileSq) {
		this.recFileSq = recFileSq;
	}
	public String getRecAgentId() {
		return recAgentId;
	}
	public void setRecAgentId(String recAgentId) {
		this.recAgentId = recAgentId;
	}
	public String getRecFileName() {
		return recFileName;
	}
	public void setRecFileName(String recFileName) {
		this.recFileName = recFileName;
	}
	public String getRecFilePath() {
		return recFilePath;
	}
	public void setRecFilePath(String recFilePath) {
		this.recFilePath = recFilePath;
	}
	public Date getRecRegistDt() {
		return recRegistDt;
	}
	public void setRecRegistDt(Date recRegistDt) {
		this.recRegistDt = recRegistDt;
	}
	public String getPrintRecRegistDt() {
		return printRecRegistDt;
	}
	public void setPrintRecRegistDt(String printRecRegistDt) {
		this.printRecRegistDt = printRecRegistDt;
	}
}