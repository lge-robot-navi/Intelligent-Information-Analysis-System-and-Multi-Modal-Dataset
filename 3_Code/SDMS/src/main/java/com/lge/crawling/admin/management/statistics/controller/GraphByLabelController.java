package com.lge.crawling.admin.management.statistics.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.lge.crawling.admin.common.util.DateSupportUtil;
import com.lge.crawling.admin.common.web.controller.BaseController;
import com.lge.crawling.admin.constants.TilesSuffix;
import com.lge.crawling.admin.management.statistics.entity.ImageFileEntity;
import com.lge.crawling.admin.management.statistics.entity.ImageTaggingDataDicEntity;
import com.lge.crawling.admin.management.statistics.service.CreateStatisticsInfoToExcel;
import com.lge.crawling.admin.management.statistics.service.ImageFileStatisticsService;

import net.sf.json.JSONObject;

/**
 * 이미지 태깅 라벨별 수집 통계 Controller
 * @version : 1.0
 * @author :  Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 */
@Controller
@RequestMapping("/statistics/graphByLabel")
public class GraphByLabelController extends BaseController {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(GraphByLabelController.class);

	private final String PREFIX = "statistics/";

	@Autowired private ImageFileStatisticsService service;

	/** Excel Download */
    @Autowired private CreateStatisticsInfoToExcel createStatisticsInfoToExcel;

