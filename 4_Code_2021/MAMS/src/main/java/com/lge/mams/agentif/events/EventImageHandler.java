package com.lge.mams.agentif.events;

import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lge.mams.agentif.udperver.UdpPacket;
import com.lge.mams.config.LgicConfig;
import com.lge.mams.config.LgicSettingsComponent;
import com.lge.mams.demon.MntrImageSaverComopent;

@Component
public class EventImageHandler {

	@Autowired
	LgicConfig config;

	@Autowired
	LgicSettingsComponent settings;

	@Autowired
	MntrImageSaverComopent mntrImageService;

	private Hashtable<Integer, EventImages> mapPohang = new Hashtable<Integer, EventImages>();
	private Hashtable<Integer, EventImages> mapGwangju = new Hashtable<Integer, EventImages>();

	public Hashtable<Integer, EventImages> getMapPohang() {
		return mapPohang;
	}

	public Hashtable<Integer, EventImages> getGwangju() {
		return mapGwangju;
	}

	public void addPohang(UdpPacket img) {
		if (img == null) return;
		Integer rid = img.getHead().getRobotId();

		// if( !settings.isPohangAgentEventImageSaveOn(rid) ) return;
		if (settings.isPohangAgentEventImageSaveOn(rid)) {
			mntrImageService.push(img);
		}

		EventImages imgs = getEventImages(mapPohang, rid);
		imgs.add(img);
	}

	public void addGwangju(UdpPacket img) {
		if (img == null) return;
		Integer rid = img.getHead().getRobotId();

		if (settings.isGwangjuAgentEventImageSaveOn(rid)) {
			// 설정되어 있으면 모니터링 하도록 처리함.
			mntrImageService.push(img);
		}

		EventImages imgs = getEventImages(mapGwangju, rid);

		imgs.add(img);
	}

	private EventImages getEventImages(Hashtable<Integer, EventImages> map, Integer rid) {
		if (map.containsKey(rid)) return map.get(rid);
		map.put(rid, new EventImages());
		return map.get(rid);
	}

	public void savePohangImages(Long statSn, Integer agentid) {
		// if( !settings.isPohangAgentEventImageSaveOn(agentid) ) return;
		String path = String.format("%sP/%d/%d/", config.getEventImageDir(), agentid, statSn);
		saveEventImages(path, mapPohang, agentid);
	}

	public void saveGwangjuImages(Long statSn, Integer agentid) {
		// if( !settings.isGwangjuAgentEventImageSaveOn(agentid) ) return;
		String path = String.format("%sG/%d/%d/", config.getEventImageDir(), agentid, statSn);
		saveEventImages(path, mapGwangju, agentid);
	}

	private void saveEventImages(String path, Hashtable<Integer, EventImages> map, Integer agentid) {
		EventImages imgs = getEventImages(map, agentid);
		imgs.save(path);
	}
}
