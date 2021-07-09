package com.lge.mams.demon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lge.mams.agentif.udperver.UdpPacket;
import com.lge.mams.config.LgicConfig;

public class MntrImageSaver extends Thread {
	private Logger logger = LoggerFactory.getLogger(getClass());

	boolean requestToQuit = false;

	TimePath pathMgr = new TimePath();

	LgicConfig config;

	public void startServer() {

		start();

	}

	LinkedBlockingQueue<UdpPacket> q = new LinkedBlockingQueue<UdpPacket>();

	public void push(UdpPacket img) {
		if (q.size() > 200) {
			logger.error("q size over : {}", q.size());
			q.clear();
		}
		q.offer(img);
	}

	public UdpPacket pop() {
		try {
			if (q.isEmpty())
				return null;
			return q.poll(1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			logger.error("E", e);
		}
		return null;
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

	private void saveImage(UdpPacket img) {
		Integer rid = img.getHead().getRobotId();
		String area = img.getHead().getAreaCode();
		String path = config.getMntrImageDir() + pathMgr.getPath(rid, area);
		// logger.debug("path : {}", path);
		pathMgr.checkPath(path);
		String filename = pathMgr.getFilename(rid, "jpg");
		String full = path + filename;
		// logger.debug("full : {}", full);

		save(full, img);
		saveFilename(path, rid, filename);

	}

	private void saveFilename(String path, Integer rid, String filename) {
		try {
			final DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
			String dstr = df.format(new Date());

			String newfile = path + "index_" + rid + ".txt";
			FileWriter fw = new FileWriter(newfile, true); // the true will append the new data
			fw.write(dstr + " : " + filename + "\n");// appends the string to the file
			fw.close();
		} catch (Exception ioe) {
			logger.error("E", ioe);
		}
	}

	@Override
	public void run() {
		try {
			logger.debug("START Image Saver Thread.");

			while (true) {

				UdpPacket img;
				img = pop();

				if (img != null) {
					// logger.debug("get image...");
					saveImage(img);
					continue;
				}

				if (requestToQuit)
					break;
				Thread.sleep(100);
			}

		} catch (Exception e) {
			logger.error("ERROR", e);
		}

		logger.debug("END Image Saver Thread.");
	}

	public void stopServer() {
		this.requestToQuit = true;
	}
}
