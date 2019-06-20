package com.lge.mams.jpa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lge.mams.agentif.model.DataAgentEvent;
import com.lge.mams.agentif.model.Header;

@Entity
@Table(name="TB_EVENT_INFO")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TbEventInfo {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="EVENT_SN")
	private Long eventSn;

	
	@Column(name="EVENT_DT", nullable=false)
	@DateTimeFormat(pattern="yyyyMMdd-HHmmss.SSS")
	@JsonFormat(pattern="yyyyMMdd-HHmmss.SSS")
	private Date eventDt;
	
	@Column(name="ROBOT_ID")
	private int robotId;
	
	@Column(name="AREA_CODE")
	private String areaCode;
	
	@Column(name="DEVICE_ID")
	private int deviceId;
	
	
	@Column(name="ABNORMAL_ID")
	private int abnormalId;
	
	@Column(name="EVENT_TIMESTAMP", nullable=false)
	@DateTimeFormat(pattern="yyyyMMdd-HHmmss.SSS")
	@JsonFormat(pattern="yyyyMMdd-HHmmss.SSS")
	private Date eventTimestamp;
	
	@Column(name="EVENT_POSX", precision=11,scale=8)
	private double eventPosX;
	
	@Column(name="EVENT_POSY", precision=11,scale=8)
	private double eventPosY;
	
	@Column(name="CONFIRM_YN", length=1)
	private String confirmYn = "N";
	
	@Transient
	private TaCodeInfo abnormal;
	
	
	public TaCodeInfo getAbnormal() {
		return abnormal;
	}

	public void setAbnormal(TaCodeInfo abnormal) {
		this.abnormal = abnormal;
	}

	public Long getEventSn() {
		return eventSn;
	}

	public void setEventSn(Long eventSn) {
		this.eventSn = eventSn;
	}

	public Date getEventDt() {
		return eventDt;
	}

	public void setEventDt(Date eventDt) {
		this.eventDt = eventDt;
	}

	public int getRobotId() {
		return robotId;
	}

	public void setRobotId(int robotId) {
		this.robotId = robotId;
	}
	
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

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

	public String getConfirmYn() {
		return confirmYn;
	}

	public void setConfirmYn(String confirmYn) {
		this.confirmYn = confirmYn;
	}

	public void load(Header header) {
		this.robotId = header.getRobotId();
		this.eventDt = header.getTimestamp();
		this.areaCode = header.getAreaCode();
	}
	
	public void load(DataAgentEvent evt) {
		this.abnormalId = evt.getAbnormalId();
		this.deviceId = evt.getDeviceId();
		this.eventPosX = evt.getEventPosX();
		this.eventPosY = evt.getEventPosY();
		this.eventTimestamp = evt.getEventTimestamp();
	}
}


