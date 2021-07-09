package com.lge.mams.agentif.model;

public class DataAgentStat {
	public double lat;
	public double lon;
	public float pan;
	public float tilt;
	public float compass;
	public float imuRate;
	public float imuAngle;
	public int encL;
	public int encR;
	public int velL;
	public int velR;
	public float tv;
	public int rv;

	public int battery;

	public int getBattery() {
		return battery;
	}

	public void setBattery(int battery) {
		this.battery = battery;
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

	public int getRv() {
		return rv;
	}

	public void setRv(int rv) {
		this.rv = rv;
	}

}
