<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom" uri="/tags/custom-taglib" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<style type="text/css">
<!--
	.tree_wrap { /* margin-top: 10px; min-height: 300px; max-height: 300px; overflow: auto; */ border: gray 1px dotted; }
	.tree { min-height: 300px; }
	.highlight {
	    background-color: cyan;
	    font-weight: bold;
	}

	select {width: 400px; text-align-last:center; font-size:50px;}

	 #tab2 .row {
	    display: table;
	    table-layout: fixed;
	    width: 100%;
	}

	 #tab2 .row article {
		display: table-cell;
	    vertical-align: middle;
	    float: none;
	}
	 #imageFileDicDownlaodModalBody .row {
	    display: table;
	    table-layout: fixed;
	    width: 100%;
	}

	 #imageFileDicDownlaodModalBody .row article {
		display: table-cell;
	    vertical-align: middle;
	    float: none;
	}
-->
</style>

<script type="text/javascript">
//<!--

	var initializeDuallistbox;

	$(function() {
		// 초기 데이터를 호출한다.
		//getChildren("");
		treeInit();

		// 등록
		$('#btnAdd').attr('disabled', true).prop('disabled', true).click(function() {
		    if( $(this).prop('disabled') ) return false;
		    fnAdd();
		    return false;
		});

		/*
		 * BOOTSTRAP DUALLIST BOX
		 */

		initializeDuallistbox = $('#initializeDuallistbox').bootstrapDualListbox({
          nonSelectedListLabel: '미할당 이미지',
          selectedListLabel: '할당 이미지',
          preserveSelectionOnMove: 'moved',
          moveOnSelect: false,
          selectorMinimalHeight:300
        });

		$('#bootstrap-wizard-1').bootstrapWizard({
		  'tabClass': 'form-wizard',
		  'onNext': function (tab, navigation, index) {
		      /* $('#bootstrap-wizard-1').find('.form-wizard').children('li').eq(index - 1).addClass('complete');
		      $('#bootstrap-wizard-1').find('.form-wizard').children('li').eq(index - 1).find('.step').html('<i class="fa fa-check"></i>'); */
		  }
		});

		$("#downloadLeftArrow").click(function(){
			$("#imageFileDicDownlaodTbody .selected").each(function(){
				$("#imageFileDicDownlaodTbody").empty();
				var selectedClass = $(this).attr("class");
				selectedClass = selectedClass.replace("selected","");
				$("."+selectedClass).css("background-color","");
			});
		});

		$("#downloadRightArrow").click(function(){
			var appedTr = [];
			// 선택된 이미지
			$("#imageFileDicListTbody .selected").each(function(){
				$(this).closest("tr").css("background-color","#ecf3f8");
				var cloneTr = $(this).closest("tr").clone()
				var orgSelectedClass = $(this).attr("class");
				orgSelectedClass = orgSelectedClass.replace("selected","");

				if ($("#imageFileDicDownlaodTbody .selected").length > 0) {
					var dupCnt = 0;
					$("#imageFileDicDownlaodTbody .selected").each(function(){
						var selectedClass = $(this).attr("class");
						selectedClass = selectedClass.replace("selected","");
						if (orgSelectedClass == selectedClass) {
							dupCnt = dupCnt + 1;
						}
					});

					if (dupCnt == 0) {
						appedTr.push(cloneTr);
					}

				} else {
					var selectedClass = $(this).attr("class");
					selectedClass = selectedClass.replace("selected","");
					appedTr.push(cloneTr);
				}
			});

			$("#imageFileDicDownlaodTbody").append(appedTr);
		});
	});

	function treeInit() {
		$('.tree > ul').attr('role', 'tree').find('ul').attr('role', 'group');
		$('.tree').find('li:has(ul)').addClass('parent_li').attr('role', 'treeitem').find(' > span').attr('title', 'Collapse this branch').on('click', function(e) {
			var children = $(this).parent('li.parent_li').find(' > ul > li');
			if (children.is(':visible')) {
				children.hide('fast');
				$(this).attr('title', 'Expand this branch').find(' > i').removeClass().addClass('fa fa-lg fa-plus-circle');
			} else {
				children.show('fast');
				$(this).attr('title', 'Collapse this branch').find(' > i').removeClass().addClass('fa fa-lg fa-minus-circle');
			}
			e.stopPropagation();
		});
	}

	function treeDataLoad(upperImageTaggingDataDicId,id,event) {

		if (!upperImageTaggingDataDicId) {
			upperImageTaggingDataDicId = 'C1000000001';
		}

		var listCnt = $(id).next().find("li").length;

		setTimeout(function(){
			if (listCnt == 0) {
				$.ajax({
					async: false,
					dataType: 'json',
					url: '${REQUEST_CONTEXT_PATH}/image/tagging-dic/json',
					data: {upperImageTaggingDataDicId: upperImageTaggingDataDicId},
					success: function(data) {
						for (var i = 0; i < data.length; i++) {
							var item = data[i];
							var addList = "<li onclick=fnUpd('"+item.imageTaggingDataDicIdSq+"','"+item.upperImageTaggingDataDicId+"','"+item.imageTaggingDataDicNm+"','"+item.imageTaggingDataDicLevel+"',event);>";
							    if (item.imageTaggingDataDicLevel == "3") {
									addList += '<span id='+item.imageTaggingDataDicIdSq+'><i class="icon-leaf"></i>'+item.imageTaggingDataDicNm+"</span>";
								} else {
									addList += '<span id='+item.imageTaggingDataDicIdSq+' onclick="javascript:treeDataLoad('+"'"+item.imageTaggingDataDicIdSq+"'"+',this);"><i class="fa fa-lg fa-plus-circle"></i>'+item.imageTaggingDataDicNm+"</span><ul></ul>";
								}
								addList += "</li>";
								$(id).next().append(addList);
						}
						if (data.length > 0) {
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
								//e.stopPropagation();
							});

						}
						$('#btnAdd').attr('disabled', false).prop('disabled', false);
					}
				});
			}
		}, 300);
	}

	// 등록
	function fnAdd() {
		var url = '${REQUEST_CONTEXT_PATH}/image/tagging-dic/form';
		$('#form').empty().text('Loading...').load(url);
	}
	// 수정
	function fnUpd(imageTaggingDataDicIdSq, upperImageTaggingDataDicId, upperImageTaggingDataDicNm, imageTaggingDataDicLevel, event) {
		event.stopPropagation();
		// 현재 선택된 노드 ID 저장
		var currentNodeInfo = {imageTaggingDataDicIdSq : imageTaggingDataDicIdSq, upperImageTaggingDataDicId : upperImageTaggingDataDicId, upperImageTaggingDataDicNm : upperImageTaggingDataDicNm, imageTaggingDataDicLevel : imageTaggingDataDicLevel};
		localStorage.setItem("currentNodeInfo", JSON.stringify(currentNodeInfo));

		var url = '${REQUEST_CONTEXT_PATH}/image/tagging-dic/update?imageTaggingDataDicIdSq='+imageTaggingDataDicIdSq;
		$('#form').empty().text('Loading...').load(url);
	}

	// 현재 선택 노드 초기화
	function initCurrentSelectedNode() {
		localStorage.removeItem("currentNodeInfo");
	}

	// 이미지 파일 사전 리스트
	function getImageTaggingDicList () {
		var url = '${REQUEST_CONTEXT_PATH}/image/tagging-dic/imageFileDicList';
		$('.imageFileDicList').load(url);
	}

	// 다운로드
	function fnDownload() {
		var checkboxValues = [];

		$("#imageFileDicDownlaodTbody input[name='imageCheck']:checked").each(function(){
			checkboxValues.push(this.value);
		});

		if (checkboxValues.length < 1) {
			alert("Please Select Tagging Dic!!");
		} else {
			var url = 'download?checkArray[]='+checkboxValues;
			location.href = url;
		}
	}

	// 다운로드
	function fnAllDownload() {
		var url = 'download-all';
		location.href = url;
	}

	/***************************************************************/
	/** 서브 페이징 */
	/***************************************************************/
	function getPage(page) {
		var url = '${REQUEST_CONTEXT_PATH}/image/tagging-dic/imageFileDicList'+'?page='+page;
		$('.imageFileDicList').load(url);
	}

	// 검색
	function imageFileDicInfoSearch() {
		var url = '${REQUEST_CONTEXT_PATH}/image/tagging-dic/imageFileDicList';
		$('.imageFileDicList').load(url,$('#imageFileDicListSearchForm').serialize());
	}
