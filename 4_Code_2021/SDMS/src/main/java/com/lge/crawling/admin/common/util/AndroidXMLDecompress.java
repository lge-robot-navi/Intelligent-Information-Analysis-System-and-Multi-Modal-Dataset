package com.lge.crawling.admin.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *  안드로이드 APK Manifest 데이터 추출 지원 유틸<br/>
 *  사용법 : main 함수 참조
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Component("androidXMLDecompress")
public class AndroidXMLDecompress {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(AndroidXMLDecompress.class);

	// decompressXML -- Parse the 'compressed' binary form of Android XML docs
	// such as for AndroidManifest.xml in .apk files
	public static int endDocTag = 0x00100101;
	public static int startTag = 0x00100102;
	public static int endTag = 0x00100103;

	public static final String MANIFEST = "AndroidManifest.xml";
	public static final String CERT_RSA = "META-INF/CERT.RSA";
	public static final String DEFAULT_SIGNING_KEY = "Android Debug";

	/**
	 * 테스트용 메인 클래스
	 * @Mehtod Name : main
	 * @param args
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

		//String fileName = args[0];
		//String file = "C:\\temp\\Test.apk";
		String file = "F:/Project/APK/하이퍼센트/HiPercent.apk";
		InputStream is = null;
		ZipFile zip = null;

		// 확장자 체크
		if (file.endsWith(".apk") || file.endsWith(".zip")) {

			File f = new File(file);
			System.out.println("file length : " + f.length());

			// Zip
			zip = new ZipFile(file);

			// ManiFest 파일 추출
			ZipEntry mft = zip.getEntry("AndroidManifest.xml");

			// 스트림 변환
			is = zip.getInputStream(mft);
		}

		byte[] buf = new byte[is.available()];		// 읽기 버퍼 생성
		is.read(buf);								// 버퍼 읽기
		is.close();									// 스트림 닫기
		if (zip != null) {							// File 닫기
			zip.close();
		}

		// XML 파일 풀기
		String xml = AndroidXMLDecompress.decompressXML(buf);
		System.out.println(xml);

		/**
		필요 정보
		packageName
		android:versionCode="1"
		android:versionName="1.0
		 */
		// 정규식을 이용한 패키지명 추출
		String[] keys = {"package", "versionCode", "versionName"};
		for (String key : keys) {
			Pattern pattern = Pattern.compile(key + "=\"(.*?)\"");
			Matcher matcher = pattern.matcher(xml);
			matcher.find();

			String value = matcher.group(1);
			System.out.println("key : " + key + ", value: " + value);
		}
	}

	static void prt(String str) {
		//System.err.print(str);
	}

	@SuppressWarnings("unused")
	public static String decompressXML(byte[] xml) {

		StringBuilder finalXML = new StringBuilder();

		// Compressed XML file/bytes starts with 24x bytes of data,
		// 9 32 bit words in little endian order (LSB first):
		// 0th word is 03 00 08 00
		// 3rd word SEEMS TO BE: Offset at then of StringTable
		// 4th word is: Number of strings in string table
		// WARNING: Sometime I indiscriminently display or refer to word in
		// little endian storage format, or in integer format (ie MSB first).
		int numbStrings = LEW(xml, 4 * 4);

		// StringIndexTable starts at offset 24x, an array of 32 bit LE offsets
		// of the length/string data in the StringTable.
		int sitOff = 0x24; // Offset of start of StringIndexTable

		// StringTable, each string is represented with a 16 bit little endian
		// character count, followed by that number of 16 bit (LE) (Unicode)
		// chars.
		int stOff = sitOff + numbStrings * 4; // StringTable follows
												// StrIndexTable

		// XMLTags, The XML tag tree starts after some unknown content after the
		// StringTable. There is some unknown data after the StringTable, scan
		// forward from this point to the flag for the start of an XML start
		// tag.
		int xmlTagOff = LEW(xml, 3 * 4); // Start from the offset in the 3rd
											// word.
		// Scan forward until we find the bytes: 0x02011000(x00100102 in normal
		// int)
		for (int ii = xmlTagOff; ii < xml.length - 4; ii += 4) {
			if (LEW(xml, ii) == startTag) {
				xmlTagOff = ii;
				break;
			}
		} // end of hack, scanning for start of first start tag

		// XML tags and attributes:
		// Every XML start and end tag consists of 6 32 bit words:
		// 0th word: 02011000 for startTag and 03011000 for endTag
		// 1st word: a flag?, like 38000000
		// 2nd word: Line of where this tag appeared in the original source file
		// 3rd word: FFFFFFFF ??
		// 4th word: StringIndex of NameSpace name, or FFFFFFFF for default NS
		// 5th word: StringIndex of Element Name
		// (Note: 01011000 in 0th word means end of XML document, endDocTag)

		// Start tags (not end tags) contain 3 more words:
		// 6th word: 14001400 meaning??
		// 7th word: Number of Attributes that follow this tag(follow word 8th)
		// 8th word: 00000000 meaning??

		// Attributes consist of 5 words:
		// 0th word: StringIndex of Attribute Name's Namespace, or FFFFFFFF
		// 1st word: StringIndex of Attribute Name
		// 2nd word: StringIndex of Attribute Value, or FFFFFFF if ResourceId
		// used
		// 3rd word: Flags?
		// 4th word: str ind of attr value again, or ResourceId of value

		// TMP, dump string table to tr for debugging
		// tr.addSelect("strings", null);
		// for (int ii=0; ii<numbStrings; ii++) {
		// // Length of string starts at StringTable plus offset in StrIndTable
		// String str = compXmlString(xml, sitOff, stOff, ii);
		// tr.add(String.valueOf(ii), str);
		// }
		// tr.parent();

		// Step through the XML tree element tags and attributes
		int off = xmlTagOff;
		int indent = 0;
		int startTagLineNo = -2;
		while (off < xml.length) {
			int tag0 = LEW(xml, off);
			// int tag1 = LEW(xml, off+1*4);
			int lineNo = LEW(xml, off + 2 * 4);
			// int tag3 = LEW(xml, off+3*4);
			int nameNsSi = LEW(xml, off + 4 * 4);
			int nameSi = LEW(xml, off + 5 * 4);

			if (tag0 == startTag) { // XML START TAG
				int tag6 = LEW(xml, off + 6 * 4); // Expected to be 14001400
				int numbAttrs = LEW(xml, off + 7 * 4); // Number of Attributes
														// to follow
				// int tag8 = LEW(xml, off+8*4); // Expected to be 00000000
				off += 9 * 4; // Skip over 6+3 words of startTag data
				String name = compXmlString(xml, sitOff, stOff, nameSi);
				// tr.addSelect(name, null);
				startTagLineNo = lineNo;

				// Look for the Attributes
				StringBuffer sb = new StringBuffer();
				for (int ii = 0; ii < numbAttrs; ii++) {
					int attrNameNsSi = LEW(xml, off); // AttrName Namespace Str
														// Ind, or FFFFFFFF
					int attrNameSi = LEW(xml, off + 1 * 4); // AttrName String
															// Index
					int attrValueSi = LEW(xml, off + 2 * 4); // AttrValue Str
																// Ind, or
																// FFFFFFFF
					int attrFlags = LEW(xml, off + 3 * 4);
					int attrResId = LEW(xml, off + 4 * 4); // AttrValue
															// ResourceId or dup
															// AttrValue StrInd
					off += 5 * 4; // Skip over the 5 words of an attribute

					String attrName = compXmlString(xml, sitOff, stOff,
							attrNameSi);
					String attrValue = attrValueSi != -1 ? compXmlString(xml,
							sitOff, stOff, attrValueSi) : "resourceID 0x"
							+ Integer.toHexString(attrResId);
					sb.append(" " + attrName + "=\"" + attrValue + "\"");
					// tr.add(attrName, attrValue);
				}
				finalXML.append("<" + name + sb + ">");
				prtIndent(indent, "<" + name + sb + ">");
				indent++;

			} else if (tag0 == endTag) { // XML END TAG
				indent--;
				off += 6 * 4; // Skip over 6 words of endTag data
				String name = compXmlString(xml, sitOff, stOff, nameSi);
				finalXML.append("</" + name + ">");
				prtIndent(indent, "</" + name + "> (line " + startTagLineNo
						 + "-" + lineNo + ")");
				// tr.parent(); // Step back up the NobTree

			} else if (tag0 == endDocTag) { // END OF XML DOC TAG
				break;

			} else {
				prt("  Unrecognized tag code '" + Integer.toHexString(tag0)
						+ "' at offset " + off);
				break;
			}
		} // end of while loop scanning tags and attributes of XML tree
		//prt("    end at offset " + off);
		return finalXML.toString();
	} // end of decompressXML

	public static String compXmlString(byte[] xml, int sitOff, int stOff, int strInd) {
		if (strInd < 0)
			return null;
		int strOff = stOff + LEW(xml, sitOff + strInd * 4);
		return compXmlStringAt(xml, strOff);
	}

	public static String spaces = "                                             ";

	public static void prtIndent(int indent, String str) {
		prt(spaces.substring(0, Math.min(indent * 2, spaces.length())) + str);
	}

	// compXmlStringAt -- Return the string stored in StringTable format at
	// offset strOff. This offset points to the 16 bit string length, which
	// is followed by that number of 16 bit (Unicode) chars.
	public static String compXmlStringAt(byte[] arr, int strOff) {
		int strLen = arr[strOff + 1] << 8 & 0xff00 | arr[strOff] & 0xff;
		byte[] chars = new byte[strLen];
		for (int ii = 0; ii < strLen; ii++) {
			chars[ii] = arr[strOff + 2 + ii * 2];
		}
		return new String(chars); // Hack, just use 8 byte chars
	} // end of compXmlStringAt

	// LEW -- Return value of a Little Endian 32 bit word from the byte array
	// at offset off.
	public static int LEW(byte[] arr, int off) {
		return arr[off + 3] << 24 & 0xff000000 | arr[off + 2] << 16 & 0xff0000
				| arr[off + 1] << 8 & 0xff00 | arr[off] & 0xFF;
	} // end of LEW

	/**
	 * AndroidManifest.xml 파일 내용 추출. (package, versionCode, versionName)
	 * @Mehtod Name : getManifestInfo
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> getManifestInfo(MultipartFile file) throws IOException {
		return getManifestInfo(file.getInputStream());
	}

	/**
	 * AndroidManifest.xml 파일 내용 추출. (package, versionCode, versionName)
	 * @Mehtod Name : getManifestInfo
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> getManifestInfo(InputStream is) throws IOException {

		Map<String, String> map = new HashMap<>();

		// zip
		ZipInputStream zis = null;
		ByteArrayOutputStream manifestBos = null;
		ByteArrayOutputStream certBos = null;

		try {

			zis = new ZipInputStream(is);
			manifestBos = new ByteArrayOutputStream();
			certBos = new ByteArrayOutputStream();

			int cnt = 0;

			ZipEntry entry = null;
			while ((entry = zis.getNextEntry()) != null) {

				String entryName = entry.getName();

				if ( entryName.equals(CERT_RSA) ) { // cert

					byte[] buf = new byte[1024];
					int readbyte;
					while ((readbyte = zis.read(buf)) > 0) {
						certBos.write(buf, 0, readbyte);
						//logger.debug("readbyte: {}", readbyte);
					}

					cnt++;

				} else if ( entryName.equals(MANIFEST) ) { // manifest

					byte[] buf = new byte[1024];
					int readbyte;
					while ((readbyte = zis.read(buf)) > 0) {
						manifestBos.write(buf, 0, readbyte);
						//logger.debug("readbyte: {}", readbyte);
					}

					cnt++;
				}

				if ( cnt == 2 ) {
					break;
				}
			}

			/**
			 *  Default Signing Key Check
			 */
			String isSigning = "T";
			if ( certBos.toString().indexOf(DEFAULT_SIGNING_KEY) > -1 ) { // 키값이 존재할 경우 False
				isSigning = "F";
			}
			logger.debug("isSigning : {}", isSigning);
			map.put("isSigning", isSigning);

			/**
			 * 필요 정보
			 * packageName
			 * android:versionCode="1"
			 * android:versionName="1.0
			 */
			String xml = AndroidXMLDecompress.decompressXML(manifestBos.toByteArray());

			logger.debug(xml);

			// 정규식을 이용한 패키지명 추출
			String[] keys = {"package", "versionCode", "versionName"};
			for (String key : keys) {
				Pattern pattern = Pattern.compile(key + "=\"(.*?)\"");
				Matcher matcher = pattern.matcher(xml);
				matcher.find();

				String value = matcher.group(1);
				if (key.equals("versionCode")) { // 버전코드 변환
					value = String.valueOf(versionCodeToInt(value));
				}

				logger.debug("key : {}, value: {}", key, value);

				map.put(key, value);
			}

		} catch (Exception e) {

			logger.error("error: {}", e.getMessage());
			throw new RuntimeException();

		} finally {
			certBos.flush();
			certBos.close();
			manifestBos.flush();
			manifestBos.close();
			zis.close();
			is.close();
		}

		return map;
	}

	/**
	 * AndroidManifest.xml 파일 내용 추출. (package, versionCode, versionName)
	 * @Mehtod Name : getManifestInfo
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> getManifestInfoBak(InputStream is) throws IOException {

		Map<String, String> map = new HashMap<>();

		// zip
		ZipInputStream zis = null;
		ByteArrayOutputStream manifestBos = null;
		ByteArrayOutputStream certBos = null;

		try {

			zis = new ZipInputStream(is);
			certBos = new ByteArrayOutputStream();
			manifestBos = new ByteArrayOutputStream();

			int cnt = 0;

			ZipEntry entry = null;
			while ((entry = zis.getNextEntry()) != null) {

				String entryName = entry.getName();

				if ( entryName.equals(CERT_RSA) ) { // cert

					byte[] buf = new byte[1024];
					int readbyte;
					while ((readbyte = zis.read(buf)) > 0) {
						certBos.write(buf, 0, readbyte);
						//logger.debug("readbyte: {}", readbyte);
					}

					cnt++;

				} else if ( entryName.equals(MANIFEST) ) { // manifest

					byte[] buf = new byte[1024];
					int readbyte;
					while ((readbyte = zis.read(buf)) > 0) {
						manifestBos.write(buf, 0, readbyte);
						//logger.debug("readbyte: {}", readbyte);
					}

					cnt++;
				}

				if ( cnt == 2 ) {
					break;
				}
			}

			/**
			 *  Default Signing Key Check
			 */
			String isSigning = "T";
			if ( certBos.toString().indexOf(DEFAULT_SIGNING_KEY) > -1 ) { // 키값이 존재할 경우 False
				isSigning = "F";
			}
			map.put("isSigning", isSigning);

			/**
			 * 필요 정보
			 * packageName
			 * android:versionCode="1"
			 * android:versionName="1.0
			 */
			String xml = AndroidXMLDecompress.decompressXML(manifestBos.toByteArray());
			logger.debug(xml);

		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    InputSource inSource = new InputSource( new StringReader( xml ) );
		    Document d = builder.parse( inSource );
		    NodeList nodeLst = d.getElementsByTagName("manifest");

		    for (int i = 0; i < nodeLst.getLength(); i++) {

		    	Node fstNode = nodeLst.item(i);

		    	NamedNodeMap attrs = fstNode.getAttributes();
		    	for (int j = 0; j < attrs.getLength(); j++) {
		    		Node attr = attrs.item(j);
		    		map.put(attr.getNodeName(), new String(attr.getNodeValue().getBytes("UTF-8")));
				}

		    }

		} catch (Exception e) {

			logger.error("error: {}", e.getMessage());
			throw new RuntimeException();

		} finally {
			certBos.flush();
			certBos.close();
			manifestBos.flush();
			manifestBos.close();
			zis.close();
			is.close();
		}

		return map;
	}

	/**
	 * 버전코드를 int 형으로 반환
	 * @Mehtod Name : versionCodeToInt
	 * @param versionCode
	 * @return
	 */
	public int versionCodeToInt(String versionCode) {

		// 16진수의 경우 진수변환
		if (versionCode.indexOf("resourceID 0x") > -1) {
			versionCode = versionCode.replaceAll("resourceID 0x", "");
			return (int) Long.parseLong(versionCode, 16);
		}

		return Integer.parseInt(versionCode);
	}
}
