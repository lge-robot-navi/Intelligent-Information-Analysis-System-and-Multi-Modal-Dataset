package com.lge.crawling.admin.management.imageInfo.entity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lge.crawling.admin.common.web.entity.AbstractPage;

/**
 * Image Json File Info Entity
 * @version : 1.0
 * @author  : Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
public class ImageJsonFileInfoEntity extends AbstractPage {

	// 이미지JSON파일일련번호
	@NotEmpty
	@Size(max=11)
	private String imageJsonFileSq							= null;

	// 이미지파일일련번호
	@NotEmpty
	@Size(max=20)
	private String imageFileSq                              = null;

	// 이미지JSON파일내용
	@NotEmpty
	@Size(max=20)
	private String imageJsonFileDesc                        = null;

	// 이미지JSON-XML변환파일내용
	@NotEmpty
	@Size(max=20)
	private String imageJsonXmlConvFileDesc                 = null;

	// 이미지JSON파일명
	@Size(max=256)
	private String imageJsonFileNm                          = null;

	// 이미지JSON파일경로
	@Size(max=512)
	private String imageJsonFilePath                        = null;

	// 이미지JSON파일크기
	@Size(max=20)
	private String imageJsonFileSize                        = null;

	public String getImageJsonFileSq() {
		return imageJsonFileSq;
	}

	public void setImageJsonFileSq(String imageJsonFileSq) {
		this.imageJsonFileSq = imageJsonFileSq;
	}

	public String getImageFileSq() {
		return imageFileSq;
	}

	public void setImageFileSq(String imageFileSq) {
		this.imageFileSq = imageFileSq;
	}

	public String getImageJsonFileDesc() {
		return imageJsonFileDesc;
	}

	public void setImageJsonFileDesc(String imageJsonFileDesc) {
		this.imageJsonFileDesc = imageJsonFileDesc;
	}

	public String getImageJsonFileNm() {
		return imageJsonFileNm;
	}

	public void setImageJsonFileNm(String imageJsonFileNm) {
		this.imageJsonFileNm = imageJsonFileNm;
	}

	public String getImageJsonFilePath() {
		return imageJsonFilePath;
	}

	public void setImageJsonFilePath(String imageJsonFilePath) {
		this.imageJsonFilePath = imageJsonFilePath;
	}

	public String getImageJsonFileSize() {
		return imageJsonFileSize;
	}

	public void setImageJsonFileSize(String imageJsonFileSize) {
		this.imageJsonFileSize = imageJsonFileSize;
	}

	public String getImageJsonXmlConvFileDesc() {
		return imageJsonXmlConvFileDesc;
	}

	public void setImageJsonXmlConvFileDesc(String imageJsonXmlConvFileDesc) {
		this.imageJsonXmlConvFileDesc = imageJsonXmlConvFileDesc;
	}

}
