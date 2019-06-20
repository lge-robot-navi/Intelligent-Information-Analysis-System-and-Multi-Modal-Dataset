package com.lge.mams.jpa.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lge.mams.agentif.model.DataAgentStat;
import com.lge.mams.agentif.model.Header;


@Entity
@Table(name="TB_AGENT_STAT")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TbAgentStat {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="STAT_SN")
	private Long statSn;
	
	@Column(name="ROBOT_ID")
	private int robotId;
	
	@Column(name="AREA_CODE")
	private String areaCode;

	@Column(name="STAT_DT", nullable=false)
	@DateTimeFormat(pattern="yyyyMMdd-HHmmss.SSS")
	@JsonFormat(pattern="yyyyMMdd-HHmmss.SSS")
	private Date statDt;
	
	
	@Column(name="LAT", precision=11,scale=8)
	private double lat;
	
	@Column(name="LON", precision=11,scale=8)
	private double lon;
	
	@Column(name="PAN", precision=6,scale=3)
	private float pan;	// 팬각도.
	
	@Column(name="TILT", precision=6,scale=3)
	private float tilt;	
	
	@Column(name="COMPASS", precision=6,scale=3)
	private float compass;
	
	
	@Column(name="IMU_RATE", precision=10,scale=3)
	private float imuRate;
	
	@Column(name="IMU_ANGLE", precision=6,scale=3)
	private float imuAngle;
	
	@Column(name="ENC_L")
	private int encL;
	
	@Column(name="ENC_R")
	private int encR;
	
	
	@Column(name="VEL_L")
	private int velL;
	
	@Column(name="VEL_R")
	private int velR;
	
	@Column(name="TV", precision=10,scale=3)
	private float tv;
	
	@Column(name="RV", precision=10,scale=3)
	private float rv;
	
	
	
	
	
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
	}
}
