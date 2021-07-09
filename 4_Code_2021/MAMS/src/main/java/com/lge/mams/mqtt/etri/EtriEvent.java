package com.lge.mams.mqtt.etri;

public class EtriEvent {
	public Integer eventid;
	public Integer status; // 이벤트 상태구분.
	public Integer agentid; // agent id
	public Integer abnormalType;
	public Integer abnormalDetail;
	public Double gpsPosx;
	public Double gpsPosy;
	public Double posx;
	public Double posy;
	public String fbNeed; // Y => 피드백이 필요함. , N => 피드백이 필요하지 않음.
}
