package com.lge.mams.agentif.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DataAgentEvent {
	private int deviceId;
	private String abnormalId;

	@DateTimeFormat(pattern = "yyyyMMdd-HHmmss.SSS")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd-HHmmss.SSS", timezone = "KST")
	private Date eventTimestamp;
	private double eventPosX;
	private double eventPosY;

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public String getAbnormalId() {
		return abnormalId;
	}

	public void setAbnormalId(String abnormalId) {
		this.abnormalId = abnormalId;
	}

	public Date getEventTimestamp() {
		return eventTimestamp;
	}

	public void setEventTimestamp(Date eventTimestamp) {
		this.eventTimestamp = eventTimestamp;
	}

	public double getEventPosX() {
		return eventPosX;
	}

	public void setEventPosX(double eventPosX) {
		this.eventPosX = eventPosX;
	}

	public double getEventPosY() {
		return eventPosY;
	}

	public void setEventPosY(double eventPosY) {
		this.eventPosY = eventPosY;
	}

}
