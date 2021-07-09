package com.lge.mams.util;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PosGpsUtil {
	static private Logger logger = LoggerFactory.getLogger(PosGpsUtil.class);

	// 포항.(OLD)
//	public static Pair<Double, Double> phConvPosToGPS(double posX, double posY) {
//		double lat = -0;
//		double lng = 0;
//		double h11 = -0.111429818;
//		double h12 = -0.0853415504;
//		double h13 = 36.1190186;
//		double h21 = -0.399244487;
//		double h22 = -0.305760771;
//		double h23 = 129.415985;
//		double h31 = -0.00308493176;
//		double h32 = -0.00236267666;
//		double h33 = 1.0;
//
//		lat = (h11 * posX + h12 * posY + h13) / (h31 * posX + h32 * posY + h33);
//		lng = (h21 * posX + h22 * posY + h23) / (h31 * posX + h32 * posY + h33);
//
//		return new MutablePair<>(lat, lng);
//	}

	public static Pair<Double, Double> phConvPosToGPS(double posX, double posY) {
		double lat = -0;
		double lng = 0;

		double h11 = -6.7982527038;
		double h12 = -5.9096309158;
		double h13 = 9073.7359567794;
		double h21 = -7.3019538205;
		double h22 = 8.3999369861;
		double h23 = 6059.9926791418;
		double h31 = 0;
		double h32 = 0;
		double h33 = 1.0;

		lat = (h11 * posX + h12 * posY + h13) / 1000000 + 36.11;
		lng = (h21 * posX + h22 * posY + h23) / 1000000 + 129.41;

		return new MutablePair<>(lat, lng);
	}

	// 광주.
	public static Pair<Double, Double> gwConvPosToGPS2(double posX, double posY) {
//		double lat = -0;
//		double lng = 0;
//		double h11 = -0.111429818;
//		double h12 = -0.0853415504;
//		double h13 = 36.1190186;
//		double h21 = -0.399244487;
//		double h22 = -0.305760771;
//		double h23 = 129.415985;
//		double h31 = -0.00308493176;
//		double h32 = -0.00236267666;
//		double h33 = 1.0;
//
//		lat = (h11 * posX + h12 * posY + h13) / (h31 * posX + h32 * posY + h33);
//		lng = (h21 * posX + h22 * posY + h23) / (h31 * posX + h32 * posY + h33);
//
//		return new MutablePair<>(lat, lng);
		double lat = -0;
		double lng = 0;

		double h11 = -6.7982527038;
		double h12 = -5.9096309158;
		double h13 = 9073.7359567794;
		double h21 = -7.3019538205;
		double h22 = 8.3999369861;
		double h23 = 6059.9926791418;
		double h31 = 0;
		double h32 = 0;
		double h33 = 1.0;

		lat = (h11 * posX + h12 * posY + h13) / 1000000 + 35.2443902;
		lng = (h21 * posX + h22 * posY + h23) / 1000000 + 126.8355429;

		return new MutablePair<>(lat, lng);
	}

	public static Pair<Double, Double> gwConvPosToGPS(double posX, double posY) {
		double lat = -0;
		double lng = 0;
		double x = posX;
		double y = posY;
		double latorig = 35.2443902 * Math.PI / 180.0;
		double lngorig = 126.8355429 * Math.PI / 180.0;

		double dist = Math.sqrt(x * x + y * y);
		// double heading = Math.atan2(y, x) - (Math.PI / 2.0);
		double heading = - Math.atan2(y, x);
		double R = 6371000;

		lat = Math.asin(Math.sin(latorig) * Math.cos(dist / R) + Math.cos(latorig) * Math.sin(dist / R) * Math.cos(heading));
		lng = lngorig
				+ Math.atan2(Math.sin(heading) * Math.sin(dist / R) * Math.cos(latorig), Math.cos(dist / R) - Math.sin(latorig) * Math.sin(lat));

		//logger.info("x={}, y={}, lat={}, lng={}", posX, posY, lat * 180.0 / Math.PI, lng * 180.0 / Math.PI);
		return new MutablePair<>(lat * 180.0 / Math.PI, lng * 180.0 / Math.PI);
		// return new MutablePair<>(lng * 180.0 / Math.PI, lat * 180.0/ Math.PI);
	}
}