//-->
</script>

<!-- button area -->
<div class="row margin-bottom">
	<div class="col-sm-12 text-align-right">
		<custom:auth type="CREATE">
			<div class="btn-group pull-left">
				<a class="btn btn-sm btn-primary" role="button" data-toggle="modal" data-target="#imageFileDicDownload" onclick="javascript:getImageTaggingDicList();">
					<i class="glyphicon glyphicon-export"></i> <spring:message code="common.export" /></a>
			</div>
			<div class="btn-group">
				<a id="btnAdd" class="btn btn-sm btn-primary" role="button">
					<i class="fa fa-plus"></i> <spring:message code="common.insert" /></a>
			</div>
		</custom:auth>
	</div>
</div>
<!-- // button area -->

<p></p>

<div class="row">
	<div class="col-md-6">
		<div class="jarviswidget jarviswidget-color-blue tree_wrap pre-scrollable">
			<div class="tree smart-form">
				<ul>
					<li>
						<span id="C1000000001" onclick="javascript:treeDataLoad('',this,event);initCurrentSelectedNode();"><i class="fa fa-lg fa-plus-circle"></i> Root</span>
						<ul></ul>
					</li>
				</ul>
			</div>
		</div>
	</div>

	<div class="col-md-6">
		<div id="form"></div>
	</div>
