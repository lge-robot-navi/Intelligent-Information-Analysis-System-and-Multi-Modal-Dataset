package com.lge.mams.common.util;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

/**
 * Encryption utility
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class CryptoUtil {

	private static final String CRYPTO_KEY = "LGE_!@#$";

	/**
	 * SHA-256 one-way encryption
	 * SHA-256 단방향 암호화
	 * @param str
	 * @return
	 */
	public static String sha256Hex(String str) {
		String result = "";

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(str.getBytes());

			byte byteData[] = md.digest();

			// convert to hex code
			// hex코드로 변환
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			result = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * SHA-256 one-way encryption
	 * SHA-256 단방향 암호화
	 * @param str
	 * @return
	 */
	public static String sha512Hex(String str) {
		String result = "";

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(str.getBytes());

			byte byteData[] = md.digest();

			// convert to hex code
			// hex코드로 변환
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			result = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Encryption
	 * 암호화
	 * @Mehtod Name : encode
	 * @param str : To encrypt the original string (암호화할 원본 문자열)
	 * @return
	 */
	@Deprecated
	public static String dsEncode(String str) {
		return dsEncode(str, "");
	}

	/**
	 * Decryption
	 * 복호화
	 * @method dsDecode
	 * @param str : Decrypt strings (암호화된 문자열)
	 * @return
	 */
	@Deprecated
	public static String dsDecode(String str) {
		return dsDecode(str, "");
	}

	/**
	 * Encryption
	 * 암호화
	 * @method dsEncode
	 * @param strIn
	 * @param macAddr
	 * @return
	 */
	@Deprecated
	public static String dsEncode(String strIn, String macAddr) {
		String retStr = "";

		strIn = strIn + StringUtils.replace(StringUtils.replace(StringUtils.stripToEmpty(macAddr), "-", ""), ":", "") + CRYPTO_KEY;

		for (int i = 0; i < strIn.length(); i++) {
			retStr += (char)(strIn.charAt(i) + (i % 5) + 1);
		}

		return retStr;
	}

	/**
	 * Decryption
	 * 복호화
	 * @method dsDecode
	 * @param strIn
	 * @param macAddr
	 * @return
	 */
	@Deprecated
	public static String dsDecode(String strIn, String macAddr) {

		String retStr = "";

		String cyperKey = StringUtils.replace(StringUtils.replace(StringUtils.stripToEmpty(macAddr), "-", ""), ":", "") + CRYPTO_KEY;

		for (int i=0; i < (strIn.length()); i++) {
			retStr += (char)((strIn.charAt( i )) - (i % 5) - 1);
		}

		if (retStr.length() < 2 || !retStr.substring(retStr.length() - cyperKey.length()).equals(cyperKey)) {
			retStr = "";
		} else {
			retStr = retStr.substring(0, retStr.length() - cyperKey.length());
		}

		return retStr;
	}

	/**
	 * AES-128 Encrypt
	 * @method aes128encrypt
	 * @param plainText
	 * @return
	 */
	public static String aes128Encrypt(String plainText) {
		return aes128Encrypt(plainText,CRYPTO_KEY);
	}
	
	/**
	 * AES-128 Encrypt
	 * @method aes128encrypt
	 * @param plainText
	 * @return
	 */
	public static String aes128Encrypt(String plainText,String secretKeyString) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKeySpec secretKey = new SecretKeySpec(secretKeyString.getBytes(), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF-8"));

			byte[] encryptedString = Base64.encodeBase64URLSafe(cipherText);
			return new String(encryptedString, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * AES-128 Decrypt
	 * @method aes128Decrypt
	 * @param encryptedText
	 * @return
	 */
	public static String aes128Decrypt(String encryptedText) {
		return aes128Decrypt(encryptedText,CRYPTO_KEY);
	}
	
	
	/**
	 * AES-128 Decrypt
	 * @method aes128Decrypt
	 * @param encryptedText
	 * @return
	 */
	public static String aes128Decrypt(String encryptedText,String secretKeyString) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKeySpec secretKey = new SecretKeySpec(secretKeyString.getBytes(), "AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);

			byte[] cipherText = Base64.decodeBase64(encryptedText);
			String decryptedString = new String(cipher.doFinal(cipherText), "UTF-8");
			return decryptedString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Encrypt Admin Password
	 * @method encryptAdminPassword
	 * @param adminId
	 * @param adminPw
	 * @return
	 */
	public static String encryptAdminPassword(String adminId, String adminPw) {
		return CryptoUtil.sha256Hex(adminId + adminPw + CRYPTO_KEY);
	}

	/**
	 * Encrypt Access Manage
	 * @method encryptAccessPassword
	 * @param accessId
	 * @param accessPw
	 * @return
	 */
	public static String encryptAccessManagePassword(String accessId, String accessPw) {
		return CryptoUtil.sha256Hex(accessId + accessPw + CRYPTO_KEY);
	}
}
