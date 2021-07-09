package com.lge.mams.jpa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@IdClass(TaCodeId.class)
@Table(name = "TA_CODE_INFO")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class TaCodeInfo {
	@Id
	@Column(name = "CDGRP_CD", length = 8)
	private String cdgrpCd;

	@Id
	@Column(name = "CODE_CD", length = 8)
	private String codeCd;

	@Column(name = "CODE_NM", length = 256)
	private String codeNm;

	@Column(name = "CODE_DS", length = 512)
	private String codeDs;

	@Column(name = "USE_YN", length = 1)
	private String useYn;

	@Column(name = "IF_CD", length = 8)
	private String ifCd;

	@Column(name = "INSERT_DT")
	private Date insertDt;

	@Column(name = "INSERT_ID", length = 20)
	private String insertId;

	@Column(name = "UPDATE_DT")
	private Date updateDt;

	@Column(name = "UPDATE_ID", length = 20)
	private String updateId;

	@Column(name = "ORDER_NO", length = 8)
	private String orderNo;

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

	public Date getInsertDt() {
		return insertDt;
	}

	public void setInsertDt(Date insertDt) {
		this.insertDt = insertDt;
	}

	public String getInsertId() {
		return insertId;
	}

	public void setInsertId(String insertId) {
		this.insertId = insertId;
	}

	public Date getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
