<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom"  uri="/tags/custom-taglib" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
  <!--
  // ready
  $(document).ready(function () {

    $.ajaxSetup({
      cache: false
    });

    // 코드 리스트
    getCodeList();
  });

  // 코드 리스트
  function getCodeList() {
    var url = '${REQUEST_CONTEXT_PATH}/system/code-group/code/list' + '?cdgrpCd=${codeGroupEntity.cdgrpCd}';
    $('#code').load(url);
  }

  // 코드 등록폼
  function getCodeForm() {
    var url = '${REQUEST_CONTEXT_PATH}/system/code-group/code/form' + '?cdgrpCd=${codeGroupEntity.cdgrpCd}';
    $('#code').load(url);
  }

  // 코드 상세
  function getCodeUpdate(codeGrpCd, codeCd) {
    var url = '${REQUEST_CONTEXT_PATH}/system/code-group/code/update' + '?cdgrpCd=${codeGroupEntity.cdgrpCd}&codeCd=' + codeCd;
    $('#code').load(url);
  }
  //
  -->
</script>

<!-- Code -->
<div>
  <div id="code"></div>
</div>