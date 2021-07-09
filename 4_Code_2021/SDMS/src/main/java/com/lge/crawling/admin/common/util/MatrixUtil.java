package com.lge.crawling.admin.common.util;

import java.awt.Point;

public class MatrixUtil {

	public static void main(String[] args) {
		
		int x = 1108;
		int y = 255;
//		int[] depthArray = {1,2,3,4,5,6,7,8,9};
//		int[] depthArray = {0,0,20,1,1,10,0,0,1};
		int[] depthArray = {1,0,0,0,1,0,0,0,1};
		
		Point depthPoint = calcTransform(new Point(x,y), depthArray);
		
		System.out.println("x: "+depthPoint.x);
		System.out.println("y: "+depthPoint.y);
	}
	
	// 3x3 매트릭스 변환
	public static Point calcTransform(Point pin, int[] h) {
		Point pout = new Point(0,0);
		
		pout.x = (h[0] * pin.x + h[1] * pin.y + h[2]) / (h[6] * pin.x + h[7] * pin.y + h[8]);
		pout.y = (h[3] * pin.x + h[4] * pin.y + h[5]) / (h[6] * pin.x + h[7] * pin.y + h[8]);
		
		return pout;
	}
	
	public static Point calcTransform(String agent, Point pin, String type) {			
		Point pout = new Point(0,0);
		
		// TODO: Agent별, 이미지Type별 9개의 정수 배열값을 받아옴
		int[] arr = new int[9];
		if("100".equals(type)) {
			return pin;
		} else if("200".equals(type)) {
			arr = new int[]{0,0,10,1,1,10,0,0,1}; // TODO: 변환값 받으면 입력(정수값)
		} else if("300".equals(type)) {
			arr = new int[]{0,0,20,1,1,10,0,0,1}; // TODO: 변환값 받으면 입력(정수값)
		} else if("400".equals(type)) {
			arr = new int[]{0,0,20,1,1,10,0,0,1}; // TODO: 변환값 받으면 입력(정수값)
		} else if("500".equals(type)) {
			arr = new int[]{0,2,1,1,0,1,0,0,1}; // TODO: 변환값 받으면 입력(정수값)
		}
		
		pout.x = (arr[0] * pin.x + arr[1] * pin.y + arr[2]) / (arr[6] * pin.x + arr[7] * pin.y + arr[8]);
		pout.y = (arr[3] * pin.x + arr[4] * pin.y + arr[5]) / (arr[6] * pin.x + arr[7] * pin.y + arr[8]);
		
		return pout;
	}
	
	// 시뮬데이터 반환
	public static Point calcTransformTest(Point pin, String type) {
		
		if("100".equals(type)) {
    		pin.x = pin.x;
        	pin.y = pin.y;
		} else if("200".equals(type)) {
			if(pin.x < 640) {
				pin.x = pin.x + 5;
			} else {
				pin.x = pin.x - 5;
			}
		} else if("300".equals(type)) {
			if(pin.x < 640) {
				pin.x = pin.x + 81;
				pin.y = pin.y - 88;
			} else {
				pin.x = pin.x - 578;
				pin.y = pin.y - 47;
			}
		} else if("400".equals(type)) {
			if(pin.x < 640) {
				pin.x = pin.x + 67;
				pin.y = pin.y - 86;
			} else {
				pin.x = pin.x - 590;
				pin.y = pin.y - 40;
			}
		} else if("500".equals(type)) {
			if(pin.x < 640) {
				pin.x = pin.x + 32;
				pin.y = pin.y - 136;
			} else {
				pin.x = pin.x - 605;
				pin.y = pin.y - 100;
			}
    	}
		
		return pin;
	}
}
