package com.lge.mams.agentif.udperver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lge.mams.config.LgicConfig;
import com.lge.mams.config.LgicSettingsComponent;
import com.lge.mams.demon.TimePath;
import com.lge.mams.websocket.WsAgentIfHandler;

@Component
public class VoiceHandler {
	
	@Autowired
	LgicConfig config;
	
	@Autowired
	LgicSettingsComponent settings;
	
	@Autowired
	private WsAgentIfHandler wsAgent;
	
	TimePath pathMgr = new TimePath();
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	public void handle(SeqImgHead head, byte[] packet) {
		UdpPacket voice = new UdpPacket(head, packet);
		if( !voice.isValid()) {
			logger.debug("image not valid");
			return;
		}
//		if(head.getLocationCode() == 1) {
//			// 광주.
//			//mapGwangju.put(head.getRobotId(), img);
//			
//			//eventImageHandler.addGwangju(img);
//		}else if( head.getLocationCode() == 2) {
//			// 포항.
//			//mapPohang.put(head.getRobotId(), img);
//			//eventImageHandler.addPohang(img);
//		}else {
//			logger.error("Unknown location code " + head.getLocationCode());
//		}
		saveVoice(voice);
	}
	
	
	private void save(String filename, UdpPacket img) {
		try {
			File ofile = new File(filename);
			FileOutputStream fos = new FileOutputStream(ofile);
			fos.write(img.getData());
			fos.close();

		} catch (Throwable e) {
			logger.error("ERROR", e);
		}
	}
	
	private void saveVoice(UdpPacket img) {
		Integer rid = img.getHead().getRobotId();
		String area = img.getHead().getAreaCode();
		String path =  config.getVoiceDir() + pathMgr.getPath(rid, area);
		//logger.debug("path : {}", path);
		pathMgr.checkPath(path);
		String filename = pathMgr.getFilename(rid, "mp3");
		String full = path + filename;
		//logger.debug("full : {}", full);
		
		save(full, img);
		saveFilename(path, rid, filename);
		
		// area : P or G
		wsAgent.pushVoiceInfo(area, rid, full);
	}
	
	private void saveFilename(String path, Integer rid, String filename) {
		try
		{
			final DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
			String dstr = df.format(new Date());
			
			String newfile = path + "index_" + rid + ".txt";
		    FileWriter fw = new FileWriter(newfile,true); //the true will append the new data
		    fw.write( dstr + " : " + filename + "\n" );//appends the string to the file
		    fw.close();
		}
		catch(Exception ioe)
		{
		    logger.error("E", ioe);
		}
	}
}
