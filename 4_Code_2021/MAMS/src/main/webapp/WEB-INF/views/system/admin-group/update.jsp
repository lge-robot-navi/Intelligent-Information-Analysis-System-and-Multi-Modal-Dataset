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
  $(function () {
    // program 목록
    getProgram();

    // all check
    $('#allChk').click(function () {
      fnAllCheck($(this).is(':checked'));
    });
  });

  // 저장
  function save() {
    var f = $('form:first');
    if (f.valid()) {
      if (confirm('<spring:message code="save.isSave" />')) {
        f.submit();
      }
    }
  }

  // 권한저장
  function programSave() {
    fnProgramSave();
  }

  // 프로그램 목록
  function getProgram() {
    var $target = $('#program');
    $.ajax({
      url: '${REQUEST_CONTEXT_PATH}/system/admin-group/group-auth/program',
      type: 'get',
      dataType: 'html',
      cache: false,
      data: {
        adminGrpId: '${adminGroupEntity.adminGrpId}'
      },
      success: function (data) {
        $target.html(data);
      },
      error: function (xhr) {
        alert('error : ' + xhr.status + '\n' + xhr.statusText);
      },
      complete: function (XMLHttpRequest, textStatus) {
        //alert(XMLHttpRequest + '\n' + textStatus);
      }
    });
  }
  //
  -->
</script>

<div class="well">
  <form:form method="post" modelAttribute="adminGroupEntity" action="update" cssClass="smart-form" role="form">
    <fieldset>
      <div class="row">
        <section class="col col-6">
          <label class="label">
            <spring:message code="system.admin-group.adminGrpId" /></label>
          <label class="input">
            <form:input path="adminGrpId" maxlength="10" cssClass="required" readonly="true" />
          </label>
        </section>
        <section class="col col-6">
          <label class="label">
            <spring:message code="system.admin-group.adminGrpNm" /></label>
          <label class="input">
            <form:input path="adminGrpNm" maxlength="100" cssClass="required" />
          </label>
        </section>
        <section class="col col-6">
          <label class="label">
            <spring:message code="system.admin-group.useYn" /></label>
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
    <custom:auth type="UPDATE">
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

<div class="pre-scrollable margin-top-10">
  <div id="program" name="program"></div>
</div>

<!-- button -->
<custom:auth type="UPDATE">
  <div class="row margin-top-10">
    <div class="col-sm-12">
      <a href="javascript:programSave();" class="btn btn-sm btn-primary pull-right" role="button">
        <i class="fa fa-plus"></i> <span>
          <spring:message code="common.save" /></span></a>
    </div>
  </div>
</custom:auth>
<!-- // button -->