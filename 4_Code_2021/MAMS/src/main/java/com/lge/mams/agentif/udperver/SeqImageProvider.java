package com.lge.mams.agentif.udperver;

import java.util.Date;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lge.mams.agentif.events.EventImageHandler;

/**
 * UDP서버에서 수신받은 최종 이미지를 저장하고 있으면서, 클라이언트에서 요청하는 경우, 이미지를 제공하는 역할.
 * 
 * @author dulee
 *
 */
@Component
public class SeqImageProvider {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private Hashtable<Integer, UdpPacket> mapPohang;
	private Hashtable<Integer, UdpPacket> mapGwangju;

	private Hashtable<Integer, Date> mapPohangDate;
	private Hashtable<Integer, Date> mapGwangjuDate;

	@Autowired
	EventImageHandler eventImageHandler;

	public SeqImageProvider() {
		mapPohang = new Hashtable<Integer, UdpPacket>();
		mapGwangju = new Hashtable<Integer, UdpPacket>();

		mapPohangDate = new Hashtable<Integer, Date>();
		mapGwangjuDate = new Hashtable<Integer, Date>();
	}

	public boolean isConnected(Date now, Integer robotId, String location/* ph, gw */, int milliseconds) {
		Hashtable<Integer, Date> map;
		if (location.equals("ph")) {
			map = mapPohangDate;
		} else {
			map = mapGwangjuDate;
		}

		if (map.containsKey(robotId)) {
			Date date = map.get(robotId);
			long ms = now.getTime() - date.getTime();
			if (ms > milliseconds) return false;
			return true;
		}

		return false;
	}

	public void setHealthy(Integer robotId, String location /* ph , gw */) {
		Hashtable<Integer, Date> map;
		if (location.equals("ph")) {
			map = mapPohangDate;
		} else {
			map = mapGwangjuDate;
		}
		map.put(robotId, new Date());
	}

	public void add(SeqImgHead head, byte[] packet) {
		UdpPacket img = new UdpPacket(head, packet);
		if (!img.isValid()) {
			logger.debug("image not valid");
			return;
		}
		if (head.getLocationCode() == 1) {
			// 광주.
			mapGwangju.put(head.getRobotId(), img);
			mapGwangjuDate.put(head.getRobotId(), new Date());

			eventImageHandler.addGwangju(img);
		} else if (head.getLocationCode() == 2) {
			// 포항.
			mapPohang.put(head.getRobotId(), img);
			mapPohangDate.put(head.getRobotId(), new Date());
			eventImageHandler.addPohang(img);
		} else {
			logger.error("robotid : {}, remoteIp : {},  Unknown location code {}",
					head.getRobotId(), head.getRemoteIp(), head.getLocationCode());
		}
	}

	public UdpPacket getSeqImg(String areaCode, int robotId) {
		if ("P".equals(areaCode)) {
			if (mapPohang.containsKey(robotId)) return mapPohang.get(robotId);
		} else if ("G".equals(areaCode)) {
			if (mapGwangju.containsKey(robotId)) return mapGwangju.get(robotId);
		} else {
			return null;
		}
		return null;
	}
}
