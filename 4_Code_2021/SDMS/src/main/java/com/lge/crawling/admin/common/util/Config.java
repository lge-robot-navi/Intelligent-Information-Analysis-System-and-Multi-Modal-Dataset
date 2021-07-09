package com.lge.crawling.admin.common.util;

import org.apache.commons.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Apache Common Configuration Support Util.
 * Apache Common Configuration 지원 유틸
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class Config {

	private static Configuration commonConfig;

	/**
	 * Constructor of CommonConfiguration.java class
	 */
	private Config() {}

	@Autowired
	@Qualifier("commonConfig")
	public void setCommon(Configuration config) {
		commonConfig = config;
	}

	public static Configuration getCommon() {
		return commonConfig;
	}
}