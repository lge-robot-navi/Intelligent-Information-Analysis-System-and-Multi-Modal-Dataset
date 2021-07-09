package com.lge.crawling.admin.management.statistics.entity;

import java.util.List;

import com.lge.crawling.admin.common.web.entity.AbstractPage;

/**
 * Image Tagging Data Dictionary Info Entity
 * @version : 1.0
 * @author  : Copyright (c) 2017 by Mirincom CORP. All Rights Reserved.
 */
public class ImageTaggingDataDicEntity extends AbstractPage {

	// 이미지Tagging데이터사전ID일련번호
	private String imageTaggingDataDicIdSq					= null;

	// 이미지Tagging데이터사전ID
	private String upperImageTaggingDataDicId               = null;

	// 이미지Tagging데이터사전단계
	private String imageTaggingDataDicLevel                 = null;

	// 이미지Tagging데이터사전명
	private String imageTaggingDataDicNm                    = null;

	// 이미지Tagging데이터사전설명
	private String imageTaggingDataDicDesc                  = null;

	// 사용여부
	private String useYn                  					= null;

	// 이미지Tagging데이터사전ID List
	private List<String> imageTaggingDataDicIdSqList 		= null;

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

	public List<String> getImageTaggingDataDicIdSqList() {
		return imageTaggingDataDicIdSqList;
	}

	public void setImageTaggingDataDicIdSqList(List<String> imageTaggingDataDicIdSqList) {
		this.imageTaggingDataDicIdSqList = imageTaggingDataDicIdSqList;
	}

}
