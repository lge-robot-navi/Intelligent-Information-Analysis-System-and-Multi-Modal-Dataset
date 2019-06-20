package com.lge.mams.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="TB_CODE")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TbCode {
	
	@Id
	@Column(name="CODE", columnDefinition="numeric(12,0)")
	private Long code ;

	@Column(name="CODE_GROUP", nullable=false, columnDefinition="numeric(6,0)")
	private Long codeGroup ;
	
	@Column(name="CODE_SN", nullable=false, columnDefinition="numeric(6,0)")
	private Long codeSn ;

	@Column(name="CODE_NM", length=50)
	private String codeNm;
	
	@Column(name="CODE_CN", length=1000)
	private String codeCn;

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public Long getCodeGroup() {
		return codeGroup;
	}

	public void setCodeGroup(Long codeGroup) {
		this.codeGroup = codeGroup;
	}

	public Long getCodeSn() {
		return codeSn;
	}

	public void setCodeSn(Long codeSn) {
		this.codeSn = codeSn;
	}

	public String getCodeNm() {
		return codeNm;
	}

	public void setCodeNm(String codeNm) {
		this.codeNm = codeNm;
	}

	public String getCodeCn() {
		return codeCn;
	}

	public void setCodeCn(String codeCn) {
		this.codeCn = codeCn;
	}
	
	
}

// ALTER TABLE TB_CODE CONVERT TO CHARSET UTF8;