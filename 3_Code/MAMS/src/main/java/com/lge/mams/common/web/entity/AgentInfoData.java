package com.lge.mams.common.web.entity;

public class AgentInfoData {
	String idx;
	String ip;
	String name;
	String color;
	String type;
	
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

 	public String toString() { 
  		return "AgentInfoData [name=" + name + ", ip=" + ip + "]"; 
  	} 
	
	
}
