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

<form:form id="adminFormPop" method="post" action="updatePop" modelAttribute="adminEntity" cssClass="smart-form" role="form">
	<form:hidden path="adminIdSq"/>

	<fieldset>
		<div class="row">
			<section class="col col-6">
				<label class="label"><span class="asterisk">*</span><spring:message code="system.admin.adminId" /></label>
				<label class="input">
					<form:input path="adminId" minlength="4" maxlength="100" readonly="true" cssClass="required" />
				</label>
			</section>

			<section class="col col-6">
				<label class="label"><span class="asterisk">*</span><spring:message code="system.admin.adminNm" /></label>
				<label class="input">
					<form:input path="adminNm" minlength="2" maxlength="20" cssClass="required" />
				</label>
			</section>

			<section class="col col-6">
				<label class="label"><span class="asterisk">*</span><spring:message code="system.admin.adminPw" /></label>
				<label class="input">
					<i class="icon-append fa fa-question-circle"></i>
					<form:password path="adminPw" minlength="4" maxlength="20" />
					<b class="tooltip tooltip-bottom-right">
						<i class="fa fa-warning txt-color-teal"></i>
						비밀번호를 입력하실경우 해당 관리자의 비밀번호가 변경됩니다.
						<br/>
						공란으로 비워두실 경우는 변경되지 않습니다.
					</b>
				</label>
			</section>

			<section class="col col-6">
				<label class="label">패스워드 확인</label>
				<label class="input">
					<i class="icon-append fa fa-question-circle"></i>
					<input type="password" id="confirmPasswd" name="confirmPasswd" minlength="4" maxlength="20" />
					<b class="tooltip tooltip-bottom-right">
						<i class="fa fa-warning txt-color-teal"></i>
						패스워드 확인을 위해 동일한 패스워드를 입력해주세요.
					</b>
				</label>
			</section>

			<section class="col col-6">
				<label class="label"><spring:message code="system.admin.adminGrpId" /></label>
				<label class="select">
					<form:select path="adminGrpId" items="${adminGroup}" itemLabel="adminGrpNm" itemValue="adminGrpId" disabled="true">
					</form:select>
					<i></i>
				</label>
			</section>

			<section class="col col-6">
        		<label class="label"><spring:message code="system.admin.adminCd" /></label>
   	        	<label class="select">
	                <tags:select id="adminCd" name="adminCd" group="TA002" value="${adminEntity.adminCd}" disabled="true">
	                </tags:select>
	                <i></i>
           		</label>
           	</section>

           	<section class="col col-6">
    	        <label class="label"><spring:message code="system.admin.adminStatusCd" /></label>
   	        	<label class="select">
	                <tags:select name="adminStatusCd" group="TA003" value="${adminEntity.adminStatusCd}" disabled="true">
	                </tags:select>
	                <i></i>
           		</label>
       		</section>

       		<section class="col col-md-12">
    	        <label class="label"><spring:message code="system.admin.adminDesc" /></label>
   	        	<label class="textarea">
	                <form:textarea path="adminDesc" rows="4" maxlength='2000' readonly="true" />
           		</label>
       		</section>
		</div>
	</fieldset>
</form:form>