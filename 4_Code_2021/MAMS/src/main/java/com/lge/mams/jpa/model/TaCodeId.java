package com.lge.mams.jpa.model;

import java.io.Serializable;

public class TaCodeId implements Serializable {
	private static final long serialVersionUID = 1L;
	public String cdgrpCd;
	public String codeCd;

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

	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (this.getClass() != other.getClass()) return false;
		TaCodeId obj = (TaCodeId) other;
		if (cdgrpCd == obj.cdgrpCd && codeCd == obj.codeCd) return true;
		return false;
	}

	@Override
	public int hashCode() {
		String a = cdgrpCd == null ? "" : cdgrpCd;
		String b = codeCd == null ? "" : codeCd;
		return (a + b).hashCode();
	}

}
