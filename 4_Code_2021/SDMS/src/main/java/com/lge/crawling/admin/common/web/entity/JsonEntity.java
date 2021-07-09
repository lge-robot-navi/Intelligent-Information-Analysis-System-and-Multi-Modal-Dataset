package com.lge.crawling.admin.common.web.entity;

public class JsonEntity {

	int msgType;
	String agentId;
	int agentType;
	String agentIp;
	int videoStreamingPort;
	int audioStreamingPort;
	
	
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public int getAgentType() {
		return agentType;
	}
	public void setAgentType(int agentType) {
		this.agentType = agentType;
	}
	public String getAgentIp() {
		return agentIp;
	}
	public void setAgentIp(String agentIp) {
		this.agentIp = agentIp;
	}
	public int getVideoStreamingPort() {
		return videoStreamingPort;
	}
	public void setVideoStreamingPort(int videoStreamingPort) {
		this.videoStreamingPort = videoStreamingPort;
	}
	public int getAudioStreamingPort() {
		return audioStreamingPort;
	}
	public void setAudioStreamingPort(int audioStreamingPort) {
		this.audioStreamingPort = audioStreamingPort;
	}
	
	
}
