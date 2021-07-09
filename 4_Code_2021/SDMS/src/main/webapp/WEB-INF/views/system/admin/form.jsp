<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom" uri="/tags/custom-taglib" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<!-- Messages -->
<c:if test="${not empty error}">
	<script type="text/javascript">
		alert('<spring:message code="user.duplicate" />');
	</script>
</c:if>

<c:if test="${not empty message}">
	<script type="text/javascript">
		alert('${message}');
		location.href = "list";
	</script>
</c:if>

<script type="text/javascript">
<!--


	$(function() {

		$('textarea[maxlength]').keyup(function(){
		    //get the limit from maxlength attribute
		    var limit = parseInt($(this).attr('maxlength'));
		    //get the current text inside the textarea
		    var text = $(this).val();
		    //count the number of characters in the text
		    var chars = text.length;

		    //check if there are more characters then allowed
		    if(chars > limit){
		        //and if there are use substr to get the text before the limit
		        var new_text = text.substr(0, limit);

		        //and change the current text with the new text
		        $(this).val(new_text);
		    }
		});

		// Validator
		$('form:first').validate({
			rules: {
				confirmPasswd: {
					equalTo: '#adminPw'
				}
			},
			messages: {
				adminId: {
					required: '<spring:message code="system.admin.adminId.validate.required" />'
				},
				adminNm: {
					required: '<spring:message code="system.admin.adminNm.validate.required" />'
				},
				adminPw: {
					required: '<spring:message code="system.admin.adminPw.validate.required" />',
					minlength: '<spring:message code="system.admin.adminPw.validate.minlength" />'
				},
				confirmPasswd: {
					equalTo: '<spring:message code="system.admin.confirmPasswd.validate.equalTo" />'
				}
			}
		});
	});

	//저장
	function save() {
		var f = $('form:first');
		if (f.valid()) {
			if (confirm('<spring:message code="save.isSave" />')) {
				f.submit();
			}
		}
	}
//-->
</script>

<div class="well">
	<form:form method="post" action="form" modelAttribute="adminEntity" cssClass="smart-form" role="form">

		<fieldset>
			<legend><i class="fa fa-user"></i> 관리자정보</legend>

			<div class="row">
				<section class="col col-6">
					<label class="label"><span class="asterisk">*</span><spring:message code="system.admin.adminId" /></label>
					<label class="input">
						<form:input path="adminId" minlength="4" maxlength="10" cssClass="required" />
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
						<form:password path="adminPw" minlength="4" maxlength="20" />
					</label>
				</section>

				<section class="col col-6">
					<label class="label">패스워드 확인</label>
					<label class="input">
						<input type="password" id="confirmPasswd" name="confirmPasswd" minlength="4" maxlength="20" />
					</label>
				</section>

				<section class="col col-6">
					<label class="label"><spring:message code="system.admin.adminGrpId" /></label>
					<label class="select">
						<form:select path="adminGrpId" items="${adminGroup}" itemLabel="adminGrpNm" itemValue="adminGrpId">
						</form:select>
						<i></i>
					</label>
				</section>

				<section class="col col-6">
	        		<label class="label"><spring:message code="system.admin.adminCd" /></label>
    	        	<label class="select">
		                <tags:select id="adminCd" name="adminCd" group="TA002">
		                </tags:select>
		                <i></i>
            		</label>
            	</section>

            	<section class="col col-6">
	    	        <label class="label"><spring:message code="system.admin.adminStatusCd" /></label>
    	        	<label class="select">
		                <tags:select name="adminStatusCd" group="TA003">
		                </tags:select>
		                <i></i>
            		</label>
        		</section>

        		<section class="col col-md-12">
	    	        <label class="label"><spring:message code="system.admin.adminDesc" /></label>
    	        	<label class="textarea">
		                <form:textarea path="adminDesc" rows="4" maxlength='2000'/>
            		</label>
        		</section>
			</div>
		</fieldset>

		<%-- <fieldset>
			<legend><i class="fa fa-phone"></i> 연락처정보</legend>

			<div class="row">
				<section class="col col-6">
					<label class="label"><spring:message code="system.admin.telNo" /></label>
					<label class="input">
						<form:input path="telNo" maxlength="18" />
					</label>
				</section>

				<section class="col col-6">
					<label class="label"><spring:message code="system.admin.phoneNo" /></label>
					<label class="input">
						<form:input path="phoneNo" maxlength="18" data-mask="019-9999-9999" data-placeholder="X" />
						<p class="note">
							- 01*-****-****
						</p>
					</label>
				</section>

				<section class="col col-6">
					<label class="label"><spring:message code="system.admin.emailDs" /></label>
					<label class="input">
						<form:input path="emailDs" maxlength="256" cssClass="email" />
					</label>
				</section>

				<section class="col col-6">
	    	        <label class="label"><spring:message code="system.admin.countryCd" /></label>
    	        	<label class="select">
		                <form:select path="countryCd">
		                	<form:option value="KR"></form:option>
		                </form:select>
		                <i></i>
            		</label>
        		</section>
			</div>
		</fieldset> --%>

		<%-- <fieldset>
			<legend><i class="fa fa-building"></i> 회사정보</legend>

			<div class="row">
    			<section class="col col-6">
	    	        <label class="label"><spring:message code="system.admin.companyNm" /></label>
    	        	<label class="input">
		                <form:input path="companyNm" maxlength="64" />
            		</label>
            	</section>

            	<section class="col col-6">
	    	        <label class="label"><spring:message code="system.admin.deptNm" /></label>
    	        	<label class="input">
		                <form:input path="deptNm" maxlength="64" />
            		</label>
        		</section>

        		<section class="col col-6">
	    	        <label class="label"><spring:message code="system.admin.positionNm" /></label>
    	        	<label class="input">
		                <form:input path="positionNm" maxlength="64" />
            		</label>
            	</section>
			</div>

           	<section>
    	        <label class="label"><spring:message code="system.admin.homepageAddr" /></label>
   	        	<label class="input">
	                <form:input path="homepageAddr" maxlength="512" cssClass="url" />
					<p class="note">
						- http://www.naver.com, http://www.daum.com
					</p>
           		</label>
       		</section>

        	<section>
        		<label class="label"><spring:message code="system.admin.companyAddr" /></label>
    	       	<label class="input">
		               <form:input path="companyAddr" maxlength="256" />
            	</label>
        	</section>
		</fieldset> --%>
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
		<custom:auth type="CREATE">
			<div class="btn-group pull-right">
				<button type="button" onclick="save();" class="btn btn-sm btn-primary" role="button">
					<i class="fa fa-save"></i> <spring:message code="common.save" />
				</button>
			</div>
		</custom:auth>
	</div>
</div>
<!-- // button -->