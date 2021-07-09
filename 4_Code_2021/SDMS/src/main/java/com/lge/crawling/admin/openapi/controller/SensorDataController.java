package com.lge.crawling.admin.openapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lge.crawling.admin.common.web.controller.BaseController;
import com.lge.crawling.admin.openapi.entity.SearchEntity;
import com.lge.crawling.admin.openapi.entity.SensorDataEntity;
import com.lge.crawling.admin.openapi.service.SensorDataService;

@Controller
@RequestMapping("/openapi")
public class SensorDataController extends BaseController {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(SensorDataController.class);

	@Autowired private SensorDataService sensorDataService;

	/**
	 * 센서 데이터 목록을 조회한다.
	 * @Mehtod Name : getSensorData
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getSensorData", method=RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public @ResponseBody String getSensorData(
			@RequestParam(value = "agent", required = false, defaultValue = "") String agent, 
			@RequestParam(value = "type", required = false, defaultValue = "") String type, 
			@RequestParam(value = "from", required = false, defaultValue = "") String from, 
			@RequestParam(value = "to", required = false, defaultValue = "") String to, 
			@RequestParam(value = "count", required = false, defaultValue = "20") String count, 
			@RequestParam(value = "pageno", required = false, defaultValue = "1") String pageno) {

		SearchEntity entity = new SearchEntity();
		
		entity.setAgent(agent);
		entity.setType(type);
		entity.setFrom(from);
		entity.setTo(to);
		entity.setCount(Integer.parseInt(count));
		entity.setPageno(Integer.parseInt(pageno));
		
		List<SensorDataEntity> list = sensorDataService.getList(entity);
		int total = sensorDataService.getCount(entity);
		
		String tmpJson = "";
		tmpJson += "{";
		tmpJson += "\"total\": " + total + ", ";
		tmpJson += "\"count\": " + count + ", ";
		tmpJson += "\"pageno\": " + pageno + ", ";
		tmpJson += "\"datas\": [";
		if(list.size() > 0) {
			for(SensorDataEntity lists : list) {
				tmpJson += "{";
				tmpJson += "\"SD_SEQ\": " + lists.getSdSeq() + ", ";
				tmpJson += "\"SD_AGENT\": \"" + lists.getSdAgent() + "\", ";
				tmpJson += "\"SD_TYPE_CD\": \"" + lists.getSdTypeCd() + "\", ";
				tmpJson += "\"SD_NM\": \"" + lists.getSdNm() + "\", ";
				tmpJson += "\"SD_URL\": \"" + lists.getSdUrl() + "\", ";
				tmpJson += "\"SD_SCALE_X\": " + lists.getSdScaleX() + ", ";
				tmpJson += "\"SD_SCALE_Y\": " + lists.getSdScaleY() + ", ";
				tmpJson += "\"SD_SIZE\": " + lists.getSdSize() + ", ";
				tmpJson += "\"SD_CREATE_DT\": \"" + lists.getSdCreateDt() + "\", ";
				tmpJson += "\"SD_REGIST_DT\": \"" + lists.getSdRegistDt() + "\"";
				tmpJson += "},";
			}
			tmpJson = tmpJson.substring(0, tmpJson.length()-1);
		}
		tmpJson += "]";
		tmpJson += "}";
		
		logger.debug("getSensorData");
		
		return tmpJson;
	}

}