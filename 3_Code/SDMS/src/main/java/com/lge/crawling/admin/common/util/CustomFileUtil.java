package com.lge.crawling.admin.common.util;

import java.io.File;

public class CustomFileUtil {

    public static String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        return suffix;
    }
    
    public static String getFilename(String filename) {
        String fileStr = "";
        int pos = filename.indexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
        	fileStr = filename.substring(0, pos);
        }
        return fileStr;
    }
    
    public static void deleteAllFiles(String path){ 
    	File file = new File(path); 
    	//폴더내 파일을 배열로 가져온다. 
    	File[] tempFile = file.listFiles(); 
    		
    	if(tempFile.length > 0){ 
    		for (int i = 0; i < tempFile.length; i++) { 
    			if(tempFile[i].isFile()){ 
    				tempFile[i].delete(); 
    			}else{ 
    				//재귀함수 
    				deleteAllFiles(tempFile[i].getPath()); 
    			} 
    			tempFile[i].delete(); 
    		} 
    		file.delete(); 
    	} 
    }

}
