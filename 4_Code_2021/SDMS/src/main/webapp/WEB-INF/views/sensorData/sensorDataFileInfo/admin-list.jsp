<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom" uri="/tags/custom-taglib" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
<!--
	$(function() {

		// 검색
		$('#adminSearchBtn').click(function() { event.preventDefault(); adminInfoSearch(); });

		$('.checkAll2').on('click', function () {
		  $(this).closest('table').find('tbody :checkbox')
		    .prop('checked', this.checked)
		    .closest('tr').toggleClass('selected', this.checked);
		});

		$('tbody :checkbox').on('click', function () {
		  $(this).closest('tr').toggleClass('selected', this.checked);
		  $(this).closest('table').find('.checkAll').prop('checked', ($(this).closest('table').find('tbody :checkbox:checked').length == $(this).closest('table').find('tbody :checkbox').length));
		});
	});

//-->
</script>

<!-- search -->
<div class="well search-wrap">
	<form:form  id="adminSearchForm" method="get" modelAttribute="adminEntity" cssClass="form-inline search-form" role="form" autocomplete="off">
		<fieldset>
			<div class="row">
				<div class="col-sm-10">
					<div class="row">
						<div class="col-sm-6">
							<spring:message var="msgAdminId" code="system.admin.adminId" />
							<label for="searchWd">${msgAdminId}</label>
							<div class="form-group">
								<form:input path="searchWd" cssClass="form-control" placeholder="${msgAdminId}" />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<spring:message var="msgAdminNm" code="system.admin.adminNm" />
								<label for="adminNm">${msgAdminNm}</label>
								<form:input path="adminNm" cssClass="form-control" placeholder="${msgAdminNm}" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<spring:message var="msgCompanyNm" code="system.admin.companyNm" />
								<label for="companyNm">${msgCompanyNm}</label>
								<form:input path="companyNm" cssClass="form-control" placeholder="${msgCompanyNm}" />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label for="adminStatusCd"><spring:message code="system.admin.adminStatusCd" /></label>
								<tags:select id="adminStatusCd" name="adminStatusCd" group="TA003" cssClass="form-control" value="${adminEntity.adminStatusCd}">
									<option value=""><spring:message code="selectbox.all" /></option>
								</tags:select>
							</div>
						</div>
					</div>
				</div>

				<div class="col-sm-2 text-center search_btn_wrap">
					<button id="adminSearchBtn" class="btn btn-default btn-primary">
						<i class="fa fa-search"></i> <spring:message code="common.search" />
					</button>
				</div>
			</div>
		</fieldset>
	</form:form>
</div>

<!-- list -->
<div class="table-responsive smart-form">
	<table class="table table-striped table-bordered table-hover">
		<colgroup>
			<col width="5%" />
			<col width="20%" />
			<col width="20%" />
			<col width="20%" />
			<col width="20%" />
			<col width="15%" />
		</colgroup>

		<thead>
			<tr>
				<th class="checkbox" style="margin-bottom: 0px;"><label class="checkbox"><input type="checkbox" class="checkAll2" name="checkAll" /><i></i></label></th>
				<th><spring:message code="system.admin.adminId" /></th>
				<th><spring:message code="system.admin.adminNm" /></th>
				<th><spring:message code="system.admin.adminGrpNm" /></th>
				<th><spring:message code="system.admin.adminCdNm" /></th>
				<th><spring:message code="system.admin.adminStatusCd" /></th>
			</tr>
		</thead>

		<tbody>
			<c:if test="${empty list}">
				<tr><td colspan="6">데이터가 존재하지 않습니다.</td></tr>
			</c:if>
			<c:forEach var="item" items="${list}">
				<tr>
					<td class="checkbox"><label class="checkbox"><input type="checkbox" name="adminCheck" value="${item.adminIdSq}" /><i></i></label></td>
					<td title="${item.adminId}">${item.adminId}</td>
					<td title="${item.adminNm}">${item.adminNm}</td>
					<td title="${item.adminGrpNm}">${item.adminGrpNm}</td>
					<td title="${item.adminCdNm}">${item.adminCdNm}</td>
					<td title="${item.adminStatusCdNm}">${item.adminStatusCdNm}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<jsp:include page="/include/sub_paging.jsp" flush="true" />
