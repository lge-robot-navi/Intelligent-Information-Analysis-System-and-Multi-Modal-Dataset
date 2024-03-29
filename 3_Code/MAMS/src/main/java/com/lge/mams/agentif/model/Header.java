package com.lge.mams.agentif.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class Header {
	private int robotId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd-HHmmss.SSS")
	private Date timestamp;
	private String msgId;
	private String areaCode;
	public int getRobotId() {
		return robotId;
	}
	public void setRobotId(int robotId) {
		this.robotId = robotId;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	
	
}
