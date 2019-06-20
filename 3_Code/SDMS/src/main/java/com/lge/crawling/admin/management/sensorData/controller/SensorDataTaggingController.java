package com.lge.crawling.admin.management.sensorData.controller;

import com.google.gson.Gson;
import com.lge.crawling.admin.common.util.DateSupportUtil;
import com.lge.crawling.admin.common.util.JsonParseUtil;
import com.lge.crawling.admin.common.util.MatrixUtil;
import com.lge.crawling.admin.constants.Constants;
import com.lge.crawling.admin.constants.TilesSuffix;
import com.lge.crawling.admin.management.sensorData.entity.SensorDataTaggingEntity;
import com.lge.crawling.admin.management.sensorData.entity.SensorDataTaggingGetInfoEntity;
import com.lge.crawling.admin.management.sensorData.service.SensorDataTaggingGetInfoService;
import com.lge.crawling.admin.management.sensorData.service.SensorDataTaggingService;
import com.lge.crawling.admin.management.sensorDataInfo.entity.SensorDataJsonFileDescEntity;
import com.lge.crawling.admin.management.sensorDataInfo.service.SensorDataFileInfoService;
import com.lge.crawling.admin.management.sensorDataInfo.entity.Regions_sd;
import com.lge.crawling.admin.management.sensorDataInfo.entity.SensorDataFileInfoEntity;
import com.lge.crawling.admin.management.image.entity.ImageTaggingEntity;
import com.lge.crawling.admin.management.imageTaggingDic.entity.TaggingDicEntity;
import com.lge.crawling.admin.management.imageTaggingDic.service.TaggingDicService;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.awt.Point;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * 이미지 태깅 Controller
 * @version : 1.0
 */
@Controller
@RequestMapping("/sensorData/tagging")
public class SensorDataTaggingController {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(SensorDataTaggingController.class);
	
	private final String PREFIX = "sensorData/tagging/";

	@Autowired
	private SensorDataTaggingService service;
	
	@Autowired
	private SensorDataFileInfoService fileInfoService;

	@Autowired
	private TaggingDicService taggingDicService;

	@Autowired
	private SensorDataTaggingGetInfoService imageTaggingGetInfoService;

