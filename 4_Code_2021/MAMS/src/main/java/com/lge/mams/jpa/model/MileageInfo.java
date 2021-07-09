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

@Entity
@Table(name = "TB_AGENT_STAT")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class MileageInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "ROBOT_ID")
	public int robotId;

	@Column(name = "STAT_DT", nullable = false)
	@DateTimeFormat(pattern = "yyyyMMdd-HHmmss.SSS")
	// @DateTimeFormat(pattern = "yyyyMMdd-HHmmss")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd-HHmmss.SSS", timezone = "KST")
	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd-HHmm", timezone = "KST")
	public Date statDt;

	@Column(name = "LAT", precision = 11, scale = 8)
	public double lat;

	@Column(name = "LON", precision = 11, scale = 8)
	public double lon;

	public int getRobotId() {
		return robotId;
	}

	public void setRobotId(int robotId) {
		this.robotId = robotId;
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
}
