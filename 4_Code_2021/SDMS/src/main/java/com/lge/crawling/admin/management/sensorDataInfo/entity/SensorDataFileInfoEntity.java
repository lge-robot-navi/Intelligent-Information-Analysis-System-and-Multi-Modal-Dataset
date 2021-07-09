package com.lge.crawling.admin.management.sensorDataInfo.entity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lge.crawling.admin.common.web.entity.AbstractPage;

/**
 * Image Info Entity
 * @version : 1.0
 * @author  : Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
public class SensorDataFileInfoEntity extends AbstractPage {

	// 이미지파일일련번호
	@NotEmpty
	@Size(max=20)
	private String sensorDataFileSq								= null;

	// 이미지파일패키지ID일련번호
	@NotEmpty
	@Size(max=40)
	private String sensorDataFilePackageIdSq                     = null;

	// 이미지그룹
	@Size(max=14)
	private String sensorDataFileGroup                           = null;
	
	// Agent
	@Size(max=10)
	private String sensorDataFileAgent                           = null;

	// 이미지파일명
	@NotEmpty
	@Size(max=256)
	private String sensorDataFileNm                              = null;

	// 이미지파일경로
	@NotEmpty
	@Size(max=512)
	private String sensorDataFilePath                            = null;

	// 이미지파일크기
	@NotEmpty
	@Size(max=20)
	private String sensorDataFileSize                            = null;

	// 이미지파일크기_X
	@NotEmpty
	@Size(max=20)
	private String sensorDataFileScaleX                          = null;

	// 이미지파일크기_Y
	@NotEmpty
	@Size(max=20)
	private String sensorDataFileScaleY                          = null;

	// 이미지파일다운로드경로URL
//	@NotEmpty
//	@Size(max=2048)
//	private String sensorDataFileDownloadPathUrl					= null;

	// 이미지파일타입구분_TA004
	@NotEmpty
	@Size(max=8)
	private String sensorDataFileTypeCd                          = null;

	// 이미지파일다운경로구분_TA005
//	@NotEmpty
//	@Size(max=8)
//	private String sensorDataFileDownloadPathCd                  = null;

	// 최종작업이미지파일크기_X
	@Size(max=20)
	private String lastUpdateSensorDataFileScaleX				= null;

	// 최종작업이미지파일크기_X
	@Size(max=20)
	private String lastUpdateSensorDataFileScaleY                = null;

	// 최종작업이미지배율
	@Size(max=10)
	private String lastUpdateImageMagnification             = null;

	// 이미지파일생성일시
	@NotEmpty
	@Size(max = 14)
	private String sensorDataFileCreateDt 						= null;

	// 이미지파일등록일시
	@NotEmpty
	@Size(max=14)
	private String sensorDataFileRegistDt                       = null;

	// #### ImageJsonFileInfo Entity ####
	// 이미지JSON파일일련번호
	@NotEmpty
	@Size(max=11)
	private String sensorDataJsonFileSq							= null;

	// 이미지JSON파일내용
	@NotEmpty
	@Size(max=20)
	private String sensorDataJsonFileDesc                        = null;

	// 이미지JSON-XML변환파일내용
	@NotEmpty
	@Size(max=20)
	private String sensorDataJsonXmlConvFileDesc                 = null;

	// 이미지JSON파일명
	@Size(max=256)
	private String sensorDataJsonFileNm                          = null;

	// 이미지JSON파일경로
	@Size(max=512)
	private String sensorDataJsonFilePath                        = null;

	// 이미지JSON파일크기
	@Size(max=20)
	private String sensorDataJsonFileSize                        = null;

	// #### SensorDataFilePackageInfo Entity ####
	// 이미지파일패키지버전코드
	@Size(max=8)
	private String sensorDataFilePackageVerCode                 = null;

	// 이미지파일패키지버전명
	@Size(max=64)
	private String sensorDataFilePackageVerNm                   = null;

	// 이미지파일패키지명
	@NotEmpty
	@Size(max=256)
	private String sensorDataFilePackageNm                      = null;

	// 이미지파일패키지경로
	@NotEmpty
	@Size(max=512)
	private String sensorDataFilePackagePath                    = null;

	// 이미지파일패키지크기
	@NotEmpty
	@Size(max=20)
	private String sensorDataFilePackageSize                    = null;

	// 이미지파일패키지설명
	@Size(max=256)
	private String sensorDataFilePackageDesc                    = null;

	// 이미지파일등록상세일시
	@NotEmpty
	@Size(max=14)
	private String sensorDataFilePackageRegistDt                = null;

	// 이미지파일패키지ID일련번호생성일시
	@NotEmpty
	@Size(max=14)
	private String sensorDataFilePackageIdSqCreateDt            = null;

	// 태깅여부
	private String taggingYn					= "";

	public String getSensorDataFileSq() {
		return sensorDataFileSq;
	}

	public void setSensorDataFileSq(String sensorDataFileSq) {
		this.sensorDataFileSq = sensorDataFileSq;
	}

	public String getSensorDataFilePackageIdSq() {
		return sensorDataFilePackageIdSq;
	}

	public void setSensorDataFilePackageIdSq(String sensorDataFilePackageIdSq) {
		this.sensorDataFilePackageIdSq = sensorDataFilePackageIdSq;
	}

	public String getSensorDataFileGroup() {
		return sensorDataFileGroup;
	}

	public void setSensorDataFileGroup(String sensorDataFileGroup) {
		this.sensorDataFileGroup = sensorDataFileGroup;
	}
	
	public String getSensorDataFileAgent() {
		return sensorDataFileAgent;
	}

	public void setSensorDataFileAgent(String sensorDataFileAgent) {
		this.sensorDataFileAgent = sensorDataFileAgent;
	}
	
	public String getSensorDataFileNm() {
		return sensorDataFileNm;
	}

	public void setSensorDataFileNm(String sensorDataFileNm) {
		this.sensorDataFileNm = sensorDataFileNm;
	}

	public String getSensorDataFilePath() {
		return sensorDataFilePath;
	}

	public void setSensorDataFilePath(String sensorDataFilePath) {
		this.sensorDataFilePath = sensorDataFilePath;
	}

	public String getSensorDataFileSize() {
		return sensorDataFileSize;
	}

	public void setSensorDataFileSize(String sensorDataFileSize) {
		this.sensorDataFileSize = sensorDataFileSize;
	}

	public String getSensorDataFileScaleX() {
		return sensorDataFileScaleX;
	}

	public void setSensorDataFileScaleX(String sensorDataFileScaleX) {
		this.sensorDataFileScaleX = sensorDataFileScaleX;
	}

	public String getSensorDataFileScaleY() {
		return sensorDataFileScaleY;
	}

	public void setSensorDataFileScaleY(String sensorDataFileScaleY) {
		this.sensorDataFileScaleY = sensorDataFileScaleY;
	}

	public String getSensorDataFileTypeCd() {
		return sensorDataFileTypeCd;
	}

	public void setSensorDataFileTypeCd(String sensorDataFileTypeCd) {
		this.sensorDataFileTypeCd = sensorDataFileTypeCd;
	}

//	public String getSensorDataFileDownloadPathCd() {
//		return sensorDataFileDownloadPathCd;
//	}
//
//	public void setSensorDataFileDownloadPathCd(String sensorDataFileDownloadPathCd) {
//		this.sensorDataFileDownloadPathCd = sensorDataFileDownloadPathCd;
//	}
	
	public String getSensorDataFileCreateDt() {
		return sensorDataFileCreateDt;
	}

	public void setSensorDataFileCreateDt(String sensorDataFileCreateDt) {
		this.sensorDataFileCreateDt = sensorDataFileCreateDt;
	}

	public String getSensorDataFileRegistDt() {
		return sensorDataFileRegistDt;
	}

	public void setSensorDataFileRegistDt(String sensorDataFileRegistDt) {
		this.sensorDataFileRegistDt = sensorDataFileRegistDt;
	}

	public String getLastUpdateSensorDataFileScaleX() {
		return lastUpdateSensorDataFileScaleX;
	}

	public void setLastUpdateSensorDataFileScaleX(String lastUpdateSensorDataFileScaleX) {
		this.lastUpdateSensorDataFileScaleX = lastUpdateSensorDataFileScaleX;
	}

	public String getLastUpdateSensorDataFileScaleY() {
		return lastUpdateSensorDataFileScaleY;
	}

	public void setLastUpdateSensorDataFileScaleY(String lastUpdateSensorDataFileScaleY) {
		this.lastUpdateSensorDataFileScaleY = lastUpdateSensorDataFileScaleY;
	}

	public String getLastUpdateImageMagnification() {
		return lastUpdateImageMagnification;
	}

	public void setLastUpdateImageMagnification(String lastUpdateImageMagnification) {
		this.lastUpdateImageMagnification = lastUpdateImageMagnification;
	}

	public String getImageJsonFileSq() {
		return sensorDataJsonFileSq;
	}

	public void setImageJsonFileSq(String sensorDataJsonFileSq) {
		this.sensorDataJsonFileSq = sensorDataJsonFileSq;
	}

	public String getImageJsonFileDesc() {
		return sensorDataJsonFileDesc;
	}

	public void setImageJsonFileDesc(String sensorDataJsonFileDesc) {
		this.sensorDataJsonFileDesc = sensorDataJsonFileDesc;
	}

	public String getImageJsonFileNm() {
		return sensorDataJsonFileNm;
	}

	public void setImageJsonFileNm(String sensorDataJsonFileNm) {
		this.sensorDataJsonFileNm = sensorDataJsonFileNm;
	}

	public String getImageJsonFilePath() {
		return sensorDataJsonFilePath;
	}

	public void setImageJsonFilePath(String sensorDataJsonFilePath) {
		this.sensorDataJsonFilePath = sensorDataJsonFilePath;
	}

	public String getImageJsonFileSize() {
		return sensorDataJsonFileSize;
	}

	public void setImageJsonFileSize(String sensorDataJsonFileSize) {
		this.sensorDataJsonFileSize = sensorDataJsonFileSize;
	}

	public String getSensorDataFilePackageVerCode() {
		return sensorDataFilePackageVerCode;
	}

	public void setSensorDataFilePackageVerCode(String sensorDataFilePackageVerCode) {
		this.sensorDataFilePackageVerCode = sensorDataFilePackageVerCode;
	}

	public String getSensorDataFilePackageVerNm() {
		return sensorDataFilePackageVerNm;
	}

	public void setSensorDataFilePackageVerNm(String sensorDataFilePackageVerNm) {
		this.sensorDataFilePackageVerNm = sensorDataFilePackageVerNm;
	}

	public String getSensorDataFilePackageNm() {
		return sensorDataFilePackageNm;
	}

	public void setSensorDataFilePackageNm(String sensorDataFilePackageNm) {
		this.sensorDataFilePackageNm = sensorDataFilePackageNm;
	}

	public String getSensorDataFilePackagePath() {
		return sensorDataFilePackagePath;
	}

	public void setSensorDataFilePackagePath(String sensorDataFilePackagePath) {
		this.sensorDataFilePackagePath = sensorDataFilePackagePath;
	}

	public String getSensorDataFilePackageSize() {
		return sensorDataFilePackageSize;
	}

	public void setSensorDataFilePackageSize(String sensorDataFilePackageSize) {
		this.sensorDataFilePackageSize = sensorDataFilePackageSize;
	}

	public String getSensorDataFilePackageDesc() {
		return sensorDataFilePackageDesc;
	}

	public void setSensorDataFilePackageDesc(String sensorDataFilePackageDesc) {
		this.sensorDataFilePackageDesc = sensorDataFilePackageDesc;
	}

	public String getSensorDataFilePackageRegistDt() {
		return sensorDataFilePackageRegistDt;
	}

	public void setSensorDataFilePackageRegistDt(String sensorDataFilePackageRegistDt) {
		this.sensorDataFilePackageRegistDt = sensorDataFilePackageRegistDt;
	}

	public String getSensorDataFilePackageIdSqCreateDt() {
		return sensorDataFilePackageIdSqCreateDt;
	}

	public void setSensorDataFilePackageIdSqCreateDt(String sensorDataFilePackageIdSqCreateDt) {
		this.sensorDataFilePackageIdSqCreateDt = sensorDataFilePackageIdSqCreateDt;
	}

	public String getImageJsonXmlConvFileDesc() {
		return sensorDataJsonXmlConvFileDesc;
	}

	public void setImageJsonXmlConvFileDesc(String sensorDataJsonXmlConvFileDesc) {
		this.sensorDataJsonXmlConvFileDesc = sensorDataJsonXmlConvFileDesc;
	}

//	public String getSensorDataFileDownloadPathUrl() {
//		return sensorDataFileDownloadPathUrl;
//	}
//
//	public void setSensorDataFileDownloadPathUrl(String sensorDataFileDownloadPathUrl) {
//		this.sensorDataFileDownloadPathUrl = sensorDataFileDownloadPathUrl;
//	}

	public String getTaggingYn() {
		return taggingYn;
	}

	public void setTaggingYn(String taggingYn) {
		this.taggingYn = taggingYn;
	}
}
