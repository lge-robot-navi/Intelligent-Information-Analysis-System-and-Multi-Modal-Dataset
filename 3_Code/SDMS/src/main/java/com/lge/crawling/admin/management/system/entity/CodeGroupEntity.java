package com.lge.crawling.admin.management.system.entity;

import java.util.List;

import com.lge.crawling.admin.common.web.entity.AbstractPage;

/**
 * 공통코드그룹 Entity
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class CodeGroupEntity extends AbstractPage {

	private String cdgrpCd;			// 그룹코드
	private String cdgrpNm;			// 그룹코드명
	private String cdgrpDs;			// 그룹코드설명
	private String useYn;			// 사용여부
	private Integer orderNo;		// 정렬순서
	private List<CodeEntity> codes;

	/**
	 * @return the codes
	 */
	public List<CodeEntity> getCodes() {
		return codes;
	}

	/**
	 * @param codes the codes to set
	 */
	public void setCodes(List<CodeEntity> codes) {
		this.codes = codes;
	}
	public String getCdgrpCd() {
		return cdgrpCd;
	}
	public void setCdgrpCd(String cdgrpCd) {
		this.cdgrpCd = cdgrpCd;
	}
	public String getCdgrpNm() {
		return cdgrpNm;
	}
	public void setCdgrpNm(String cdgrpNm) {
		this.cdgrpNm = cdgrpNm;
	}
	public String getCdgrpDs() {
		return cdgrpDs;
	}
	public void setCdgrpDs(String cdgrpDs) {
		this.cdgrpDs = cdgrpDs;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
}