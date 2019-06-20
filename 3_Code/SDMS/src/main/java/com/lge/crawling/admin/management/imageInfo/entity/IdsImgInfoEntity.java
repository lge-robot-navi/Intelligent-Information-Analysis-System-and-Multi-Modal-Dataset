package com.lge.crawling.admin.management.imageInfo.entity;

import java.util.List;

/**
 * Ids Image Info Entity
 * @version : 1.0
 * @author  : Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
public class IdsImgInfoEntity {

	// 이미지파일패키지파일ID일련번호
	private String imageFilePackageIdSq						= null;

	// 이미지파일파일패키지설명
	private String imageFilePackageDesc	                    = null;

	// 이미지파일패키지승인여부
	private String imageFilePackageYn	                    = null;

	// 작업구분
	private String workTp	                                = null;

	// 이미지파일패키지파일명
	private String imageFilePackageFileNm	                = null;

	// 이미지파일목록
	private List<ImageFileListEntity> imageFileList	        = null;

	// 메타파일생성일시
	private String metaFileRegistDt	                        = null;

	public String getImageFilePackageIdSq() {
		return imageFilePackageIdSq;
	}

	public void setImageFilePackageIdSq(String imageFilePackageIdSq) {
		this.imageFilePackageIdSq = imageFilePackageIdSq;
	}

	public String getImageFilePackageDesc() {
		return imageFilePackageDesc;
	}

	public void setImageFilePackageDesc(String imageFilePackageDesc) {
		this.imageFilePackageDesc = imageFilePackageDesc;
	}

	public String getImageFilePackageYn() {
		return imageFilePackageYn;
	}

	public void setImageFilePackageYn(String imageFilePackageYn) {
		this.imageFilePackageYn = imageFilePackageYn;
	}

	public String getWorkTp() {
		return workTp;
	}

	public void setWorkTp(String workTp) {
		this.workTp = workTp;
	}

	public String getImageFilePackageFileNm() {
		return imageFilePackageFileNm;
	}

	public void setImageFilePackageFileNm(String imageFilePackageFileNm) {
		this.imageFilePackageFileNm = imageFilePackageFileNm;
	}

	public List<ImageFileListEntity> getImageFileList() {
		return imageFileList;
	}

	public void setImageFileList(List<ImageFileListEntity> imageFileList) {
		this.imageFileList = imageFileList;
	}

	public String getMetaFileRegistDt() {
		return metaFileRegistDt;
	}

	public void setMetaFileRegistDt(String metaFileRegistDt) {
		this.metaFileRegistDt = metaFileRegistDt;
	}

}
