package com.lge.crawling.admin.common.util;

import java.awt.Point;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import tv.twelvetone.json.JsonValue;
import tv.twelvetone.rjson.RJsonParserFactory;

public class JsonParsingSampleUtil {

	public static void main(String[] args) {

		String jsonSampleText = "{\"image\":{\"fileref\":\"\",\"size\":1070370,\"filename\":\"FX01_RGB_180830_131829_07.png\",\"base64_img_data\":\"\",\"regions\":{\"0\":{\"shape_attributes\":{\"name\":\"polygon\",\"all_points_x\":[22,56,96,211,259,326,305,203,80,19,22],\"all_points_y\":[399,381,362,369,402,418,476,503,484,482,399]},\"region_attributes\":{\"tagging_dic_1_depth_id\":\"C1000000029\",\"tagging_dic_1_depth_nm\":\"사람/사물\",\"tagging_dic_2_depth_id\":\"C1000000031\",\"tagging_dic_2_depth_nm\":\"사물\",\"tagging_dic_3_depth_id\":\"C1000000034\",\"tagging_dic_3_depth_nm\":\"자동차\"}}}}}";

		try {

			JSONParser jsonParser = new JSONParser();

			// JSON데이터를 넣어 JSON Object 로 만들어 준다.
			JSONObject jsonObjectParent = (JSONObject) jsonParser.parse(jsonSampleText);
			JSONObject jsonObject = (JSONObject) jsonObjectParent.get("image");

			Long sizeCol = (Long) jsonObject.get("size");
			String filenameCol = (String) jsonObject.get("filename");

//			System.out.println("sizeCol: " + sizeCol);
//			System.out.println("filenameCol: " + filenameCol);

			for (int i = 0; i < 100; i++) {
				JSONObject regionsObject = (JSONObject) jsonObject.get("regions");
				JSONObject numberObject = (JSONObject) regionsObject.get("" + i);

//				System.out.println("numberObject: " + numberObject);

				if (numberObject != null) {
					JSONObject shapeObject = (JSONObject) numberObject.get("shape_attributes");

//					Long xCol = (Long) shapeObject.get("x");
//					Long yCol = (Long) shapeObject.get("y");
//
//					System.out.println("xCol: " + xCol);
//					System.out.println("yCol: " + yCol);
					
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
					convertPoint = MatrixUtil.calcTransformTest(taggingPoint, "300"); // simul data
					
					int diffX = arrX[0] - convertPoint.x;
					int diffY = arrY[0] - convertPoint.y;
					
					// polygon의 x, y 좌표값 들을 변경
					for(int k=0; k < arrX.length; k++) {
						convArrX[k] = arrX[k] - diffX;
						convArrY[k] = arrY[k] - diffY;
						xPointsConv.add(k, convArrX[k]);
						yPointsConv.add(k, convArrY[k]);
					}

					
					System.out.println("xPoints: " + xPoints);
					System.out.println("yPoints: " + yPoints);
					System.out.println("xPointsConv: " + xPointsConv);
					System.out.println("yPointsConv: " + yPointsConv);
					
					System.out.println("arrX[0]: " + arrX[0]);
					System.out.println("arrY[0]: " + arrY[0]);
					System.out.println("convX: " + convertPoint.x);
					System.out.println("convY: " + convertPoint.y);
					System.out.println("diffX: " + diffX);
					System.out.println("diffY: " + diffY);

					System.out.println("before: " + jsonSampleText);
					String afterJsonText = getUpdateJsonStr(jsonSampleText, xPointsConv, yPointsConv);
					System.out.println("after: " + afterJsonText);
					
				} else {
					break;
				}
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		// String jsonSampleText =
		// "{\"image\":{\"fileref\":\"\",\"size\":1069542,\"filename\":\"FX01_RGB_180830_131829_08.png\",\"base64_img_data\":\"\",\"regions\":{\"0\":{\"shape_attributes\":{\"name\":\"rect\",\"x\":10,\"y\":351,\"width\":308,\"height\":166},\"region_attributes\":{\"tagging_dic_1_depth_id\":\"C1000000029\",\"tagging_dic_1_depth_nm\":\"사람/사물\",\"tagging_dic_2_depth_id\":\"C1000000033\",\"tagging_dic_2_depth_nm\":\"사물\",\"tagging_dic_3_depth_id\":\"C1000000034\",\"tagging_dic_3_depth_nm\":\"자동차\"}},\"1\":{\"shape_attributes\":{\"name\":\"rect\",\"x\":1114,\"y\":251,\"width\":123,\"height\":249},\"region_attributes\":{\"tagging_dic_1_depth_id\":\"C1000000029\",\"tagging_dic_1_depth_nm\":\"사람/사물\",\"tagging_dic_2_depth_id\":\"C1000000030\",\"tagging_dic_2_depth_nm\":\"사람\",\"tagging_dic_3_depth_id\":\"C1000000031\",\"tagging_dic_3_depth_nm\":\"남자\"}}}}}";
		//// String jsonSampleText =
		// "{\"image\":{\"fileref\":\"\",\"size\":1070370,\"filename\":\"FX01_RGB_180830_131829_07.png\",\"base64_img_data\":\"\",\"regions\":{\"0\":{\"shape_attributes\":{\"name\":\"rect\",\"x\":1122,\"y\":262,\"width\":101,\"height\":227},\"region_attributes\":{\"tagging_dic_1_depth_id\":\"C1000000029\",\"tagging_dic_1_depth_nm\":\"사람/사물\",\"tagging_dic_2_depth_id\":\"C1000000030\",\"tagging_dic_2_depth_nm\":\"사람\",\"tagging_dic_3_depth_id\":\"C1000000031\",\"tagging_dic_3_depth_nm\":\"남자\"}}}}}";
		//
		// try {
		//
		// JSONParser jsonParser = new JSONParser();
		//
		// //JSON데이터를 넣어 JSON Object 로 만들어 준다.
		// JSONObject jsonObjectParent = (JSONObject) jsonParser.parse(jsonSampleText);
		// JSONObject jsonObject = (JSONObject) jsonObjectParent.get("image");
		//
		// Long sizeCol = (Long) jsonObject.get("size");
		// String filenameCol = (String) jsonObject.get("filename");
		//
		// System.out.println("sizeCol: " + sizeCol);
		// System.out.println("filenameCol: " + filenameCol);
		//
		// for(int i=0; i<100; i++) {
		// JSONObject regionsObject = (JSONObject) jsonObject.get("regions");
		// JSONObject numberObject = (JSONObject) regionsObject.get(""+i);
		//
		// System.out.println("numberObject: "+numberObject);
		//
		// if(numberObject != null) {
		// JSONObject shapeObject = (JSONObject) numberObject.get("shape_attributes");
		//
		// Long xCol = (Long) shapeObject.get("x");
		// Long yCol = (Long) shapeObject.get("y");
		//
		// System.out.println("xCol: " + xCol);
		// System.out.println("yCol: " + yCol);
		// } else {
		// break;
		// }
		// }
		//
		//// System.out.println("before: "+jsonObjectParent);
		// System.out.println("before: "+jsonSampleText);
		// String afterJsonText = getUpdateJsonStr(jsonSampleText, "gintoki.png", 9999L,
		// 640L, 480L);
		// System.out.println("after: "+afterJsonText);
		//
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
	}

	public static String getUpdateJsonStr(String jsonText, String filename, Long size, Long x, Long y) {

		try {

			JSONParser jsonParser = new JSONParser();

			// JSON데이터를 넣어 JSON Object 로 만들어 준다.
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
	
	public static String getUpdateJsonStr(String jsonText, JSONArray xArr, JSONArray yArr) {

		try {

			JSONParser jsonParser = new JSONParser();

			// JSON데이터를 넣어 JSON Object 로 만들어 준다.
			JSONObject jsonObjectParent = (JSONObject) jsonParser.parse(jsonText);
			JSONObject jsonObject = (JSONObject) jsonObjectParent.get("image");

			JSONObject regionsObject = (JSONObject) jsonObject.get("regions");
			JSONObject numberObject = (JSONObject) regionsObject.get("0");
			JSONObject shapeObject = (JSONObject) numberObject.get("shape_attributes");

			shapeObject.put("all_points_x", xArr);
			shapeObject.put("all_points_y", yArr);

			return jsonObjectParent.toJSONString();

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "";
	}

}
