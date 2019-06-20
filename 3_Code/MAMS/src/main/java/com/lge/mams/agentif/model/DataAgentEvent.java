package com.lge.mams.agentif.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;


public class DataAgentEvent {
	private int deviceId;
	private int abnormalId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd-HHmmss.SSS")
	private Date eventTimestamp;
	private double eventPosX;
	private double eventPosY;
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	public int getAbnormalId() {
		return abnormalId;
	}
	public void setAbnormalId(int abnormalId) {
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
