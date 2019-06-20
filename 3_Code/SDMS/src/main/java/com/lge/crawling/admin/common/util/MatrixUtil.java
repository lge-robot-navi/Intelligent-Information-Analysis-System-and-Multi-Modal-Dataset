package com.lge.crawling.admin.common.util;

import java.awt.Point;

public class MatrixUtil {

	public static void main(String[] args) {
		
		int x = 1108;
		int y = 255;
		int[] depthArray = {1,2,3,4,5,6,7,8,9};
		
		Point depthPoint = calcTransform(new Point(x,y), depthArray);
		
		System.out.println("x: "+depthPoint.x);
		System.out.println("y: "+depthPoint.y);
	}
	
	
	public static Point calcTransform(Point pin, int[] h) {
		Point pout = new Point(0,0);
		
		pout.x = (h[0] * pin.x + h[1] * pin.y + h[2]) / (h[6] * pin.x + h[7] * pin.y + h[8]);
		pout.y = (h[3] * pin.x + h[4] * pin.y + h[5]) / (h[6] * pin.x + h[7] * pin.y + h[8]);
		
		return pout;
	}
	
	// 3x3 매트릭스 변환
	public static Point calcTransform(Point pin, String type) {
		Point pout = new Point(0,0);
		
		int[] arr = new int[9];
		if("100".equals(type)) {
			return pin;
		} else if("200".equals(type)) {
			arr = new int[]{0,1,10,1,1,15,0,0,1};
		} else if("300".equals(type)) {
			arr = new int[]{0,0,20,1,1,10,0,0,1};
		} else if("400".equals(type)) {
			arr = new int[]{0,0,20,1,-1,-82,0,0,-1};
		} else if("500".equals(type)) {
			arr = new int[]{0,2,1,1,0,1,0,0,1};
		}
		
		pout.x = (arr[0] * pin.x + arr[1] * pin.y + arr[2]) / (arr[6] * pin.x + arr[7] * pin.y + arr[8]);
		pout.y = (arr[3] * pin.x + arr[4] * pin.y + arr[5]) / (arr[6] * pin.x + arr[7] * pin.y + arr[8]);
		
		return pout;
	}
}
