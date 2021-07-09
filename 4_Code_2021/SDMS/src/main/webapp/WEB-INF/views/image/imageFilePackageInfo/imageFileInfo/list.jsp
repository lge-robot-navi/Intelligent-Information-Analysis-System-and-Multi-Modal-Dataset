<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom" uri="/tags/custom-taglib" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
<!--
	// 상세조회
	function fnSelect(imageFilePackageIdSq, imageFileSq) {
		// parent function call
		getImageUpdate(imageFilePackageIdSq, imageFileSq);
	}
//-->
</script>

<div class="table">
	<table class="table table-striped table-bordered table-hover">
		<colgroup>
			<col width="15%" />
			<col width="15%" />
			<col width="35%" />
			<col width="10%" />
			<col width="15%" />
		</colgroup>

		<thead>
			<tr>
				<th><spring:message code="admin.image.imageFileSq" /></th>
				<th><spring:message code="admin.image.imageFilePackageIdSq" /></th>
				<th><spring:message code="admin.image.imageFileNm" /></th>
				<th><spring:message code="admin.image.imageFileTypeCd" /></th>
				<th><spring:message code="admin.image.imageFileDownloadPathCd" /></th>
			</tr>
		</thead>

		<tbody>
			<c:if test="${empty list}">
				<tr><td colspan="5"><spring:message code="common.dataIsEmpty" /></td></tr>
			</c:if>
			<c:forEach var="item" items="${list}">
				<tr onclick="javascript:fnSelect('${item.imageFilePackageIdSq}', '${item.imageFileSq}');">
					<td>${item.imageFileSq}</td>
					<td class="le ellipsis" title="${item.imageFilePackageIdSq}">${item.imageFilePackageIdSq}</td>
					<td class="le ellipsis" title="${item.imageFileNm}">${item.imageFileNm}</td>
					<spring:eval var="imageFileTypeCdNm" expression="@commonCode.getCode('TA004', item.imageFileTypeCd)"/>
					<td>${imageFileTypeCdNm}</td>
					<spring:eval var="imageFileDownloadPathCdNm" expression="@commonCode.getCode('TA005', item.imageFileDownloadPathCd)"/>
					<td>${imageFileDownloadPathCdNm}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
