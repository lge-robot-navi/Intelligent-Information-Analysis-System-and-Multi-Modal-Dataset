<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom" uri="/tags/custom-taglib" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags/" %>

<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/jquery/skin-vista/ui.dynatree.css" />" />
<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery.dynatree.min.js" />"></script>
<script type="text/javascript">
<!--
	/* 트리 생성 */
	function initTree() {
		$("#tree").dynatree({
			title: 'Category Mgmt Tree',
			fx: { height: 'toggle', duration: 200 },
			clickFolderMode: 1,
			autoFocus: false,
			onActivate: function(node) {
				getActivateEvent(node);
			},
			onLazyRead: function(node) {
				getAppendAjax(node);
			}
			<c:if test="${param.mode == 'T'}">
			,dnd: {
				preventVoidMoves: true,
				onDragStart: function(node) {
					return true;
				},
				onDragEnter: function(node, sourceNode) {
					return true;
				},
				onDrop: function(node, sourceNode, hitMode, ui, draggable) {
					var nodeData = node.data;
					var sourceData = sourceNode.data;

					if( nodeData.categoryUpperNo == null ) {
						alert('최상위 카테고리로는 이동할 수 없습니다.');
						return false;
					}

					sourceNode.move(node, hitMode);

					if(hitMode == 'over') {
						// 뎁스이동
						var priorityNo = 1;
						var nodes = node.getChildren();
						$.each(nodes, function(i, item) {
							priorityNo++;
						});
						$.ajax({
							url: getContextPath()+'/hcportal/category/category/update-depth.json?p='+Math.random(),
							async: false,
							data: {
								'categoryUpperNo': nodeData.categorySq,
								'categorySq': sourceData.categorySq,
								'categoryDepthNo': (Number(nodeData.categoryDepthNo) + 1) + '',
								'priorityNo': priorityNo + 1,
								'countryCd': nodeData.countryCd
							},
							success: function(data) {
								node.reloadChildren(function(node, isOk) {
									node.activate();
								});
							}
						});
					} else {
						// reordering
						if ( nodeData.categoryDepthNo == sourceData.categoryDepthNo ) {
							// 순위 이동
							$.ajax({
								url: getContextPath()+'/hcportal/category/category/update-rank.json?p='+Math.random(),
								async: false,
								data: {
									'categoryUpperNo': sourceData.categoryUpperNo,
									'categorySq': sourceData.categorySq,
									'categoryDepthNo': sourceData.categoryDepthNo,
									'priorityNo': nodeData.priorityNo,
									'countryCd': nodeData.countryCd
								},
								success: function(data) {
									node.getParent().reloadChildren(function(node, isOk) {
										node.activate();
									});
								}
							});
						} else {
							// 뎁스이동
							//alert(hitMode);
							var priorityNo = 1;
							var nodes = node.getParent().getChildren();
							$.each(nodes, function(i, item) {
								if( node.data.categorySq == item.data.categorySq ) {
									if( hitMode == 'after') {
										priorityNo = ( i + 2 ) + '';
									} else {
										priorityNo = ( i ) + '';
									}
								}
							});

							$.ajax({
								url: getContextPath()+'/hcportal/category/category/update-depth.json?p='+Math.random(),
								async: false,
								data: {
									'categoryUpperNo': nodeData.categoryUpperNo,
									'categorySq': sourceData.categorySq,
									'categoryDepthNo': nodeData.categoryDepthNo,
									'priorityNo': priorityNo,
									'countryCd': nodeData.countryCd
								},
								success: function(data) {
									node.getParent().reloadChildren(function(node, isOk) {
										node.activate();
									});
								}
							});
						}
					}

					return false;
				}
			}
			</c:if>
		});

		treeLoad( $('#categoryClassCd').val() );
	}

	// 트리 데이터 구성
	function treeLoad( categoryClassCd ) {
		$.ajax({
			url: getContextPath() + '/hcportal/category/category/list.json?p='+Math.random(),
			data:{ categoryClassCd : categoryClassCd, useYn : $('#useYn').val(), depth: '3', countryCd: $('#countryCd').val() },
			success: function(data) {
				var tree = $("#tree");
				var rootNode = tree.dynatree('getRoot');

				$(data).each(function(key, val){
					if(val.categoryUpperNo == null) {
						rootNode.addChild(val);
					}
					else {
						var node = tree.dynatree("getTree").getNodeByKey(val.categoryUpperNo);
						if(node != null) node.addChild(val);
					}
				});
			}
		});
	}

	// 카테고리구분 타입 별 트리 리로드
	function fnReloadTree() {
		var rootNode = $("#tree").dynatree('getRoot');
		rootNode.removeChildren();
		treeLoad( $('#categoryClassCd').val() );
	}

	// 카테고리구분 타입 별 트리 리로드
	function reloadTree(categoryClassCd) {
		var rootNode = $("#tree").dynatree('getRoot');
		rootNode.removeChildren();
		treeLoad(categoryClassCd);
	}

	// 저장 성공시 작동 메소드
	function success() {
		var tree = $('#tree');
		var activeNode = tree.dynatree('getActiveNode');
		var parentNode = activeNode.getParent();
		var rootNode = tree.dynatree('getRoot');

		if (parentNode == rootNode) {

			rootNode.removeChildren();
			treeLoad($('#categoryClassCd').val());

		} else {

			var method = $('#categoryForm').contents().find('#method').val();
			var targetNode = (method == 'input') ? activeNode : parentNode;
			targetNode.reloadChildren(function(node, isOk) {
				node.activate();
			});
		}
	}

	// Ajax
	function getAppendAjax(node) {
		node.appendAjax({
			//debugLazyDelay: 750,
			url: getContextPath() + '/hcportal/category/category/list.json?p='+Math.random(),
			data: { 'categoryUpperNo': node.data.categorySq, useYn : $('#useYn').val(), countryCd: $('#countryCd').val() },
		});
	}
//-->
</script>
<div>
	<input type="hidden" name="useYn" id="useYn" value="${param.useYn}" />
	<c:if test="${!empty param.countryCd}">
		<tags:select name="countryCd" id="countryCd" group="MB004" cssClass="sel160" cssStyle="margin: 2px"  value="${param.countryCd}"></tags:select>
	</c:if>
	<c:if test="${empty param.countryCd}">
		<input type="hidden" name="countryCd" id="countryCd" value="" />
	</c:if>
	<c:if test="${empty param.categoryClassCd}">
		<tags:select name="categoryClassCd" id="categoryClassCd" group="ED001" exclude="40" cssClass="sel160" cssStyle="margin: 2px"></tags:select>
	</c:if>
	<c:if test="${not empty param.categoryClassCd}">
		<input type="hidden" name="categoryClassCd" id="categoryClassCd" value="${param.categoryClassCd}" />
	</c:if>
</div>
<div class="tree_wrap">
	<div id="tree"></div>
</div>