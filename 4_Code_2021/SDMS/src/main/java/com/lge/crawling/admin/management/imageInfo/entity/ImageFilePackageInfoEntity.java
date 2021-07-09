package com.lge.crawling.admin.management.imageInfo.entity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lge.crawling.admin.common.web.entity.AbstractPage;

/**
 * Image File Package Info Entity
 * @version : 1.0
 * @author  : Copyright (c) 2017 by UBIVELOX CORP. All Rights Reserved.
 */
public class ImageFilePackageInfoEntity extends AbstractPage {

	// 이미지파일패키지ID일련번호
	@NotEmpty
	@Size(max=20)
	private String imageFilePackageIdSq                    = null;

	// 이미지파일패키지버전코드
	@Size(max=8)
	private String imageFilePackageVerCode                 = null;

	// 이미지파일패키지버전명
	@Size(max=64)
	private String imageFilePackageVerNm                   = null;

	// 이미지파일패키지명
	@NotEmpty
	@Size(max=256)
	private String imageFilePackageNm                      = null;

	// 이미지파일패키지경로
	@NotEmpty
	@Size(max=512)
	private String imageFilePackagePath                    = null;

	// 이미지파일패키지크기
	@NotEmpty
	@Size(max=20)
	private String imageFilePackageSize                    = null;

	// 이미지파일패키지설명
	@Size(max=256)
	private String imageFilePackageDesc                    = null;

	// 이미지파일등록상세일시
	@NotEmpty
	@Size(max=14)
	private String imageFilePackageRegistDt                = null;

	// 이미지파일패키지ID일련번호생성일시
	@NotEmpty
	@Size(max=14)
	private String imageFilePackageIdSqCreateDt            = null;

	public String getImageFilePackageIdSq() {
		return imageFilePackageIdSq;
	}

	public void setImageFilePackageIdSq(String imageFilePackageIdSq) {
		this.imageFilePackageIdSq = imageFilePackageIdSq;
	}

	public String getImageFilePackageVerCode() {
		return imageFilePackageVerCode;
	}

	public void setImageFilePackageVerCode(String imageFilePackageVerCode) {
		this.imageFilePackageVerCode = imageFilePackageVerCode;
	}

	public String getImageFilePackageVerNm() {
		return imageFilePackageVerNm;
	}

	public void setImageFilePackageVerNm(String imageFilePackageVerNm) {
		this.imageFilePackageVerNm = imageFilePackageVerNm;
	}

	public String getImageFilePackageNm() {
		return imageFilePackageNm;
	}

	public void setImageFilePackageNm(String imageFilePackageNm) {
		this.imageFilePackageNm = imageFilePackageNm;
	}

	public String getImageFilePackagePath() {
		return imageFilePackagePath;
	}

	public void setImageFilePackagePath(String imageFilePackagePath) {
		this.imageFilePackagePath = imageFilePackagePath;
	}

	public String getImageFilePackageSize() {
		return imageFilePackageSize;
	}

	public void setImageFilePackageSize(String imageFilePackageSize) {
		this.imageFilePackageSize = imageFilePackageSize;
	}

	public String getImageFilePackageDesc() {
		return imageFilePackageDesc;
	}

	public void setImageFilePackageDesc(String imageFilePackageDesc) {
		this.imageFilePackageDesc = imageFilePackageDesc;
	}

	public String getImageFilePackageRegistDt() {
		return imageFilePackageRegistDt;
	}

	public void setImageFilePackageRegistDt(String imageFilePackageRegistDt) {
		this.imageFilePackageRegistDt = imageFilePackageRegistDt;
	}

	public String getImageFilePackageIdSqCreateDt() {
		return imageFilePackageIdSqCreateDt;
	}

	public void setImageFilePackageIdSqCreateDt(String imageFilePackageIdSqCreateDt) {
		this.imageFilePackageIdSqCreateDt = imageFilePackageIdSqCreateDt;
	}

}
