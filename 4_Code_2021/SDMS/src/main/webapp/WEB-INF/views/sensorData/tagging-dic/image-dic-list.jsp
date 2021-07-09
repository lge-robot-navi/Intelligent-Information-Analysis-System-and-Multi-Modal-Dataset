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
		$('#imageFileDicBtn').click(function() { event.preventDefault(); imageFileDicInfoSearch(); });

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
	<form:form id="imageFileDicListSearchForm" method="get" modelAttribute="taggingDicEntity" cssClass="form-inline search-form" role="form" autocomplete="off">
		<fieldset>
			<div class="row">
				<div class="col-sm-10">
					<div class="row">
						<div class="form-group">
							<spring:message var="msgImageTaggingDataDicNm" code="admin.image.imageTaggingDataDicNm" />
							<label class="col-sm-2" for="imageTaggingDataDicNm">${msgImageTaggingDataDicNm}</label>
							<form:input path="imageTaggingDataDicNm" cssClass="form-control" placeholder="${msgImageTaggingDataDicNm}" />
						</div>
					</div>
				</div>

				<div class="col-sm-2 text-center search_btn_wrap">
					<button id="imageFileDicBtn" class="btn btn-default btn-primary">
						<i class="fa fa-search"></i> <spring:message code="common.search" />
					</button>
				</div>
			</div>
		</fieldset>
	</form:form>
</div>

<!-- list -->
<div class="table-responsive smart-form">
	<table class="table table-bordered table-hover">
		<colgroup>
			<col width="5%" />
			<col width="50%" />
			<col width="45%" />
		</colgroup>

		<thead>
			<tr>
				<th class="checkbox" style="margin-bottom: 0px;"><label class="checkbox"><input type="checkbox" class="checkAll" name="checkAll" /><i></i></label></th>
				<th><spring:message code="admin.image.upperImageTaggingDataDicNm" /></th>
				<th><spring:message code="admin.image.imageTaggingDataDicNm" /></th>
			</tr>
		</thead>

		<tbody id="imageFileDicListTbody">
			<c:if test="${empty list}">
				<tr><td colspan="5">데이터가 존재하지 않습니다.</td></tr>
			</c:if>
			<c:forEach var="item" items="${list}">
				<tr class="B${item.imageTaggingDataDicIdSq}">
					<td class="checkbox"><label class="checkbox"><input type="checkbox" name="imageCheck" value="${item.imageTaggingDataDicIdSq}" /><i></i></label></td>
					<td class="le ellipsis" title="${item.upperImageTaggingDataDicNm}">${item.upperImageTaggingDataDicNm}</td>
					<td class="le ellipsis" title="${item.imageTaggingDataDicNm}">${item.imageTaggingDataDicNm}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<jsp:include page="/include/sub_paging.jsp" flush="true" />
