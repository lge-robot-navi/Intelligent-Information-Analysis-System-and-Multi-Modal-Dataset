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
		$('#imgSearchBtn').click(function() { event.preventDefault(); imageInfoSearch(); });

		$('.checkAll').on('click', function () {
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
	<form:form id="imageSearchForm" method="get" modelAttribute="imageFileInfoEntity" cssClass="form-inline search-form" role="form" autocomplete="off">
		<fieldset>
			<div class="row">
				<div class="col-sm-10">
					<div class="row">
						<div class="col-sm-12">
							<label><spring:message code="admin.image.imageFileRegistDt" /></label>
							<div class="form-group">
								<div class="input-group">
									<form:input path="startDt" readonly="true" cssClass="form-control datepicker" data-dateformat="yy-mm-dd" />
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
								~
								<div class="input-group">
									<form:input path="endDt" readonly="true" cssClass="form-control datepicker" data-dateformat="yy-mm-dd" />
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
		        		<div class="col-sm-6">
							<div class="form-group">
								<label for="imageFileTypeCd"><spring:message code="admin.image.imageFileTypeCd" /></label>
								<label class="select">
									<tags:select id="imageFileTypeCd" name="imageFileTypeCd" group="TA007" cssClass="form-control" value="${imageFileInfoEntity.imageFileTypeCd}">
										<option value=""><spring:message code="common.all" /></option>
									</tags:select>
									<i></i>
								</label>
							</div>
						</div>

		        		<div class="col-sm-6">
							<div class="form-group">
								<label for="imageFileDownloadPathCd"><spring:message code="admin.image.imageFileDownloadPathCd" /></label>
								<label class="select">
									<tags:select id="imageFileDownloadPathCd" name="imageFileDownloadPathCd" group="TA005" cssClass="form-control" value="${imageFileInfoEntity.imageFileDownloadPathCd}">
										<option value=""><spring:message code="common.all" /></option>
									</tags:select>
									<i></i>
								</label>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<spring:message var="msgImageFileNm" code="admin.image.imageFileNm" />
								<label for="imageFileNm">${msgImageFileNm}</label>
								<form:input path="imageFileNm" cssClass="form-control" placeholder="${msgImageFileNm}" />
							</div>
						</div>
					</div>
				</div>

				<div class="col-sm-2 text-center search_btn_wrap">
					<button id="imgSearchBtn" class="btn btn-default btn-primary">
						<i class="fa fa-search"></i> <spring:message code="common.search" />
					</button>
				</div>
			</div>
		</fieldset>
	</form:form>
</div>

<!-- list -->
<div class="table-responsive smart-form">
	<table style="width:1000px; table-layout:fixed;" class="table table-bordered table-hover">
		<colgroup>
			<col width="5%" />
			<%-- <col width="10%" />
			<col width="10%" /> --%>
			<col width="50%" />
			<col width="15%" />
			<col width="10%" />
			<col width="10%" />
		</colgroup>

		<thead>
			<tr>
				<th class="checkbox" style="margin-bottom: 0px;"><label class="checkbox"><input type="checkbox" class="checkAll" name="checkAll" /><i></i></label></th>
				<%-- <th><spring:message code="admin.image.imageFileSq" /></th>
				<th><spring:message code="admin.image.imageFilePackageIdSq" /></th> --%>
				<th><spring:message code="admin.image.imageFilePath" /></th>
				<th><spring:message code="admin.image.imageFileNm" /></th>
				<th><spring:message code="admin.image.imageFileTypeCd" /></th>
				<th><spring:message code="admin.image.imageFileDownloadPathCd" /></th>
			</tr>
		</thead>

		<tbody id="imageListTbody">
			<c:if test="${empty list}">
				<tr><td colspan="7">데이터가 존재하지 않습니다.</td></tr>
			</c:if>
			<c:forEach var="item" items="${list}">
				<tr class="B${item.sensorDataFileSq}">
					<td class="checkbox"><label class="checkbox"><input type="checkbox" name="imageCheck" value="${item.sensorDataFileSq}" /><i></i></label></td>
					<%-- <td>${item.sensorDataFileSq}</td>
					<td class="ellipsis" title="${item.imageFilePackageIdSq}">${item.imageFilePackageIdSq}</td> --%>
					<td class="ellipsis" title="${item.imageFilePath}">${item.imageFilePath}</td>
					<td class="ellipsis" title="${item.imageFileNm}">${item.imageFileNm}</td>
					<spring:eval var="imageFileTypeCdNm" expression="@commonCode.getCode('TA007', item.imageFileTypeCd)"/>
					<td>${imageFileTypeCdNm}</td>
					<spring:eval var="imageFileDownloadPathCdNm" expression="@commonCode.getCode('TA005', item.imageFileDownloadPathCd)"/>
					<td>${imageFileDownloadPathCdNm}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<jsp:include page="/include/sub_paging.jsp" flush="true" />
