<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" uri="/tags/custom-taglib" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<!-- Messages -->
<c:if test="${not empty message}">
	<script type="text/javascript">
		alert('${message}');
		//location.href = "list";
	</script>
</c:if>

<style>
<!--
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
 #fileDownloadModalBody .row {
    display: table;
    table-layout: fixed;
    width: 100%;
}

 #fileDownloadModalBody .row article {
	display: table-cell;
    vertical-align: middle;
    float: none;
}
-->
</style>

<script type="text/javascript">
<!--
	//최근 접속 페이지 값 설정
	fnSetLatestPage();

	var initializeDuallistbox;

	$(function() {

		// Checkbox Load
		if (getParameter("page") == "") {
			checkboxClear();
		} else {
			checkboxLoad();
		}

		// 검색
		$('#btnSearch').click(function() { search(); });

    	// Datepicker
		setDatepickerStartEnd($('#startDt'), $('#endDt'));

		$('.checkAll').on('click', function () {
			checkboxSave('all');
		  $(this).closest('table').find('tbody :checkbox')
		    .prop('checked', this.checked)
		    .closest('tr').toggleClass('selected', this.checked);
		});

		$('#imageListTbody :checkbox').on('click', function () {
			checkboxSave('');
		  $(this).closest('tr').toggleClass('selected', this.checked);
		  $(this).closest('table').find('.checkAll').prop('checked', ($(this).closest('table').find('tbody :checkbox:checked').length == $(this).closest('table').find('tbody :checkbox').length));
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

		$("#leftArrow").click(function(){
			/* $("#assignImageTbody .selected").each(function(){
				$("#assignImageTbody").empty();
				var selectedClass = $(this).attr("class");
				selectedClass = selectedClass.replace("selected","");
				$("."+selectedClass).css("background-color","");
			}); */

			if ($("#assignImageTbody input[name='imageCheck']:checked").length > 0) {
				$("#assignImageTbody input[name='imageCheck']:checked").each(function(){
					var selectedClass = $(this).closest('tr').attr("class");
					selectedClass = selectedClass.replace("selected","");
					$("."+selectedClass).css("background-color","");
					$(this).closest('tr').remove();
				});
			} else {
				alert("Please Select Image");
			}
		});

		$("#rightArrow").click(function(){
			var appedTr = [];
			// 선택된 이미지
			if ($("#imageAssignDivBody").find("#imageListTbody .selected").length > 0) {
				$("#imageAssignDivBody").find("#imageListTbody .selected").each(function(){
					$(this).closest("tr").css("background-color","#ecf3f8");
					var cloneTr = $(this).closest("tr").clone()
					var orgSelectedClass = $(this).attr("class");
					orgSelectedClass = $.trim(orgSelectedClass.replace("selected",""));

					if ($("#assignImageTbody .selected").length > 0) {
						var dupCnt = 0;
						$("#assignImageTbody .selected").each(function(){
							var selectedClass = $(this).attr("class");
							selectedClass = $.trim(selectedClass.replace("selected",""));
							if (orgSelectedClass == selectedClass) {
								dupCnt = dupCnt + 1;
							}
						});

						if (dupCnt == 0) {
							appedTr.push(cloneTr);
						}

					} else {
						var selectedClass = $(this).attr("class");
						selectedClass = $.trim(selectedClass.replace("selected",""));
						var dupCnt = 0;
						$("#assignImageTbody tr").each(function(){
							var leftSelectedClass = $(this).attr("class");
							leftSelectedClass = $.trim(leftSelectedClass.replace("selected",""));
							if (leftSelectedClass == selectedClass) {
								dupCnt = dupCnt + 1;
							}
						});
						console.log(dupCnt);

						if (dupCnt == 0) {
							appedTr.push(cloneTr);
						}
					}
				});

				$("#assignImageTbody").append(appedTr);
			} else {
				alert("Please Select Image");
			}
		});

		$("#downloadLeftArrow").click(function(){
			if ($("#fileDownloadTbody input[name='imageCheck']:checked").length > 0) {
				$("#fileDownloadTbody input[name='imageCheck']:checked").each(function(){
					var selectedClass = $(this).closest('tr').attr("class");
					selectedClass = selectedClass.replace("selected","");
					$("."+selectedClass).css("background-color","");
					$(this).closest('tr').remove();
				});
			} else {
				alert("Please Select Images.");
			}
		});

		$("#downloadRightArrow").click(function(){
			var appedTr = [];

			if ($("#imageExportDivBody").find("#imageListTbody .selected").length > 0) {
				// 선택된 이미지
				$("#imageExportDivBody").find("#imageListTbody .selected").each(function(){
					$(this).closest("tr").css("background-color","#ecf3f8");
					var cloneTr = $(this).closest("tr").clone()
					var orgSelectedClass = $(this).attr("class");
					orgSelectedClass = $.trim(orgSelectedClass.replace("selected",""));

					if ($("#fileDownloadTbody .selected").length > 0) {
						var dupCnt = 0;
						$("#fileDownloadTbody .selected").each(function(){
							var selectedClass = $(this).attr("class");
							selectedClass = $.trim(selectedClass.replace("selected",""));
							if (orgSelectedClass == selectedClass) {
								dupCnt = dupCnt + 1;
							}
						});

						if (dupCnt == 0) {
							appedTr.push(cloneTr);
						}

					} else {
						var selectedClass = $(this).attr("class");
						selectedClass = $.trim(selectedClass.replace("selected",""));
						var dupCnt = 0;
						$("#fileDownloadTbody tr").each(function(){
							var leftSelectedClass = $(this).attr("class");
							leftSelectedClass = $.trim(leftSelectedClass.replace("selected",""));
							if (leftSelectedClass == selectedClass) {
								dupCnt = dupCnt + 1;
							}
						});
						console.log(dupCnt);

						if (dupCnt == 0) {
							appedTr.push(cloneTr);
						}
					}
				});

				$("#fileDownloadTbody").append(appedTr);

			} else {
				alert("Please Select Images.");
			}
		});

	});

	// 검색
	function search() {
		$('form:first').submit();
	}

	// 상세조회
	function fnSelect(imageFileSq,imageFileTypeCd) {
		if(imageFileTypeCd == "600") {
			alert("Sound 는 준비중입니다.");
			return;
		}
		if(imageFileTypeCd == "700") {
			alert("Lidar 는 준비중입니다.");
			return;
		}
		var url = 'update?sensorDataFileSq=' + imageFileSq;
		location.href = url;
	}

	// 업로드
	function fnUpload() {
		$("#fileUploadForm").submit();
	}

	// 다운로드
	function fnDownload() {
		var checkboxValues = [];
		$("#fileDownloadTbody input[name='imageCheck']:checked").each(function(){
			checkboxValues.push(this.value);
		});

		if (checkboxValues.length < 1) {
			alert("Please Select Images!!");
		} else {
			var url = 'download?checkArray[]='+checkboxValues;
			location.href = url;
		}
	}

	// XML 다운로드
	function fnXmlDownload() {
		var checkboxValues = [];

		$("#fileDownloadTbody input[name='imageCheck']:checked").each(function(){
			checkboxValues.push(this.value);
		});

		if (checkboxValues.length < 1) {
			alert("Please Select Images!!");
		} else {
			var url = 'xml-download?checkArray[]='+checkboxValues;
			location.href = url;
		}
	}

	// 체크박스 데이터 저장
	function checkboxSave(type){
		checkboxClear();
		if (type == 'all') {
			$("input[name='imageFileCheck']").each(function(){
			    var id = $(this).parent().parent().next().text();
			    var value = $(this).parent().parent().next().next().next().text();
		    	localStorage.setItem('imageFileCheck'+id, value);
			});
		} else {
			$("input[name='imageFileCheck']").each(function(){
			    var id = $(this).parent().parent().next().text();
			    var value = $(this).parent().parent().next().next().next().text();
			    var checked = this.checked;
			    if (checked) {
			    	localStorage.setItem('imageFileCheck'+id, value);
				} else {
			    	localStorage.setItem('imageFileCheck'+id, '');
				}
			});
		}
	}

	// 체크박스 데이터 로드
	function checkboxLoad(){
		$("input[name='imageFileCheck']").each(function(){
		    var id = $(this).parent().parent().next().text();
		    var checked = JSON.parse(localStorage.getItem('imageFileCheck'+id));
		    if (checked != "") {
			    this.checked = true;
			}
		});
	}

	// 체크박스 데이터 클리어
	function checkboxClear(){
	    localStorage.clear()
	}

	// 파라메터 가져오기
	function getParameter(paramName) {
		var parameters = $(location).attr("search").slice($(location).attr("search").indexOf("?")+1).split("&");
		for (var i = 0; i < parameters.length; i++) {
			var queryName = parameters[i].split("=")[0];
			if (queryName == paramName) {
				return decodeURIComponent(parameters[i].split("=")[1]);
			} else {
				return "";
			}
		}
	}

	// 작업자 이미지 할당
	function assignImageToWorker() {
		var imageArray = [];
		var workerArray = [];

		$("input[name='adminCheck']:checked").each(function(){
			workerArray.push(this.value);
		});

		$("#assignImageTbody input[name='imageCheck']:checked").each(function(){
			imageArray.push(this.value);
		});

		var sendData = {imageArray:imageArray, workerArray:workerArray};

		if (workerArray.length == 0) {
			alert("Please Select Worker!!");
			$("a[href$='#tab1']").click();
			return;
		}

		if (imageArray != null) {
			if (confirm('<spring:message code="save.isSave" />')) {
				$.ajax({
					type: 'POST',
					url: '${REQUEST_CONTEXT_PATH}/image/imageInfo/assignWorker',
					data: JSON.stringify(sendData),
					contentType : "application/json",
					dataType : 'json',
					success: function(data) {
						if (data.resultCd == 'success') { // 성공
							alert(data.resultMsg);
							location.href = location.href;
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
			}
		} else {
			$("#assignWorker").modal('hide');
			alert("Please Select Images!!");
		}
	}
//-->
</script>

<script type="text/javascript">
function fnDataExport() {
	
	var sdt = $('#startDt').val();
	var edt = $('#endDt').val();
	var type = $('#sensorDataFileTypeCd').val();
	var name = $('#sensorDataFileNm').val();
	
	console.log('sdt: '+sdt);
	console.log('edt: '+edt);
	console.log('type: '+type);
	console.log('name: '+name);
	
	var url = 'downloadPackage?sdt='+sdt+'&edt='+edt+'&type='+type+'&name='+name;
	location.href = url;
	
	/*
	var checkboxValues = [];
	$("#fileDownloadTbody input[name='imageCheck']:checked").each(function(){
		checkboxValues.push(this.value);
	});

	if (checkboxValues.length < 1) {
		alert("Please Select Images!!");
	} else {
		var url = 'download?checkArray[]='+checkboxValues;
		location.href = url;
	}
	*/
}
</script>

<!-- button area -->
<div class="row button-wrap">
	<div class="col-xs-12 text-align-right">
		<custom:auth type="CREATE">
			<div class="btn-group">
				<a href="#" class="btn btn-sm btn-success" role="button" data-toggle="modal" data-target="#myUpload">
				<!-- <a href="#" class="btn btn-sm btn-success" role="button" data-toggle="modal" data-target="#myModal"> -->
					<i class="fa fa-upload"></i> <spring:message code="common.import" /></a>
			</div>
			<div class="btn-group">
				<!-- <a href="#" class="btn btn-sm btn-info" role="button"  data-toggle="modal" data-target="#fileDownload"> -->
				<a href="#" class="btn btn-sm btn-info" role="button" onclick="fnDataExport()">
					<i class="fa fa-download"></i> <spring:message code="common.export" /></a>
			</div>
			<!-- 
			<div class="btn-group">
				<a href="#" class="btn btn-sm btn-warning" role="button" data-toggle="modal" data-target="#assignWorker">
					<i class="fa fa-group"></i> <spring:message code="admin.image.assignImageWorker" /></a>
			</div>
			 -->
		</custom:auth>
	</div>
</div>
<!-- // button area -->

<p></p>

<!-- search -->
<div class="well search-wrap">
	<form:form method="get" action="list" modelAttribute="sensorDataFileInfoEntity" cssClass="form-inline search-form" role="form" autocomplete="off">
		<fieldset>
			<div class="row">
				<div class="col-sm-10">
					<div class="row">
						<div class="col-sm-12">
							<label><spring:message code="admin.image.imageFileRegistDt" /></label>
							<div class="form-group">
								<div class="input-group">
									<form:input path="startDt" readonly="true" cssClass="form-control datepicker" data-dateformat="yy-mm-dd" />
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
								~
								<div class="input-group">
									<form:input path="endDt" readonly="true" cssClass="form-control datepicker" data-dateformat="yy-mm-dd" />
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
		        		<div class="col-sm-6">
							<div class="form-group">
								<label for="sensorDataFileTypeCd"><spring:message code="admin.image.imageFileTypeCd" /></label>
								<label class="select">
									<tags:select id="sensorDataFileTypeCd" name="sensorDataFileTypeCd" group="TA007" cssClass="form-control" value="${sensorDataFileInfoEntity.sensorDataFileTypeCd}">
										<option value=""><spring:message code="common.all" /></option>
									</tags:select>
									<i></i>
								</label>
							</div>
						</div>


					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<spring:message var="msgImageFileNm" code="admin.image.imageFileNm" />
								<label for="sensorDataFileNm">${msgImageFileNm}</label>
								<form:input path="sensorDataFileNm" cssClass="form-control" placeholder="${msgImageFileNm}" />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label class="control-label"><spring:message code="admin.image.imageTagggingYn" /></label>
								<label class="select">
									<select id="taggingYn" name="taggingYn" class="form-control">
										<option value=""><spring:message code="common.all" /></option>
										<option value="Y" <c:if test="${sensorDataFileInfoEntity.taggingYn eq 'Y'}"> selected</c:if> ><spring:message code="admin.image.imageTagggingY" /></option>
										<option value="N" <c:if test="${sensorDataFileInfoEntity.taggingYn eq 'N'}"> selected</c:if> ><spring:message code="admin.image.imageTagggingN" /></option>
									</select>
									<i></i>
								</label>
							</div>
						</div>
					</div>
				</div>

				<div class="col-sm-2 text-center search_btn_wrap">
					<button type="submit" class="btn btn-default btn-primary" role="button">
						<i class="fa fa-search"></i> <spring:message code="common.search" />
					</button>
				</div>
			</div>
		</fieldset>
	</form:form>
</div>

<!-- list -->
<div class="table-responsive smart-form">
	<table class="table table-striped table-bordered table-hover" style="table-layout:fixed;">
		<colgroup>
			<col width="8%" />
			<col width="20%" />
			<col width="44%" />
			<col width="20%" />
			<col width="8%" />
		</colgroup>

		<thead>
			<tr>
				<th><%--<spring:message code="admin.image.imageFileSq" />--%>일련번호</th>
				<th><%--<spring:message code="admin.image.imageFileGroup" />--%>데이터그룹</th>
				<th><spring:message code="admin.image.imageFilePath" /></th>
				<th><spring:message code="admin.image.imageFileNm" /></th>
				<th><spring:message code="admin.image.imageFileTypeCd" /></th>
			</tr>
		</thead>

		<tbody>
			<c:if test="${empty list}">
				<tr><td colspan="6"><spring:message code="common.dataIsEmpty" /></td></tr>
			</c:if>
			<c:forEach var="item" items="${list}">
				<tr onclick="javascript:fnSelect('${item.sensorDataFileSq}','${item.sensorDataFileTypeCd}');">
					<td>${item.sensorDataFileSq}</td>
					<%-- <td class="ellipsis" title="${item.sensorDataFilePackageIdSq}">${item.sensorDataFilePackageIdSq}</td> --%>
					<td class="ellipsis" title="${item.sensorDataFileGroup}">${item.sensorDataFileGroup}</td>
					<td class="ellipsis" title="${item.sensorDataFilePath}">${item.sensorDataFilePath}</td>
					<td class="ellipsis" title="${item.sensorDataFileNm}">${item.sensorDataFileNm}</td>
					<spring:eval var="sensorDataFileTypeCdNm" expression="@commonCode.getCode('TA007', item.sensorDataFileTypeCd)"/>
					<td>${sensorDataFileTypeCdNm}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<jsp:include page="/include/paging.jsp" flush="true" />

<!-- Upload -->
<div class="container modal fade" id="myUpload" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">

			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">File Upload</h4>
			</div>

			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">

						<p>
							1. 업로드할 파일을 선택 후 먼저 파일을 업로드합니다.<br/>
							2. 정보입력 버튼을 눌러 업로드 한 파일의 압축을 풀고 DB정보를 입력합니다. 
						</p>

						<div class="alert alert-warining hidden" id="support-alert">
							<b>Warning!</b> Your browser does not seem to support the
							features necessary to run tus-js-client. The buttons below may
							work but probably will fail silently.
						</div>

						<br />

						<!-- <input type="hidden" id="endpoint" name="endpoint" value="http://192.168.0.190:1080/files/"> -->
						<!-- <input type="hidden" id="endpoint" name="endpoint" value="http://127.0.0.1:1080/files/">  -->
                        <input type="hidden" id="endpoint" name="endpoint" value="<c:out value="${commonConfig.getProperty('TUSD_FILES') }"/>">
						<input type="hidden" id="chunksize" name="chunksize">
						<input type="hidden" id="resume" value="fasle">
						
						<!--
						<table>
							<tr>
								<td><label for="endpoint"> Upload endpoint: </label></td>
								<td>
									<input type="text" id="endpoint" name="endpoint" value="https://master.tus.io/files/">
									<input type="text" id="endpoint" name="endpoint"
									value="http://localhost:1080/files/">
								</td>
							</tr>
							<tr>
								<td><label for="chunksize"> Chunk size (bytes): </label></td>
								<td><input type="number" id="chunksize" name="chunksize">
								</td>
							</tr>
							<tr>
								<td><label for="resume"> Perform full upload: <br />
										<small>(even if we could resume)</small>
								</label></td>
								<td><input type="checkbox" id="resume"></td>
							</tr> 
						</table>
						-->

						<form id="fileUploadNewForm" name="fileUploadNewForm" role="form" method="get">
						<input type="hidden" id="fileuid" name="fileuid" />
						</form>
						
						<div class="well well-sm well-primary">
							<div class="input input-file">
								<input type="file">
							</div>
							<div style="padding-top:10px">
							 	<div class="progress">
								  <div class="progress-bar progress-bar-warning progress-bar-striped" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
								    <div id="progress-bar-text">0%</div>
								  </div>
								</div>
							</div>
							<div>
								<button type="button" class="btn btn-primary stop" id="toggle-btn">Start Upload</button>
								<button id="db-insert" type="button" class="btn btn-success" style="visibility:hidden" onclick="addSensorDatafile();">정보입력</button>
							</div>
							
						</div>

						<div>
							<p id="upload-list"></p>
						</div>

					</div>
				</div>
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					Cancel
				</button>
			</div>

			<script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/tus-js-client/dist/tus.js"></script>
			<script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/tus-js-client/demo/demo.js"></script>
 
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<script type="text/javascript">
function addSensorDatafile() {
	var frm = document.fileUploadNewForm;
	//frm.action = "/admin/sensorData/sensorDataInfo/inputData?fileuid="+frm.fileuid.value;
	frm.action = "addSensorData?fileuid="+frm.fileuid.value;
	frm.submit();
}
</script>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">File Upload</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<div class="well well-sm well-primary">
							<form id="fileUploadForm" class="smart-form" role="form" action="upload" enctype="multipart/form-data" method="post">
								<fieldset>
									<section>
										<label class="label">File input</label>
										<div class="input input-file">
											<span class="button"><input type="file" id="file" name="file" onchange="this.parentNode.nextSibling.value = this.value">Browse</span><input type="text" placeholder="Include some files" readonly="">
										</div>
									</section>
								</fieldset>
							</form>
						</div>
					</div>
				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					Cancel
				</button>
				<button type="button" class="btn btn-primary" onclick="javascript:fnUpload();">
					Upload
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- Modal -->
<div class="modal fade" id="assignWorker" tabindex="-1" role="dialog" aria-labelledby="assignWorkerLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 90%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="assignWorkerLabel">Assign Worker</h4>
			</div>
			<div class="modal-body">
				<form id="wizard-1" novalidate="novalidate">
					<div id="bootstrap-wizard-1" class="col-sm-12">
						<div class="form-bootstrapWizard">
							<ul class="bootstrapWizard form-wizard text-align">
								<li class="active" data-target="#step1" style="width: 60%;">
									<a href="#tab1" data-toggle="tab"> <span class="step">1</span> <span class="title">작업자 선택</span> </a>
								</li>
								<li data-target="#step2">
									<a href="#tab2" data-toggle="tab"> <span class="step">2</span> <span class="title">할당 이미지 선택</span> </a>
								</li>
							</ul>
							<div class="clearfix"></div>
						</div>
						<div class="tab-content">
							<div class="tab-pane active" id="tab1">
								<br>
								<h3><strong>Step 1</strong> - 작업자 선택</h3>
								<div class="alert alert-info fade in">
									<button class="close" data-dismiss="alert">
										×
									</button>
									<i class="fa-fw fa fa-info"></i>
									<strong>Info!</strong> Place an info message box if you wish.
								</div>
								<div>
									<jsp:include page="/WEB-INF/views/sensorData/sensorDataFileInfo/list-sub.jsp" flush="false"/>
								</div>
							</div>

							<div class="tab-pane" id="tab2">
								<br>
								<h3><strong>Step 2</strong> - 할당 이미지 선택</h3>
								<br>
								<section id="widget-grid" class="">
									<!-- row -->
									<div class="row">
										<article class="col-sm-12 col-md-12 col-lg-6">
											<!-- Widget ID (each widget will need unique ID)-->
											<div class="jarviswidget jarviswidget-sortable jarviswidget-color-blueDark" id="wid-id-2" data-widget-togglebutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false" data-widget-fullscreenbutton="false" data-widget-collapsed="false" data-widget-attstyle="jarviswidget-color-blueDark">
												<header>
													<h2>이미지 리스트 </h2>
												</header>
												<!-- widget div-->
												<div>
													<div id="imageAssignDivBody" class="widget-body" style="min-height: 900px; max-height: 1000px;">
														<div>
															<jsp:include page="/WEB-INF/views/sensorData/sensorDataFileInfo/img-list-assign-work.jsp" flush="false"/>
														</div>
													</div>
												</div>
											</div>
										</article>
										<article class="col-sm-12 col-md-12 col-lg-1">
											<div style="text-align: center;">
												<a id="leftArrow" class="btn btn-default btn-lg" href="javascript:void(0);"><i class="glyphicon glyphicon-arrow-left"></i></a>
												<a id="rightArrow" class="btn btn-default btn-lg" href="javascript:void(0);"><i class="glyphicon glyphicon-arrow-right"></i></a>
											</div>
										</article>
										<article class="col-sm-12 col-md-12 col-lg-5">
											<!-- Widget ID (each widget will need unique ID)-->
											<div class="jarviswidget jarviswidget-sortable jarviswidget-color-blueDark" id="wid-id-2" data-widget-togglebutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false" data-widget-fullscreenbutton="false" data-widget-collapsed="false" data-widget-attstyle="jarviswidget-color-blueDark">
												<header>
													<h2>할당 이미지 </h2>
												</header>
												<!-- widget div-->
												<div>
													<div class="widget-body table-responsive smart-form" style="min-height: 900px; max-height: 1000px;">
														<table style="width:1000px; table-layout:fixed;" class="table table-bordered table-hover">
															<colgroup>
																<col width="5%" />
																<col width="50%" />
																<col width="15%" />
																<col width="10%" />
																<col width="10%" />
															</colgroup>

															<thead>
																<tr>
																	<th class="checkbox" style="margin-bottom: 0px;"><label class="checkbox"><input type="checkbox" class="checkAll" name="checkAll" /><i></i></label></th>
																	<th><spring:message code="admin.image.imageFilePath" /></th>
																	<th><spring:message code="admin.image.imageFileNm" /></th>
																	<th><spring:message code="admin.image.imageFileTypeCd" /></th>
																	<th><spring:message code="admin.image.imageFileDownloadPathCd" /></th>
																</tr>
															</thead>

															<tbody id="assignImageTbody">
																<%-- <tr><td colspan="6"><spring:message code="common.dataIsEmpty" /></td></tr> --%>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</article>
									</div>
								</section>
								<br>
								<br>
								<div align="center">
									<button type="button" class="btn btn-success btn-lg" onclick="javascript:assignImageToWorker();">
										<spring:message code="common.assign" />
									</button>
								</div>
							</div>

							<div class="form-actions">
								<div class="row">
									<div class="col-sm-12">
										<ul class="pager wizard no-margin">
											<!--<li class="previous first disabled">
											<a href="javascript:void(0);" class="btn btn-lg btn-default"> First </a>
											</li>-->
											<li class="previous disabled">
												<a href="javascript:void(0);" class="btn btn-lg btn-default"> Previous </a>
											</li>
											<!--<li class="next last">
											<a href="javascript:void(0);" class="btn btn-lg btn-primary"> Last </a>
											</li>-->
											<li class="next">
												<a href="javascript:void(0);" class="btn btn-lg txt-color-darken"> Next </a>
											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- Modal -->
<div class="modal fade" id="fileDownload" tabindex="-1" role="dialog" aria-labelledby="fileDownloadLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 90%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="fileDownloadLabel">SensorData Export</h4>
			</div>
			<div id="fileDownloadModalBody" class="modal-body">
				<!-- row -->
				<div class="row">
					<article class="col-sm-12 col-md-12 col-lg-6">
						<!-- Widget ID (each widget will need unique ID)-->
						<div class="jarviswidget jarviswidget-sortable jarviswidget-color-blueDark" id="wid-id-3" data-widget-togglebutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false" data-widget-fullscreenbutton="false" data-widget-collapsed="false" data-widget-attstyle="jarviswidget-color-blueDark">
							<header>
								<h2>데이터 목록 </h2>
							</header>
							<!-- widget div-->
							<div>
								<div id="imageExportDivBody" class="widget-body" style="min-height: 600px; max-height: 800px;">
									<div>
										<jsp:include page="/WEB-INF/views/sensorData/sensorDataFileInfo/img-list-export.jsp" flush="false"/>
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
								<h2>다운로드 데이터 목록 </h2>
							</header>
							<!-- widget div-->
							<div>
								<div class="widget-body table-responsive smart-form" style="min-height: 600px; max-height: 800px;">
									<table style="width:1000px; table-layout:fixed;" class="table table-bordered table-hover">
										<colgroup>
											<col width="5%" />
											<col width="50%" />
											<col width="15%" />
											<col width="10%" />
											<col width="10%" />
										</colgroup>

										<thead>
											<tr>
												<th class="checkbox" style="margin-bottom: 0px;"><label class="checkbox"><input type="checkbox" class="checkAll" name="checkAll" /><i></i></label></th>
												<th><spring:message code="admin.image.imageFilePath" /></th>
												<th><spring:message code="admin.image.imageFileNm" /></th>
												<th><spring:message code="admin.image.imageFileTypeCd" /></th>
												<th><spring:message code="admin.image.imageFileDownloadPathCd" /></th>
											</tr>
										</thead>

										<tbody id="fileDownloadTbody">
											<%-- <tr><td colspan="6"><spring:message code="common.dataIsEmpty" /></td></tr> --%>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</article>
				</div>
				<div align="center">
					<button type="button" class="btn btn-info btn-lg" onclick="javascript:fnXmlDownload();">
						<spring:message code="common.export.xml" />
					</button>
					<button type="button" class="btn btn-success btn-lg" onclick="javascript:fnDownload();">
						<spring:message code="common.export" />
					</button>
				</div>
			</div>
			<div class="modal-footer">
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->