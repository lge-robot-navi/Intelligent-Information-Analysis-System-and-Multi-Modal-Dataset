package com.lge.crawling.admin.common.web.controller;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import com.lge.crawling.admin.common.web.view.DownloadView;

/**
 * Download Controller
 * 다운로드 컨트롤러
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Controller
public class DownloadController {

	/**
	 * Download View
	 * 다운로드 처리 컨트롤러<br/>
	 * 파일 위치를 정확하게 알고 있는 경우 아래와 같이 사용하는 공통 컨트롤러.
	 * <br/>
	 * 사용방법)
	 * <pre>
	 * <c:url var="downloadFileURL" value="/download.do">
	 *     <c:param name="path">F:/Temp</c:param>              <-- 파일경로
	 * 	   <c:param name="fileName">Test.apk</c:param>         <-- 파일명
	 * </c:url>
	 * <a target="_blank" href="${downloadFileURL}">다운로드</a>
	 * </pre>
	 * @method download
	 * @param fullPath
	 * @param path
	 * @param fileName
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("download.do")
	public View download(@RequestParam(value = "fullPath", required = false) String fullPath,
			@RequestParam(value = "path", required = false) String path,
			@RequestParam(value = "fileName", required = false) String fileName, Model model) throws Exception {

		if (StringUtils.isNotEmpty(path) && StringUtils.isNotEmpty(fileName)) {
			fullPath = path + "/" + fileName;
		}

		model.addAttribute(DownloadView.DOWNLOAD_FILE, new File(fullPath));

		return new DownloadView();
	}
}