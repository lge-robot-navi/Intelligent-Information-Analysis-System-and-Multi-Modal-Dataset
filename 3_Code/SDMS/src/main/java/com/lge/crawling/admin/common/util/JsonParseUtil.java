package com.lge.crawling.admin.common.util;

import java.awt.Point;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonParseUtil {

	public static Point getPointFromJson(String jsonText) {
		Point pout = new Point(0,0);
		
		try {
			 
			JSONParser jsonParser = new JSONParser();
             
            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObjectParent = (JSONObject) jsonParser.parse(jsonText);
            JSONObject jsonObject = (JSONObject) jsonObjectParent.get("image");
            
            JSONObject regionsObject = (JSONObject) jsonObject.get("regions");
            JSONObject numberObject = (JSONObject) regionsObject.get("0");
            JSONObject shapeObject = (JSONObject) numberObject.get("shape_attributes");
            
            Long xCol = (Long) shapeObject.get("x");
            Long yCol = (Long) shapeObject.get("y");
            
            pout.x = xCol.intValue();
            pout.y = yCol.intValue();
            
		} catch (ParseException e) {
            e.printStackTrace();
        }
		
		return pout;
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
