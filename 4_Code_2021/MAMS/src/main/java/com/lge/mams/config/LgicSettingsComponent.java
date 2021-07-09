package com.lge.mams.config;

import org.springframework.stereotype.Component;

@Component
public class LgicSettingsComponent {
	LgicSettings settingsPohang = new LgicSettings();

	public LgicSettings getSettingsPohang() {
		return settingsPohang;
	}

	public void setSettingsPohang(LgicSettings settingsPohang) {
		this.settingsPohang = settingsPohang;
	}

	public LgicSettings getSettingsGwangju() {
		return settingsGwangju;
	}

	public void setSettingsGwangju(LgicSettings settingsGwangju) {
		this.settingsGwangju = settingsGwangju;
	}

	LgicSettings settingsGwangju = new LgicSettings();

	public boolean isPohangAgentEventImageSaveOn(Integer agentId) {
		for (AgentConfig conf : settingsPohang.getList()) {
			if (conf.agentId == agentId) return conf.ison;
		}

		return false;
	}

	public boolean isGwangjuAgentEventImageSaveOn(Integer agentId) {
		for (AgentConfig conf : settingsGwangju.getList()) {
			if (conf.agentId == agentId) return conf.ison;
		}

		return false;
	}
}
