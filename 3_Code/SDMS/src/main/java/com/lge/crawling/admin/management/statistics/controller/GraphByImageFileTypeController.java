package com.lge.crawling.admin.management.statistics.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lge.crawling.admin.common.util.DateSupportUtil;
import com.lge.crawling.admin.common.web.controller.BaseController;
import com.lge.crawling.admin.constants.TilesSuffix;
import com.lge.crawling.admin.management.statistics.entity.ImageFileEntity;
import com.lge.crawling.admin.management.statistics.service.CreateStatisticsInfoToExcel;
import com.lge.crawling.admin.management.statistics.service.ImageFileStatisticsService;

/**
 * 이미지 파일 타입별 수집 통계 Controller
 * @version : 1.0
 * @author :  Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
@Controller
@RequestMapping("/statistics/graphByImageFileType")
public class GraphByImageFileTypeController extends BaseController {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(GraphByImageFileTypeController.class);

	private final String PREFIX = "statistics/";

	@Autowired private ImageFileStatisticsService service;

	/** Excel Download */
    @Autowired private CreateStatisticsInfoToExcel createStatisticsInfoToExcel;

	/**
	 * 이미지 파일 타입별 통계 그래프 정보 조회
	 * @Mehtod Name : getGraphByImageFileType
	 * @param entity
	 * @param model
	 * @return
	 */
    @RequestMapping(value = {"","list"})
	public String getGraphByImageFileType(@ModelAttribute ImageFileEntity entity, Model model) {

		if (StringUtils.isEmpty(entity.getStartDt()) && StringUtils.isEmpty(entity.getEndDt())) {
			entity.setStartDt(DateSupportUtil.getDefaultStart());
			entity.setEndDt(DateSupportUtil.getDefaultEnd());
		}

		model.addAttribute("barList", service.getBarGraphByImageFileTypeCdList(entity));
		model.addAttribute("barAllList", service.getBarGraphByImageFileTypeCd(entity));
		model.addAttribute("pieList", service.getPieGraphByImageFileTypeCd(entity));
		model.addAttribute("paging", entity.getPaging());

		return PREFIX + "graphByImageFIleType" + TilesSuffix.DEFAULT;
	}

    /**
	 * 이미지 파일 타입별 수집 통계 엑셀 다운로드
	 * @method downloadToExcelFile
	 * @param entity
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "download-excel-file")
    public void downloadToExcelFile(HttpServletResponse response, HttpServletRequest request, @ModelAttribute ImageFileEntity entity) {

		if (StringUtils.isEmpty(entity.getStartDt()) && StringUtils.isEmpty(entity.getEndDt())) {
    		entity.setStartDt(DateSupportUtil.getDefaultStart());
    		entity.setEndDt(DateSupportUtil.getDefaultEnd());
    	}

		List<ImageFileEntity> barList = service.getBarGraphByImageFileTypeCd(entity);
		List<ImageFileEntity> pieList = service.getPieGraphByImageFileTypeCd(entity);

    	try {
    	 response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    	 String fileName = String.format("이미지_타입별_수집_통계.xls");
    	 fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
    	 response.setHeader("Content-Disposition", String.format("attachment; filename=%s", fileName));
         response.setHeader("Set-Cookie", "fileDownload=true; path=/");
         response.setHeader("Content-Transfer-Encoding", "binary;");
         response.setHeader("Pragma", "no-cache;");
         response.setHeader("Expires", "-1;");
         createStatisticsInfoToExcel.writeGraphByImageFileTypeReport(response.getOutputStream(), barList, pieList);

    	 response.getOutputStream().close();

    	 logger.debug(fileName + " excel File Created.");
    	 } catch (Exception e) {
    		 e.printStackTrace();
    	 }
    }
}