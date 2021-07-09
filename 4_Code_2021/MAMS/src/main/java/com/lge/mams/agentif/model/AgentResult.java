package com.lge.mams.agentif.model;

public class AgentResult {
	private int resultCode;
	private String resultMsg;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public static AgentResult fail(int code, String msg) {
		AgentResult rlt = new AgentResult();
		rlt.setResultCode(code);
		rlt.setResultMsg(msg);
		return rlt;
	}

	public static AgentResult success(String msg) {
		AgentResult rlt = new AgentResult();
		rlt.setResultCode(0);
		rlt.setResultMsg(msg);
		return rlt;
	}
}
