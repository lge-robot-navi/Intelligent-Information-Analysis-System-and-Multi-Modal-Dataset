package com.lge.crawling.admin.management.image.controller;

import com.google.gson.Gson;
import com.lge.crawling.admin.common.util.DateSupportUtil;
import com.lge.crawling.admin.constants.TilesSuffix;
import com.lge.crawling.admin.management.image.entity.ImageTaggingEntity;
import com.lge.crawling.admin.management.image.entity.ImageTaggingGetInfoEntity;
import com.lge.crawling.admin.management.image.service.ImageTaggingGetInfoService;
import com.lge.crawling.admin.management.image.service.ImageTaggingService;
import com.lge.crawling.admin.management.imageInfo.entity.ImageJsonFileDescEntity;
import com.lge.crawling.admin.management.imageInfo.entity.Regions;
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

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 이미지 태깅 Controller
 * @version : 1.0
 */
@Controller
@RequestMapping("/image/tagging")
public class ImageTaggingController {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(ImageTaggingController.class);
	
	private final String PREFIX = "image/tagging/";

	@Autowired
	private ImageTaggingService service;

	@Autowired
	private TaggingDicService taggingDicService;

	@Autowired
	private ImageTaggingGetInfoService imageTaggingGetInfoService;

	/**
	 * 이미지 태깅 정보 List 를 조회한다.
	 * @Mehtod Name : getList
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"", "list"})
	public String list(@ModelAttribute ImageTaggingEntity entity, Model model) {

		if (StringUtils.isEmpty(entity.getStartDt()) && StringUtils.isEmpty(entity.getEndDt())) {
			entity.setStartDt(DateSupportUtil.getDefaultStart());
			entity.setEndDt(DateSupportUtil.getDefaultEnd());
		}

		entity.setPageSize(10);
		model.addAttribute("list", service.getList(entity));
		model.addAttribute("paging", entity.getPaging());

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
	public @ResponseBody Object add(@ModelAttribute ImageTaggingEntity entity, BindingResult bindingResult) {
		Map<String, String> ret = new HashMap<>();

		// Validator Error
		if (bindingResult.hasErrors()) {
		}

		try {
			ImageTaggingEntity search = new ImageTaggingEntity();
			search.setImageFileSq(entity.getImageFileSq());
			search = service.get(search);
			if (search != null) {
				entity.setImageJsonFileDesc(HtmlUtils.htmlUnescape(URLDecoder.decode(entity.getImageJsonFileDesc(), "UTF-8")));
				JSONObject json = new JSONObject();
				json.put("annotation", new JSONObject(entity.getImageJsonFileDesc()));
				entity.setImageJsonXmlConvFileDesc(XML.toString(json));
				if (StringUtils.isNotEmpty(search.getImageJsonFileDesc())) {
					service.update(entity);
				} else {
					service.insert(entity);
				}

				ImageTaggingGetInfoEntity imageTaggingGetInfoEntity = new ImageTaggingGetInfoEntity();
				imageTaggingGetInfoEntity.setImageFileSq(entity.getImageFileSq());
				imageTaggingGetInfoService.delete(imageTaggingGetInfoEntity);
				Gson gson = new Gson();
				ImageJsonFileDescEntity descEntity = gson.fromJson(entity.getImageJsonFileDesc(), ImageJsonFileDescEntity.class);
				logger.info("entity.getImageFileSq() = {}", entity.getImageJsonFileDesc());
				logger.info("descEntity = {}", descEntity);
				Map<String, Regions> regions = descEntity.getImage().getRegions();
				Iterator<String> iter = regions.keySet().iterator();
				while (iter.hasNext()) {
					String key =  iter.next();
					Regions region = regions.get(key);

					ImageTaggingGetInfoEntity taggingGetEntity = new ImageTaggingGetInfoEntity();
					taggingGetEntity.setImageFileSq(entity.getImageFileSq());
					taggingGetEntity.setFirstImageTaggingDataDicIdSq(region.getRegion_attributes().getTagging_dic_1_depth_id());
					taggingGetEntity.setSecondImageTaggingDataDicIdSq(region.getRegion_attributes().getTagging_dic_2_depth_id());
					taggingGetEntity.setThirdImageTaggingDataDicIdSq(region.getRegion_attributes().getTagging_dic_3_depth_id());
					if( StringUtils.equals("rect", region.getShape_attributes().getName()) ) {
						taggingGetEntity.setImageTaggingTypeCd("100");
					} else if( StringUtils.equals("polygon", region.getShape_attributes().getName()) ) {
						taggingGetEntity.setImageTaggingTypeCd("200");
					}
					taggingGetEntity.setLoginIDInSession(entity.getLoginIDInSession());
					imageTaggingGetInfoService.insert(taggingGetEntity);
				}
			}

			ret.put("result", "success");
			ret.put("json", entity.toString());
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
