package com.lge.crawling.admin.task;

import java.io.File;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Clean the temporary storage space.
 * 임시저장공간을 정리한다.
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Component
public class TemporaryCleanupTask {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(TemporaryCleanupTask.class);

	@Autowired
	private FileSystemResource fileSystemResource;

	/**
	 * Delete temporary files scheduler
	 * 임시파일삭제 스케쥴러
	 * @Mehtod doScheduled
	 */
	@Scheduled(cron = "0 0 0 * * *")
	public void doScheduled() {
		logger.info("########## TASK Start ##########");
		String path = fileSystemResource.getPath();
		logger.info("path : {}", path);
		String[] paths = path.split(";");
		if (paths != null && paths.length > 0) {
			for (String p : paths) {
				logger.info("directory : {}", p);
				clearTemp(p);
			}
		}

		logger.info("########## TASK End ##########");
	}

	/**
	 * Clean up the directory.
	 * 해당 디렉토리를 정리한다.
	 * @method clearTemp
	 * @param path
	 */
	public void clearTemp(String path) {
		File root = new File(path);

		File[] list = root.listFiles();
		for (File f : list) {
			if (f.isDirectory()) {					// 디렉토리일 경우
				clearTemp(f.getAbsolutePath());		// recursive
				if (f.list().length == 0) {
					logger.info("{} is empty! Delete Directory!!!", f);
					f.delete();
				}
			} else { // 파일일 경우
				compareDeleteFile(f);
			}
		}
	}

	/**
	 * compare date, after delete.
	 * 일자 비교 후 삭제
	 * @param file
	 * @return
	 */
	public void compareDeleteFile(File file) {
		// yesterday
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);

		long baseTime = cal.getTimeInMillis();
		long lastModified = file.lastModified();		// File Last Modified
		if (baseTime > lastModified) {					// One days or more
			boolean rslt = file.delete();
			logger.info("Delete File Result : {}", rslt);
		}
	}
}
