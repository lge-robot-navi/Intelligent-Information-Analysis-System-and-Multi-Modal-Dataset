package com.lge.mams.tag;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lge.mams.common.web.entity.PagingValue;

/**
 * paging custom tag
 * 페이징 처리 custom tag
 * 
 * @version : 1.0
 * @author : Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class PagingTag extends TagSupport {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(PagingTag.class);

	/**
	 * UID
	 */
	private static final long serialVersionUID = 909803101738802116L;

	private String name = null;
	private String href = null;
	private int totalRows = 0;
	private int pageSize = 0;
	private int currentIndex = 0;
	private int startIndex = 0;
	private int endIndex = 0;
	private int lastIndex = 0;
	private List<Page> pages = new ArrayList<Page>();
	private Page firstPage = null;
	private Page lastPage = null;
	private Page previousPage = null;
	private Page nextPage = null;

	public String getName() {
		return name;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public Page getFirstPage() {
		return firstPage;
	}

	public Page getLastPage() {
		return lastPage;
	}

	public Page getPreviousPage() {
		return previousPage;
	}

	public Page getNextPage() {
		return nextPage;
	}

	public List<Page> getPages() {
		return pages;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFirstPage(Page firstPage) {
		this.firstPage = firstPage;
	}

	public void setLastPage(Page lastPage) {
		this.lastPage = lastPage;
	}

	@Override
	public int doStartTag() throws JspException {

		try {
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

			PagingValue value = (PagingValue) request.getAttribute("paging");

			totalRows = value.getTotalRows();
			pageSize = value.getPageSize();
			currentIndex = value.getCurrentIndex();
			// logger.debug("total rows : {}", totalRows);
			// logger.debug("page size : {}", pageSize);
			// logger.debug("current index : {}", currentIndex);
			findStartEndIndex();
			// logger.debug("start index : {}", startIndex);
			// logger.debug("end index : {}", endIndex);

			this.href = getHref(request);
			for (int i = startIndex; i <= endIndex; i++) {
				pages.add(new Page(i, getURL(i)));
			}

			firstPage = new Page(startIndex, getURL(1));
			lastPage = new Page(lastIndex, getURL(lastIndex));

			if (currentIndex - 1 > 0) {
				previousPage = new Page(currentIndex - 1, getURL(currentIndex - 1));
			} else {
				previousPage = firstPage;
			}

			if (currentIndex + 1 < endIndex || currentIndex + 1 < lastIndex) {
				nextPage = new Page(currentIndex + 1, getURL(currentIndex + 1));
			} else {
				nextPage = lastPage;
			}

			pageContext.setAttribute(this.getName(), this);

		} catch (Exception e) {
			logger.error(e.toString());
			throw new JspException(e);
		}
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		pages.clear();
		firstPage = null;
		lastPage = null;
		previousPage = null;
		nextPage = null;
		return SKIP_BODY;
	}

	private void findStartEndIndex() {

		int interval = 10;
		lastIndex = totalRows / pageSize;
		if (totalRows % pageSize > 0) {
			lastIndex += 1;
		}

		// logger.debug("interval : {}", interval);
		// logger.debug("last index : {}", lastIndex);
		startIndex = 1;
		while (true) {
			endIndex = startIndex + interval - 1;
			if (currentIndex >= startIndex && currentIndex <= endIndex && endIndex <= lastIndex) {
				break;
			}

			if (endIndex > lastIndex) {
				endIndex = lastIndex;
				break;
			}
			startIndex = endIndex + 1;
		}

		if (endIndex == 0) {
			endIndex = 1;
		}
	}

	/**
	 * @Mehtod Name : getURL
	 * @param index
	 * @return
	 */
	private String getURL(int index) {
		// return href + "&amp;page=" + index;
		String concatUrl = (href.indexOf("?") == -1) ? "?" : "&amp;";
		StringBuffer sb = new StringBuffer();
		sb.append(href).append(concatUrl).append("page=").append(index);
		return sb.toString();
	}

	/**
	 * @Mehtod Name : getHref
	 * @param request
	 * @return
	 */
	private String getHref(HttpServletRequest request) {
		try {
			String href = (String) request.getAttribute("javax.servlet.forward.request_uri");

			StringBuffer sb = new StringBuffer(256);
			sb.append(href);

			int i = 0;
			Enumeration<String> e = request.getParameterNames();
			while (e.hasMoreElements()) {
				String param = e.nextElement();
				if (!param.equals("page")) {
					String[] values = request.getParameterValues(param);
					for (String value : values) {
						if (i++ == 0) {
							sb.append("?");
						} else {
							sb.append("&amp;");
						}
						sb.append(param).append("=").append(URLEncoder.encode(value, "UTF-8"));
					}
				}
			}
			// log.debug("sb.toString(): {}", sb.toString());
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Inner Class
	 * 
	 * @version : 1.0
	 * @author : Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
	 */
	public class Page {
		private int index = 0;
		private String href = null;

		private Page(int index, String href) {
			this.index = index;
			this.href = href;
		}

		public int getIndex() {
			return index;
		}

		public String getHref() {
			return href;
		}
	}
}