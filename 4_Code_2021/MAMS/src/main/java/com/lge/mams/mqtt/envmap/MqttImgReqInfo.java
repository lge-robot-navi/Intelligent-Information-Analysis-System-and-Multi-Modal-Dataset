package com.lge.mams.mqtt.envmap;

public class MqttImgReqInfo {
	public Integer width; // 이미지 가로 크기.
	public Integer height; // 이미지 세로 크기.
	public String location; // ph or gw (지역구분)
	public boolean mapsearch; // 수색영역
	public boolean mapheight; // 높이지도.
	public boolean maptemperature; // 온도.
	public boolean mapobjprob; // 사람, 차량 존재 확률.
	public boolean mapheightprob; // 장애물 확률지도.

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isMapsearch() {
		return mapsearch;
	}

	public void setMapsearch(boolean mapsearch) {
		this.mapsearch = mapsearch;
	}

	public boolean isMapheight() {
		return mapheight;
	}

	public void setMapheight(boolean mapheight) {
		this.mapheight = mapheight;
	}

	public boolean isMaptemperature() {
		return maptemperature;
	}

	public void setMaptemperature(boolean maptemperature) {
		this.maptemperature = maptemperature;
	}

	public boolean isMapobjprob() {
		return mapobjprob;
	}

	public void setMapobjprob(boolean mapobjprob) {
		this.mapobjprob = mapobjprob;
	}

	public boolean isMapheightprob() {
		return mapheightprob;
	}

	public void setMapheightprob(boolean mapheightprob) {
		this.mapheightprob = mapheightprob;
	}

}
