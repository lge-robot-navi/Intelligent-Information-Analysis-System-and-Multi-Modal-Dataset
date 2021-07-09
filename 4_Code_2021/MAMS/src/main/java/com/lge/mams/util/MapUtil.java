package com.lge.mams.util;

import java.util.Map;

public class MapUtil {

	static public String tostr(Map map, String key) {
		if (map == null)
			return "";
		if (map.containsKey(key)) {
			Object val = map.get(key);
			if (val == null)
				return "";
			return val.toString();
		} else {
			return "";
		}
	}

	static public Integer toint(Map map, String key) {
		if (map == null)
			return null;
		if (map.containsKey(key)) {
			Object val = map.get(key);
			if (val == null)
				return null;
			return Integer.parseInt(val.toString());
		} else {
			return null;
		}
	}
}
