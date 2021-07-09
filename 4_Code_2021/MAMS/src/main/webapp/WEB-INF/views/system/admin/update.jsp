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
  $(document).ready(function () {

    $('textarea[maxlength]').keyup(function () {
      //get the limit from maxlength attribute
      var limit = parseInt($(this).attr('maxlength'));
      //get the current text inside the textarea
      var text = $(this).val();
      //count the number of characters in the text
      var chars = text.length;

      //check if there are more characters then allowed
      if (chars > limit) {
        //and if there are use substr to get the text before the limit
        var new_text = text.substr(0, limit);

        //and change the current text with the new text
        $(this).val(new_text);
      }
    });

  });


  $(function () {
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
  //
  -->
</script>

<div class="well">
  <form:form method="post" action="update" modelAttribute="adminEntity" cssClass="smart-form" role="form">
    <form:hidden path="adminIdSq" />

    <fieldset>
      <legend><i class="fa fa-user"></i> 관리자정보</legend>

      <div class="row">
        <section class="col col-6">
          <label class="label"><span class="asterisk">*</span>
            <spring:message code="system.admin.adminId" /></label>
          <label class="input">
            <form:input path="adminId" minlength="4" maxlength="100" readonly="true" cssClass="required" />
          </label>
        </section>

        <section class="col col-6">
          <label class="label"><span class="asterisk">*</span>
            <spring:message code="system.admin.adminNm" /></label>
          <label class="input">
            <form:input path="adminNm" minlength="2" maxlength="20" cssClass="required" />
          </label>
        </section>

        <section class="col col-6">
          <label class="label"><span class="asterisk">*</span>
            <spring:message code="system.admin.adminPw" /></label>
          <label class="input">
            <i class="icon-append fa fa-question-circle"></i>
            <form:password path="adminPw" minlength="4" maxlength="20" />
            <b class="tooltip tooltip-bottom-right">
              <i class="fa fa-warning txt-color-teal"></i>
              비밀번호를 입력하실경우 해당 관리자의 비밀번호가 변경됩니다.
              <br />
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
          <label class="label">
            <spring:message code="system.admin.adminGrpId" /></label>
          <label class="select">
            <form:select path="adminGrpId" items="${adminGroup}" itemLabel="adminGrpNm" itemValue="adminGrpId">
            </form:select>
            <i></i>
          </label>
        </section>

        <section class="col col-6">
          <label class="label">
            <spring:message code="system.admin.adminCd" /></label>
          <label class="select">
            <tags:select id="adminCd" name="adminCd" group="TA002" value="${adminEntity.adminCd}">
            </tags:select>
            <i></i>
          </label>
        </section>

        <section class="col col-6">
          <label class="label">
            <spring:message code="system.admin.adminStatusCd" /></label>
          <label class="select">
            <tags:select name="adminStatusCd" group="TA003" value="${adminEntity.adminStatusCd}">
            </tags:select>
            <i></i>
          </label>
        </section>

        <section class="col col-md-12">
          <label class="label">
            <spring:message code="system.admin.adminDesc" /></label>
          <label class="textarea">
            <form:textarea path="adminDesc" rows="4" maxlength='2000' />
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
							- 휴대폰번호형식 : 01*-****-****
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
						- 홈페이지 주소 형식 : http://www.naver.com, http://www.daum.com
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
        <i class="fa fa-list"></i>
        <spring:message code="common.list" />
      </a>
    </div>
    <custom:auth type="UPDATE">
      <div class="btn-group pull-right">
        <button type="button" onclick="save();" class="btn btn-sm btn-primary" role="button">
          <i class="fa fa-save"></i>
          <spring:message code="common.save" />
        </button>
      </div>
    </custom:auth>
  </div>
</div>
<!-- // button -->