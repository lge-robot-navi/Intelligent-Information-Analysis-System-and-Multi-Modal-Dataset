package com.lge.mams.mqtt.envmap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 이미지를 생성하는 클래스.
 */
@Component
public class MqttEnvMapImageBuilder {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MqttEnvMapManager envmap;

	@Autowired
	ServletContext sc;

	public BufferedImage build(MqttImgReqInfo info) throws IOException {
		BufferedImage img = new BufferedImage(info.width, info.height, BufferedImage.TYPE_INT_ARGB);
		// Grab the graphics object off the image
		Graphics2D graphics = img.createGraphics();
		Color color;
		color = new Color(255, 255, 255);
		graphics.fill(new Rectangle(0, 0, info.width, info.height));

		String imgpath = "";
		BufferedImage rcvimg = null;
		if ("ph".equals(info.location)) {
			imgpath = sc.getRealPath("/resources/images/pohang-map.jpg");
			logger.debug("imagepath : {}", imgpath);
			drawBackground(imgpath, graphics, info);

			if (info.mapsearch) {
				rcvimg = envmap.ph().search.getRGBImage();
			} else if (info.mapheight) {
				rcvimg = envmap.ph().height.getRGBImage();
			} else if (info.maptemperature) {
				rcvimg = envmap.ph().temperature.getRGBImage();
			} else if (info.mapheightprob) {
				rcvimg = envmap.ph().height_probability.get1ByteARGBImage(128, 0);
			} else if (info.mapobjprob) {
				rcvimg = envmap.ph().object_probability.get1ByteARGBImage(128, 120);
			}
		} else if ("gw".equals(info.location)) {
			imgpath = sc.getRealPath("/resources/images/gwangju-map.jpg");
			logger.debug("imagepath : {}", imgpath);
			drawBackground(imgpath, graphics, info);

			if (info.mapsearch) {
				rcvimg = envmap.gw().search.getRGBImage();
			} else if (info.mapheight) {
				rcvimg = envmap.gw().height.getRGBImage();
			} else if (info.maptemperature) {
				rcvimg = envmap.gw().temperature.getRGBImage();
			} else if (info.mapheightprob) {
				rcvimg = envmap.gw().height_probability.get1ByteARGBImage(128, 0);
			} else if (info.mapobjprob) {
				rcvimg = envmap.gw().object_probability.get1ByteARGBImage(128, 120);
			}
		}
		if (rcvimg != null) {
			graphics.drawImage(rcvimg, 0, 0, info.width, info.height, null);
		}

//		color = new Color(250, 50, 50, 50);
//		Stroke stroke = new BasicStroke(1f);
//
//		// graphics.setStroke(stroke);
//		graphics.setPaint(color);
//
//		graphics.fill(new Rectangle(0, 0, 100, 10));

		graphics.dispose();
		return img;
	}

	private void drawBackground(String path, Graphics2D graphics, MqttImgReqInfo info) {
		BufferedImage img = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				logger.warn("file not exists.");
				return;
			}
			img = ImageIO.read(file);

			graphics.drawImage(img, 0, 0, info.width, info.height, null);
		} catch (IOException e) {
			logger.error("E", e);
		}
	}

}
