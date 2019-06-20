package com.lge.crawling.admin.management.system.entity;

import com.lge.crawling.admin.common.web.entity.AbstractPage;

/**
 * 공통코드 Entity
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class CodeEntity extends AbstractPage {

	private String cdgrpCd;			// 그룹코드
	private String codeCd;			// 코드
	private String codeNm;			// 코드명
	private String codeDs;			// 코드설명
	private String useYn;			// 사용여부
	private String ifCd;			// 연동연계코드
	private Integer orderNo;		// 정렬순서

	public String getCdgrpCd() {
		return cdgrpCd;
	}
	public void setCdgrpCd(String cdgrpCd) {
		this.cdgrpCd = cdgrpCd;
	}
	public String getCodeCd() {
		return codeCd;
	}
	public void setCodeCd(String codeCd) {
		this.codeCd = codeCd;
	}
	public String getCodeNm() {
		return codeNm;
	}
	public void setCodeNm(String codeNm) {
		this.codeNm = codeNm;
	}
	public String getCodeDs() {
		return codeDs;
	}
	public void setCodeDs(String codeDs) {
		this.codeDs = codeDs;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getIfCd() {
		return ifCd;
	}
	public void setIfCd(String ifCd) {
		this.ifCd = ifCd;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
}