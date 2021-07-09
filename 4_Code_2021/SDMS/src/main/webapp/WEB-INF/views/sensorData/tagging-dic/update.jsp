<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom" uri="/tags/custom-taglib" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<!-- Messages -->
<%-- <c:if test="${not empty message}">
	<script type="text/javascript">
		alert('${message}');
		parent.changeTitle('${programEntity.pgmNm}');
	</script>
</c:if> --%>

<div class="well">
	<form:form id="program-form" method="post" action="update" cssClass="smart-form" modelAttribute="taggingDicEntity">
		<form:hidden path="imageTaggingDataDicIdSq" />
		<form:hidden path="upperImageTaggingDataDicId" />

		<fieldset>
			<section>
				<label class="label"><spring:message code="admin.image.imageTaggingDataDicNm" /></label>
				<label class="input">
					<form:input path="imageTaggingDataDicNm" maxlength="50" cssClass="txt4 required" />
				</label>
			</section>

			<div class="row">
				<section class="col col-6">
					<label class="label"><spring:message code="admin.image.imageTaggingDataDicLevel" /></label>
					<label class="input">
						<form:input path="imageTaggingDataDicLevel" maxlength="4" cssClass="required digits" readonly="true" />
					</label>
				</section>
				<section class="col col-6">
					<label class="label"><spring:message code="admin.image.useYn" /></label>
					<label class="select">
						<form:select path="useYn">
							<form:option value="Y">사용</form:option>
							<form:option value="N">미사용</form:option>
						</form:select>
						<i></i>
					</label>
				</section>
			</div>

			<div class="row">
				<section class="col col-md-12">
	    	        <label class="label"><spring:message code="admin.image.imageFilePackageDesc" /></label>
    	        	<label class="textarea">
		                <form:textarea path="imageTaggingDataDicDesc" rows="4" maxlength='256'/>
            		</label>
        		</section>
			</div>
		</fieldset>
	</form:form>
</div>

<!-- button -->
<div class="row">
	<div class="col-sm-12">
		<custom:auth type="UPDATE">
			<div class="btn-group pull-right">
				<button type="button" onclick="fnSave();" class="btn btn-sm btn-primary" role="button">
					<i class="fa fa-plus"></i> <spring:message code="common.save" />
				</button>
			</div>
		</custom:auth>
	</div>
</div>
<!-- // button -->

<script type="text/javascript">
<!--
	// Check for Checkbox
	$(function() {
		var treeLevel = "${taggingDicEntity.imageTaggingDataDicLevel}";
		if (treeLevel == "3") {
			$('#btnAdd').attr('disabled', true).prop('disabled', true);
		} else {
			$('#btnAdd').attr('disabled', false).prop('disabled', false);
		}
	});

	// 저장
	function fnSave() {
		// ajax submit
		var options = {
			dataType : 'json',
			beforeSubmit: function(arr, $form, options) {
				if ($form.valid()) {
					if (confirm('<spring:message code="save.isSave" />')) {
						return true;
					}
				}
				return false;
			},
			success: successResponse
		};

		$('#program-form').ajaxSubmit(options);
	}

	// success response
	function successResponse(responseText, statusText, xhr, $form) {

		if (responseText && typeof responseText == 'object') {
			responseText = JSON.stringify(responseText);
		}

		var json = jQuery.parseJSON(responseText);
		if (json && json.message) {
			alert(json.message);
			$("#"+json.taggingDicEntity.imageTaggingDataDicIdSq).text(json.taggingDicEntity.imageTaggingDataDicNm);
		}
	}
//-->
</script>