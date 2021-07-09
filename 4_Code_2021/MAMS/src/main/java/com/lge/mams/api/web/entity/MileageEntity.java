package com.lge.mams.api.web.entity;

public class MileageEntity {
	private int robotId;
	private String statDt;
	private String startDate;
	private String endDate;
	private Double lat;
	private Double lon;
	
	public int getRobotId() {
		return robotId;
	}

	public void setRobotId(int id) {
		this.robotId = id;
	}

	public String getStatDt() {
		return statDt;
	}
	
	public void setStatDt(String statDt) {
		this.statDt = statDt;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String date) {
		this.startDate = date;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String date) {
		this.endDate = date;
	}
	
	public Double getLat() {
		return lat;
	}
	
	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}
	
	public void setLon(Double lon) {
		this.lon = lon;
	}
}
