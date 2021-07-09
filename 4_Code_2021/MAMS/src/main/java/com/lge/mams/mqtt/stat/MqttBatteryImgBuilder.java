package com.lge.mams.mqtt.stat;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqttBatteryImgBuilder {

	@Autowired
	ServletContext sc;

	public BufferedImage build(Integer batteryValue) throws IOException {
		String imgpath = sc.getRealPath("/resources/images/ic_battery.png");
		File batFile = new File(imgpath);
		BufferedImage imgBat = null;
		imgBat = ImageIO.read(batFile);

		BufferedImage img = new BufferedImage(imgBat.getWidth(), imgBat.getHeight(), BufferedImage.TYPE_INT_ARGB);
		// Grab the graphics object off the image
		Graphics2D graphics = img.createGraphics();
		Color color;
		color = new Color(255, 255, 255);
		// graphics.fill(new Rectangle(0,0,info.width,info.height));

		color = new Color(250, 50, 50, 50);
		Stroke stroke = new BasicStroke(1f);

		// graphics.setStroke(stroke);
		graphics.setPaint(color);

		graphics.fill(new Rectangle(0, 0, 100, 10));

		graphics.dispose();
		return img;
	}
}
