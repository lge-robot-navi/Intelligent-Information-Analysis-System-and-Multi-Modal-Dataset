package com.lge.crawling.admin.common.util;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONParserUtil {

	public static String cmdParser(String data) {
		
		try {
			JSONParser jsonParser = new JSONParser();
             
            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObject = (JSONObject) jsonParser.parse(data);
            
            if (jsonObject.get("cmd") == null) 
            	return null;
            
            String cmdCol = jsonObject.get("cmd").toString();
            
            return cmdCol;
            
		} catch (ParseException e) {
			return null;
        } catch (ClassCastException e) {
        	return null;
        }
	}
	
	public static void cmd1001Parser(String data) {
		
		System.out.println("******* JSON data parsing *******");
		try {
			 
			JSONParser jsonParser = new JSONParser();
             
            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObject = (JSONObject) jsonParser.parse(data);
            
            String cmdCol = (String) jsonObject.get("cmd");
            String resultCol = (String) jsonObject.get("result");
            String msgCol = (String) jsonObject.get("msg");
            
            System.out.println("cmd: " + cmdCol);
            System.out.println("result: " + resultCol);
            System.out.println("msg: " + msgCol);
            
            //data의 배열을 추출
            JSONArray datasArray = (JSONArray) jsonObject.get("data");
 
            for(int i=0; i<datasArray.size(); i++){
            	
                System.out.println("[ DATAS_"+i+" ]");
                 
                //배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
                JSONObject datasObject = (JSONObject) datasArray.get(i);
                 
                //JSON name으로 추출
                System.out.println("datas: nic_sn==>"+datasObject.get("nic_sn"));
                System.out.println("datas: nic_nm==>"+datasObject.get("nic_nm"));
                System.out.println("datas: nic_dc==>"+datasObject.get("nic_dc"));
                System.out.println("datas: nic_ips==>"+datasObject.get("nic_ips"));
                System.out.println("datas: nic_mntrg_yn==>"+datasObject.get("nic_mntrg_yn"));
                
            }
            
		} catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	public static void cmd1002Parser(String data) {
		
		System.out.println("******* JSON data parsing *******");
		System.out.println(">>>>>>>>>>> data: " + data);
		try {
			 
			JSONParser jsonParser = new JSONParser();
             
            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObject = (JSONObject) jsonParser.parse(data);
            JSONObject jsonObject2 = (JSONObject) jsonParser.parse(jsonObject.get("data").toString());
            
            String cmdCol = (String) jsonObject.get("cmd");
            String resultCol = (String) jsonObject.get("result");
            String msgCol = (String) jsonObject.get("msg");
            String emsgCol = (String) jsonObject.get("emsg");
            String filenameCol = (String) jsonObject2.get("filename");
            
            System.out.println("cmd: " + cmdCol);
            System.out.println("result: " + resultCol);
            System.out.println("msg: " + msgCol);
            System.out.println("emsg: " + emsgCol);
            System.out.println("filename: " + filenameCol);
            
		} catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	public static void cmd1005Parser(String data) {
		
		System.out.println("******* JSON data parsing *******");
		try {
			 
			JSONParser jsonParser = new JSONParser();
             
            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObject = (JSONObject) jsonParser.parse(data);
            
            String cmdCol = (String) jsonObject.get("cmd");
            String resultCol = (String) jsonObject.get("result");
            String msgCol = (String) jsonObject.get("msg");
            
            System.out.println("cmd: " + cmdCol);
            System.out.println("result: " + resultCol);
            System.out.println("msg: " + msgCol);
            
            //data의 배열을 추출
            JSONArray datasArray = (JSONArray) jsonObject.get("data");
 
            for(int i=0; i<datasArray.size(); i++){
            	
                System.out.println("[ DATAS_"+i+" ]");
                 
                //배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
                JSONObject datasObject = (JSONObject) datasArray.get(i);
                 
                //JSON name으로 추출
                System.out.println("datas: module==>"+datasObject.get("module"));
                System.out.println("datas: stat==>"+datasObject.get("stat"));
            }
            
		} catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	public static Map<String, Object> cmd1011Parser(String data) {
		
//		System.out.println("******* JSON data parsing *******");
//		System.out.println(">>>>>>>>>>> data: " + data);
		try {
			JSONParser jsonParser = new JSONParser();
             
            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObject = (JSONObject) jsonParser.parse(data);
            
            //String cmdCol = (String) jsonObject.get("cmd");
            String resultCol = (String) jsonObject.get("result");
            String msgCol = (String) jsonObject.get("msg");
            String emsgCol = (String) jsonObject.get("emsg");
            
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("RESULT", resultCol);
            map.put("MSG", msgCol);
            map.put("EMSG", emsgCol);
            
            return map;
            
		} catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
	}

	public static Map<String, Object> cmd1012Parser(String data) {
		
//		System.out.println("******* JSON data parsing *******");
//		System.out.println(">>>>>>>>>>> data: " + data);
		try {
			JSONParser jsonParser = new JSONParser();
             
            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObject = (JSONObject) jsonParser.parse(data);
            
            //String cmdCol = (String) jsonObject.get("cmd");
            String resultCol = (String) jsonObject.get("result");
            //String msgCol = (String) jsonObject.get("msg");
            //String emsgCol = (String) jsonObject.get("emsg");
            
//            System.out.println("cmd: " + cmdCol);
//            System.out.println("result: " + resultCol);
//            System.out.println("msg: " + msgCol);
//            System.out.println("emsg: " + emsgCol);
            
            if("0".equals(resultCol)) {
            	JSONObject jsonObject2 = (JSONObject) jsonParser.parse(jsonObject.get("data").toString());
            	String iedNameCol = (String) jsonObject2.get("ied_name");
                String ipAdrrCol = (String) jsonObject2.get("ip_adres");
                String macAddrCol = (String) jsonObject2.get("mac_adres");
                String sclJsonStrCol = (String) jsonObject2.get("scl_json_str");
                
//                System.out.println("iedNameCol: " + iedNameCol);
//                System.out.println("ipAdrrCol: " + ipAdrrCol);
//                System.out.println("macAddrCol: " + macAddrCol);
                
//                TbDev tbDev = new TbDev();
//                tbDev.setDevNm(iedNameCol);
//                tbDev.setDevIp(ipAdrrCol);
//                tbDev.setDevMacAdres(macAddrCol);
//                
//                return tbDev;
                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("IED_NAME", iedNameCol);
                map.put("IP_ADRES", ipAdrrCol);
                map.put("MAC_ADRES", macAddrCol);
                map.put("SCL_JSON_STR", sclJsonStrCol);
                
                return map;
                
            } else {
            	return null;
            }
            
		} catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
	}

	public static void cmd5001Parser(String data) {
		
		System.out.println("******* JSON data parsing *******");
		try {
			 
			JSONParser jsonParser = new JSONParser();
             
            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObject = (JSONObject) jsonParser.parse(data);
            
            String cmdCol = (String) jsonObject.get("cmd");
            String resultCol = (String) jsonObject.get("result");
            String msgCol = (String) jsonObject.get("msg");
            
            System.out.println("cmd: " + cmdCol);
            System.out.println("result: " + resultCol);
            System.out.println("msg: " + msgCol);
            
            //data의 배열을 추출
            JSONArray datasArray = (JSONArray) jsonObject.get("data");
 
            for(int i=0; i<datasArray.size(); i++){
            	
                System.out.println("[ DATAS_"+i+" ]");
                 
                //배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
                JSONObject datasObject = (JSONObject) datasArray.get(i);
                 
                //JSON name으로 추출
                System.out.println("datas: date_time==>"+datasObject.get("date_time"));
                System.out.println("datas: cpu==>"+datasObject.get("cpu"));
                System.out.println("datas: memory==>"+datasObject.get("memory"));
                
                //disk의 배열을 추출
                JSONArray diskArray = (JSONArray) datasObject.get("disk");
                
                for(int j=0; i<diskArray.size(); j++){
                	JSONObject diskObject = (JSONObject) diskArray.get(j);
                	System.out.println("datas: disk-drive==>"+diskObject.get("drive"));
                	System.out.println("datas: disk-usage==>"+diskObject.get("usage"));
                }
                
            }
            
		} catch (ParseException e) {
            e.printStackTrace();
        }
	}
}
