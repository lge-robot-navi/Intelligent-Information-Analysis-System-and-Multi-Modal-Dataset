package com.lge.crawling.admin.gson;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * String Adapter for GSON
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class StringAdapter extends TypeAdapter<String> {

	@Override
	public String read(JsonReader reader) throws IOException {
		if (reader.peek() == JsonToken.NULL) {
			reader.nextNull();
			return null;	//return "";
		}
		return reader.nextString();
	}

	@Override
	public void write(JsonWriter writer, String value) throws IOException {
		if (value == null) {
			//writer.nullValue();
			writer.value("");
			return;
		}
		writer.value(value);
	}
}
