package com.lge.crawling.admin.management.sensorDataInfo.entity;

import java.util.List;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

import com.lge.crawling.admin.common.web.entity.AbstractPage;

/**
 * Image Info Worker Entity
 * @version : 1.0
 * @author  : Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
public class SensorDataFileWorkerInfoEntity extends AbstractPage {

	// 이미지파일작업자일련번호
	private String sensorDataFileWorkerSq						= null;

	// 이미지파일일련번호
	private String sensorDataFileSq								= null;

	// 이미지파일작업자ID
	private String sensorDataFileWorkerId                        = null;

	// 이미지 파일 일련번호 List
	private String[] sensorDataArray							= null;

	// 이미지 파일 작업자 List
	private String[] workerArray						= null;

	public String getSensorDataFileWorkerSq() {
		return sensorDataFileWorkerSq;
	}

	public void setSensorDataFileWorkerSq(String sensorDataFileWorkerSq) {
		this.sensorDataFileWorkerSq = sensorDataFileWorkerSq;
	}

	public String getSensorDataFileSq() {
		return sensorDataFileSq;
	}

	public void setSensorDataFileSq(String sensorDataFileSq) {
		this.sensorDataFileSq = sensorDataFileSq;
	}

	public String getSensorDataFileWorkerId() {
		return sensorDataFileWorkerId;
	}

	public void setSensorDataFileWorkerId(String sensorDataFileWorkerId) {
		this.sensorDataFileWorkerId = sensorDataFileWorkerId;
	}

	public String[] getImageArray() {
		return sensorDataArray;
	}

	public void setImageArray(String[] sensorDataArray) {
		this.sensorDataArray = sensorDataArray;
	}

	public String[] getWorkerArray() {
		return workerArray;
	}

	public void setWorkerArray(String[] workerArray) {
		this.workerArray = workerArray;
	}

}
