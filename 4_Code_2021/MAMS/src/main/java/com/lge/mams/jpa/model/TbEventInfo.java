package com.lge.mams.jpa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lge.mams.agentif.model.DataAgentEvent;
import com.lge.mams.agentif.model.Header;
import com.lge.mams.mqtt.etri.EtriEvent;
import com.lge.mams.util.DateUtil;
import com.lge.mams.util.PosGpsUtil;

/**
 * public class EtriEvent {
 * public Integer eventid;
 * public Integer status; // 이벤트 상태구분.
 * public Integer agentid; // agent id
 * public Integer abnormalType;
 * public Integer abnormalDetail;
 * public double gpsPosx;
 * public double gpsPosy;
 * public String fbNeed; // Y => 피드백이 필요함. , N => 피드백이 필요하지 않음.
 * }
 * 의 ETRI의 이벤트 와 변환이 필요하다. 현재 아래의 형태로 저장되기 때문.
 *
 */

@Entity
@Table(name = "TB_EVENT_INFO")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class TbEventInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EVENT_SN")
	private Long eventSn;

	@Column(name = "STATUS")
	private Integer status; // 0, 1, 2

	@Column(name = "ABNORMAL_TYPE")
	private Integer abnormalType;

	@Column(name = "ABNORMAL_DETAIL")
	private Integer abnormalDetail;

	@Column(name = "FB_NEED")
	private String fbNeed; // Y => 피드백이 필요함. , N => 피드백이 필요하지 않음.

	@Column(name = "TIME_T")
	private Integer timeT; // 0, 1, 2

	@Column(name = "EVENT_DT", nullable = false)
	@DateTimeFormat(pattern = "yyyyMMdd-HHmmss.SSS")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd-HHmmss.SSS", timezone = "KST")
	private Date eventDt; // 이벤트 ID로 사용.

	@Column(name = "ROBOT_ID")
	private int robotId; // agent id 대응.

	@Column(name = "AREA_CODE")
	private String areaCode;

	@Column(name = "DEVICE_ID")
	private int deviceId;

	@Column(name = "ABNORMAL_ID")
	private String abnormalId;

	@Column(name = "EVENT_TIMESTAMP", nullable = false)
	@DateTimeFormat(pattern = "yyyyMMdd-HHmmss.SSS")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd-HHmmss.SSS", timezone = "KST")
	private Date eventTimestamp;

	@Column(name = "EVENT_POSX", precision = 11, scale = 8)
	private Double eventPosX; // gps pos x

	@Column(name = "EVENT_POSY", precision = 11, scale = 8)
	private Double eventPosY; // gps pos y

	@Column(name = "EVENT_WAYX", precision = 11, scale = 8)
	private Double eventWayX; // gps pos x

	@Column(name = "EVENT_WAYY", precision = 11, scale = 8)
	private Double eventWayY; // gps pos y

	@Column(name = "CONFIRM_YN", length = 1)
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

	public Double getEventPosX() {
		return eventPosX;
	}

	public void setEventPosX(Double eventPosX) {
		this.eventPosX = eventPosX;
	}

	public Double getEventPosY() {
		return eventPosY;
	}

	public void setEventPosY(Double eventPosY) {
		this.eventPosY = eventPosY;
	}

	public String getConfirmYn() {
		return confirmYn;
	}

	public void setConfirmYn(String confirmYn) {
		this.confirmYn = confirmYn;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAbnormalType() {
		return abnormalType;
	}

	public void setAbnormalType(Integer abnormalType) {
		this.abnormalType = abnormalType;
	}

	public Integer getAbnormalDetail() {
		return abnormalDetail;
	}

	public void setAbnormalDetail(Integer abnormalDetail) {
		this.abnormalDetail = abnormalDetail;
	}

	public String getFbNeed() {
		return fbNeed;
	}

	public void setFbNeed(String fbNeed) {
		this.fbNeed = fbNeed;
	}

	public Integer getTimeT() {
		return timeT;
	}

	public void setTimeT(Integer timeT) {
		this.timeT = timeT;
	}

	public Double getEventWayX() {
		return eventWayX;
	}

	public void setEventWayX(Double eventWayX) {
		this.eventWayX = eventWayX;
	}

	public Double getEventWayY() {
		return eventWayY;
	}

	public void setEventWayY(Double eventWayY) {
		this.eventWayY = eventWayY;
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

	public void load(EtriEvent evt, String PG) {
		status = evt.status;
		abnormalType = evt.abnormalType;
		abnormalDetail = evt.abnormalDetail;
		fbNeed = evt.fbNeed;
		timeT = evt.eventid;
		robotId = evt.agentid;
		areaCode = PG;
		abnormalId = "N" + abnormalType + "_" + abnormalDetail + "_" + status;
		eventDt = new Date();
		eventTimestamp = DateUtil.timetToDate(timeT);
//		if( evt.gpsPosx != 0 ) {
//			eventPosX = evt.gpsPosx;
//			eventPosY = evt.gpsPosy;
//		}

		eventWayX = evt.posx;
		eventWayY = evt.posy;

		if (areaCode == "P") {
			Pair<Double, Double> pair = PosGpsUtil.phConvPosToGPS(evt.posx, evt.posy);
			eventPosX = pair.getLeft();
			eventPosY = pair.getRight();
		} else if (areaCode == "G") {
			Pair<Double, Double> pair = PosGpsUtil.gwConvPosToGPS(evt.posx, evt.posy);
			eventPosX = pair.getLeft(); // gps 장비임.
			eventPosY = pair.getRight(); // gps 장비임.
		} else {
			Logger logger = LoggerFactory.getLogger(getClass());
			logger.error("nuknown area code : {}", areaCode);
		}

		//
	}
}
