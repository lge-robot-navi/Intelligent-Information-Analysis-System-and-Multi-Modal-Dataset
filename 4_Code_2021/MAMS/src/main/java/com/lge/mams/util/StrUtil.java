package com.lge.mams.util;

public class StrUtil {

	public static String bytesToHexStr(byte[] bytes) {

		StringBuilder sb = new StringBuilder();

		for (byte b : bytes) {

			sb.append(String.format("%02X", b & 0xff));
		}

		return sb.toString();
	}

	public static String bytesToHexStr(byte[] bytes, int max) {

		StringBuilder sb = new StringBuilder();

		int idx = 0;
		for (byte b : bytes) {
			sb.append(String.format("%02X ", b & 0xff));
			idx++;
			if (idx >= max) {
				sb.append(" ...");
			}
		}

		return sb.toString();
	}

	static public boolean isEmpty(String s) {
		if (s == null)
			return true;
		if (s.trim() == "")
			return true;
		return false;
	}

	static public boolean isEqual(String s1, String s2) {
		if (s1 == null && s2 == null)
			return true;
		if (s1 == null || s2 == null)
			return false;
		return s1.equals(s2);
	}
}
