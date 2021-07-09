<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom" uri="/tags/custom-taglib" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<!-- Messages -->
<c:if test="${not empty message}">
	<script type="text/javascript">
		alert("${message}");
	</script>
</c:if>

<script type="text/javascript">
<!--
	// ready
	$(document).ready(function() {
	});

	// 저장
	function save() {
		var frm = $('form:first');
		if (frm.valid()) {
			if (confirm('<spring:message code="save.isSave" />')) {
				frm.submit();
			}
		}
	}
//-->
</script>

<div class="well">
	<form:form method="post" action="update" modelAttribute="codeGroupEntity" cssClass="smart-form" role="form">
		<fieldset>
			<div class="row">
				<section class="col col-6">
					<label class="label"><spring:message code="system.code-group.cdgrpCd" /></label>
					<label class="input">
						<form:input path="cdgrpCd" maxlength="10" cssClass="required" readonly="true" />
					</label>
				</section>

				<section class="col col-6">
					<label class="label"><spring:message code="system.code-group.cdgrpNm" /></label>
					<label class="input">
						<form:input path="cdgrpNm" maxlength="50" cssClass="required" />
					</label>
				</section>
			</div>

			<div class="row">
				<section class="col col-6">
					<label class="label"><spring:message code="system.code-group.orderNo" /></label>
					<label class="input">
						<form:input path="orderNo" maxlength="4" cssClass="required digits" />
					</label>
				</section>

				<section class="col col-6">
					<label class="label"><spring:message code="system.code-group.useYn" /></label>
					<label class="select">
						<form:select path="useYn" cssClass="form-control">
							<form:option value="Y">사용</form:option>
							<form:option value="N">미사용</form:option>
						</form:select>
						<i></i>
					</label>
				</section>
			</div>

			<section>
				<label class="label"><spring:message code="system.code-group.cdgrpDs" /></label>
				<label class="textarea">
<form:textarea path="cdgrpDs" id="cdgrpDs" rows="5" class="form-control" placeholder="내용을 입력 해 주세요."></form:textarea>
				</label>
			</section>
		</fieldset>
	</form:form>
</div>

<!-- button -->
<div class="row">
	<div class="col-sm-12">
		<div class="btn-group">
			<a href="javascript:fnLatestPage();" class="btn btn-sm btn-default" role="button">
				<i class="fa fa-list"></i> <spring:message code="common.list" />
			</a>
		</div>
		<custom:auth type="UPDATE">
			<div class="btn-group pull-right">
				<button type="button" onclick="save();" class="btn btn-sm btn-primary" role="button">
					<i class="fa fa-plus"></i> <spring:message code="common.save" />
				</button>
			</div>
		</custom:auth>
	</div>
</div>
<!-- // button -->

<p></p>

<jsp:include page="/WEB-INF/views/system/code-group/update-sub.jsp"/>
