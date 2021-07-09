package com.lge.mams.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Generating a file path as a rule.
 * 규칙에 기준하여 파일 저장 경로를 생성하는 클래스
 *
 * @author :  Copyright (c) 2017 by MIRINCOM CORP. All Rights Reserved.
 * @version : 1.0
 */
public class FilePathUtil {

	/**
	 * The enum Domain type.
	 */
	public enum DOMAIN_TYPE {

		/**
		 * Web domain type.
		 */
		WEB,
		/**
		 * Mobile domain type.
		 */
		MOBILE,
		/**
		 * Admin domain type.
		 */
		ADMIN
		;

		@Override
		public String toString() {
			return "NASDATA.DOMAIN." + name();
		}
	};

	/**
	 * see file CommonConfig.xml
	 * CommonConfig.xml 파일 디렉토리 설정값을 참조 한다.
	 *
	 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
	 * @version : 1.0
	 */
	public enum PATH_TYPE {

		/**
		 * Prod main image path type.
		 */
		PROD_MAIN_IMAGE,

		/**
		 * Prod html pc path type.
		 */
		PROD_HTML_PC,
		/**
		 * Prod html pc image path type.
		 */
		PROD_HTML_PC_IMAGE,
		/**
		 * Prod html pc video path type.
		 */
		PROD_HTML_PC_VIDEO,

		/**
		 * Prod html mobile path type.
		 */
		PROD_HTML_MOBILE,
		/**
		 * Prod html mobile image path type.
		 */
		PROD_HTML_MOBILE_IMAGE,
		/**
		 * Prod html mobile video path type.
		 */
		PROD_HTML_MOBILE_VIDEO,

		/**
		 * Bulletine html path type.
		 */
		BULLETINE_HTML,
		/**
		 * Bulletine image path type.
		 */
		BULLETINE_IMAGE,

		/**
		 * Promotion html path type.
		 */
		PROMOTION_HTML,
		/**
		 * Promotion image path type.
		 */
		PROMOTION_IMAGE,

		/**
		 * Tstory html path type.
		 */
		TSTORY_HTML,
		/**
		 * Tstory image path type.
		 */
		TSTORY_IMAGE,

		/**
		 * Mail html path type.
		 */
		MAIL_HTML,
		/**
		 * Mail image path type.
		 */
		MAIL_IMAGE,

		/**
		 * Coupon html path type.
		 */
		COUPON_HTML,
		/**
		 * Coupon image path type.
		 */
		COUPON_IMAGE,

		/**
		 * Faq html path type.
		 */
		FAQ_HTML,
		/**
		 * Faq image path type.
		 */
		FAQ_IMAGE,
		
		/**
		 * Franchise html path type.
		 */
		FRANCHISE_HTML,
		/**
		 * Franchise image path type.
		 */
		FRANCHISE_IMAGE,
		
		/**
		 * 1:1 상담 image
		 */
		SUPPORT_ONETOONE_IMAGE,

		/**
		 * 그룹코드 image
		 */
		GROUPCODE_IMAGE,

		/**
		 * T-Station main image
		 */
		TSTATIONMAIN_IMAGE,

		/**
		 * T-Station main html
		 */
		TSTATIONMAIN_HTML
		;

		@Override
		public String toString() {
			return "NASDATA." + name().replaceAll("_", ".");
		}
	};

	private String base;
	private String service;
	private String prefix;
	private String suffix;
	private String datePath;
	private String idPath;
	private String fileName;

	/**
	 * 테스트용 Main 메소드
	 *
	 * @param args the input arguments
	 * @Mehtod Name : main
	 */
	public static void main(String[] args) {
		System.out.println("main start");
		/*
		System.out.println("IMG_PROD               : " + FilePathUtil.PATH_TYPE.IMG_PROD);
		System.out.println("IMG_COMPOSE            : " + FilePathUtil.PATH_TYPE.IMG_COMPOSE);
		System.out.println("VIDEO_PROD             : " + FilePathUtil.PATH_TYPE.VIDEO_PROD);
		System.out.println("HTML_PROD              : " + FilePathUtil.PATH_TYPE.HTML_PROD);
		System.out.println("HTML_SUBJECT_LINK      : " + FilePathUtil.PATH_TYPE.HTML_SUBJECT_LINK);
		System.out.println("HTML_GUIDE             : " + FilePathUtil.PATH_TYPE.HTML_GUIDE);
		System.out.println("HTML_BULLETIN          : " + FilePathUtil.PATH_TYPE.HTML_BULLETIN);
		System.out.println("HTML_PROMOTION         : " + FilePathUtil.PATH_TYPE.HTML_PROMOTION);
		System.out.println("HTML_EDUPLAN           : " + FilePathUtil.PATH_TYPE.HTML_EDUPLAN);
		System.out.println("HTML_MAIL              : " + FilePathUtil.PATH_TYPE.HTML_MAIL);
		System.out.println("PDF_PREVIEW            : " + FilePathUtil.PATH_TYPE.PDF_PREVIEW);
		*/
		for (PATH_TYPE pathType : PATH_TYPE.values()) {
			System.out.println("#######################");
			System.out.println(pathType);
			System.out.println("DB PATH 		: " + FilePathUtil.getInstance(pathType).getDBPath());
			System.out.println("FULL PATH 		: " + FilePathUtil.getInstance(pathType).getFullPath());
			System.out.println("-----------------------\n");
		}
		//System.out.println( FilePathUtil.getInstance(PATH_TYPE.EPACK_IMG).getDBPath() );
		//System.out.println( FilePathUtil.getInstance(PATH_TYPE.VIDEO_ENQUETE, "10000", "20000", "33333").getFullPath() );
		System.out.println("main end");
	}