	/**
	 * 이미지 태깅 라벨별 통계 그래프 정보 조회
	 * @Mehtod Name : getGraphByLabel
	 * @param entity
	 * @param model
	 * @return
	 */
    @RequestMapping(value = {"","list"})
	public String getGraphByLabel(@ModelAttribute ImageFileEntity entity, Model model) {

		if (StringUtils.isEmpty(entity.getStartDt()) && StringUtils.isEmpty(entity.getEndDt())) {
			entity.setStartDt(DateSupportUtil.getDefaultStart());
			entity.setEndDt(DateSupportUtil.getDefaultEnd());
		}

		try {
			Map<String, String> imageTaggingDataDicMap = new HashMap<>();

			List<String> imageTaggingDataDicSqList = entity.getImageTaggingDataDicIdSqList();
			if (imageTaggingDataDicSqList == null) {
				// 초기 1단계 5개 항목 선택
				imageTaggingDataDicSqList = new ArrayList<>();
				ImageTaggingDataDicEntity imageTaggingDataDicEntity = new ImageTaggingDataDicEntity();
				imageTaggingDataDicEntity.setImageTaggingDataDicLevel("1");
				List<ImageTaggingDataDicEntity> imageTaggingDataDicList = service.getTaggingDicData(imageTaggingDataDicEntity);
				if (imageTaggingDataDicList.size() > 5) {
					imageTaggingDataDicList = imageTaggingDataDicList.subList(0, 5);
				}
				for (ImageTaggingDataDicEntity imageTaggingData : imageTaggingDataDicList) {
					imageTaggingDataDicSqList.add(imageTaggingData.getImageTaggingDataDicIdSq());
					imageTaggingDataDicMap.put(imageTaggingData.getImageTaggingDataDicIdSq(), imageTaggingData.getImageTaggingDataDicNm());
				}
				entity.setImageTaggingDataDicIdSqList(imageTaggingDataDicSqList);
			} else {
				// 검색 라벨 정보 조회
				if (imageTaggingDataDicSqList.size() > 0) {
					ImageTaggingDataDicEntity imageTaggingDataDicEntity = new ImageTaggingDataDicEntity();
					imageTaggingDataDicEntity.setImageTaggingDataDicIdSqList(imageTaggingDataDicSqList);
					List<ImageTaggingDataDicEntity> imageTaggingDataDicList = service.getTaggingDicData(imageTaggingDataDicEntity);
					for (ImageTaggingDataDicEntity imageTaggingData : imageTaggingDataDicList) {
						imageTaggingDataDicMap.put(imageTaggingData.getImageTaggingDataDicIdSq(), imageTaggingData.getImageTaggingDataDicNm());
					}
				} else {
					// 초기 1단계 5개 항목 선택
					imageTaggingDataDicSqList = new ArrayList<>();
					ImageTaggingDataDicEntity imageTaggingDataDicEntity = new ImageTaggingDataDicEntity();
					imageTaggingDataDicEntity.setImageTaggingDataDicLevel("1");
					List<ImageTaggingDataDicEntity> imageTaggingDataDicList = service.getTaggingDicData(imageTaggingDataDicEntity);
					imageTaggingDataDicList = imageTaggingDataDicList.subList(0, 5);
					if (imageTaggingDataDicList.size() > 5) {
						imageTaggingDataDicList = imageTaggingDataDicList.subList(0, 5);
					}
					for (ImageTaggingDataDicEntity imageTaggingData : imageTaggingDataDicList) {
						imageTaggingDataDicSqList.add(imageTaggingData.getImageTaggingDataDicIdSq());
						imageTaggingDataDicMap.put(imageTaggingData.getImageTaggingDataDicIdSq(), imageTaggingData.getImageTaggingDataDicNm());
					}
					entity.setImageTaggingDataDicIdSqList(imageTaggingDataDicSqList);
				}
			}

			// Bar List 데이터 편집
			List<ImageFileEntity> barAllList = service.getBarGraphByLabel(entity);
			List<ImageFileEntity> barList = service.getBarGraphByLabelList(entity);
			List<String> totalLabelList = new ArrayList<>();
			for (ImageFileEntity imageFileEntity : barAllList) {
				String taggingNames = imageFileEntity.getImageTaggingDataDicNm();
				if (taggingNames != null) {
					totalLabelList.addAll(Arrays.asList(taggingNames.split(";")));
				}
			}

			// 중복제거
			List<String> removeDupLabel = new ArrayList<String>(new HashSet<String>(totalLabelList));
			for (ImageFileEntity imageFileEntity : barAllList) {
				List<String> taggingNames = new ArrayList<>();
				List<String> tagCntList = Arrays.asList(imageFileEntity.getTaggingCnt().split(";"));
				if (imageFileEntity.getImageTaggingDataDicNm() != null) {
					taggingNames = Arrays.asList(imageFileEntity.getImageTaggingDataDicNm().split(";"));
				}
				imageFileEntity.setImageTaggingDataDicNmList(removeDupLabel);
				List<String> totalTagCntList = new ArrayList<>(removeDupLabel.size());
				for (int i = 0; i < removeDupLabel.size(); i++) {
					String label = removeDupLabel.get(i);

					if (taggingNames.indexOf(label) >= 0) {
						int index = taggingNames.indexOf(label);
						totalTagCntList.add(tagCntList.get(index));
					} else {
						totalTagCntList.add("0");
					}
				}
				imageFileEntity.setTaggingCntList(totalTagCntList);
			}

			for (ImageFileEntity imageFileEntity : barList) {
				List<String> taggingNames = new ArrayList<>();
				List<String> tagCntList = Arrays.asList(imageFileEntity.getTaggingCnt().split(";"));
				if (imageFileEntity.getImageTaggingDataDicNm() != null) {
					taggingNames = Arrays.asList(imageFileEntity.getImageTaggingDataDicNm().split(";"));
				}
				imageFileEntity.setImageTaggingDataDicNmList(removeDupLabel);
				List<String> totalTagCntList = new ArrayList<>(removeDupLabel.size());
				for (int i = 0; i < removeDupLabel.size(); i++) {
					String label = removeDupLabel.get(i);

					if (taggingNames.indexOf(label) >= 0) {
						int index = taggingNames.indexOf(label);
						totalTagCntList.add(tagCntList.get(index));
					} else {
						totalTagCntList.add("0");
					}
				}
				imageFileEntity.setTaggingCntList(totalTagCntList);
			}

			// Pie List 데이터 편집
			List<ImageFileEntity> pieList = service.getPieGraphByLabel(entity);
			for (ImageFileEntity imageFileEntity : pieList) {
				if (imageFileEntity != null) {
					double totalPercentage = 0.0;
					String taggingNames = "";
						taggingNames = imageFileEntity.getImageTaggingDataDicNm();
					imageFileEntity.setImageTaggingDataDicNmList(Arrays.asList(taggingNames.split(";")));
					String taggingCnt = imageFileEntity.getTaggingCnt();
					imageFileEntity.setTaggingCntList(Arrays.asList(taggingCnt.split(";")));
					List<String> percentList = new ArrayList<>();
					for (String tagCount : imageFileEntity.getTaggingCntList()) {
						int total = Integer.parseInt(imageFileEntity.getTotalCnt());
						int count = Integer.parseInt(tagCount);
						double percent = ((double)count / (double)total) * 100.0;
						percentList.add(String.valueOf(Math.round(percent*10.0)/10.0));
						totalPercentage += Math.round(percent*10.0)/10.0;
					}
					imageFileEntity.setTaggingCntPerList(percentList);
					imageFileEntity.setTotalPercentage(String.valueOf(Math.round(totalPercentage)));
				}
			}

			model.addAttribute("barList", barList);
			model.addAttribute("barAllList", barAllList);
			model.addAttribute("pieList", pieList);
			model.addAttribute("imageTaggingDataDicMap", imageTaggingDataDicMap);
			model.addAttribute("paging", entity.getPaging());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return PREFIX + "graphByLabel" + TilesSuffix.DEFAULT;
	}

	/**
	 * 태깅사전 데이터 조회
	 * @Mehtod Name : taggingDataSearch
	 * @return
	 */
	@RequestMapping(value = "taggingDataSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject taggingDataSearch(@RequestBody ImageTaggingDataDicEntity sendData, BindingResult bindingResult) {

		JSONObject result = new JSONObject();
		result.put("resultCd", "fail");

		try {
			// Validator Error
			if (bindingResult.hasErrors()) {
			}

			List<ImageTaggingDataDicEntity> taggingDicDataList = service.getTaggingDicData(sendData);

			result.put("resultCd", "success");
			result.put("taggingDicDataList",new Gson().toJson(taggingDicDataList));

		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

    /**
	 * 이미지 태깅 라벨별 수집 통계 엑셀 다운로드
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

		// Bar List 데이터 편집
		List<ImageFileEntity> barList = service.getBarGraphByLabel(entity);
		List<String> totalLabelList = new ArrayList<>();
		for (ImageFileEntity imageFileEntity : barList) {
			String taggingNames = imageFileEntity.getImageTaggingDataDicNm();
			totalLabelList.addAll(Arrays.asList(taggingNames.split(";")));
		}
		// 중복제거
		List<String> removeDupLabel = new ArrayList<String>(new HashSet<String>(totalLabelList));
		for (ImageFileEntity imageFileEntity : barList) {
			List<String> taggingNames = Arrays.asList(imageFileEntity.getImageTaggingDataDicNm().split(";"));
			List<String> tagCntList = Arrays.asList(imageFileEntity.getTaggingCnt().split(";"));
			imageFileEntity.setImageTaggingDataDicNmList(removeDupLabel);
			List<String> totalTagCntList = new ArrayList<>(removeDupLabel.size());
			for (int i = 0; i < removeDupLabel.size(); i++) {
				String label = removeDupLabel.get(i);

				if (taggingNames.indexOf(label) >= 0) {
					int index = taggingNames.indexOf(label);
					totalTagCntList.add(tagCntList.get(index));
				} else {
					totalTagCntList.add("0");
				}
			}
			imageFileEntity.setTaggingCntList(totalTagCntList);
		}

		// Pie List 데이터 편집
		List<ImageFileEntity> pieList = service.getPieGraphByLabel(entity);
		for (ImageFileEntity imageFileEntity : pieList) {
			double totalPercentage = 0.0;
			String taggingNames = imageFileEntity.getImageTaggingDataDicNm();
			imageFileEntity.setImageTaggingDataDicNmList(Arrays.asList(taggingNames.split(";")));
			String taggingCnt = imageFileEntity.getTaggingCnt();
			imageFileEntity.setTaggingCntList(Arrays.asList(taggingCnt.split(";")));
			List<String> percentList = new ArrayList<>();
			for (String tagCount : imageFileEntity.getTaggingCntList()) {
				int total = Integer.parseInt(imageFileEntity.getTotalCnt());
				int count = Integer.parseInt(tagCount);
				double percent = ((double)count / (double)total) * 100.0;
				percentList.add(String.valueOf(Math.round(percent*10.0)/10.0));
				totalPercentage += Math.round(percent*10.0)/10.0;
			}
			imageFileEntity.setTaggingCntPerList(percentList);
			imageFileEntity.setTotalPercentage(String.valueOf(Math.round(totalPercentage)));
		}

    	try {
    	 response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    	 String fileName = String.format("이미지_라벨별_수집_통계.xls");
    	 fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
    	 response.setHeader("Content-Disposition", String.format("attachment; filename=%s", fileName));
         response.setHeader("Set-Cookie", "fileDownload=true; path=/");
         response.setHeader("Content-Transfer-Encoding", "binary;");
         response.setHeader("Pragma", "no-cache;");
         response.setHeader("Expires", "-1;");
         createStatisticsInfoToExcel.writeGraphByLabelReport(response.getOutputStream(), barList, pieList);

    	 response.getOutputStream().close();

    	 logger.debug(fileName + " excel File Created.");
    	 } catch (Exception e) {
    		 e.printStackTrace();
    	 }
    }

}