package com.lge.mams.jpa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lge.mams.agentif.model.DataAgentStat;
import com.lge.mams.agentif.model.Header;
import com.lge.mams.mqtt.cloudstat.CloudStat;
import com.lge.mams.util.PosGpsUtil;

@Entity
@Table(name = "TB_AGENT_STAT")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class TbAgentStat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STAT_SN")
	public Long statSn;

	@Column(name = "ROBOT_ID")
	public int robotId;

	@Column(name = "AREA_CODE")
	public String areaCode;

	@Column(name = "STAT_DT", nullable = false)
	@DateTimeFormat(pattern = "yyyyMMdd-HHmmss.SSS")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd-HHmmss.SSS", timezone = "Asia/Seoul")
	public Date statDt;

	@Column(name = "LAT", precision = 11, scale = 8)
	public double lat;

	@Column(name = "LON", precision = 11, scale = 8)
	public double lon;

	@Column(name = "POSX", precision = 11, scale = 8)
	public double posx;

	@Column(name = "POSY", precision = 11, scale = 8)
	public double posy;

	@Column(name = "BATTERY", precision = 11, scale = 8)
	public double battery;

	@Column(name = "CUR_TARGETX", precision = 11, scale = 8)
	public double curTargetx;

	@Column(name = "CUR_TARGETY", precision = 11, scale = 8)
	public double curTargety;

	@Column(name = "CUR_TARGET_THETA", precision = 11, scale = 8)
	public double curTargetTheta;

	@Column(name = "PREV_TARGETX", precision = 11, scale = 8)
	public double prevTargetx;

	@Column(name = "PREV_TARGETY", precision = 11, scale = 8)
	public double prevTargety;

	@Column(name = "PREV_TARGET_THETA", precision = 11, scale = 8)
	public double prevTargetTheta;

	@Column(name = "PAN", precision = 6, scale = 3)
	public float pan; // 팬각도.

	@Column(name = "TILT", precision = 6, scale = 3)
	public float tilt;

	@Column(name = "COMPASS", precision = 6, scale = 3)
	public float compass;

	@Column(name = "IMU_RATE", precision = 10, scale = 3)
	public float imuRate;

	@Column(name = "IMU_ANGLE", precision = 6, scale = 3)
	public float imuAngle;

	@Column(name = "ENC_L")
	public int encL;

	@Column(name = "ENC_R")
	public int encR;

	@Column(name = "VEL_L")
	public int velL;

	@Column(name = "VEL_R")
	public int velR;

	@Column(name = "TV", precision = 10, scale = 3)
	public float tv;

	@Column(name = "RV", precision = 10, scale = 3)
	public float rv;

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

	public double getBattery() {
		return battery;
	}

	public void setBattery(double battery) {
		this.battery = battery;
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

	public Long getStatSn() {
		return statSn;
	}

	public void setStatSn(Long statSn) {
		this.statSn = statSn;
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

	public Date getStatDt() {
		return statDt;
	}

	public void setStatDt(Date statDt) {
		this.statDt = statDt;
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

	public float getPan() {
		return pan;
	}

	public void setPan(float pan) {
		this.pan = pan;
	}

	public float getTilt() {
		return tilt;
	}

	public void setTilt(float tilt) {
		this.tilt = tilt;
	}

	public float getCompass() {
		return compass;
	}

	public void setCompass(float compass) {
		this.compass = compass;
	}

	public float getImuRate() {
		return imuRate;
	}

	public void setImuRate(float imuRate) {
		this.imuRate = imuRate;
	}

	public float getImuAngle() {
		return imuAngle;
	}

	public void setImuAngle(float imuAngle) {
		this.imuAngle = imuAngle;
	}

	public int getEncL() {
		return encL;
	}

	public void setEncL(int encL) {
		this.encL = encL;
	}

	public int getEncR() {
		return encR;
	}

	public void setEncR(int encR) {
		this.encR = encR;
	}

	public int getVelL() {
		return velL;
	}

	public void setVelL(int velL) {
		this.velL = velL;
	}

	public int getVelR() {
		return velR;
	}

	public void setVelR(int velR) {
		this.velR = velR;
	}

	public float getTv() {
		return tv;
	}

	public void setTv(float tv) {
		this.tv = tv;
	}

	public float getRv() {
		return rv;
	}

	public void setRv(float rv) {
		this.rv = rv;
	}

	public void load(Header header) {
		this.robotId = header.getRobotId();
		this.statDt = header.getTimestamp();
		this.areaCode = header.getAreaCode();
	}

	public void load(DataAgentStat stat) {
		this.compass = stat.getCompass();
		this.encL = stat.getEncL();
		this.encR = stat.getEncR();
		this.imuAngle = stat.getImuAngle();
		this.imuRate = stat.getImuRate();
		this.lat = stat.getLat();
		this.lon = stat.getLon();
		this.pan = stat.getPan();
		this.rv = stat.getRv();
		this.tilt = stat.getTilt();
		this.tv = stat.getTv();
		this.velL = stat.getVelL();
		this.velR = stat.getVelR();
		this.battery = stat.getBattery();
	}

	public void load(CloudStat stat) {
		this.robotId = stat.getRobotId();
		this.statDt = stat.getTimestamp();
		this.areaCode = stat.getAreaCode();
//		this.lat = stat.getLat();
//		this.lon = stat.getLon();
		this.battery = stat.getBattery();
		this.posx = stat.posx;
		this.posy = stat.posy;

		if ("P".equals(this.areaCode)) {
			Pair<Double, Double> pair = PosGpsUtil.phConvPosToGPS(stat.posx, stat.posy);
			this.lat = pair.getLeft();
			this.lon = pair.getRight();
		} else {
			// Pair<Double, Double> pair = PosGpsUtil.gwConvPosToGPS(stat.posx,stat.posy);
			Pair<Double, Double> pair = PosGpsUtil.gwConvPosToGPS(stat.posx, stat.posy);
			this.lat = pair.getLeft();
			this.lon = pair.getRight();
		}

		this.curTargetTheta = stat.curTargetTheta;
		this.curTargetx = stat.curTargetx;
		this.curTargety = stat.curTargety;
		this.prevTargetTheta = stat.prevTargetTheta;
		this.prevTargetx = stat.prevTargetx;
		this.prevTargety = stat.prevTargety;
	}
}
