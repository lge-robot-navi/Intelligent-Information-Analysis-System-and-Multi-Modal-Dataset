<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom" uri="/tags/custom-taglib" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
  <!--
  // 상세조회
  function fnSelect(cdgrpCd, codeCd) {
    // parent function call
    getCodeUpdate(cdgrpCd, codeCd);
  }
  //
  -->
</script>

<div class="table">
  <table class="table table-striped table-bordered table-hover">
    <colgroup>
      <col width="15%" />
      <col width="35%" />
      <col width="15%" />
      <col width="10%" />
      <col width="15%" />
    </colgroup>

    <thead>
      <tr>
        <th>
          <spring:message code="system.code.cdgrpCd" />
        </th>
        <th>
          <spring:message code="system.code.codeNm" />
        </th>
        <th>
          <spring:message code="system.code.ifCd" />
        </th>
        <th>
          <spring:message code="system.code.orderNo" />
        </th>
        <th>
          <spring:message code="system.code.useYn" />
        </th>
      </tr>
    </thead>

    <tbody>
      <c:if test="${empty list}">
        <tr>
          <td colspan="5">
            <spring:message code="common.dataIsEmpty" />
          </td>
        </tr>
      </c:if>
      <c:forEach var="item" items="${list}">
        <tr onclick="javascript:fnSelect('${item.cdgrpCd}', '${item.codeCd}');">
          <td>${item.codeCd}</td>
          <td class="le ellipsis" title="${item.codeNm}">${item.codeNm}</td>
          <td class="le ellipsis" title="${item.ifCd}">${item.ifCd}</td>
          <td>${item.orderNo}</td>
          <td>${item.useYn eq 'Y' ? '사용' : '미사용'}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>

<!-- button area -->
<div class="row">
  <div class="col-sm-12 text-align-right">
    <custom:auth authUrl="/system/code-group/" type="CREATE">
      <div class="btn-group">
        <a class="btn btn-sm btn-primary" href="javascript:getCodeForm();" role="button">
          <i class="fa fa-plus"></i>
          <spring:message code="common.insert" />
        </a>
      </div>
    </custom:auth>
  </div>
</div>
<!-- // button area -->