package com.lge.crawling.admin.openapi.entity;

import com.lge.crawling.admin.common.web.entity.AbstractPage;


public class SensorDataEntity extends AbstractPage {
	private int total;
	private int count;
	private int page;
	private Long sdSeq;
	private String sdAgent;
	private String sdTypeCd;
	private String sdNm;
	private String sdUrl;
	private String sdScaleX;
	private String sdScaleY;
	private Long sdSize;
	private String sdCreateDt;
	private String sdRegistDt;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public Long getSdSeq() {
		return sdSeq;
	}
	public void setSdSeq(Long sdSeq) {
		this.sdSeq = sdSeq;
	}
	public String getSdAgent() {
		return sdAgent;
	}
	public void setSdAgent(String sdAgent) {
		this.sdAgent = sdAgent;
	}
	public String getSdTypeCd() {
		return sdTypeCd;
	}
	public void setSdTypeCd(String sdTypeCd) {
		this.sdTypeCd = sdTypeCd;
	}
	public String getSdNm() {
		return sdNm;
	}
	public void setSdNm(String sdNm) {
		this.sdNm = sdNm;
	}
	public String getSdUrl() {
		return sdUrl;
	}
	public void setSdUrl(String sdUrl) {
		this.sdUrl = sdUrl;
	}
	public String getSdScaleX() {
		return sdScaleX;
	}
	public void setSdScaleX(String sdScaleX) {
		this.sdScaleX = sdScaleX;
	}
	public String getSdScaleY() {
		return sdScaleY;
	}
	public void setSdScaleY(String sdScaleY) {
		this.sdScaleY = sdScaleY;
	}
	public Long getSdSize() {
		return sdSize;
	}
	public void setSdSize(Long sdSize) {
		this.sdSize = sdSize;
	}
	public String getSdCreateDt() {
		return sdCreateDt;
	}
	public void setSdCreateDt(String sdCreateDt) {
		this.sdCreateDt = sdCreateDt;
	}
	public String getSdRegistDt() {
		return sdRegistDt;
	}
	public void setSdRegistDt(String sdRegistDt) {
		this.sdRegistDt = sdRegistDt;
	}
	
	
}
