<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" uri="/tags/custom-taglib" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
<!--
	//최근 접속 페이지 값 설정
	fnSetLatestPage();

	$(function() {

		// 검색
		$('#btnSearch').click(function() { search(); });

    	// Datepicker
		setDatepickerStartEnd($('#startDt'), $('#endDt'));
	});

	// 검색
	function search() {
		$('form:first').submit();
	}

	// 상세조회
	function fnSelect(imageFilePackageIdSq) {
		var url = 'update?imageFilePackageIdSq=' + imageFilePackageIdSq;
		location.href = url;
	}

	// 업로드
	function fnUpload() {
		$("#fileUploadForm").submit();
	}

	// 다운로드
	function fnDownload(imageFilePackageIdSq) {
		var url = 'download?imageFilePackageIdSq=' + imageFilePackageIdSq;
		location.href = url;
	}
//-->
</script>

<!-- button area -->
<div class="row button-wrap">
	<div class="col-xs-12 text-align-right">
		<custom:auth type="CREATE">
			<div class="btn-group">
				<a href="#" class="btn btn-sm btn-primary" role="button" data-toggle="modal" data-target="#myModal">
					<i class="fa fa-upload"></i> <spring:message code="common.upload" /></a>
			</div>
		</custom:auth>
	</div>
</div>
<!-- // button area -->

<p></p>

<!-- search -->
<div class="well search-wrap">
	<form:form method="get" action="list" modelAttribute="imageFilePackageInfoEntity" cssClass="form-inline search-form" role="form" autocomplete="off">
		<fieldset>
			<div class="row">
				<div class="col-sm-10">
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<spring:message var="msgImageFilePackageVerNm" code="admin.image.imageFilePackageVerNm" />
								<label for="imageFilePackageVerNm">${msgImageFilePackageVerNm}</label>
								<form:input path="imageFilePackageVerNm" cssClass="form-control" placeholder="${msgImageFilePackageVerNm}" />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<label><spring:message code="admin.image.imageFilePackageRegistDt" /></label>
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
				</div>

				<div class="col-sm-2 text-center search_btn_wrap">
					<button type="submit" class="btn btn-default btn-primary" role="button">
						<i class="fa fa-search"></i> <spring:message code="common.search" />
					</button>
				</div>
			</div>
		</fieldset>
	</form:form>
</div>

<!-- list -->
<div class="table-responsive">
	<table class="table table-striped table-bordered table-hover">
		<colgroup>
			<col width="20%" />
			<col width="15%" />
			<col width="15%" />
			<col width="20%" />
			<col width="20%" />
			<col width="10%" />
		</colgroup>

		<thead>
			<tr>
				<th><spring:message code="admin.image.imageFilePackageIdSq" /></th>
				<th><spring:message code="admin.image.imageFilePackageVerCode" /></th>
				<th><spring:message code="admin.image.imageFilePackageVerNm" /></th>
				<th><spring:message code="admin.image.imageFilePackageNm" /></th>
				<th><spring:message code="admin.image.imageFilePackageRegistDt" /></th>
				<th><spring:message code="common.download" /></th>
			</tr>
		</thead>

		<tbody>
			<c:if test="${empty list}">
				<tr><td colspan="6">데이터가 존재하지 않습니다.</td></tr>
			</c:if>
			<c:forEach var="item" items="${list}">
				<fmt:parseDate var="imageFilePackageRegistDt" value="${item.imageFilePackageRegistDt}" pattern="yyyyMMddHHmmss"/>
				<fmt:formatDate var="packageRegistDt" value="${imageFilePackageRegistDt}" type="both" pattern="yyyy/MM/dd HH:mm:ss"/>
				<tr onclick="javascript:fnSelect('${item.imageFilePackageIdSq}');">
					<td title="${item.imageFilePackageIdSq}">${item.imageFilePackageIdSq}</td>
					<td title="${item.imageFilePackageVerCode}">${item.imageFilePackageVerCode}</td>
					<td title="${item.imageFilePackageVerNm}">${item.imageFilePackageVerNm}</td>
					<td title="${item.imageFilePackageNm}">${item.imageFilePackageNm}</td>
					<td title="${packageRegistDt}">${packageRegistDt}</td>
					<td>
						<a href="javascript:fnDownload('${item.imageFilePackageIdSq}');" class="btn btn-sm btn-primary" role="button">
						<i class="fa fa-downlaod"></i> <spring:message code="common.download" /></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<jsp:include page="/include/paging.jsp" flush="true" />

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">File Upload</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<div class="well well-sm well-primary">
							<form id="fileUploadForm" class="smart-form" role="form" action="upload" enctype="multipart/form-data" method="post">
								<fieldset>
									<section>
										<label class="label">File input</label>
										<div class="input input-file">
											<span class="button"><input type="file" id="file" name="file" onchange="this.parentNode.nextSibling.value = this.value">Browse</span><input type="text" placeholder="Include some files" readonly="">
										</div>
									</section>
								</fieldset>
							</form>
						</div>
					</div>
				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					Cancel
				</button>
				<button type="button" class="btn btn-primary" onclick="javascript:fnUpload();">
					Upload
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
