<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom" uri="/tags/custom-taglib" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<!-- Messages -->
<c:if test="${not empty message}">
	<script type="text/javascript">
		alert('${message}');
		//location.href = "list";
	</script>
</c:if>

<script type="text/javascript">
<!--
	$(document).ready(function() {
		$.ajaxSetup({
			cache: false
		});

		// Admin Detail Popup
		getAdminDetailPop();

	});

	// 관리자 상세 팝업
	function getAdminDetailPop() {
		var url = '${REQUEST_CONTEXT_PATH}/system/admin/updatePop' + '?adminIdSq=${sessionScope.SESSION_USER_SEQ}';
		$('#admin-detail').load(url);
	}

	//저장
	function adminSave() {
		var f = $('#adminFormPop');
		if (f.valid()) {
			if (confirm('<spring:message code="save.isSave" />')) {
				f.attr("action", "${REQUEST_CONTEXT_PATH}/system/admin/updatePop");
				f.submit();
			}
		}
	}

	/*function backspace_event(id,event) {
		if (event.keyCode == 8) {
			var string = $(id).val();
			$(id).val(string.substring(0, string.length-1));
		}
	}*/

//-->
</script>

<div>
	<div>
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="myModalLabel">Profile</h4>
			</div>
			<div id="admin-detail" class="modal-body"></div>
			<div class="modal-footer">
				<button type="button" onclick="javascript:adminSave();" class="btn btn-primary">
					Save
				</button>
			</div>
		</div>
	</div>
</div>