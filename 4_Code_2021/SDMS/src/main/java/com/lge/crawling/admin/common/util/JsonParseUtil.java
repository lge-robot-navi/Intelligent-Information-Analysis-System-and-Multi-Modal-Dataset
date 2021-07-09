package com.lge.crawling.admin.common.util;

import java.awt.Point;

import org.json.simple.JSONArray;
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
	
	public static String getUpdateJsonStr(int i, String jsonText, Long x, Long y, int w, int h) {
		
		try {
			 
			JSONParser jsonParser = new JSONParser();
             
            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObjectParent = (JSONObject) jsonParser.parse(jsonText);
            JSONObject jsonObject = (JSONObject) jsonObjectParent.get("image");
            
            JSONObject regionsObject = (JSONObject) jsonObject.get("regions");
            JSONObject numberObject = (JSONObject) regionsObject.get(""+i);
            JSONObject shapeObject = (JSONObject) numberObject.get("shape_attributes");
            
            shapeObject.put("x", x);
            shapeObject.put("y", y);
            shapeObject.put("width", w);
            shapeObject.put("height", h);
            
            return jsonObjectParent.toJSONString();
            
		} catch (ParseException e) {
            e.printStackTrace();
        }
		
		return "";
	}
	
	public static String getUpdateJsonStr(int i, String jsonText, JSONArray xArr, JSONArray yArr) {
		
		try {
			 
			JSONParser jsonParser = new JSONParser();
             
            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObjectParent = (JSONObject) jsonParser.parse(jsonText);
            JSONObject jsonObject = (JSONObject) jsonObjectParent.get("image");
            
            JSONObject regionsObject = (JSONObject) jsonObject.get("regions");
            JSONObject numberObject = (JSONObject) regionsObject.get(""+i);
            JSONObject shapeObject = (JSONObject) numberObject.get("shape_attributes");
            
            shapeObject.put("all_points_x", xArr);
			shapeObject.put("all_points_y", yArr);
            
            return jsonObjectParent.toJSONString();
            
		} catch (ParseException e) {
            e.printStackTrace();
        }
		
		return "";
	}
	
	public static String getShapeType(String jsonText) {
		String name = "";
		
		try {
			 
			JSONParser jsonParser = new JSONParser();
             
            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObjectParent = (JSONObject) jsonParser.parse(jsonText);
            JSONObject jsonObject = (JSONObject) jsonObjectParent.get("image");
            
            JSONObject regionsObject = (JSONObject) jsonObject.get("regions");
            JSONObject numberObject = (JSONObject) regionsObject.get("0");
            JSONObject shapeObject = (JSONObject) numberObject.get("shape_attributes");
            
            name = (String) shapeObject.get("name");
            
		} catch (ParseException e) {
            e.printStackTrace();
        }
		
		return name;
	}
	
	public static Point convScaleFromJson(String jsonText, int scaleX, int scaleY) {
		Point pout = new Point(0,0);
		
		try {
			 
			JSONParser jsonParser = new JSONParser();
             
            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObjectParent = (JSONObject) jsonParser.parse(jsonText);
            JSONObject jsonObject = (JSONObject) jsonObjectParent.get("image");
            
            JSONObject regionsObject = (JSONObject) jsonObject.get("regions");
            JSONObject numberObject = (JSONObject) regionsObject.get("0");
            JSONObject shapeObject = (JSONObject) numberObject.get("shape_attributes");
            
            Long wCol = (Long) shapeObject.get("width");
            Long hCol = (Long) shapeObject.get("height");
            
            wCol = (scaleX*wCol)/1280;
        	hCol = (scaleY*hCol)/720;
            
            pout.x = wCol.intValue();
            pout.y = hCol.intValue();
            
		} catch (ParseException e) {
            e.printStackTrace();
        }
		
		return pout;
	}
}
