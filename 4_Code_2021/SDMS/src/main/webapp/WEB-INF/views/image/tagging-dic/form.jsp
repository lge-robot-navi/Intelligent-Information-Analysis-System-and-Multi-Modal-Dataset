<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom" uri="/tags/custom-taglib" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<!-- Messages -->
<%-- <c:if test="${not empty message}">
	<script type="text/javascript">
		alert('${message}');
		var data = {
			'title': '${programEntity.pgmNm}',
			'key': '${programEntity.pgmId}',
			'activate': 'true',
			'isFolder': '${programEntity.pgmTp != "U"}',
			'upperPgmId': '${programEntity.upperPgmId}',
			'pgmId': '${programEntity.pgmId}',
		};

		parent.appendChild(data);
	</script>
</c:if> --%>

<style type="text/css">
	#urlDsWrap { display: none; }
</style>

<div class="well">
	<form:form id="program-form" method="post" action="form" cssClass="smart-form" modelAttribute="taggingDicEntity">
		<form:hidden path="upperImageTaggingDataDicId" id="upperImageTaggingDataDicId" />

		<fieldset>
			<div class="row">
				<section class="col col-6">
					<label class="label"><spring:message code="admin.image.upperImageTaggingDataDicNm" /></label>
					<label class="input">
						<input type="input" id="upperImageTaggingDataDicNm" readonly="readonly" />
					</label>
				</section>
				<section class="col col-6">
					<label class="label"><spring:message code="admin.image.imageTaggingDataDicNm" /></label>
					<label class="input">
						<form:input path="imageTaggingDataDicNm" cssClass="required" />
					</label>
				</section>
			</div>

			<div class="row">
				<section class="col col-6">
					<label class="label"><spring:message code="admin.image.imageTaggingDataDicLevel" /></label>
					<label class="input">
						<form:input path="imageTaggingDataDicLevel" maxlength="4" cssClass="required digits" readonly="true" />
					</label>
				</section>
				<section class="col col-6">
					<label class="label"><spring:message code="admin.image.useYn" /></label>
					<label class="select">
						<form:select path="useYn">
							<form:option value="Y">사용</form:option>
							<form:option value="N">미사용</form:option>
						</form:select>
						<i></i>
					</label>
				</section>
			</div>

			<div class="row">
				<section class="col col-md-12">
	    	        <label class="label"><spring:message code="admin.image.imageFilePackageDesc" /></label>
    	        	<label class="textarea">
		                <form:textarea path="imageTaggingDataDicDesc" rows="4" maxlength='256'/>
            		</label>
        		</section>
			</div>
		</fieldset>
	</form:form>
</div>

<!-- button -->
<div class="row">
	<div class="col-sm-12">
		<custom:auth type="CREATE">
			<div class="btn-group pull-right">
				<button type="button" onclick="save();" class="btn btn-sm btn-primary" role="button">
					<i class="fa fa-plus"></i> 저장
				</button>
			</div>
		</custom:auth>
	</div>
</div>
<!-- // button -->

<script type="text/javascript">
<!--
	$(function() {
		// 부모 노드 정보
		var parentNode = localStorage.getItem("currentNodeInfo");
		if (parentNode != null) {
			var currentNodeInfo = JSON.parse(parentNode);
			if (currentNodeInfo.imageTaggingDataDicIdSq != "1") {
				$('#upperImageTaggingDataDicNm').val(currentNodeInfo.upperImageTaggingDataDicNm);
				$('#upperImageTaggingDataDicId').val(currentNodeInfo.imageTaggingDataDicIdSq);
				$('#imageTaggingDataDicLevel').val(parseInt(currentNodeInfo.imageTaggingDataDicLevel)+1);
			} else {
				$('#upperImageTaggingDataDicNm').val("ROOT");
				$('#upperImageTaggingDataDicId').val(1);
				$('#imageTaggingDataDicLevel').val(parseInt(currentNodeInfo.imageTaggingDataDicLevel)+1);
			}
		} else {
			$('#upperImageTaggingDataDicNm').val("ROOT");
			$('#upperImageTaggingDataDicId').val(1);
			$('#imageTaggingDataDicLevel').val(1);
		}
	});

	// 저장
	function save() {
		// ajax submit
		var options = {
			beforeSubmit: function(arr, $form, options) {
				if ($form.valid()) {
					if (confirm('<spring:message code="save.isSave" />')) {
						return true;
					}
				}
				return false;
			},
			success: successResponse,
		};

		$('#program-form').ajaxSubmit(options);
	}

	// success response
	function successResponse(responseText, statusText, xhr, $form) {

		if (responseText && typeof responseText == 'object') {
			responseText = JSON.stringify(responseText);
		}

		var json = jQuery.parseJSON(responseText);
		if (json && json.message) {
			alert(json.message);

			if (json.taggingDicEntity) {
				var item = json.taggingDicEntity;
				var addList = "<li onclick=fnUpd('"+item.imageTaggingDataDicIdSq+"','"+item.upperImageTaggingDataDicId+"','"+item.imageTaggingDataDicNm+"','"+item.imageTaggingDataDicLevel+"',event);>";
				    if (item.imageTaggingDataDicLevel == "3") {
						addList += '<span id='+item.imageTaggingDataDicIdSq+'><i class="icon-leaf"></i>'+item.imageTaggingDataDicNm+"</span>";
					} else {
						addList += '<span id='+item.imageTaggingDataDicIdSq+' onclick="javascript:treeDataLoad('+item.imageTaggingDataDicIdSq+',this);"><i class="fa fa-lg fa-plus-circle"></i>'+item.imageTaggingDataDicNm+"</span><ul></ul>";
					}
				addList += "</li>";
				var currentNodeInfo = JSON.parse(localStorage.getItem("currentNodeInfo"));
				$("#"+currentNodeInfo.imageTaggingDataDicIdSq).parent().find("ul:first").append(addList);

				var id = "#"+currentNodeInfo.imageTaggingDataDicIdSq;
				$(id).parent().find('ul').attr('role', 'tree').find('ul').attr('role', 'group');
				$(id).parent().find('li:has(ul)').addClass('parent_li').attr('role', 'treeitem').find(' > span').attr('title', 'Collapse this branch').on('click', function(e) {
					var children = $(this).parent('li.parent_li').find(' > ul > li');
					if (children.is(':visible')) {
						children.hide('fast');
						$(this).attr('title', 'Expand this branch').find(' > i').removeClass().addClass('fa fa-lg fa-plus-circle');
					} else {
						children.show('fast');
						$(this).attr('title', 'Collapse this branch').find(' > i').removeClass().addClass('fa fa-lg fa-minus-circle');
					}
				});
			}
		}
	}
//-->
</script>