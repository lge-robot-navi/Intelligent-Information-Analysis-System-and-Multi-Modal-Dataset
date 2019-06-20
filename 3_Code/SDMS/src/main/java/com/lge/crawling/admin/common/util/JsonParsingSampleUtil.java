package com.lge.crawling.admin.common.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonParsingSampleUtil {

	public static void main(String[] args) {
		
//		String jsonSampleText = "{\"image\":{\"fileref\":\"\",\"size\":1069542,\"filename\":\"FX01_RGB_180830_131829_08.png\",\"base64_img_data\":\"\",\"regions\":{\"0\":{\"shape_attributes\":{\"name\":\"rect\",\"x\":10,\"y\":351,\"width\":308,\"height\":166},\"region_attributes\":{\"tagging_dic_1_depth_id\":\"C1000000029\",\"tagging_dic_1_depth_nm\":\"사람/사물\",\"tagging_dic_2_depth_id\":\"C1000000033\",\"tagging_dic_2_depth_nm\":\"사물\",\"tagging_dic_3_depth_id\":\"C1000000034\",\"tagging_dic_3_depth_nm\":\"자동차\"}},\"1\":{\"shape_attributes\":{\"name\":\"rect\",\"x\":1114,\"y\":251,\"width\":123,\"height\":249},\"region_attributes\":{\"tagging_dic_1_depth_id\":\"C1000000029\",\"tagging_dic_1_depth_nm\":\"사람/사물\",\"tagging_dic_2_depth_id\":\"C1000000030\",\"tagging_dic_2_depth_nm\":\"사람\",\"tagging_dic_3_depth_id\":\"C1000000031\",\"tagging_dic_3_depth_nm\":\"남자\"}}}}}";
		String jsonSampleText = "{\"image\":{\"fileref\":\"\",\"size\":1070370,\"filename\":\"FX01_RGB_180830_131829_07.png\",\"base64_img_data\":\"\",\"regions\":{\"0\":{\"shape_attributes\":{\"name\":\"rect\",\"x\":1122,\"y\":262,\"width\":101,\"height\":227},\"region_attributes\":{\"tagging_dic_1_depth_id\":\"C1000000029\",\"tagging_dic_1_depth_nm\":\"사람/사물\",\"tagging_dic_2_depth_id\":\"C1000000030\",\"tagging_dic_2_depth_nm\":\"사람\",\"tagging_dic_3_depth_id\":\"C1000000031\",\"tagging_dic_3_depth_nm\":\"남자\"}}}}}";
		
		try {
			 
			JSONParser jsonParser = new JSONParser();
             
            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObjectParent = (JSONObject) jsonParser.parse(jsonSampleText);
            JSONObject jsonObject = (JSONObject) jsonObjectParent.get("image");
            
            Long sizeCol = (Long) jsonObject.get("size");
            String filenameCol = (String) jsonObject.get("filename");
            
            System.out.println("sizeCol: " + sizeCol);
            System.out.println("filenameCol: " + filenameCol);
            
            JSONObject regionsObject = (JSONObject) jsonObject.get("regions");
            JSONObject numberObject = (JSONObject) regionsObject.get("0");
            JSONObject shapeObject = (JSONObject) numberObject.get("shape_attributes");
            
            Long xCol = (Long) shapeObject.get("x");
            Long yCol = (Long) shapeObject.get("y");
            
            System.out.println("xCol: " + xCol);
            System.out.println("yCol: " + yCol);
            
//            System.out.println("before: "+jsonObjectParent);
            System.out.println("before: "+jsonSampleText);
            String afterJsonText = getUpdateJsonStr(jsonSampleText, "gintoki.png", 9999L, 640L, 480L);
            System.out.println("after: "+afterJsonText);
            
		} catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	
	public static String getUpdateJsonStr(String jsonText, String filename, Long size, Long x, Long y) {
		
		try {
			 
			JSONParser jsonParser = new JSONParser();
             
            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObjectParent = (JSONObject) jsonParser.parse(jsonText);
            JSONObject jsonObject = (JSONObject) jsonObjectParent.get("image");
            
            JSONObject regionsObject = (JSONObject) jsonObject.get("regions");
            JSONObject numberObject = (JSONObject) regionsObject.get("0");
            JSONObject shapeObject = (JSONObject) numberObject.get("shape_attributes");
            
            jsonObject.put("filename", filename);
            jsonObject.put("size", size);
            shapeObject.put("x", x);
            shapeObject.put("y", y);
            
            return jsonObjectParent.toJSONString();
            
		} catch (ParseException e) {
            e.printStackTrace();
        }
		
		return "";
	}
	
}