</div>

<!-- Modal -->
<div class="modal fade" id="imageFileDicDownload" tabindex="-1" role="dialog" aria-labelledby="imageFileDicDownloadLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 90%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="imageFileDicDownloadLabel">Image File Dictionary Download</h4>
			</div>
			<div id="imageFileDicDownlaodModalBody" class="modal-body">
				<!-- row -->
				<div class="row">
					<article class="col-sm-12 col-md-12 col-lg-6">
						<!-- Widget ID (each widget will need unique ID)-->
						<div class="jarviswidget jarviswidget-sortable jarviswidget-color-blueDark" id="wid-id-3" data-widget-togglebutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false" data-widget-fullscreenbutton="false" data-widget-collapsed="false" data-widget-attstyle="jarviswidget-color-blueDark">
							<header>
								<h2>Tagging Dictionary List </h2>
							</header>
							<!-- widget div-->
							<div>
								<div class="widget-body" style="min-height: 800px; max-height: 900px;">
									<div>
										<div class="imageFileDicList"></div>
									</div>
								</div>
							</div>
						</div>
					</article>
					<article class="col-sm-12 col-md-12 col-lg-1">
						<div style="text-align: center;">
							<a id="downloadLeftArrow" class="btn btn-default btn-lg" href="javascript:void(0);"><i class="glyphicon glyphicon-arrow-left"></i></a>
							<a id="downloadRightArrow" class="btn btn-default btn-lg" href="javascript:void(0);"><i class="glyphicon glyphicon-arrow-right"></i></a>
						</div>
					</article>
					<article class="col-sm-12 col-md-12 col-lg-5">
						<!-- Widget ID (each widget will need unique ID)-->
						<div class="jarviswidget jarviswidget-sortable jarviswidget-color-blueDark" id="wid-id-2" data-widget-togglebutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false" data-widget-fullscreenbutton="false" data-widget-collapsed="false" data-widget-attstyle="jarviswidget-color-blueDark">
							<header>
								<h2>Download Image Tagging Dictionary </h2>
							</header>
							<!-- widget div-->
							<div>
								<div class="widget-body table-responsive smart-form" style="min-height: 800px; max-height: 900px;">
									<table class="table table-bordered table-hover">
										<colgroup>
											<col width="5%" />
											<col width="50%" />
											<col width="45%" />
										</colgroup>

										<thead>
											<tr>
												<th class="checkbox" style="margin-bottom: 0px;"><label class="checkbox"><input type="checkbox" class="checkAll" name="checkAll" /><i></i></label></th>
												<th><spring:message code="admin.image.upperImageTaggingDataDicNm" /></th>
												<th><spring:message code="admin.image.imageTaggingDataDicNm" /></th>
											</tr>
										</thead>

										<tbody id="imageFileDicDownlaodTbody">
											<%-- <tr><td colspan="6"><spring:message code="common.dataIsEmpty" /></td></tr> --%>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</article>
				</div>
				<div align="center">
					<button type="button" class="btn btn-success btn-lg" onclick="javascript:fnAllDownload();">
						전체다운로드
					</button>
					<button type="button" class="btn btn-success btn-lg" onclick="javascript:fnDownload();">
						선택다운로드
					</button>
				</div>
			</div>
			<div class="modal-footer">
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->