	/**
	 * Constructor of FilePathUtil.java class
	 * @param pathType
	 * @param params
	 */
	private FilePathUtil(PATH_TYPE pathType, String ... params) {
		this.base		= Config.getCommon().getString(pathType + ".BASE");
		this.service	= Config.getCommon().getString(pathType + ".SERVICE");
		this.prefix		= Config.getCommon().getString(pathType + ".PREFIX");
		this.suffix		= Config.getCommon().getString(pathType + ".SUFFIX");
		this.datePath	= DateUtil.getCurrentDate("/yyyy/MMdd");

		int sz = params.length;
		if (sz > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < sz; i++) {
				sb.append("/").append(params[i]);
			}
			this.idPath = sb.toString();
		}
	}

	/**
	 * FilePathUtil Instance를 돌려준다.
	 *
	 * @param pathType the path type
	 * @param params   the params
	 * @return instance instance
	 * @Mehtod Name : getInstance
	 */
	public static FilePathUtil getInstance(PATH_TYPE pathType, String ... params) {
		return new FilePathUtil(pathType, params);
	}

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	/**
	 * 화면에서 사용할 URL을 돌려준다.
	 *
	 * @param domainType the domain type
	 * @param pathType   the path type
	 * @param loc        the loc
	 * @return full url path
	 * @Mehtod Name : getDBPath
	 */
	public static String getFullUrlPath(DOMAIN_TYPE domainType, PATH_TYPE pathType, String loc) {
		StringBuffer sb = new StringBuffer();
		sb.append(Config.getCommon().getString(domainType + ""));
		sb.append(Config.getCommon().getString(pathType + ".SERVICE"));
		sb.append(loc);
		return sb.toString();
	}

	/**
	 * 전체 경로를 돌려준다.
	 *
	 * @param pathType the path type
	 * @param loc      the loc
	 * @return the full path
	 */
	public static String getFullPath(PATH_TYPE pathType, String loc) {
		StringBuffer sb = new StringBuffer();
		sb.append(Config.getCommon().getString(pathType + ".BASE"));
		sb.append(Config.getCommon().getString(pathType + ".SERVICE"));
		sb.append(loc);
		return sb.toString();
	}

	/**
	 * DB 에서 사용할 URL을 돌려준다.
	 *
	 * @return db path
	 * @Mehtod Name : getDBPath
	 */
	public String getDBPath() {

		StringBuffer sb = new StringBuffer();

		if (StringUtils.isNotEmpty(prefix)) {
			sb.append(prefix);
		}

		if (StringUtils.isNotEmpty(idPath)) {
			sb.append(idPath);
		}

		if (StringUtils.isNotEmpty(datePath)) {
			sb.append(datePath);
		}

		if (StringUtils.isNotEmpty(suffix)) {
			sb.append(suffix);
		}

		if (StringUtils.isNotEmpty(fileName)) {
			sb.append("/").append(fileName);
		} else {
			sb.append("/");
		}


		return sb.toString();
	}

	/**
	 * 전체 경로를 돌려준다.
	 *
	 * @return full path
	 * @Mehtod Name : getFullPath
	 */
	public String getFullPath() {

		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotEmpty(base)) {
			sb.append(base);
		}
		if (StringUtils.isNotEmpty(service)) {
			sb.append(service);
		}

		sb.append(getDBPath());

		return sb.toString();
	}

	/**
	 * Gets base.
	 *
	 * @return the base
	 */
	public String getBase() {
		return base;
	}

	/**
	 * Sets base.
	 *
	 * @param base the base to set
	 */
	public void setBase(String base) {
		this.base = base;
	}

	/**
	 * Gets prefix.
	 *
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Sets prefix.
	 *
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * Gets suffix.
	 *
	 * @return the suffix
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * Sets suffix.
	 *
	 * @param suffix the suffix to set
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	/**
	 * Gets date path.
	 *
	 * @return the datePath
	 */
	public String getDatePath() {
		return datePath;
	}

	/**
	 * Sets date path.
	 *
	 * @param datePath the datePath to set
	 */
	public void setDatePath(String datePath) {
		this.datePath = datePath;
	}

	/**
	 * Gets id path.
	 *
	 * @return the idPath
	 */
	public String getIdPath() {
		return idPath;
	}

	/**
	 * Sets id path.
	 *
	 * @param idPath the idPath to set
	 */
	public void setIdPath(String idPath) {
		this.idPath = idPath;
	}

	/**
	 * Gets file name.
	 *
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets file name.
	 *
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Gets service.
	 *
	 * @return the service
	 */
	public String getService() {
		return service;
	}

	/**
	 * Sets service.
	 *
	 * @param service the service
	 */
	public void setService(String service) {
		this.service = service;
	}
}