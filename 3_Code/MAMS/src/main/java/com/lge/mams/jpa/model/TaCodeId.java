package com.lge.mams.jpa.model;

import java.io.Serializable;

public class TaCodeId implements Serializable {
	private String cdgrpCd ;
	private String codeCd ;
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
	
	
}
