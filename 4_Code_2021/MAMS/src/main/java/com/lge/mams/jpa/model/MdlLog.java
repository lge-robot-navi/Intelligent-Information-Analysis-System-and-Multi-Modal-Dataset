package com.lge.mams.jpa.model;

public class MdlLog {
	public enum Level {
		DEBUG, INFO, WARN, ERROR
	}

	private Level level;
	private String msg;

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
