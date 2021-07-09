package com.lge.crawling.admin.management.sensorDataInfo.entity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lge.crawling.admin.common.web.entity.AbstractPage;

/**
 * Image Json File Info Entity
 * @version : 1.0
 * @author  : Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
public class SensorDataJsonFileInfoEntity extends AbstractPage {

	// 이미지JSON파일일련번호
	@NotEmpty
	@Size(max=11)
	private String sensorDataJsonFileSq							= null;

	// 이미지파일일련번호
	@NotEmpty
	@Size(max=20)
	private String sensorDataFileSq                              = null;

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

	public String getSensorDataJsonFileSq() {
		return sensorDataJsonFileSq;
	}

	public void setSensorDataJsonFileSq(String sensorDataJsonFileSq) {
		this.sensorDataJsonFileSq = sensorDataJsonFileSq;
	}

	public String getSensorDataFileSq() {
		return sensorDataFileSq;
	}

	public void setSensorDataFileSq(String sensorDataFileSq) {
		this.sensorDataFileSq = sensorDataFileSq;
	}

	public String getSensorDataJsonFileDesc() {
		return sensorDataJsonFileDesc;
	}

	public void setSensorDataJsonFileDesc(String sensorDataJsonFileDesc) {
		this.sensorDataJsonFileDesc = sensorDataJsonFileDesc;
	}

	public String getSensorDataJsonFileNm() {
		return sensorDataJsonFileNm;
	}

	public void setSensorDataJsonFileNm(String sensorDataJsonFileNm) {
		this.sensorDataJsonFileNm = sensorDataJsonFileNm;
	}

	public String getSensorDataJsonFilePath() {
		return sensorDataJsonFilePath;
	}

	public void setSensorDataJsonFilePath(String sensorDataJsonFilePath) {
		this.sensorDataJsonFilePath = sensorDataJsonFilePath;
	}

	public String getSensorDataJsonFileSize() {
		return sensorDataJsonFileSize;
	}

	public void setSensorDataJsonFileSize(String sensorDataJsonFileSize) {
		this.sensorDataJsonFileSize = sensorDataJsonFileSize;
	}

	public String getSensorDataJsonXmlConvFileDesc() {
		return sensorDataJsonXmlConvFileDesc;
	}

	public void setSensorDataJsonXmlConvFileDesc(String sensorDataJsonXmlConvFileDesc) {
		this.sensorDataJsonXmlConvFileDesc = sensorDataJsonXmlConvFileDesc;
	}

}
