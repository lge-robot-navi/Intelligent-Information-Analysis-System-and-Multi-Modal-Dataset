<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="/tags/custom-taglib" prefix="custom" %>

<!-- Messages -->
<c:if test="${not empty message}">
	<script type="text/javascript">
		alert('${message}');
	</script>
</c:if>

<style type="text/css">
<!--
	.depth1 { margin-left: 0; }
	.depth2 { margin-left: 2em; }
	.depth3 { margin-left: 4em; }
	.depth4 { margin-left: 6em; }
	.depth5 { margin-left: 8em; }
	.depth6 { margin-left: 10em; }
-->
</style>

<script type="text/javascript">
	$(function() {

		// 초기화
		$('input[name=available]:checkbox').not(':checked').each(function() {
			fnToggleChecked( $(this) );
		});

		// '선택 체크박스' 이벤트 설정
		$('input[name=available]').click(function(event) {
			if( $(this).prop('checked') && $(this).parent().next().find("div").attr("class") == "depth2" ) {
				fnToggleChecked($(this));
				var prev = $(this).parent().parent().prev();
				if ( prev.find("div").attr("class") == "depth1" ) {
					prev.find("input[name=available]").prop('checked',true);
					fnToggleChecked( prev.find('input[name=available]') );
				}else{
					while(prev.find("div").attr("class") != "depth1"){
						prev = prev.prev();
						if(prev.find("div").attr("class") == "depth1"){
							prev.find("input[name=available]").prop('checked',true);
							fnToggleChecked( prev.find('input[name=available]') );
						}
					}
				}
			} else {
				fnToggleChecked( $(this) );
			}
		});

		// 전체선택 이벤트 설정
		$('#allChek').click(function() {
			var isChecked = $(this).is(':checked');
			fnAllCheck(isChecked);
		});
	});

	/* 대상 객체 가져오기 */
	function getTarget(obj, elem) {
		return obj.parents('tr').find(elem).not(obj);
	}

	/**
	* '선택 체크박스' 선택 토글 처리
	*/
	function fnToggleChecked(obj) {
		var isCheck = obj.is(':checked');
		getTarget(obj, 'input:checkbox').prop('disabled', !isCheck).prop('checked', isCheck).val(isCheck ? 'Y' : 'N');
	}

	/**
	* 저장
	*/
	function fnProgramSave() {

		$('input[name=available]:checkbox').each(function() {
			var $this = $(this);
			if ($this.is(':checked')) {
				//var $target = getTarget($this, 'input:checkbox');
				//$target.find(':checked').val('true');
				//$target.not(':checked').prop('checked', true).val('false');
			} else {
				getTarget($this, 'input:hidden').prop('disabled', true);
			}
		});

		if (confirm('<spring:message code="save.isSave" />')) {
			var frm = $('form:last');
			$.ajax({
				type: frm.attr('method'),
				url: '<c:url value="/system/admin-group/group-auth/program" />',
				data: frm.serialize(),
				success: function(data) {
					if (data.resultCd == 'success') { // 성공
						alert(data.resultMsg);
						getProgram();
					} else { // 실패
						alert(data.resultMsg);
					}
				},
				error: function(xhr) {
					alert('error : ' + xhr.status + '\n' + xhr.statusText);
				}, complete: function(XMLHttpRequest, textStatus) {
					//alert(XMLHttpRequest + '\n' + textStatus);
				}
			});

		} else {
			$('input:checkbox[value=N]').prop('checked', false);
		}
	}

	/**
	* 전체 선택/해제 토글
	*/
	function fnAllCheck(isCheck) {
		$('input[name=available]:checkbox').prop('checked', isCheck).each(function() {
			getTarget($(this), 'input:checkbox').prop('disabled', !isCheck).prop('checked', isCheck).val(isCheck ? 'Y' : 'N');
		});
	}
</script>

<div class="table">
	<form:form method="post" action="program" modelAttribute="groupAuthFormEntity" role="form">
		<form:hidden path="adminGrpId"/>

		<table class="table table-striped table-bordered table-hover">
			<colgroup>
				<col width="10%" />
				<col width="50%" />
				<col width="10%" />
				<col width="10%" />
				<col width="10%" />
				<col width="10%" />
			</colgroup>

			<thead>
				<tr>
					<th><input type="checkbox" id="allChek" /></th>
					<th>메뉴명</th>
					<th>조회</th>
					<th>등록</th>
					<th>수정</th>
					<th>삭제</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="item" items="${groupAuthFormEntity.programs}" varStatus="itemStatus">
					<tr>
						<td>
							<form:hidden path="programs[${itemStatus.index}].pgmId" />
							<input type="checkbox" name="available" value="${item.pgmId}" ${item.available eq item.pgmId ? 'checked="checked"' : ''} />
						</td>
						<td class="ellipsis" style="text-align: left;" title="${item.fullPathNm}">
							<c:set var="depth" value="depth${item.levelNo}" />
							<div class="${depth}">
								<span class="fa ${item.iconDs}"></span>
								${item.pgmNm}
							</div>
						</td>
						<td>
							<c:choose>
								<c:when test="${item.ynSel == 'Y'}">
									<form:checkbox path="programs[${itemStatus.index}].authSel" value="Y" />
								</c:when>
								<c:otherwise>
									<form:hidden path="programs[${itemStatus.index}].authSel" value="N" />
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${item.ynIns == 'Y'}">
									<form:checkbox path="programs[${itemStatus.index}].authIns" value="Y" />
								</c:when>
								<c:otherwise>
									<form:hidden path="programs[${itemStatus.index}].authIns" value="N" />
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${item.ynUpd == 'Y'}">
									<form:checkbox path="programs[${itemStatus.index}].authUpd" value="Y" />
								</c:when>
								<c:otherwise>
									<form:hidden path="programs[${itemStatus.index}].authUpd" value="N" />
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${item.ynDel == 'Y'}">
									<form:checkbox path="programs[${itemStatus.index}].authDel" value="Y" />
								</c:when>
								<c:otherwise>
									<form:hidden path="programs[${itemStatus.index}].authDel" value="N" />
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form:form>
</div>
