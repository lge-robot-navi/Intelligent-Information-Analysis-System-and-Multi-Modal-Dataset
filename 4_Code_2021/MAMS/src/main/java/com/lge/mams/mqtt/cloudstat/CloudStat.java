package com.lge.mams.mqtt.cloudstat;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class CloudStat {
	public int robotId;

	@DateTimeFormat(pattern = "yyyyMMdd-HHmmss.SSS")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd-HHmmss.SSS", timezone = "Asia/Seoul")
	public Date timestamp;

	public String areaCode;

	public double lat;
	public double lon;
	public double posx;
	public double posy;
	public double curTargetx;

	public double curTargety;
	public double curTargetTheta;
	public double prevTargetx;
	public double prevTargety;
	public double prevTargetTheta;
	public double battery;

	public double getPosx() {
		return posx;
	}

	public void setPosx(double posx) {
		this.posx = posx;
	}

	public double getPosy() {
		return posy;
	}

	public void setPosy(double posy) {
		this.posy = posy;
	}

	public double getCurTargetx() {
		return curTargetx;
	}

	public void setCurTargetx(double curTargetx) {
		this.curTargetx = curTargetx;
	}

	public double getCurTargety() {
		return curTargety;
	}

	public void setCurTargety(double curTargety) {
		this.curTargety = curTargety;
	}

	public double getCurTargetTheta() {
		return curTargetTheta;
	}

	public void setCurTargetTheta(double curTargetTheta) {
		this.curTargetTheta = curTargetTheta;
	}

	public double getPrevTargetx() {
		return prevTargetx;
	}

	public void setPrevTargetx(double prevTargetx) {
		this.prevTargetx = prevTargetx;
	}

	public double getPrevTargety() {
		return prevTargety;
	}

	public void setPrevTargety(double prevTargety) {
		this.prevTargety = prevTargety;
	}

	public double getPrevTargetTheta() {
		return prevTargetTheta;
	}

	public void setPrevTargetTheta(double prevTargetTheta) {
		this.prevTargetTheta = prevTargetTheta;
	}

	public double getBattery() {
		return battery;
	}

	public void setBattery(double battery) {
		this.battery = battery;
	}


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

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

}
