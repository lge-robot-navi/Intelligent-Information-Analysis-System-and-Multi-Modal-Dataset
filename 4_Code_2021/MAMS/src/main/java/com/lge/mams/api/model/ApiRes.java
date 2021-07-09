package com.lge.mams.api.model;

public class ApiRes {
	public String resultCode;
	public String resultMsg;

	public Object obj;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public boolean isError() {
		if (resultCode != "OK")
			return true;
		return false;
	}

	public void setFail() {
		this.resultCode = "NG";
		this.resultMsg = "실패하였습니다.";
	}

	public void setFail(String msg) {
		this.resultCode = "NG";
		this.resultMsg = msg;
	}

	public static ApiRes success() {
		ApiRes o = new ApiRes();
		o.resultCode = "OK";
		o.resultMsg = "성공하였습니다";
		return o;
	}

	public static ApiRes success(Object obj) {
		ApiRes o = new ApiRes();
		o.resultCode = "OK";
		o.resultMsg = "성공하였습니다";
		o.obj = obj;
		return o;
	}

	public static ApiRes fail(String code, String msg) {
		ApiRes o = new ApiRes();
		o.resultCode = code;
		o.resultMsg = msg;
		return o;
	}

	public static ApiRes fail() {
		return fail("NG", "실패하였습니다");
	}

	public static ApiRes fail(String msg) {
		return fail("NG", msg);
	}
}