package com.lge.mams.agentif.events;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.cxf.helpers.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lge.mams.agentif.udperver.UdpPacket;

public class EventImages {
	private Logger logger = LoggerFactory.getLogger(getClass());
	int maxImages = 10 * 20;
	ArrayList<UdpPacket> list = new ArrayList<UdpPacket>();

	public void add(UdpPacket img) {
		synchronized (list) {
			list.add(img);
			while (list.size() > maxImages) {
				list.remove(0);
			}
		}
	}

	public void save(String path) {
		// path를 체크하고, 해당 위치에 파일을 저장할 것. 0001 ~...
		File dir = new File(path);
		if (!dir.exists()) {
			FileUtils.mkDir(dir);
		}

		ArrayList<UdpPacket> imgs = new ArrayList<UdpPacket>();
		synchronized (list) {
			for (UdpPacket img : list) {
				imgs.add(img);
			}
		}

		int idx = 1;
		for (UdpPacket img : imgs) {
			String filename = path + String.format("evt-img%04d.jpg", idx);
			// save iamge.
			try {
				File ofile = new File(filename);
				FileOutputStream fos = new FileOutputStream(ofile);
				fos.write(img.getData());
				fos.close();

			} catch (Throwable e) {
				logger.error("ERROR", e);
			}
			idx++;
		}
	}
}
