package com.lge.crawling.admin.constants;

/**
 * Tiles Enum
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public enum TilesSuffix {

	DEFAULT,
	EMPTY,
	TMPL,
	POPUP;

	@Override
	public String toString() {
		return "." + name().toString().toLowerCase();
	}
}