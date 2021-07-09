package com.lge.mams.mqtt.msg;

public class DroneCmder {
	public String cmd;
	public Double lat;
	public Double lng;
	public Double pitch;
	public Double yaw;
	public Double hdg;

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getPitch() {
		return pitch;
	}

	public void setPitch(Double pitch) {
		this.pitch = pitch;
	}

	public Double getYaw() {
		return yaw;
	}

	public void setYaw(Double yaw) {
		this.yaw = yaw;
	}

	public Double getHdg() {
		return hdg;
	}

	public void setHdg(Double hdg) {
		this.hdg = hdg;
	}
}
