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
    alert('${message}');
    location.href = "list";
  </script>
</c:if>

<script type="text/javascript">
  <!--
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
  <form:form method="post" modelAttribute="adminGroupEntity" cssClass="smart-form" role="form">
    <input type="hidden" name="useYn" value="Y" />
    <fieldset>
      <section>
        <label class="label">
          <spring:message code="system.admin-group.adminGrpNm" /></label>
        <label class="input">
          <form:input path="adminGrpNm" maxlength="100" cssClass="required" />
        </label>
      </section>

      <section>
        <label class="label">
          <spring:message code="system.admin-group.adminGrpDs" /></label>
        <label class="textarea">
          <form:textarea path="adminGrpDs" rows="5" cssClass="form-control" placeholder="내용을 입력 해 주세요."></form:textarea>
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
        <i class="fa fa-list"></i>
        <spring:message code="common.list" />
      </a>
    </div>
    <custom:auth type="CREATE">
      <div class="btn-group pull-right">
        <button type="button" onclick="save();" class="btn btn-sm btn-primary" role="button">
          <i class="fa fa-plus"></i>
          <spring:message code="common.save" />
        </button>
      </div>
    </custom:auth>
  </div>
</div>
<!-- // button -->