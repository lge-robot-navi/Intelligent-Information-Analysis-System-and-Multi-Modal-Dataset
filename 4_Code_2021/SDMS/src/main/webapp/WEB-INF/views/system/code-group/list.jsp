<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom" uri="/tags/custom-taglib" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
<!--
	// 최근 접속 페이지 값 설정
	fnSetLatestPage();

	$(function() {

		// 검색
		$('#btnSearch').click(function() { search(); });

		// 엔터키 이벤트
		$('form:first input:text').keypress(function(e) {
			if (e.keyCode == 13)
				search();
		});

		// 사용여부 이벤트
		$('select[name=useYn]').change(function() { search(); });
	});

	// 검색
	function search() {
		$('form:first').submit();
	}

	// 상세조회
	function fnSelect(cdgrpCd) {
		var url = 'update?cdgrpCd=' + cdgrpCd;
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
	<form:form method="get" action="list" modelAttribute="codeGroupEntity" cssClass="form-inline search-form" role="form" autocomplete="off">
		<fieldset>
			<div class="row">
				<div class="col-sm-10">
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<spring:message var="msgCdgrpCd" code="system.code-group.cdgrpCd" />
								<label for="searchWd">${msgCdgrpCd}</label>
								<div class="form-group">
									<form:input path="searchWd" cssClass="form-control" placeholder="${msgCdgrpCd}" />
								</div>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<spring:message var="msgCdgrpNm" code="system.code-group.cdgrpNm" />
								<label for="cdgrpNm">${msgCdgrpNm}</label>
								<form:input path="cdgrpNm" cssClass="form-control" placeholder="${msgCdgrpNm}" />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label for="useYn"><spring:message code="system.code-group.useYn" /></label>
								<form:select path="useYn" cssClass="form-control">
									<form:option value=""><spring:message code="selectbox.all" /></form:option>
									<form:option value="Y">사용</form:option>
									<form:option value="N">미사용</form:option>
								</form:select>
							</div>
						</div>
					</div>
				</div>

				<div class="col-sm-2 text-center">
					<button type="submit" class="btn btn-default btn-primary">
						<i class="fa fa-search"></i> <spring:message code="common.search" />
					</button>
				</div>
			</div>
		</fieldset>
	</form:form>
</div>
<!-- //search -->

<!-- list -->
<div class="table-responsive">
	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th><spring:message code="system.code-group.cdgrpCd" /></th>
				<th><spring:message code="system.code-group.cdgrpNm" /></th>
				<th><spring:message code="system.code-group.cdgrpDs" /></th>
				<th><spring:message code="system.code-group.orderNo" /></th>
				<th><spring:message code="system.code-group.useYn" /></th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${empty list}">
				<tr><td colspan="4"><spring:message code="common.dataIsEmpty" /></td></tr>
			</c:if>
			<c:forEach var="item" items="${list}">
				<tr onclick="javascript:fnSelect('${item.cdgrpCd}');">
					<td title="${item.cdgrpCd}">${item.cdgrpCd}</td>
					<td class="text-left" title="${item.cdgrpNm}">${item.cdgrpNm}</td>
					<td class="text-left" >${item.cdgrpDs}</td>
					<td title="${item.orderNo}">${item.orderNo}</td>
					<td title="${item.useYn eq 'Y' ? '사용' : '미사용'}">${item.useYn eq 'Y' ? '사용' : '미사용'}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<!-- /.row -->

<jsp:include page="/include/paging.jsp" flush="true" />
