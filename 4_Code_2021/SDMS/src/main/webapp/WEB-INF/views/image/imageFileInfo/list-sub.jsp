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

		// 작업자 리스트
		getWorkerList();

	});

	// 작업자 리스트
	function getWorkerList() {
		var url = '${REQUEST_CONTEXT_PATH}/image/imageInfo/adminList';
		$('#adminList').load(url);
	}

	/***************************************************************/
	/** 서브 페이징 */
	/***************************************************************/
	function getPage(page) {
		var url = '${REQUEST_CONTEXT_PATH}/image/imageInfo/adminList'+'?page='+page;
		$('#adminList').load(url,$('#adminSearchForm').serialize());
	}

	// 검색
	function adminInfoSearch() {
		var url = '${REQUEST_CONTEXT_PATH}/image/imageInfo/adminList';
		$('#adminList').load(url,$('#adminSearchForm').serialize());
	}
//-->
</script>

<!-- Code -->
<div>
	<div id="adminList"></div>
</div>