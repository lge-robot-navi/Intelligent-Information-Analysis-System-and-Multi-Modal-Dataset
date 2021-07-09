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

	});


	$(function() {
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
	<form:form method="post" action="update" modelAttribute="imageFilePackageInfo" cssClass="smart-form" role="form">
		<form:hidden path="imageFilePackageIdSq"/>

		<fieldset>
			<legend><i class="fa fa-archive"></i> <spring:message code="admin.image.imageFilePackageInfo" /></legend>

			<div class="row">
				<section class="col col-6">
					<label class="label"><span class="asterisk">*</span><spring:message code="admin.image.imageFilePackageIdSq" /></label>
					<label class="input">
						<form:input path="imageFilePackageIdSq" maxlength="20" disabled="true" cssClass="required" />
					</label>
				</section>

				<section class="col col-6">
					<label class="label"><span class="asterisk">*</span><spring:message code="admin.image.imageFilePackageVerCode" /></label>
					<label class="input">
						<form:input path="imageFilePackageVerCode" maxlength="8" cssClass="required digits" />
					</label>
				</section>

				<section class="col col-6">
					<label class="label"><span class="asterisk">*</span><spring:message code="admin.image.imageFilePackageVerNm" /></label>
					<label class="input">
						<form:input path="imageFilePackageVerNm" maxlength="64" cssClass="required" />
					</label>
				</section>

				<section class="col col-6">
					<label class="label"><span class="asterisk">*</span><spring:message code="admin.image.imageFilePackageNm" /></label>
					<label class="input">
						<form:input path="imageFilePackageNm" maxlength="256" cssClass="required" />
					</label>
				</section>

				<section class="col col-6">
					<label class="label"><span class="asterisk">*</span><spring:message code="admin.image.imageFilePackagePath" /></label>
					<label class="input">
						<form:input path="imageFilePackagePath" maxlength="512" cssClass="required" />
					</label>
				</section>

				<section class="col col-6">
					<label class="label"><span class="asterisk">*</span><spring:message code="admin.image.imageFilePackageSize" /></label>
					<label class="input">
						<form:input path="imageFilePackageSize" maxlength="20" cssClass="required digits" />
					</label>
				</section>

        		<section class="col col-md-12">
	    	        <label class="label"><spring:message code="admin.image.imageFilePackageDesc" /></label>
    	        	<label class="textarea">
		                <form:textarea path="imageFilePackageDesc" rows="4" maxlength='256'/>
            		</label>
        		</section>
			</div>
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
					<i class="fa fa-save"></i> <spring:message code="common.save" />
				</button>
			</div>
		</custom:auth>
	</div>
</div>
<!-- // button -->

<p></p>

<jsp:include page="/WEB-INF/views/image/imageFilePackageInfo/update-sub.jsp"/>
