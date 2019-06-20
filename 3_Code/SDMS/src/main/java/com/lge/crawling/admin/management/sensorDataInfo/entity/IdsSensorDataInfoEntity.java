package com.lge.crawling.admin.management.sensorDataInfo.entity;

import java.util.List;

/**
 * Ids Image Info Entity
 * @version : 1.0
 * @author  : Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
public class IdsSensorDataInfoEntity {

	// 이미지파일패키지파일ID일련번호
	private String sensorDataFilePackageIdSq						= null;

	// 이미지파일파일패키지설명
	private String sensorDataFilePackageDesc	                    = null;

	// 이미지파일패키지승인여부
	private String sensorDataFilePackageYn	                    = null;

	// 작업구분
	private String workTp	                                = null;

	// 이미지파일패키지파일명
	private String sensorDataFilePackageFileNm	                = null;

	// 이미지파일목록
	private List<SensorDataFileListEntity> sensorDataFileList	        = null;

	// 메타파일생성일시
	private String metaFileRegistDt	                        = null;

	public String getSensorDataFilePackageIdSq() {
		return sensorDataFilePackageIdSq;
	}

	public void setSensorDataFilePackageIdSq(String sensorDataFilePackageIdSq) {
		this.sensorDataFilePackageIdSq = sensorDataFilePackageIdSq;
	}

	public String getSensorDataFilePackageDesc() {
		return sensorDataFilePackageDesc;
	}

	public void setSensorDataFilePackageDesc(String sensorDataFilePackageDesc) {
		this.sensorDataFilePackageDesc = sensorDataFilePackageDesc;
	}

	public String getSensorDataFilePackageYn() {
		return sensorDataFilePackageYn;
	}

	public void setSensorDataFilePackageYn(String sensorDataFilePackageYn) {
		this.sensorDataFilePackageYn = sensorDataFilePackageYn;
	}

	public String getWorkTp() {
		return workTp;
	}

	public void setWorkTp(String workTp) {
		this.workTp = workTp;
	}

	public String getSensorDataFilePackageFileNm() {
		return sensorDataFilePackageFileNm;
	}

	public void setSensorDataFilePackageFileNm(String sensorDataFilePackageFileNm) {
		this.sensorDataFilePackageFileNm = sensorDataFilePackageFileNm;
	}

	public List<SensorDataFileListEntity> getSensorDataFileList() {
		return sensorDataFileList;
	}

	public void setSensorDataFileList(List<SensorDataFileListEntity> sensorDataFileList) {
		this.sensorDataFileList = sensorDataFileList;
	}

	public String getMetaFileRegistDt() {
		return metaFileRegistDt;
	}

	public void setMetaFileRegistDt(String metaFileRegistDt) {
		this.metaFileRegistDt = metaFileRegistDt;
	}

}
