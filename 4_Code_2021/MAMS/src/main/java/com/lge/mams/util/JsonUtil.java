package com.lge.mams.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	ObjectMapper mapper;
	JsonNode root;

	public JsonUtil(String json) {
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);

		readJson(json);
	}

	public JsonUtil() {
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
	}

	public String toJsonStr(Object o) {
		try {
			// human readable json string
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
		} catch (Exception ex) {
			throw new RuntimeException("json parsing", ex);
		}
	}

	public void readJson(String json) {
		try {
			root = mapper.readTree(json);
		} catch (Exception e) {
			logger.error("e", e);
		}
	}

	public <T> T treeToValue(JsonNode node, Class<T> cls) {
		T obj = null;
		try {
			obj = mapper.treeToValue(node, cls);
		} catch (JsonProcessingException e) {
			logger.error("e", e);
		}
		return obj;
	}

	public <T> T treeToValue(String path, Class<T> cls) {
		T obj = null;
		JsonNode node = root.path(path);
		return treeToValue(node, cls);
	}

	public <T> T treeToValue(Class<T> cls) {
		return treeToValue(root, cls);
	}

	public Map<String, Object> treeToMap() {
		LinkedHashMap<String, Object> obj = null;
		try {
			obj = mapper.convertValue(root, new TypeReference<LinkedHashMap<String, Object>>() {
			});
		} catch (Exception e) {
			logger.error("e", e);
		}
		return obj;
	}

	public <T> List<T> treeToArray(String path, Class<T> cls) {
		JsonNode node = root.path(path);
		List<T> list = new ArrayList<T>();
		if (!node.isArray()) return null;
		for (JsonNode n : node) {
			T obj = treeToValue(n, cls);
			if (obj == null) continue;
			list.add(obj);
		}
		return list;
	}

	public String treeToStr(String path) {
		JsonNode node = root.path(path);
		return node.asText();
	}

	public String treeToJson(String path) {
		JsonNode node = root.path(path);
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
		} catch (JsonProcessingException e) {
			logger.error("E", e);
			return "";
		}
	}

	/**
	 * 로깅을 위해서.
	 * JSON로깅이 가장 간단한듯.(한줄로그)
	 */
	static public String json(Object o) {
		ObjectMapper mapper;
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
		try {
			return mapper.writeValueAsString(o);
		} catch (Exception ex) {
			logger.error("E", ex);
			return "";
		}
	}

	/**
	 * 로깅. json formatted string.
	 */
	static public String pretty(Object o) {
		ObjectMapper mapper;
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
			// return mapper.writeValueAsString(o);
		} catch (Exception ex) {
			logger.error("E", ex);
			return "";
		}
	}
}