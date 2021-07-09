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
	<form:form id="program-form" method="post" action="update" cssClass="smart-form" modelAttribute="programEntity">
		<form:hidden path="pgmId" />
		<form:hidden path="upperPgmId" />
		<form:hidden path="pgmTp" />

		<fieldset>
			<section>
				<label class="label"><spring:message code="system.program.pgmNm" /></label>
				<label class="input">
					<form:input path="pgmNm" maxlength="50" cssClass="txt4 required" />
				</label>
			</section>

			<div class="row">
				<section class="col col-6">
					<label class="label"><spring:message code="system.program.rankNo" /></label>
					<label class="input">
						<form:input path="rankNo" maxlength="4" cssClass="required digits" readonly="true" />
					</label>
				</section>
				<section class="col col-6">
					<label class="label"><spring:message code="system.program.useYn" /></label>
					<label class="select">
						<form:select path="useYn">
							<form:option value="Y">사용</form:option>
							<form:option value="N">미사용</form:option>
						</form:select>
						<i></i>
					</label>
				</section>
			</div>

			<section>
				<label class="label"><spring:message code="system.program.pgmTp" /></label>
				<div class="inline-group">
					<label class="radio state-disabled">
						<form:radiobutton path="pgmTp" disabled="true" value="U" />
						<i></i>URL
					</label>
					<label class="radio state-disabled">
						<form:radiobutton path="pgmTp" disabled="true" value="M" />
						<i></i>메뉴
					</label>
				</div>
			</section>
			
			<div class="row">
				<section class="col col-2">
					<label class="label"><spring:message code="system.program.iconDs" /></label>
					<label class="input">
						<button id="iconDs" name="iconDs" class="btn btn-default" data-iconset="fontawesome" data-rows="6" data-cols="15" role="iconpicker"></button>
					</label>
				</section>
	
				<section id="urlDsWrap" class="col col-10">
					<label class="label"><spring:message code="system.program.urlDs" /></label>
					<label class="input">
						<form:input path="urlDs" id="urlDs" maxlength="256" cssClass="required" />
					</label>
				</section>
			</div>

			<section>
				<label class="label"><spring:message code="system.program.functions" /></label>
				<div class="inline-group">
					<label class="checkbox">
						<form:checkbox path="ynSel" id="ynSel" value="Y" cssClass="fnc fnc_menu" />
						<i></i><spring:message code="system.program.ynSel" /></label>
					<label class="checkbox">
						<form:checkbox path="ynIns" id="ynIns" value="Y" cssClass="fnc fnc_url" />
						<i></i><spring:message code="system.program.ynIns" /></label>
					<label class="checkbox">
						<form:checkbox path="ynUpd" id="ynUpd" value="Y" cssClass="fnc fnc_url" />
						<i></i><spring:message code="system.program.ynUpd" /></label>
					<label class="checkbox">
						<form:checkbox path="ynDel" id="ynDel" value="Y" cssClass="fnc fnc_url" />
						<i></i><spring:message code="system.program.ynDel" /></label>
				</div>
			</section>
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
		fnChangeMenu("${programEntity.pgmTp}");
		
		// icon picker
		var $icon = $('#iconDs').iconpicker();
		$icon.iconpicker('setIcon', '${programEntity.iconDs}');
	});
	
	// 메뉴타입에 따라 변경
	function fnChangeMenu(pgmTp) {
		if (pgmTp == 'U') {
			fnURLType();
		} else {
			fnMenuType();
		}
	}

	// URL
	function fnURLType() {
		$('#urlDsWrap').show();
		$('#urlDs').addClass('required');
		$(':checkbox.fnc:not(.fnc_menu)')
			.prop('disabled', false);
	}

	// MENU
	function fnMenuType() {
		$('#urlDsWrap').hide();
		$('#urlDs').val('').removeClass('required');
		$(':checkbox.fnc:not(.fnc_menu)')
			.prop('checked', false)
			.prop('disabled', true);
	}

	// 저장
	function fnSave() {
		/* var f = $('form:first');
		if (f.valid()) {
			if (confirm('<spring:message code="save.isSave" />')) {
				f.submit();
			}
		} */

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
			changeTitle(json.programEntity.pgmNm, json.programEntity.iconDs);
		}
	}
//-->
</script>