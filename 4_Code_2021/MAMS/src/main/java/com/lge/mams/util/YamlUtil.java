package com.lge.mams.util;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YamlUtil {
	private static Logger logger = LoggerFactory.getLogger(YamlUtil.class);

	static public String pretty(Object o) {
		ObjectMapper mapper;
		mapper = new ObjectMapper(new YAMLFactory());
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
		}catch(Exception ex) {
			logger.error("E", ex);
			return "";
		}
	}
}
