package com.lge.crawling.admin.openapi.entity;

import com.lge.crawling.admin.common.web.entity.AbstractPage;

import lombok.Getter;
import lombok.Setter;


public class SearchEntity extends AbstractPage {
	private String agent;
	private String type;
	private String from;
	private String to;
	private int count;
	private int pageno;
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPageno() {
		return pageno;
	}
	public void setPageno(int pageno) {
		this.pageno = pageno;
	}
	
	
}
