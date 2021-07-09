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
    location.href = "list?cdgrpCd=${codeEntity.cdgrpCd}";
  </script>
</c:if>

<script type="text/javascript">
  <!--
  // 저장
  function fnCodeSave() {
    var frm = $('form:last');
    if (frm.valid()) {
      if (confirm('<spring:message code="save.isSave" />')) {
        $.ajax({
          type: 'POST',
          url: '${REQUEST_CONTEXT_PATH}/system/code/update',
          data: frm.serialize(),
          success: function (data) {
            if (data.resultCd == 'success') { // 성공
              alert(data.resultMsg);
              getCodeList();
            } else { // 실패
              alert(data.resultMsg);
            }
          },
          error: function (xhr) {
            alert('error : ' + xhr.status + '\n' + xhr.statusText);
          },
          complete: function (XMLHttpRequest, textStatus) {
            //alert(XMLHttpRequest + '\n' + textStatus);
          }
        });
      }
    }
  }
  //
  -->
</script>

<div class="well">
  <form:form method="post" action="update" modelAttribute="codeEntity" cssClass="smart-form" role="form">
    <form:hidden path="cdgrpCd" />

    <fieldset>
      <div class="row">
        <section class="col col-6">
          <label class="label">
            <spring:message code="system.code.codeCd" /></label>
          <label class="input">
            <form:input path="codeCd" maxlength="10" cssClass="required" readonly="true" />
          </label>
        </section>
        <section class="col col-6">
          <label class="label">
            <spring:message code="system.code.codeNm" /></label>
          <label class="input">
            <form:input path="codeNm" maxlength="100" cssClass="required" />
          </label>
        </section>
      </div>

      <div class="row">
        <section class="col col-6">
          <label class="label">
            <spring:message code="system.code.orderNo" /></label>
          <label class="input">
            <form:input path="orderNo" maxlength="4" cssClass="required digits" />
          </label>
        </section>
        <section class="col col-6">
          <label class="label">
            <spring:message code="system.code.useYn" /></label>
          <label class="select">
            <form:select path="useYn" cssClass="form-control">
              <form:option value="Y">사용</form:option>
              <form:option value="N">미사용</form:option>
            </form:select>
            <i></i>
          </label>
        </section>
      </div>

      <div class="row">
        <section class="col col-6">
          <label class="label">
            <spring:message code="system.code.ifCd" /></label>
          <label class="input">
            <form:input path="ifCd" maxlength="8" cssClass="form-control" />
          </label>
        </section>
      </div>

      <section>
        <label class="label">
          <spring:message code="system.code.codeDs" /></label>
        <label class="textarea">
          <form:textarea path="codeDs" id="codeDs" rows="5" class="form-control" placeholder="내용을 입력 해 주세요."></form:textarea>
        </label>
      </section>
    </fieldset>
  </form:form>

</div>

<!-- button -->
<div class="row">
  <div class="col-sm-12">
    <div class="btn-group">
      <a href="javascript:getCodeList();" class="btn btn-sm btn-default" role="button">
        <i class="fa fa-list"></i>
        <spring:message code="common.list" />
      </a>
    </div>
    <custom:auth authUrl="/system/code-group/" type="UPDATE">
      <div class="btn-group pull-right">
        <button type="button" onclick="fnCodeSave();" class="btn btn-sm btn-primary" role="button">
          <i class="fa fa-plus"></i>
          <spring:message code="common.save" />
        </button>
      </div>
    </custom:auth>
  </div>
</div>
<!-- // button -->