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
		alert("${message}");
		location.href = "list?sensorDataFilePackageIdSq=${sensorDataFileInfo.sensorDataFilePackageIdSq}";
	</script>
</c:if>



<div class="well">
	<form:form method="post" action="update" modelAttribute="sensorDataFileInfo" cssClass="smart-form" role="form">
		<form:hidden path="sensorDataFileSq" />
		<%--<form:hidden path="imageFilePackageIdSq" />--%>

		<fieldset>
			<legend><i class="fa fa-file-image-o"></i> 데이터파일정보<!--<spring:message code="admin.image.imageFileInfo" />--></legend>

			<div class="row">
				<section class="col col-6">
					<label class="label"><span class="asterisk">*</span>일련번호</label>
					<label class="input">
						<form:input path="sensorDataFileSq" maxlength="10" cssClass="required" disabled="true" />
					</label>
				</section>
				<section class="col col-6">
					<label class="label"><span class="asterisk">*</span>데이터그룹<!-- <spring:message code="admin.image.imageFileGroup" /> --></label>
					<label class="input">
						<form:input path="sensorDataFileGroup" maxlength="100" cssClass="required" disabled="true" />
					</label>
				</section>
			</div>

			<div class="row">
				<section class="col col-6">
					<label class="label"><span class="asterisk">*</span><spring:message code="admin.image.imageFileNm" /></label>
					<label class="input">
						<form:input path="sensorDataFileNm" maxlength="4" cssClass="required" />
					</label>
				</section>
				<section class="col col-6">
					<label class="label"><span class="asterisk">*</span><spring:message code="admin.image.imageFileTypeCd" /></label>
    	        	<label class="select">
		                <tags:select name="sensorDataFileTypeCd" group="TA007" value="${sensorDataFileInfo.sensorDataFileTypeCd}">
		                </tags:select>
		                <i></i>
            		</label>
				</section>
			</div>

			<div class="row">
				<section class="col col-6">
					<label class="label"><span class="asterisk">*</span><spring:message code="admin.image.imageFilePath" /></label>
					<label class="input">
						<form:input path="sensorDataFilePath" maxlength="4" cssClass="required" />
					</label>
				</section>
				<section class="col col-6">
					<label class="label"><span class="asterisk">*</span><spring:message code="admin.image.imageFileSize" /></label>
					<label class="input">
						<form:input path="sensorDataFileSize" maxlength="4" cssClass="required digits" />
					</label>
				</section>
			</div>

			<div class="row">
				<section class="col col-6">
					<label class="label"><span class="asterisk">*</span><spring:message code="admin.image.imageFileScaleX" /></label>
					<label class="input">
						<form:input path="sensorDataFileScaleX" maxlength="4" cssClass="required digits" />
					</label>
				</section>
				<section class="col col-6">
					<label class="label"><span class="asterisk">*</span><spring:message code="admin.image.imageFileScaleY" /></label>
					<label class="input">
						<form:input path="sensorDataFileScaleY" maxlength="4" cssClass="required digits" />
					</label>
				</section>
			</div>

			<div class="row">
				<section class="col col-6">
					<label class="label">미리보기</label>
					<img src="/uploadHome${sensorDataFileInfo.sensorDataFilePath}" width="200" />
					<%--
					<label class="label"><span class="asterisk">*</span><spring:message code="admin.image.imageFileDownloadPathCd" /></label>
    	        	<label class="select">
		                <tags:select name="imageFileDownloadPathCd" group="TA005" value="${imageFileInfo.imageFileDownloadPathCd}">
		                </tags:select>
		                <i></i>
            		</label>
            		--%>
				</section>
			</div>
		</fieldset>
		
	</form:form>
	
	<form:form method="post" action="update" modelAttribute="sensorDataJsonFileInfo" cssClass="smart-form" role="form">
	
		<fieldset>
			<legend><i class="fa fa-file-text-o"></i> <spring:message code="admin.image.imageJsonFileInfo" /></legend>
			<div class="btn-group pull-right">
			<%--
				<button type="button" onclick="fnInitTaggingData(${imageJsonFileInfo.imageJsonFileSq});" class="btn btn-sm btn-primary" role="button">
					<i class="glyphicon glyphicon-trash"></i> <spring:message code="admin.image.initImageTaggingInfo" />
				</button> --%>
			</div>

			<div class="row">
				<section class="col col-6">
					<label class="label"><span class="asterisk">*</span><spring:message code="admin.image.imageFileSq" /></label>
					<label class="input">
						<form:input path="sensorDataFileSq" maxlength="10" cssClass="required" disabled="true" value="${sensorDataFileInfo.sensorDataFileSq}" />
					</label>
				</section>
			</div>
			
			<div class="row">
        		<section class="col col-md-12">
	    	        <label class="label"><spring:message code="admin.image.imageJsonFileDesc" /></label>
	    	         <label class="textarea">
		                <form:textarea path="sensorDataJsonFileDesc" rows="15" maxlength='2000'/>
            		</label>
        		</section>
        	</div>

			<div class="row">
        		<section class="col col-md-12">
	    	        <label class="label"><spring:message code="admin.image.imageJsonXmlConvFileDesc" /></label>
	    	         <label class="textarea">
		                <form:textarea path="sensorDataJsonXmlConvFileDesc" rows="15" maxlength='2000'/>
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
		<%--
		<custom:auth authUrl="/sensorData/sensorDataInfo/" type="DELETE">
			<div class="btn-group pull-right">
				<button type="button" onclick="fnImageFileDelete();" class="btn btn-sm btn-primary" role="button">
					<i class="glyphicon glyphicon-trash"></i> <spring:message code="common.delete" />
				</button>
			</div>
		</custom:auth>
		--%>
	</div>
</div>
<!-- // button -->
