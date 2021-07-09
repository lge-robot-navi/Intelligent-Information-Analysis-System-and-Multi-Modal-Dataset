<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom"  uri="/tags/custom-taglib" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
<!--
	// ready
	$(document).ready(function() {

		$.ajaxSetup({
			cache: false
		});

		// 이미지 리스트
		getImageList();
	});

	// 이미지 리스트
	function getImageList() {
		var url = '${REQUEST_CONTEXT_PATH}/image/imageInfo/imageList';
		$('.imageList').load(url);
	}

	/***************************************************************/
	/** 서브 페이징 */
	/***************************************************************/
	function getPage(page) {
		var url = '${REQUEST_CONTEXT_PATH}/image/imageInfo/imageList'+'?page='+page;
		$('.imageList').load(url,$('#imageSearchForm').serialize());
	}

	// 검색
	function imageInfoSearch() {
		var url = '${REQUEST_CONTEXT_PATH}/image/imageInfo/imageList';
		//$('.imageList').load(url,$('#imageSearchForm').serialize());
	}
//-->
</script>

<!-- Code -->
<div>
	<div class="imageList"></div>
</div>