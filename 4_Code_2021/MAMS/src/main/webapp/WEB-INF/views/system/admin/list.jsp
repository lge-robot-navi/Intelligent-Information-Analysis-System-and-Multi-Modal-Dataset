<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom" uri="/tags/custom-taglib" %>

<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
  <!--
  //최근 접속 페이지 값 설정
  fnSetLatestPage();

  $(function () {

    // 검색
    $('#btnSearch').click(function () {
      search();
    });

    // 사용여부 이벤트
    $('select[name=useYn]').change(function () {
      search();
    });
  });

  // 검색
  function search() {
    $('form:first').submit();
  }

  // 상세조회
  function fnSelect(adminIdSq) {
    var url = 'update?adminIdSq=' + adminIdSq;
    location.href = url;
  }
  //
  -->
</script>

<!-- button area -->
<div class="row button-wrap">
  <div class="col-xs-12 text-align-right">
    <custom:auth type="CREATE">
      <div class="btn-group">
        <a href="form" class="btn btn-sm btn-primary" role="button">
          <i class="fa fa-plus"></i>
          <spring:message code="common.insert" /></a>
      </div>
    </custom:auth>
  </div>
</div>
<!-- // button area -->

<p></p>

<!-- search -->
<div class="well search-wrap">
  <form:form method="get" action="list" modelAttribute="adminEntity" cssClass="form-inline search-form" role="form" autocomplete="off">
    <fieldset>
      <div class="row">
        <div class="col-sm-10">
          <div class="row">
            <div class="col-sm-6">
              <spring:message var="msgAdminId" code="system.admin.adminId" />
              <label for="searchWd">${msgAdminId}</label>
              <div class="form-group">
                <form:input path="searchWd" cssClass="form-control" placeholder="${msgAdminId}" />
              </div>
            </div>
            <div class="col-sm-6">
              <div class="form-group">
                <spring:message var="msgAdminNm" code="system.admin.adminNm" />
                <label for="adminNm">${msgAdminNm}</label>
                <form:input path="adminNm" cssClass="form-control" placeholder="${msgAdminNm}" />
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6">
              <div class="form-group">
                <spring:message var="msgCompanyNm" code="system.admin.companyNm" />
                <label for="companyNm">${msgCompanyNm}</label>
                <form:input path="companyNm" cssClass="form-control" placeholder="${msgCompanyNm}" />
              </div>
            </div>
            <div class="col-sm-6">
              <div class="form-group">
                <label for="adminStatusCd">
                  <spring:message code="system.admin.adminStatusCd" /></label>
                <tags:select id="adminStatusCd" name="adminStatusCd" group="TA003" cssClass="form-control" value="${adminEntity.adminStatusCd}">
                  <option value="">
                    <spring:message code="selectbox.all" />
                  </option>
                </tags:select>
              </div>
            </div>
          </div>
        </div>

        <div class="col-sm-2 text-center search_btn_wrap">
          <button type="submit" class="btn btn-default btn-primary" role="button">
            <i class="fa fa-search"></i>
            <spring:message code="common.search" />
          </button>
        </div>
      </div>
    </fieldset>
  </form:form>
</div>

<!-- list -->
<div class="table-responsive">
  <table class="table table-striped table-bordered table-hover">
    <colgroup>
      <col width="20%" />
      <col width="20%" />
      <col width="20%" />
      <col width="20%" />
      <col width="20%" />
    </colgroup>

    <thead>
      <tr>
        <th>
          <spring:message code="system.admin.adminId" />
        </th>
        <th>
          <spring:message code="system.admin.adminNm" />
        </th>
        <th>
          <spring:message code="system.admin.adminGrpNm" />
        </th>
        <th>
          <spring:message code="system.admin.adminCdNm" />
        </th>
        <th>
          <spring:message code="system.admin.adminStatusCd" />
        </th>
      </tr>
    </thead>

    <tbody>
      <c:if test="${empty list}">
        <tr>
          <td colspan="5">데이터가 존재하지 않습니다.</td>
        </tr>
      </c:if>
      <c:forEach var="item" items="${list}">
        <tr onclick="javascript:fnSelect('${item.adminIdSq}');">
          <td title="${item.adminId}">${item.adminId}</td>
          <td title="${item.adminNm}">${item.adminNm}</td>
          <td title="${item.adminGrpNm}">${item.adminGrpNm}</td>
          <td title="${item.adminCdNm}">${item.adminCdNm}</td>
          <td title="${item.adminStatusCdNm}">${item.adminStatusCdNm}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>