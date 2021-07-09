package com.lge.mams.mqtt.envmap;

import java.awt.Rectangle;

public class MqttImgUtil {

	/**
	 * 170 x 140
	 */
	public Rectangle getRec(int width, int height, int xpos, int ypos) {
		// 170 x 140
		double w = width;
		double h = height;
		double x = xpos;
		double y = ypos;
		double xstep = w / 170d;
		double ystep = h / 140d;

		Rectangle r = new Rectangle();
		r.width = (int) xstep;
		r.height = (int) ystep;
		r.x = (int) (xstep * x);
		r.y = (int) (ystep * y);
		return r;
	}
}
