package com.lge.crawling.admin.management.sensorDataInfo.view;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;

import com.lge.crawling.admin.common.util.Config;

/**
 * DownloadView
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@SuppressWarnings("deprecation")
public class DownloadView extends AbstractView {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(DownloadView.class);

	/** DownloadCount */
	private static AtomicInteger downloadCount = new AtomicInteger(0);

	public DownloadView() {
		super.setContentType("application/octet-stream");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		StringBuilder sb = new StringBuilder();

		int downloadMaxCount = Config.getCommon().getInt("DOWNLOAD_MAX_COUNT");
		int result = 0;// 파일 다운로드 결과

		sb.append("DownLoad Start =====================================================================\r\n");
		sb.append("Download Count : [[ " + downloadCount + " ]]\r\n");

		if (downloadCount.get() > downloadMaxCount) {

			String errMsg = String.format("It is no longer available for download. (Max/Count) = (%s/%s)",
					downloadMaxCount,
					downloadCount);

			response.setStatus(505, errMsg);

			sb.append(errMsg).append("\r\n");

			result = -1;

		} else {

			result = fileDownload(model, request, response);
		}

		sb.append("DownLoad End =====================================================================\r\n");
		sb.append("Download result (0 : succes, -1 : fail) : " + result);

		logger.debug(sb.toString());
	}

	/**
	 *  File Download
	 *  @Mehtod Name : fileDownload
	 *  @param model
	 *  @param request
	 *  @param response
	 *  @return
	 *  @throws Exception
	 */
	public int fileDownload(Map<String, Object> model,HttpServletRequest request, HttpServletResponse response) {

		// Increment Download Count
		downloadCount.incrementAndGet();

		File file = null;
		BufferedOutputStream bos = null;
		RandomAccessFile raf = null;

		long seekPos = 0l;
		long startTime = System.currentTimeMillis();
		logger.debug("Download Start time : [[[ " + startTime + " ]]]");

		StringBuilder sb = new StringBuilder();
		try {

			// 출력을 위한 Stream 생성
			bos = new BufferedOutputStream(response.getOutputStream());

			file = (File) model.get("downloadFile");

			String fileName = new String(file.getName().getBytes("UTF-8"));// Request File Name
			String range = request.getHeader("Range");// Request Range

			sb.append("Download File Full Path : " + file.getPath() + "\r\n");
			sb.append("Download File Full Name : " + fileName + "\r\n");
			sb.append("App Total Bytes         : " + file.length() + "\r\n");
			sb.append("App Total Kb            : " + ( file.length() / 1024 ) + "\r\n");
			sb.append("App Total Exists        : " + file.exists() + "\r\n");
			sb.append("Request Header Range    : " + range);
			logger.debug(sb.toString());

			// Response setting
			response.setContentType(getContentType() );
			response.setContentLength((int) file.length() );

			// Response Header setting
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Range", range + ( file.length() ) + "/" + file.length());

			// Request Range null
			if(range == null || "".equals(range)) {
				range = "0-";
			}

			// 이어받기시에 이어받기 시작할 Byte setting
			seekPos = Long.parseLong(range.substring(0, range.length()-1));
			logger.debug("## RandomAccessFile seek Position : " + Long.parseLong(range.substring(0, range.length()-1)));

			try {

				int bytesRead = 0;
				byte[] buffer = new byte[1024];	//	1kb
				long totalsize = 0l;
				raf = new RandomAccessFile(file, "r");
				if (seekPos == 0l) {

					logger.info("Download the new file.");

					while ((bytesRead = raf.read(buffer, 0, buffer.length)) != -1) {
						bos.write(buffer, 0, bytesRead);
						totalsize = totalsize + bytesRead;
					}

					logger.debug("Download Total Bytes : " + totalsize + " Total kb : " + (totalsize/1024));

				}else {// 이어 받기 처리

					logger.debug("[ " + seekPos + " ] byte 부터 이어 받기를 합니다.");
					raf.seek(seekPos);
					while ((bytesRead = raf.read(buffer, 0, buffer.length)) != -1) {
						bos.write(buffer, 0, bytesRead);
						totalsize = totalsize + bytesRead;
					}

					logger.debug("Download resume Total Bytes : " + totalsize + "    Total kb : " + (totalsize/1024));
				}

				sb.setLength(0);

				sb.append("Response getContentType : " + response.getContentType() + "\r\n");
				sb.append("Response Content-Range  : " + range + (file.length()) +"/"+file.length() + "\r\n");

				sb.append("Response File Send Start  : [[[ " + startTime + "ms ]]]\r\n");
				sb.append("Response File Send End    : [[[ " + (System.currentTimeMillis() - startTime) + "ms ]]]\r\n");
				sb.append("Download SUCCESS!!\r\n");

				logger.debug(sb.toString());

				bos.flush();
				bos.close();
				raf.close();

				return 0;

			} catch (SocketException se) {

				sb.setLength(0);

				sb.append("The download was interrupted by Agent. -").append(se.getMessage());

				response.setStatus(800, sb.toString());

				sb.append("\r\n");
				sb.append("Response File Send Start  : [[[ " + startTime + "ms ]]]\r\n");
				sb.append("Response File Send End    : [[[ " + (System.currentTimeMillis() - startTime) + "ms ]]]\r\n");
				sb.append("Download Fail!!");

				logger.error(sb.toString());

				return -1;

			} catch(Exception e){

				sb.setLength(0);

				sb.append("Estimates by the Agent who disconnection was done. -").append(e.getMessage());

				response.setStatus(800, sb.toString());

				sb.append("\r\n");
				sb.append("Response File Send Start  : [[[ " + startTime + "ms ]]]\r\n");
				sb.append("Response File Send End    : [[[ " + (System.currentTimeMillis() - startTime) + "ms ]]]\r\n");
				sb.append("Download Fail!!");

				logger.error(sb.toString());

				return -1;

			} finally {
				if (raf != null) try { raf.close(); } catch (IOException ioe) { logger.error(ioe.getMessage()); }
				if (bos != null) try { bos.close(); } catch (IOException ioe) { logger.error(ioe.getMessage()); }
			}

		} catch(Exception e){

			sb.setLength(0);

			sb.append("Download Error. -").append(e.getMessage());

			response.setStatus(800, sb.toString());

			sb.append("\r\n");
			sb.append("Response File Send Start  : [[[ " + startTime + "ms ]]]\r\n");
			sb.append("Response File Send End    : [[[ " + (System.currentTimeMillis() - startTime) + "ms ]]]\r\n");
			sb.append("Download Fail!!");

			logger.error(sb.toString());

			return -1;

		} finally {

			downloadCount.decrementAndGet();

			if (raf != null) try { raf.close(); } catch (IOException ioe) { logger.error(ioe.getMessage()); }
			if (bos != null) try { bos.close(); } catch (IOException ioe) { logger.error(ioe.getMessage()); }
		}
	}
}
