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
		var data = {
			'title': '${programEntity.pgmNm}',
			'key': '${programEntity.pgmId}',
			'activate': 'true',
			'isFolder': '${programEntity.pgmTp != "U"}',
			'upperPgmId': '${programEntity.upperPgmId}',
			'pgmId': '${programEntity.pgmId}',
		};

		parent.appendChild(data);
	</script>
</c:if> --%>

<style type="text/css">
	#urlDsWrap { display: none; }
</style>

<div class="well">
	<form:form id="program-form" method="post" action="form" cssClass="smart-form" modelAttribute="programEntity">
		<form:hidden path="upperPgmId" id="upperPgmId" />
		
		<fieldset>
			<section>
				<label class="label"><spring:message code="system.program.upperPgmNm" /></label>
				<label class="input">
					<input type="input" id="upperPgmNm" readonly="readonly" />
				</label>
			</section>

			<section>
				<label class="label"><spring:message code="system.program.pgmNm" /></label>
				<label class="input">
					<form:input path="pgmNm" maxlength="50" cssClass="required" />
				</label>
			</section>

			<div class="row">
				<section class="col col-6">
					<label class="label"><spring:message code="system.program.rankNo" /></label>
					<label class="input">
						<form:input path="rankNo" maxlength="4" cssClass="required digits" readonly="true" value="0" />
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
					<label class="radio">
						<form:radiobutton path="pgmTp" checked="true" value="U" />
						<i></i>URL
					</label>
					<label class="radio">
						<form:radiobutton path="pgmTp" value="M" />
						<i></i>메뉴
					</label>
				</div>
			</section>

			<div class="row">
				<section class="col col-2">
					<label class="label"><spring:message code="system.program.iconDs" /></label>
					<label class="input">
						<button id="iconDs" name="iconDs" class="btn btn-default" data-iconset="fontawesome" data-rows="5" data-cols="12" role="iconpicker"></button>
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
		<custom:auth type="CREATE">
			<div class="btn-group pull-right">
				<button type="button" onclick="save();" class="btn btn-sm btn-primary" role="button">
					<i class="fa fa-plus"></i> 저장
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

		$('form:first input[name=pgmTp]').change(function() {
			fnChangeMenu( $(this).val() );
		});
		
		$('form:first input[name=pgmTp]:checked').trigger('change');

		// select box
		$('#ynSel').attr('checked', true);

		// 부모 노드 정보
		var parentNode = getParentActiveNode();
		if (parentNode) {
			var level = parentNode.getLevel();
			if (level > 3) {	// 3depth 이후로는 URL 만 등록
				var radio = $('#program-form input:radio[value=M]');
				radio.parent().remove();
			}

			$('#upperPgmId').val(parentNode.key);
			$('#upperPgmNm').val(parentNode.title);

			var len = parentNode.countChildren() + 1;
			$('#rankNo').val(len);
		}
		
		// icon picker
		var $icon = $('#iconDs').iconpicker();
		$icon.iconpicker('setIcon', 'fa-cube');
	});

	// 메뉴타입에 따라 변경
	function fnChangeMenu(pgmTp) {
		if (pgmTp == 'U') {
			fnURLType();
		} else {
			fnMenuType();
		}
	}

	// 메뉴타입 : URL
	function fnURLType() {
		$('#urlDsWrap').show();
		$('#urlDs').addClass('required');
		$(':checkbox.fnc:not(.fnc_menu)')
			.prop('disabled', false);
	}

	// 메뉴타입 : MENU
	function fnMenuType() {
		$('#urlDsWrap').hide();
		$('#urlDs').val('').removeClass('required');
		$(':checkbox.fnc:not(.fnc_menu)')
			.prop('checked', false)
			.prop('disabled', true);
	}

	// 부모 노드 정보
	function getParentActiveNode() {
		return getActiveNode();
	}

	// 저장
	function save() {
		/* var f = $('form:first');
		if (f.valid()) {
			if (confirm('<spring:message code="save.isSave" />')) {
				f.submit();
			}
		} */
		
		// ajax submit
		var options = {
			beforeSubmit: function(arr, $form, options) {
				if ($form.valid()) {
					if (confirm('<spring:message code="save.isSave" />')) {
						return true;
					}
				}
				return false;
			},
			success: successResponse,
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

			if (json.programEntity) {
				var programEntity = json.programEntity;
				var data = {
					'key': programEntity.pgmId,
					'title': programEntity.pgmNm,
					'folder': (programEntity.pgmTp != 'U') ? true : false,
					'upperPgmId': programEntity.upperPgmId,
					'pgmId': programEntity.pgmId,
					'iconDs': json.programEntity.iconDs
				};

				appendChild(data);
			}
		}
	}
//-->
</script>