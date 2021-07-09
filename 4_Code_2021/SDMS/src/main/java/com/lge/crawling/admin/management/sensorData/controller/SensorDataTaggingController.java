package com.lge.crawling.admin.management.sensorData.controller;

import java.awt.Point;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
//import org.json.JSONObject;
import org.json.XML;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

import com.google.gson.Gson;
import com.lge.crawling.admin.common.util.DateSupportUtil;
import com.lge.crawling.admin.common.util.JsonParseUtil;
import com.lge.crawling.admin.common.util.MatrixUtil;
import com.lge.crawling.admin.constants.Constants;
import com.lge.crawling.admin.constants.TilesSuffix;
import com.lge.crawling.admin.management.image.entity.ImageTaggingEntity;
import com.lge.crawling.admin.management.imageTaggingDic.entity.TaggingDicEntity;
import com.lge.crawling.admin.management.imageTaggingDic.service.TaggingDicService;
import com.lge.crawling.admin.management.sensorData.entity.SensorDataTaggingEntity;
import com.lge.crawling.admin.management.sensorData.entity.SensorDataTaggingGetInfoEntity;
import com.lge.crawling.admin.management.sensorData.service.SensorDataTaggingGetInfoService;
import com.lge.crawling.admin.management.sensorData.service.SensorDataTaggingService;
import com.lge.crawling.admin.management.sensorDataInfo.entity.Regions_sd;
import com.lge.crawling.admin.management.sensorDataInfo.entity.SensorDataFileInfoEntity;
import com.lge.crawling.admin.management.sensorDataInfo.entity.SensorDataJsonFileDescEntity;
import com.lge.crawling.admin.management.sensorDataInfo.service.SensorDataFileInfoService;
import com.lge.crawling.admin.management.system.entity.CodeEntity;
import com.lge.crawling.admin.management.system.service.CodeService;

