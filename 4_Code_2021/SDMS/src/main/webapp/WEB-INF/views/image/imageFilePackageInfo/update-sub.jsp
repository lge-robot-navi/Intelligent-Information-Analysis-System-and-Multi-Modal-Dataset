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
		var url = '${REQUEST_CONTEXT_PATH}/image/imagePackageInfo/imageInfo/list' + '?imageFilePackageIdSq=${imageFileInfo.imageFilePackageIdSq}';
		$('#image').load(url);
	}

	// 이미지 등록폼
	function getImageForm() {
		var url = '${REQUEST_CONTEXT_PATH}/image/imagePackageInfo/imageInfo/form' + '?imageFilePackageIdSq=${imageFileInfo.imageFilePackageIdSq}';
		$('#image').load(url);
	}

	// 이미지 상세
	function getImageUpdate(imageFilePackageIdSq, imageFileSq) {
		var url = '${REQUEST_CONTEXT_PATH}/image/imagePackageInfo/imageInfo/update' + '?imageFilePackageIdSq=${imageFileInfo.imageFilePackageIdSq}&imageFileSq=' + imageFileSq;
		$('#image').load(url);
	}
//-->
</script>

<!-- Code -->
<div>
	<div id="image"></div>
</div>