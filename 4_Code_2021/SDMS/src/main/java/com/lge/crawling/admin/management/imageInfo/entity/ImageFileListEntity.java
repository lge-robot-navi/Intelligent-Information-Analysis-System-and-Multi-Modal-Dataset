package com.lge.crawling.admin.management.imageInfo.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Image File List Entity
 * @version : 1.0
 * @author  : Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
public class ImageFileListEntity {

	// 이미지파일위치
	private String imageFileLoc	                            = null;

	// 이미지파일명
	private String imageFileNm	                            = null;

	// 이미지파일크기
	private String imageFileSize	                        = null;

	// 이미지파일크기_X
	private String imageFileSizeX	                        = null;

	// 이미지파일크기_Y
	private String imageFileSizeY	                        = null;

	// 이미지파일다운로드경로URL
	private String imageFileDownloadPathUrl					= null;

	// 이미지파일구분
	private String imageFileTp	                            = null;

	// 이미지파일다운로드경로구분
	private String imageFileDownloadPathTp	                = null;

	// 이미지JSON파일위치
	private String imageJsonFileLoc	                        = null;

	// 이미지JSON파일명
	private String imageJsonFileNm	                        = null;

	// 삭제여부
	private String deleteYn	                                = null;

	// 생성일시
	private String createDate	                            = null;

	public String getImageFileLoc() {
		return imageFileLoc;
	}

	public void setImageFileLoc(String imageFileLoc) {
		this.imageFileLoc = imageFileLoc;
	}

	public String getImageFileNm() {
		return imageFileNm;
	}

	public void setImageFileNm(String imageFileNm) {
		this.imageFileNm = imageFileNm;
	}

	public String getImageFileSizeX() {
		return imageFileSizeX;
	}

	public void setImageFileSizeX(String imageFileSizeX) {
		this.imageFileSizeX = imageFileSizeX;
	}

	public String getImageFileSizeY() {
		return imageFileSizeY;
	}

	public void setImageFileSizeY(String imageFileSizeY) {
		this.imageFileSizeY = imageFileSizeY;
	}

	public String getImageFileTp() {
		return imageFileTp;
	}

	public void setImageFileTp(String imageFileTp) {
		this.imageFileTp = imageFileTp;
	}

	public String getImageFileDownloadPathTp() {
		return imageFileDownloadPathTp;
	}

	public void setImageFileDownloadPathTp(String imageFileDownloadPathTp) {
		this.imageFileDownloadPathTp = imageFileDownloadPathTp;
	}

	public String getImageJsonFileLoc() {
		return imageJsonFileLoc;
	}

	public void setImageJsonFileLoc(String imageJsonFileLoc) {
		this.imageJsonFileLoc = imageJsonFileLoc;
	}

	public String getImageJsonFileNm() {
		return imageJsonFileNm;
	}

	public void setImageJsonFileNm(String imageJsonFileNm) {
		this.imageJsonFileNm = imageJsonFileNm;
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

	public String getImageFileSize() {
		return imageFileSize;
	}

	public void setImageFileSize(String imageFileSize) {
		this.imageFileSize = imageFileSize;
	}

	public String getImageFileDownloadPathUrl() {
		return imageFileDownloadPathUrl;
	}

	public void setImageFileDownloadPathUrl(String imageFileDownloadPathUrl) {
		this.imageFileDownloadPathUrl = imageFileDownloadPathUrl;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
