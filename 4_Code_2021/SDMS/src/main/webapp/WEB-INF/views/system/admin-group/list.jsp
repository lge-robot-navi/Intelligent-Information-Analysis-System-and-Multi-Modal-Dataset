<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom" uri="/tags/custom-taglib" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
<!--
	//최근 접속 페이지 값 설정
	fnSetLatestPage();

	$(function() {

		// 검색
		$('#btnSearch').click(function() { search(); });

		// 사용여부 이벤트
		$('select[name=useYn]').change(function() { search(); });
	});

	// 검색
	function search() {
		$('form:first').submit();
	}

	// 상세조회
	function fnSelect(adminGrpId) {
		var url = 'update?adminGrpId=' + adminGrpId;
		location.href = url;
	}
//-->
</script>

<!-- button area -->
<div class="row">
	<div class="col-sm-12 text-align-right">
		<custom:auth type="CREATE">
			<div class="btn-group">
				<a href="form" class="btn btn-sm btn-primary" role="button">
					<i class="fa fa-plus"></i> <spring:message code="common.insert" /></a>
			</div>
		</custom:auth>
	</div>
</div>
<!-- // button area -->

<p></p>

<!-- search -->
<div class="well search-wrap">
	<form:form method="get" action="list" modelAttribute="adminGroupEntity" cssClass="form-inline search-form" role="form">
		<fieldset>
			<div class="row">
				<div class="col-sm-10">
					<div class="row">
						<div class="col-sm-6">
							<spring:message var="msgAdminGrpId" code="system.admin-group.adminGrpId" />
							<label for="cdgrpCd">${msgAdminGrpId}</label>
							<div class="form-group">
								<form:input path="adminGrpId" cssClass="form-control" placeholder="${msgAdminGrpId}" />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<spring:message var="msgAdminGrpNm" code="system.admin-group.adminGrpNm" />
								<label for="cdgrpNm">${msgAdminGrpNm}</label>
								<form:input path="adminGrpNm" cssClass="form-control" placeholder="${msgAdminGrpNm}" />
							</div>
						</div>
					</div>
				</div>

				<div class="col-sm-2 text-center">
					<button type="button" id="btnSearch" class="btn btn-default btn-primary" role="button">
						<i class="fa fa-search"></i> <spring:message code="common.search" />
					</button>
				</div>
			</div>
		</fieldset>
	</form:form>
</div>
<!-- // search -->

<!-- list -->
<div class="table-responsive">
	<table class="table table-striped table-bordered table-hover">
		<colgroup>
			<col width="20%" />
			<col width="20%" />
			<col width="60%" />
		</colgroup>
	
		<thead>
			<tr>
				<th scope="col"><spring:message code="system.admin-group.adminGrpId" /></th>
				<th scope="col"><spring:message code="system.admin-group.adminGrpNm" /></th>
				<th scope="col"><spring:message code="system.admin-group.adminGrpDs" /></th>
			</tr>
		</thead>
	
		<tbody>
			<c:if test="${empty list}">
				<tr><td colspan="3"><spring:message code="common.dataIsEmpty" /></td></tr>
			</c:if>
			<c:forEach var="item" items="${list}">
				<tr onclick="javascript:fnSelect('${item.adminGrpId}');">
					<td title="${item.adminGrpId}">${item.adminGrpId}</td>
					<td class="ellipsis" title="${item.adminGrpNm}">${item.adminGrpNm}</td>
					<td class="ellipsis" title="${item.adminGrpDs}">${item.adminGrpDs}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<jsp:include page="/include/paging.jsp" flush="true" />