import tv.twelvetone.json.JsonValue;
import tv.twelvetone.rjson.RJsonParserFactory;

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
	
	@Autowired private CodeService codeService;

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
//			logger.debug(">>>>>>>>>>>>>>>>>>>>> getSensorDataFileTypeCd: "+ entity.getSensorDataFileTypeCd());
			
			// 메인센서타입 (저장기준)
			String mainTypeCd = entity.getSensorDataFileTypeCd();
			String agentId = entity.getSensorDataFileAgent();
			
			// TODO: 포항, 광주 구분하여 Agent명 확정 필요함
			// 일단 하드 코딩
			String cdgrpCd = "TA009";
			String codeCd = "100";
			
			// Agent 별 Matrix 정보를 가져옴.
			CodeEntity codeEntity = new CodeEntity();
			codeEntity.setCdgrpCd(cdgrpCd);
			codeEntity.setCodeCd(codeCd);
			codeEntity = codeService.get(codeEntity);
			String codeDs = codeEntity.getCodeDs();
			
			/* JSON 에서 센서타입별 matrix 정보를 가져옴. */ 
			JSONArray rgbArr = null;
			JSONArray depthArr = null;
			JSONArray nv1Arr = null;
			JSONArray nv2Arr = null;
			JSONArray thermalArr = null;
			try {
				// JSON String으로 변환
				JsonValue parsed = new RJsonParserFactory().createParser().stringToValue(codeDs);
				String parsedStr = parsed.toString();

				// JSON데이터를 넣어 JSON Object 로 만듬
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObjectParent = (JSONObject) jsonParser.parse(parsedStr);
				JSONObject jsonObject = (JSONObject) jsonObjectParent.get("matrix");
				
				// JSON Array 값 얻어옴
				rgbArr = (JSONArray) jsonObject.get("rgb");
				depthArr = (JSONArray) jsonObject.get("depth");
				nv1Arr = (JSONArray) jsonObject.get("nv1");
				nv2Arr = (JSONArray) jsonObject.get("nv2");
				thermalArr = (JSONArray) jsonObject.get("thermal");
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
//			logger.debug("********************************** codeDs : "+codeDs);
			
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
					int scaleX = Integer.parseInt(infoEntity.getSensorDataFileScaleX());
					int scaleY = Integer.parseInt(infoEntity.getSensorDataFileScaleY());	
										
//					logger.debug("====================> jsonFileDesc : "+jsonFileDesc);
					
					JSONArray jsonArr = null;
					if("100".equals(fileTypeCd)) {
						jsonArr = rgbArr;
					} else if("200".equals(fileTypeCd)) {
						jsonArr = depthArr;
					} else if("300".equals(fileTypeCd)) {
						jsonArr = nv1Arr;
					} else if("400".equals(fileTypeCd)) {
						jsonArr = nv2Arr;
					} else if("500".equals(fileTypeCd)) {
						jsonArr = thermalArr;
					}

					int[] matArr = new int[9];
					for(int i=0; i<jsonArr.size(); i++) {
						Long matVal = (Long) jsonArr.get(i);
						matArr[i] = Math.toIntExact(matVal);
					}

					try {
						JSONParser jsonParser = new JSONParser();
			             
			            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
						JSONObject jsonObjectParent = (JSONObject) jsonParser.parse(jsonFileDesc);
			            JSONObject jsonObject = (JSONObject) jsonObjectParent.get("image");
			            
			            // set to convert string
			            entity.setSensorDataJsonFileDesc(jsonFileDesc);
			            
			            // regions object가 배열로 저장되지 않아, 아래 로직으로 처리
			            for(int i=0; i<100; i++) {
				            JSONObject regionsObject = (JSONObject) jsonObject.get("regions");
				            JSONObject numberObject = (JSONObject) regionsObject.get(""+i);
				            
				            if(numberObject != null) {
					            JSONObject shapeObject = (JSONObject) numberObject.get("shape_attributes");				            
					            String shapeType = (String) shapeObject.get("name");
								
								// 사각형(rect) 일 경우
								if("rect".equals(shapeType.trim())) {
								
									// JSON 파일에서 x,y 포인트 값을 가져옴
									Point taggingPoint = new Point(0,0);
									Long xCol = (Long) shapeObject.get("x");
						            Long yCol = (Long) shapeObject.get("y");
						            taggingPoint.x = xCol.intValue();
						            taggingPoint.y = yCol.intValue();
						            
									// 이미지타입에 따른 3x3 매트릭스스 변환을 통해 x,y 변환값을 가져옴
						            Point convertPoint = new Point(0,0);
									convertPoint = MatrixUtil.calcTransformTest(taggingPoint, fileTypeCd); // simul data
//						            if("100".equals(fileTypeCd)) {
//						            	convertPoint = taggingPoint;
//						            } else {
//						            	convertPoint = MatrixUtil.calcTransform(taggingPoint, matArr); // real data
//						            }
									
									// 이미지크기에 따라 width, height 변환값을 가져옴
						            // TODO: scale 변경은 나중에 지울 것.
									Point covertScale = new Point(0,0);
									Long wCol = (Long) shapeObject.get("width");
						            Long hCol = (Long) shapeObject.get("height");
						            wCol = (scaleX*wCol)/1280;
						        	hCol = (scaleY*hCol)/720;
						        	covertScale.x = wCol.intValue();
						        	covertScale.y = hCol.intValue();
									
									logger.debug("====================> fileType : "+fileTypeCd+", taggingPoint: "+taggingPoint+", convertPoint: "+convertPoint+", covertScale: "+covertScale);
									
									// JSON 데이터 업데이트 (x, y, width, height)
									String convertJsonFileDesc = JsonParseUtil.getUpdateJsonStr(i, entity.getSensorDataJsonFileDesc(), Long.valueOf(convertPoint.x), Long.valueOf(convertPoint.y), covertScale.x, covertScale.y);
									entity.setSensorDataJsonFileDesc(convertJsonFileDesc);
									
								// 다각형(polygon) 일 경우
								} else {
									
//									"shape_attributes": {
//										"name": "polygon",
//										"all_points_x": [22,56,96,211,259,326,305,203,80,19,22],
//										"all_points_y": [399,381,362,369,402,418,476,503,484,482,399]
//									}
									JSONArray xPoints = (JSONArray) shapeObject.get("all_points_x");
									JSONArray yPoints = (JSONArray) shapeObject.get("all_points_y");
									JSONArray xPointsConv = new JSONArray();
									JSONArray yPointsConv = new JSONArray();
									
									int[] arrX = new int[xPoints.size()];
									int[] arrY = new int[yPoints.size()];
									int[] convArrX = new int[xPoints.size()];
									int[] convArrY = new int[yPoints.size()];
									
									for(int k=0; k<xPoints.size(); k++) {
										Long matVal = (Long) xPoints.get(k);
										arrX[k] = Math.toIntExact(matVal);
									}
									for(int k=0; k<yPoints.size(); k++) {
										Long matVal = (Long) yPoints.get(k);
										arrY[k] = Math.toIntExact(matVal);
									}
									
									// JSON 파일에서 x,y 포인트 값을 가져옴
									Point taggingPoint = new Point(0,0);
						            taggingPoint.x = arrX[0];
						            taggingPoint.y = arrY[0];
						            
						            // 이미지타입에 따른 3x3 매트릭스스 변환을 통해 x,y 변환값을 가져옴
						            Point convertPoint = new Point(0,0);
									convertPoint = MatrixUtil.calcTransformTest(taggingPoint, fileTypeCd); // simul data
//						            if("100".equals(fileTypeCd)) {
//						            	convertPoint = taggingPoint;
//						            } else {
//						            	convertPoint = MatrixUtil.calcTransform(taggingPoint, matArr); // real data
//						            }
									
									// x, y matrix 변환 차이 값
									int diffX = arrX[0] - convertPoint.x;
									int diffY = arrY[0] - convertPoint.y;
									
									// polygon의 x, y 좌표값 들을 변경
									for(int k=0; k < arrX.length; k++) {
										convArrX[k] = arrX[k] - diffX;
										convArrY[k] = arrY[k] - diffY;
										xPointsConv.add(k, convArrX[k]);
										yPointsConv.add(k, convArrY[k]);
									}
									
									// JSON 데이터 업데이트 (x points, y points)
									String convertJsonFileDesc = JsonParseUtil.getUpdateJsonStr(i, entity.getSensorDataJsonFileDesc(), xPointsConv, yPointsConv);
									entity.setSensorDataJsonFileDesc(convertJsonFileDesc);
//									entity.setSensorDataJsonFileDesc(HtmlUtils.htmlUnescape(URLDecoder.decode(entity.getSensorDataJsonFileDesc(), "UTF-8")));
								}
								
				            } else {
				            	break;
				            }
			            }

					} catch (ParseException e) {
			            e.printStackTrace();
			        }
					
					
					JSONObject json = new JSONObject();
//					json.put("annotation", new JSONObject(entity.getSensorDataJsonFileDesc())); // org.json.JSONObject
					json.put("annotation", entity.getSensorDataJsonFileDesc()); // org.json.simple.JSONObject
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
