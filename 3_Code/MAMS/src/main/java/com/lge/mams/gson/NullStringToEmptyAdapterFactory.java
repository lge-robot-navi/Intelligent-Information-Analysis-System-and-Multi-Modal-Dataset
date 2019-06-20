package com.lge.mams.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * Null String to empty Adapter Factory
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {

	@SuppressWarnings({ "unchecked", "hiding" })
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

		Class<T> rawType = (Class<T>) type.getRawType();
		if (rawType != String.class) {
			return null;
		}

		return (TypeAdapter<T>) new StringAdapter();
	}
}
