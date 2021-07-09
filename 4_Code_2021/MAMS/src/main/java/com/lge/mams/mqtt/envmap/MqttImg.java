package com.lge.mams.mqtt.envmap;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttImg {
	private Logger logger = LoggerFactory.getLogger(getClass());
	public Integer timestamp; // 4
	public Integer width; // 2
	public Integer height; // 2
	public Integer channel; // 2

	public byte[] payload;

	public boolean fromBytes(byte[] bytes) {
		if (bytes.length < 10) return false;
		ByteBuffer bb = ByteBuffer.wrap(bytes);

		bb.order(ByteOrder.LITTLE_ENDIAN);
		// bb.order(ByteOrder.BIG_ENDIAN);
		timestamp = bb.getInt() & 0xffff;
		width = bb.getShort() & 0xffff;
		height = bb.getShort() & 0xffff;
		channel = bb.getShort() & 0xffff;

		logger.info("timestamp : {}, width : {}, height  : {}, channel : {}", timestamp, width, height, channel);

		this.payload = bytes;
		return true;
	}
	// 이미지를 생성하고, 이를 스트레칭하면 될 듯.

	public Color color3byte(int x, int y, int alpha) {
		// x : 1 -> 1*3
		// y : 1 -> 170 * 3
		if (payload == null) return Color.lightGray;
		// int idx = 3 * x + y * 170 * 3 + 10;
		int idx = 3 * x + y * width * 3 + 10;
		if (idx + 2 >= payload.length) {
			return Color.lightGray;
		}
		return new Color((int) payload[idx], (int) payload[idx + 1], (int) payload[idx + 2], alpha);
	}

	// bgr 임.
	public int rgb(int x, int y) {
		// x : 1 -> 1*3
		// y : 1 -> 170 * 3
		// int idx = 3 * x + y * 170 * 3 + 10;
		int idx = 3 * x + y * width * 3 + 10;
		if (payload == null) return 0xaaaaaa;
		if (idx + 2 >= payload.length) return 0xaaaaaa;

		int rgb = (int) payload[idx];
		rgb = (rgb << 8) + (int) payload[idx + 1];
		rgb = (rgb << 8) + (int) payload[idx + 2];
		return rgb;
	}

	public int argb(int x, int y, int alpha) {
		// x : 1 -> 1*3
		// y : 1 -> 170 * 3
		// int idx = 3 * x + y * 170 * 3 + 10;
		int idx = 3 * x + y * width * 3 + 10;
		if (payload == null) return 0xaaaaaa;
		if (idx + 2 >= payload.length) return 0xaaaaaa;

		int rgb = alpha & 0xff;
		rgb = (rgb << 8) + (int) payload[idx];
		rgb = (rgb << 8) + (int) payload[idx + 1];
		rgb = (rgb << 8) + (int) payload[idx + 2];
		return rgb;
	}

	public Color color1byte(int x, int y, int alpha, float hue) {
		if (payload == null) return Color.lightGray;
		int idx = 1 * x + y * width * 1 + 10;
		// int idx = 1 * x + y * 170 * 1 + 10;
		if (idx >= payload.length) {
			return Color.lightGray;
		}
		Color c = Color.getHSBColor(hue, (float) payload[idx] / 256, 1);
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);
	}

	public int argb1byte(int x, int y, int alpha, float hue) {
		if (payload == null) return (alpha << 24) + Color.lightGray.getRGB();
		// int idx = 1 * x + y * 170 * 1 + 10;
		int idx = 1 * x + y * width * 1 + 10;
		if (idx >= payload.length) {
			return (alpha << 24) + Color.lightGray.getRGB();
		}
		Color c = Color.getHSBColor(hue, (float) payload[idx] / 256, 1);
		return (new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha)).getRGB();
	}

	public BufferedImage getRGBImage(int width, int height) {
		// BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				img.setRGB(x, y, rgb(x, y));
			}
		}
		return img;
	}

	public BufferedImage getRGBImage() {
		// BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				img.setRGB(x, y, rgb(x, y));
			}
		}
		return img;
	}

	public BufferedImage getARGBImage(int width, int height, int alpha) {
		// BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				img.setRGB(x, y, argb(x, y, alpha));
			}
		}
		return img;
	}

	public BufferedImage get1ByteARGBImage(int width, int height, int alpha, float hue) {
		// BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				img.setRGB(x, y, argb1byte(x, y, alpha, hue));
			}
		}
		return img;
	}

	public BufferedImage get1ByteARGBImage(int alpha, float hue) {
		// BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				img.setRGB(x, y, argb1byte(x, y, alpha, hue));
			}
		}
		return img;
	}
}
