package com.lge.crawling.admin.management.sensorDataInfo.entity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lge.crawling.admin.common.web.entity.AbstractPage;

/**
 * Image File Package Info Entity
 * @version : 1.0
 * @author  : Copyright (c) 2017 by UBIVELOX CORP. All Rights Reserved.
 */
public class SensorDataFilePackageInfoEntity extends AbstractPage {

	// 이미지파일패키지ID일련번호
	@NotEmpty
	@Size(max=20)
	private String sensorDataFilePackageIdSq                    = null;

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

	public String getSensorDataFilePackageIdSq() {
		return sensorDataFilePackageIdSq;
	}

	public void setSensorDataFilePackageIdSq(String sensorDataFilePackageIdSq) {
		this.sensorDataFilePackageIdSq = sensorDataFilePackageIdSq;
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

}
