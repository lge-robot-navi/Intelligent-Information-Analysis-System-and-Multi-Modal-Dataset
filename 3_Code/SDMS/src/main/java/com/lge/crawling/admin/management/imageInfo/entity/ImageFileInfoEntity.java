package com.lge.crawling.admin.management.imageInfo.entity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lge.crawling.admin.common.web.entity.AbstractPage;

/**
 * Image Info Entity
 * @version : 1.0
 * @author  : Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
public class ImageFileInfoEntity extends AbstractPage {

	// 이미지파일일련번호
	@NotEmpty
	@Size(max=20)
	private String imageFileSq								= null;

	// 이미지파일패키지ID일련번호
	@NotEmpty
	@Size(max=20)
	private String imageFilePackageIdSq                     = null;

	// 이미지파일명
	@NotEmpty
	@Size(max=256)
	private String imageFileNm                              = null;

	// 이미지파일경로
	@NotEmpty
	@Size(max=512)
	private String imageFilePath                            = null;

	// 이미지파일크기
	@NotEmpty
	@Size(max=20)
	private String imageFileSize                            = null;

	// 이미지파일크기_X
	@NotEmpty
	@Size(max=20)
	private String imageFileScaleX                          = null;

	// 이미지파일크기_Y
	@NotEmpty
	@Size(max=20)
	private String imageFileScaleY                          = null;

	// 이미지파일다운로드경로URL
	@NotEmpty
	@Size(max=2048)
	private String imageFileDownloadPathUrl					= null;

	// 이미지파일타입구분_TA004
	@NotEmpty
	@Size(max=8)
	private String imageFileTypeCd                          = null;

	// 이미지파일다운경로구분_TA005
	@NotEmpty
	@Size(max=8)
	private String imageFileDownloadPathCd                  = null;

	// 최종작업이미지파일크기_X
	@Size(max=20)
	private String lastUpdateImageFileScaleX				= null;

	// 최종작업이미지파일크기_X
	@Size(max=20)
	private String lastUpdateImageFileScaleY                = null;

	// 최종작업이미지배율
	@Size(max=10)
	private String lastUpdateImageMagnification             = null;

	// 이미지파일등록상세일시
	@NotEmpty
	@Size(max=14)
	private String imageFileRegistDt                        = null;

	// #### ImageJsonFileInfo Entity ####
	// 이미지JSON파일일련번호
	@NotEmpty
	@Size(max=11)
	private String imageJsonFileSq							= null;

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

	// #### ImageFilePackageInfo Entity ####
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

	// 태깅여부
	private String taggingYn					= "";

	public String getImageFileSq() {
		return imageFileSq;
	}

	public void setImageFileSq(String imageFileSq) {
		this.imageFileSq = imageFileSq;
	}

	public String getImageFilePackageIdSq() {
		return imageFilePackageIdSq;
	}

	public void setImageFilePackageIdSq(String imageFilePackageIdSq) {
		this.imageFilePackageIdSq = imageFilePackageIdSq;
	}

	public String getImageFileNm() {
		return imageFileNm;
	}

	public void setImageFileNm(String imageFileNm) {
		this.imageFileNm = imageFileNm;
	}

	public String getImageFilePath() {
		return imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	public String getImageFileSize() {
		return imageFileSize;
	}

	public void setImageFileSize(String imageFileSize) {
		this.imageFileSize = imageFileSize;
	}

	public String getImageFileScaleX() {
		return imageFileScaleX;
	}

	public void setImageFileScaleX(String imageFileScaleX) {
		this.imageFileScaleX = imageFileScaleX;
	}

	public String getImageFileScaleY() {
		return imageFileScaleY;
	}

	public void setImageFileScaleY(String imageFileScaleY) {
		this.imageFileScaleY = imageFileScaleY;
	}

	public String getImageFileTypeCd() {
		return imageFileTypeCd;
	}

	public void setImageFileTypeCd(String imageFileTypeCd) {
		this.imageFileTypeCd = imageFileTypeCd;
	}

	public String getImageFileDownloadPathCd() {
		return imageFileDownloadPathCd;
	}

	public void setImageFileDownloadPathCd(String imageFileDownloadPathCd) {
		this.imageFileDownloadPathCd = imageFileDownloadPathCd;
	}

	public String getImageFileRegistDt() {
		return imageFileRegistDt;
	}

	public void setImageFileRegistDt(String imageFileRegistDt) {
		this.imageFileRegistDt = imageFileRegistDt;
	}

	public String getLastUpdateImageFileScaleX() {
		return lastUpdateImageFileScaleX;
	}

	public void setLastUpdateImageFileScaleX(String lastUpdateImageFileScaleX) {
		this.lastUpdateImageFileScaleX = lastUpdateImageFileScaleX;
	}

	public String getLastUpdateImageFileScaleY() {
		return lastUpdateImageFileScaleY;
	}

	public void setLastUpdateImageFileScaleY(String lastUpdateImageFileScaleY) {
		this.lastUpdateImageFileScaleY = lastUpdateImageFileScaleY;
	}

	public String getLastUpdateImageMagnification() {
		return lastUpdateImageMagnification;
	}

	public void setLastUpdateImageMagnification(String lastUpdateImageMagnification) {
		this.lastUpdateImageMagnification = lastUpdateImageMagnification;
	}

	public String getImageJsonFileSq() {
		return imageJsonFileSq;
	}

	public void setImageJsonFileSq(String imageJsonFileSq) {
		this.imageJsonFileSq = imageJsonFileSq;
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

	public String getImageJsonXmlConvFileDesc() {
		return imageJsonXmlConvFileDesc;
	}

	public void setImageJsonXmlConvFileDesc(String imageJsonXmlConvFileDesc) {
		this.imageJsonXmlConvFileDesc = imageJsonXmlConvFileDesc;
	}

	public String getImageFileDownloadPathUrl() {
		return imageFileDownloadPathUrl;
	}

	public void setImageFileDownloadPathUrl(String imageFileDownloadPathUrl) {
		this.imageFileDownloadPathUrl = imageFileDownloadPathUrl;
	}

	public String getTaggingYn() {
		return taggingYn;
	}

	public void setTaggingYn(String taggingYn) {
		this.taggingYn = taggingYn;
	}
}
