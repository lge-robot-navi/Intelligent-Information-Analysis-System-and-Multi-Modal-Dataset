package com.lge.mams.common.web.entity;

/**
 * Paging Class
 * 페이징 값 처리
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class PagingValue {

	private int pageSize = 0;
	private int currentIndex = 1;
	private int totalRows = 0;

	public PagingValue(int pageSize, int currentIndex, int totalRows) {
		this.pageSize = (pageSize == 0 ? totalRows : pageSize);
		this.currentIndex = currentIndex;
		this.totalRows = totalRows;
	}

	public int getTotalPages() {
		return (int) Math.ceil((double) totalRows / pageSize);
	}

	public int getStartRow() {
		if (currentIndex <= 0) {
			currentIndex = 1;
		}
		return (currentIndex - 1) * pageSize;
	}

	public int getNextPageRow() {
		return getStartRow() + pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public int getTotalRows() {
		return totalRows;
	}
}