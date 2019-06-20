package com.lge.mams.agentif.udperver;

import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * UDP서버에서 수신받은 최종 이미지를 저장하고 있으면서, 클라이언트에서 요청하는 경우, 이미지를 제공하는 역할.  
 * @author dulee
 *
 */
@Component
public class SeqImageProvider {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Hashtable<Integer , SeqImg> mapPohang;
	private Hashtable<Integer , SeqImg> mapGwangju;
	
	public SeqImageProvider() {
		mapPohang = new Hashtable<Integer, SeqImg>();
		mapGwangju = new Hashtable<Integer, SeqImg>();
	}
	
	
	public void add(SeqImgHead head, byte[] packet) {
		SeqImg img = new SeqImg(head, packet);
		if( !img.isValid()) {
			logger.debug("image not valid");
			return;
		}
		if(head.getLocationCode() == 1) {
			// 광주.
			mapGwangju.put(head.getRobotId(), img);
		}else if( head.getLocationCode() == 2) {
			// 포항.
			mapPohang.put(head.getRobotId(), img);
		}else {
			logger.error("Unknown location code " + head.getLocationCode());
		}
	}
	
	public SeqImg getSeqImg(String areaCode, int robotId) {
		if( "P".equals(areaCode)) {
			if( mapPohang.containsKey(robotId)) return mapPohang.get(robotId);
		}else if( "G".equals(areaCode)) {
			if( mapGwangju.containsKey(robotId)) return mapGwangju.get(robotId);
		}else {
			return null;
		}
		return null;
	}
}
