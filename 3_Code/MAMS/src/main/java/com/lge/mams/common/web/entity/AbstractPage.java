package com.lge.mams.common.web.entity;

/**
 * Paging supports Entity
 * 페이징 지원 Entity<br/>
 * 본 클래스를 상속 후 전체 카운트 값을 인자로 하여<br/>
 * getPagingValue(int totalRows)<br/>
 * 함수를 사용하면 페이징과 관련된 값이 연산되어 처리된다.<br/>
 * <h1>### 사용방법 ###</h1>
 * <ol>
 *   <li>Controller 등에서 Model에 paging 값을 저장한다.<br/>
 *       -> model.addAttribute("paging", entity.getPagingValue(service.getCount(entity)) );<br/>
 *   </li>
 *   <li>JSP 페이지에서 페이징 관련 데이터를 include 해 준다.<br/>
 *       -> &lt;jsp:include page="/include/paging.jsp" flush="true" /&gt;
 *   </li>
 * </ol>
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class AbstractPage extends BaseEntity {

	private int page = 1;
	private int pageSize = 15;
	private transient PagingValue paging = null;
	private String orderBy = null;

	/**
	 *
	 * @Mehtod Name : getPagingValue
	 * @param totalRows
	 * @return
	 */
	public PagingValue getPagingValue(int totalRows) {
		if (paging == null) {
			paging = new PagingValue(pageSize, page, totalRows);
		}
		return paging;
	}

	/**
	 *
	 * @Mehtod Name : getPagingValue
	 * @param pageSize
	 * @param totalRows
	 * @return
	 */
	public PagingValue getPagingValue(int pageSize, int totalRows) {
		if (paging == null) {
			paging = new PagingValue(pageSize, page, totalRows);
		}
		return paging;
	}

	/**
	 * Paging 시작값을 돌려준다.
	 * @Mehtod Name : getStartRow
	 * @return
	 */
	public int getStartRow() {

		if (paging == null) {
			return 1;
		}

		return paging.getStartRow();
	}
	
	/**
	 * MySQL 에서 start row count 을 이용한 명시적인 쿼리에서 사용
	 * @Mehtod Name : getStartRowCount
	 * @return
	 */
	public int getStartRowCount() {
		return page * pageSize - pageSize;
	}

	/**
	 * SQL 에서 rownum 을 이용한 명시적인 쿼리에서 사용
	 * @Mehtod Name : getStartRownum
	 * @return
	 */
	public int getStartRownum() {
		return getStartRow();
	}

	/**
	 * SQL 에서 rownum 을 이용한 명시적인 쿼리에서 사용
	 * @Mehtod Name : getEndRownum
	 * @return
	 */
	public int getEndRownum() {
		return getPageSize();
	}

	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public PagingValue getPaging() {
		return paging;
	}
	public void setPaging(PagingValue paging) {
		this.paging = paging;
	}

	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}