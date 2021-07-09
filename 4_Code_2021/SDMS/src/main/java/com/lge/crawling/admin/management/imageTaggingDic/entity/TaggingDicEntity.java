package com.lge.crawling.admin.management.imageTaggingDic.entity;

import java.util.List;

import com.lge.crawling.admin.common.web.entity.AbstractPage;

/**
 * Tagging Dictionary Entity
 * @version : 1.0
 * @author :  Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
public class TaggingDicEntity extends AbstractPage {

	// 이미지Tagging데이터사전ID일련번호
	private String imageTaggingDataDicIdSq		= "";

	// 상위이미지Tagging데이터사전ID
	private String upperImageTaggingDataDicId   = "";

	// 상위이미지Tagging데이터사전명
	private String upperImageTaggingDataDicNm   = "";

	// 이미지Tagging데이터사전단계
	private String imageTaggingDataDicLevel     = "";

	// 이미지Tagging데이터사전명
	private String imageTaggingDataDicNm        = "";

	// 이미지Tagging데이터사전설명
	private String imageTaggingDataDicDesc      = "";

	// 사용여부
	private String useYn                        = "";

	// 이미지 태깅 데이터 사전 일련번호 리스트
	private List<String> imageTaggingDicIdSqList    = null;

	public String getImageTaggingDataDicIdSq() {
		return imageTaggingDataDicIdSq;
	}

	public void setImageTaggingDataDicIdSq(String imageTaggingDataDicIdSq) {
		this.imageTaggingDataDicIdSq = imageTaggingDataDicIdSq;
	}

	public String getUpperImageTaggingDataDicId() {
		return upperImageTaggingDataDicId;
	}

	public void setUpperImageTaggingDataDicId(String upperImageTaggingDataDicId) {
		this.upperImageTaggingDataDicId = upperImageTaggingDataDicId;
	}

	public String getImageTaggingDataDicLevel() {
		return imageTaggingDataDicLevel;
	}

	public void setImageTaggingDataDicLevel(String imageTaggingDataDicLevel) {
		this.imageTaggingDataDicLevel = imageTaggingDataDicLevel;
	}

	public String getImageTaggingDataDicNm() {
		return imageTaggingDataDicNm;
	}

	public void setImageTaggingDataDicNm(String imageTaggingDataDicNm) {
		this.imageTaggingDataDicNm = imageTaggingDataDicNm;
	}

	public String getImageTaggingDataDicDesc() {
		return imageTaggingDataDicDesc;
	}

	public void setImageTaggingDataDicDesc(String imageTaggingDataDicDesc) {
		this.imageTaggingDataDicDesc = imageTaggingDataDicDesc;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getUpperImageTaggingDataDicNm() {
		return upperImageTaggingDataDicNm;
	}

	public void setUpperImageTaggingDataDicNm(String upperImageTaggingDataDicNm) {
		this.upperImageTaggingDataDicNm = upperImageTaggingDataDicNm;
	}

	public List<String> getImageTaggingDicIdSqList() {
		return imageTaggingDicIdSqList;
	}

	public void setImageTaggingDicIdSqList(List<String> imageTaggingDicIdSqList) {
		this.imageTaggingDicIdSqList = imageTaggingDicIdSqList;
	}

}