	/**
	 * 이미지 태깅 정보 List 를 조회한다.
	 * @Mehtod Name : getList
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"", "list"})
	public String list(@ModelAttribute SensorDataTaggingEntity entity, Model model) {

		if (StringUtils.isEmpty(entity.getCStartDt()) && StringUtils.isEmpty(entity.getCEndDt())) {
//			entity.setCStartDt(DateSupportUtil.getDefaultStart());
			entity.setCStartDt("2018-08-30");
			entity.setCEndDt(DateSupportUtil.getDefaultEnd());
		}
		
		// default 로 RGB 를 선택
		String sensorDataFileTypeCd = "100";
		if (StringUtils.isEmpty(entity.getSensorDataFileTypeCd())) {
			entity.setSensorDataFileTypeCd("100"); 
		} else {
			sensorDataFileTypeCd = entity.getSensorDataFileTypeCd();
		}

		entity.setPageSize(10);
		model.addAttribute("list", service.getList(entity));
		model.addAttribute("paging", entity.getPaging());
		
		// audio 추가
		entity.setSensorDataFileTypeCd("600"); // audio로 typecd 변경
		model.addAttribute("listAudio", service.getList(entity));
		entity.setSensorDataFileTypeCd(sensorDataFileTypeCd); // typecd 재변경

		// get root id
		TaggingDicEntity dicInfoEntity = new TaggingDicEntity();
		dicInfoEntity.setUpperImageTaggingDataDicId("ROOT");
		dicInfoEntity.setUseYn("Y");
		TaggingDicEntity root = taggingDicService.get(dicInfoEntity);

		// get tagging dic tree list
		dicInfoEntity.setUpperImageTaggingDataDicId(root.getImageTaggingDataDicIdSq());
		List<TaggingDicEntity> attributeList = taggingDicService.getAllList(dicInfoEntity);
		Map<String, Object> map = new HashMap<>();
		for(int i = 0; i < attributeList.size(); i++) {
			TaggingDicEntity info = attributeList.get(i);
			dicInfoEntity.setUpperImageTaggingDataDicId(info.getImageTaggingDataDicIdSq());
			List<TaggingDicEntity> subList = taggingDicService.getAllList(dicInfoEntity);
			for(int j = 0; j < subList.size(); j++) {
				TaggingDicEntity subInfo = subList.get(j);
				dicInfoEntity.setUpperImageTaggingDataDicId(subInfo.getImageTaggingDataDicIdSq());
				List<TaggingDicEntity> subSubList = taggingDicService.getAllList(dicInfoEntity);
				map.put(subInfo.getImageTaggingDataDicIdSq(), subSubList);
			}
			map.put(info.getImageTaggingDataDicIdSq(), subList);
		}

		model.addAttribute("attributeList", attributeList);
		model.addAttribute("attributeMap", new Gson().toJson(map));

		return PREFIX + "list" + TilesSuffix.DEFAULT;
	}

	/**
	 * 이미지 태깅 정보를 저장한다.
	 * @Mehtod Name : add
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "form", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object add(HttpSession session, @ModelAttribute SensorDataTaggingEntity entity, @ModelAttribute ImageTaggingEntity entity2, BindingResult bindingResult) {
		Map<String, String> ret = new HashMap<>();
		
		// Validator Error
		if (bindingResult.hasErrors()) {
		}

		try {
			SensorDataTaggingEntity search = new SensorDataTaggingEntity();
			search.setSensorDataFileSq(entity.getSensorDataFileSq());
			search = service.get(search);

//			logger.debug("====================> entity.getSensorDataFileSq(): "+entity.getSensorDataFileSq());
//			logger.debug("====================> entity.getSensorDataFilePackageIdSq(): "+entity.getSensorDataFilePackageIdSq());
//			logger.debug("====================> entity.getSensorDataFileGroup(): "+entity.getSensorDataFileGroup());
//			logger.debug("====================> entity.getImageJsonFileDesc(): "+entity2.getImageJsonFileDesc());
//			logger.debug("====================> entity.getImageJsonFileDesc(): "+HtmlUtils.htmlUnescape(URLDecoder.decode(entity2.getImageJsonFileDesc(), "UTF-8")));
//			logger.debug("====================> entity: "+entity);
//			logger.debug("====================> search: "+search);
			
			List<SensorDataFileInfoEntity> fileInfoList = fileInfoService.getFileTypeList(entity);
			
			// 하나의 영상이미지 태깅 시 모든 타입의 영상이미지 태깅 정보도 같이 저장함.
			for(SensorDataFileInfoEntity infoEntity : fileInfoList) {
				
				entity.setSensorDataFileSq(infoEntity.getSensorDataFileSq());
				entity.setSensorDataJsonFileDesc(entity2.getImageJsonFileDesc()); // JSON데이터를 이미지태킹엔티티에서 받아와서 셋팅함.
				
				if (search != null) {
					
					/**
					 * 3x3 Matrix 변환을 통해 JSON 데이터를 변형하여 저장
					 * JsonParseUtil.getPointFromJson : x, y 위치값를 가져옴
					 * MatrixUtil.calcTransform : x, y 변환 위치값을 가져옴
					 */
					String jsonFileDesc = HtmlUtils.htmlUnescape(URLDecoder.decode(entity2.getImageJsonFileDesc(), "UTF-8"));
					String fileTypeCd = infoEntity.getSensorDataFileTypeCd();
					String fileNm = infoEntity.getSensorDataFileNm();
					String fileSize = infoEntity.getSensorDataFileSize();
					
					// JSON 파일에서 x,y 포인트 값을 가져옴
					Point taggingPoint = JsonParseUtil.getPointFromJson(jsonFileDesc);
					// 이미지타입에 따른 3x3 매트릭스스 변환을 통해 x,y 변환값을 가져옴 
					Point convertPoint = MatrixUtil.calcTransform(taggingPoint, fileTypeCd);
					
