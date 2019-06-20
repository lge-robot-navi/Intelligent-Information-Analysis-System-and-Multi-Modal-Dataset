package com.lge.crawling.admin.management.imageInfo.entity;

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
public class ImageFileWorkerInfoEntity extends AbstractPage {

	// 이미지파일작업자일련번호
	private String imageFileWorkerSq						= null;

	// 이미지파일일련번호
	private String imageFileSq								= null;

	// 이미지파일작업자ID
	private String imageFileWorkerId                        = null;

	// 이미지 파일 일련번호 List
	private String[] imageArray							= null;

	// 이미지 파일 작업자 List
	private String[] workerArray						= null;

	public String getImageFileWorkerSq() {
		return imageFileWorkerSq;
	}

	public void setImageFileWorkerSq(String imageFileWorkerSq) {
		this.imageFileWorkerSq = imageFileWorkerSq;
	}

	public String getImageFileSq() {
		return imageFileSq;
	}

	public void setImageFileSq(String imageFileSq) {
		this.imageFileSq = imageFileSq;
	}

	public String getImageFileWorkerId() {
		return imageFileWorkerId;
	}

	public void setImageFileWorkerId(String imageFileWorkerId) {
		this.imageFileWorkerId = imageFileWorkerId;
	}

	public String[] getImageArray() {
		return imageArray;
	}

	public void setImageArray(String[] imageArray) {
		this.imageArray = imageArray;
	}

	public String[] getWorkerArray() {
		return workerArray;
	}

	public void setWorkerArray(String[] workerArray) {
		this.workerArray = workerArray;
	}

}
