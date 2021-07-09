package com.lge.crawling.admin.management.sensorDataInfo.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Image File List Entity
 * @version : 1.0
 * @author  : Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
public class SensorDataFileListEntity {

	// 이미지파일위치
	private String sensorDataFileLoc	                            = null;

	// 이미지파일명
	private String sensorDataFileNm	                            = null;

	// 이미지파일크기
	private String sensorDataFileSize	                        = null;

	// 이미지파일크기_X
	private String sensorDataFileSizeX	                        = null;

	// 이미지파일크기_Y
	private String sensorDataFileSizeY	                        = null;

	// 이미지파일다운로드경로URL
//	private String sensorDataFileDownloadPathUrl					= null;

	// 이미지파일구분
	private String sensorDataFileTp	                            = null;

	// 이미지파일다운로드경로구분
	private String sensorDataFileDownloadPathTp	                = null;

	// 이미지JSON파일위치
	private String sensorDataJsonFileLoc	                        = null;

	// 이미지JSON파일명
	private String sensorDataJsonFileNm	                        = null;

	// 삭제여부
	private String deleteYn	                                = null;

	// 생성일시
	private String createDate	                            = null;

	public String getSensorDataFileLoc() {
		return sensorDataFileLoc;
	}

	public void setSensorDataFileLoc(String sensorDataFileLoc) {
		this.sensorDataFileLoc = sensorDataFileLoc;
	}

	public String getSensorDataFileNm() {
		return sensorDataFileNm;
	}

	public void setSensorDataFileNm(String sensorDataFileNm) {
		this.sensorDataFileNm = sensorDataFileNm;
	}

	public String getSensorDataFileSizeX() {
		return sensorDataFileSizeX;
	}

	public void setSensorDataFileSizeX(String sensorDataFileSizeX) {
		this.sensorDataFileSizeX = sensorDataFileSizeX;
	}

	public String getSensorDataFileSizeY() {
		return sensorDataFileSizeY;
	}

	public void setSensorDataFileSizeY(String sensorDataFileSizeY) {
		this.sensorDataFileSizeY = sensorDataFileSizeY;
	}

	public String getSensorDataFileTp() {
		return sensorDataFileTp;
	}

	public void setSensorDataFileTp(String sensorDataFileTp) {
		this.sensorDataFileTp = sensorDataFileTp;
	}

	public String getSensorDataFileDownloadPathTp() {
		return sensorDataFileDownloadPathTp;
	}

	public void setSensorDataFileDownloadPathTp(String sensorDataFileDownloadPathTp) {
		this.sensorDataFileDownloadPathTp = sensorDataFileDownloadPathTp;
	}

	public String getSensorDataJsonFileLoc() {
		return sensorDataJsonFileLoc;
	}

	public void setSensorDataJsonFileLoc(String sensorDataJsonFileLoc) {
		this.sensorDataJsonFileLoc = sensorDataJsonFileLoc;
	}

	public String getSensorDataJsonFileNm() {
		return sensorDataJsonFileNm;
	}

	public void setSensorDataJsonFileNm(String sensorDataJsonFileNm) {
		this.sensorDataJsonFileNm = sensorDataJsonFileNm;
	}

	public String getDeleteYn() {
		return deleteYn;
	}

	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getSensorDataFileSize() {
		return sensorDataFileSize;
	}

	public void setSensorDataFileSize(String sensorDataFileSize) {
		this.sensorDataFileSize = sensorDataFileSize;
	}

//	public String getSensorDataFileDownloadPathUrl() {
//		return sensorDataFileDownloadPathUrl;
//	}
//
//	public void setSensorDataFileDownloadPathUrl(String sensorDataFileDownloadPathUrl) {
//		this.sensorDataFileDownloadPathUrl = sensorDataFileDownloadPathUrl;
//	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
