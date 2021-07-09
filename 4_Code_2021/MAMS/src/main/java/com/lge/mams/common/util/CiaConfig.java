package com.lge.mams.common.util;

import org.springframework.stereotype.Component;

@Component
public class CiaConfig {

	private boolean udpLog = false;
	private boolean statLog = false;
	private boolean eventLog = false;

	private String help = "/monitoring/config?type=udp, event, stat&val=true,false";

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public boolean isStatLog() {
		return statLog;
	}

	public void setStatLog(boolean statLog) {
		this.statLog = statLog;
	}

	public boolean isEventLog() {
		return eventLog;
	}

	public void setEventLog(boolean eventLog) {
		this.eventLog = eventLog;
	}

	public boolean isUdpLog() {
		return udpLog;
	}

	public void setUdpLog(boolean udpLog) {
		this.udpLog = udpLog;
	};
}