					logger.debug("====================> fileType : "+fileTypeCd+", taggingPoint: "+taggingPoint+", covertPoint: "+convertPoint);
					
					// JSON 데이터 업데이트 (파일명, 파일크기, x, y)
					String convertJsonFileDesc = JsonParseUtil.getUpdateJsonStr(jsonFileDesc, fileNm, 
							Long.parseLong(fileSize), Long.valueOf(convertPoint.x), Long.valueOf(convertPoint.y));
					
//					entity.setSensorDataJsonFileDesc(HtmlUtils.htmlUnescape(URLDecoder.decode(entity.getSensorDataJsonFileDesc(), "UTF-8")));
					entity.setSensorDataJsonFileDesc(convertJsonFileDesc);
					JSONObject json = new JSONObject();
					json.put("annotation", new JSONObject(entity.getSensorDataJsonFileDesc()));
					entity.setSensorDataJsonXmlConvFileDesc(XML.toString(json));
					
//					if (StringUtils.isNotEmpty(search.getSensorDataJsonFileDesc())) {
//						service.update(entity);
//					} else {
//						service.insert(entity);
//					}
					
					// base entity에 login id 가 null 이어서, DB insert 시 오류가 발생하여 강제로 다시 셋팅함.
					String loginIDInSession = (String) session.getAttribute(Constants.SESSION_USER_SEQ);
					entity.setLoginIDInSession(loginIDInSession);
					
					service.delete(entity);
					service.insert(entity);

					SensorDataTaggingGetInfoEntity sensorDataTaggingGetInfoEntity = new SensorDataTaggingGetInfoEntity();
					sensorDataTaggingGetInfoEntity.setSensorDataFileSq(entity.getSensorDataFileSq());
					imageTaggingGetInfoService.delete(sensorDataTaggingGetInfoEntity);
					
					Gson gson = new Gson();
					SensorDataJsonFileDescEntity descEntity = gson.fromJson(entity.getSensorDataJsonFileDesc(), SensorDataJsonFileDescEntity.class);
					logger.info("entity.getSensorDataFileSq() = {}", entity.getSensorDataJsonFileDesc());
					logger.info("descEntity = {}", descEntity);
					Map<String, Regions_sd> regions = descEntity.getSensorData().getRegions();
					Iterator<String> iter = regions.keySet().iterator();
					while (iter.hasNext()) {
						String key =  iter.next();
						Regions_sd region = regions.get(key);

						SensorDataTaggingGetInfoEntity taggingGetEntity = new SensorDataTaggingGetInfoEntity();
						taggingGetEntity.setSensorDataFileSq(entity.getSensorDataFileSq());
						taggingGetEntity.setFirstSensorDataTaggingDataDicIdSq(region.getRegion_attributes().getTagging_dic_1_depth_id());
						taggingGetEntity.setSecondSensorDataTaggingDataDicIdSq(region.getRegion_attributes().getTagging_dic_2_depth_id());
						taggingGetEntity.setThirdSensorDataTaggingDataDicIdSq(region.getRegion_attributes().getTagging_dic_3_depth_id());
						if( StringUtils.equals("rect", region.getShape_attributes().getName()) ) {
							taggingGetEntity.setSensorDataTaggingTypeCd("100");
						} else if( StringUtils.equals("polygon", region.getShape_attributes().getName()) ) {
							taggingGetEntity.setSensorDataTaggingTypeCd("200");
						}
						taggingGetEntity.setLoginIDInSession(entity.getLoginIDInSession());
						imageTaggingGetInfoService.insert(taggingGetEntity);
					}
				}

				ret.put("result", "success");
				ret.put("json", entity.toString());
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ret.put("result", "fail");
			ret.put("message", e.getMessage());
		}
		return ret;
	}

	@RequestMapping(value = "getTaggingDicList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getTaggingDicList(@ModelAttribute TaggingDicEntity entity) {
		entity.setUseYn("Y");
		return taggingDicService.getAllList(entity);
	}
}
