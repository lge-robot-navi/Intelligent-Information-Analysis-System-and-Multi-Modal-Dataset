<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="custom" uri="/tags/custom-taglib"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page trimDirectiveWhitespaces="true"%>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css" media="screen" href="${REQUEST_CONTEXT_PATH}/resources/css/via.css?1.0.3">
<script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/wavesurfer.js/wavesurfer.min.js"></script>
<style>
	.well { min-width: 1180px; min-height:780px; margin: 0; }
	.photo_list {overflow:hidden; list-style:none; margin:0 0 0 -10px; padding:0;}
	.photo_list li {float:left; width:10%;}
	.photo_list li .img {position:relative; height:80px; margin:0 0 0 10px; padding:0; opacity: 10; }
	.photo_list li .img > img {position:absolute; left:0; top:0; width:100%; height:100%; object-fit:cover;}
	.photo_list li .img .bg_cover {position:absolute; left:0; top:0; width:100%; height:80px; z-index:2; background:#000; opacity:.50;}
	.photo_list li .img.on .bg_cover {background:none; opacity:0;}
	.photo_list li .tit {margin:0 0 0 10px; padding:4px 0 0; text-align:center; font-size:12px; color:#777; text-overflow:ellipsis; white-space:nowrap; overflow:hidden;}
	.pagination { margin: 8px 0 16px; }
	.on { border: solid 2px #ff0000 }
	.select_label { width: 120px; }
</style>

<div class="well search-wrap">
	<form:form id="taggingForm" name="taggingForm" method="get" action="list" modelAttribute="sensorDataTaggingEntity" cssClass="form-inline search-form" role="form" autocomplete="off">
		<fieldset>
			<div class="row">
				<div class="col-sm-10">
					<div class="row">
						<div class="col-sm-12">
							<label>이미지생성일시</label>
							<div class="form-group">
								<div class="input-group">
									<form:input path="cStartDt" readonly="true" cssClass="form-control datepicker" data-dateformat="yy-mm-dd" />
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
								~
								<div class="input-group">
									<form:input path="cEndDt" readonly="true" cssClass="form-control datepicker" data-dateformat="yy-mm-dd" />
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</div>
						</div>
					</div>
					
					<form:input type="hidden" path="sensorDataFileTypeCd" cssClass="form-control" placeholder="${sensorDataFileTypeCd}" />
					
					<%-- 
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label for="sensorDataFileTypeCd"><spring:message code="admin.image.imageFileTypeCd" /></label>
								<label class="select">
									<tags:select id="sensorDataFileTypeCd" name="sensorDataFileTypeCd" group="TA007" cssClass="form-control" value="${sensorDataTaggingEntity.sensorDataFileTypeCd}">
										<option value=""><spring:message code="common.all" /></option>
									</tags:select>
									<i></i>
								</label>
							</div>
						</div>
					</div>
					--%>
					
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
							<%--
								<spring:message var="msgImageFileNm" code="admin.image.imageFileNm" />
								<label for="sensorDataFileNm">${msgImageFileNm}</label>
								<form:input path="sensorDataFileNm" cssClass="form-control" placeholder="${msgImageFileNm}" />
							--%>
								<label class="control-label">Agent</label>
								<label class="select">
									<select id="sensorDataFileAgent" name="sensorDataFileAgent" class="form-control">
										<option value=""><spring:message code="common.all" /></option>
										<option value="FX01" <c:if test="${sensorDataTaggingEntity.sensorDataFileAgent eq 'FX01'}"> selected</c:if> >FX01</option>
										<option value="FX02" <c:if test="${sensorDataTaggingEntity.sensorDataFileAgent eq 'FX02'}"> selected</c:if> >FX02</option>
										<option value="FX03" <c:if test="${sensorDataTaggingEntity.sensorDataFileAgent eq 'FX03'}"> selected</c:if> >FX03</option>
										<option value="FX04" <c:if test="${sensorDataTaggingEntity.sensorDataFileAgent eq 'FX04'}"> selected</c:if> >FX04</option>
										<option value="FX05" <c:if test="${sensorDataTaggingEntity.sensorDataFileAgent eq 'FX05'}"> selected</c:if> >FX05</option>
										<option value="FX06" <c:if test="${sensorDataTaggingEntity.sensorDataFileAgent eq 'FX06'}"> selected</c:if> >FX06</option>
										<option value="FX07" <c:if test="${sensorDataTaggingEntity.sensorDataFileAgent eq 'FX07'}"> selected</c:if> >FX07</option>
										<option value="FX08" <c:if test="${sensorDataTaggingEntity.sensorDataFileAgent eq 'FX08'}"> selected</c:if> >FX08</option>
										<option value="FX09" <c:if test="${sensorDataTaggingEntity.sensorDataFileAgent eq 'FX09'}"> selected</c:if> >FX09</option>
										<option value="FX10" <c:if test="${sensorDataTaggingEntity.sensorDataFileAgent eq 'FX10'}"> selected</c:if> >FX10</option>
									</select>
									<i></i>
								</label>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label class="control-label"><spring:message code="admin.image.imageTagggingYn" /></label>
								<label class="select">
									<select id="taggingYn" name="taggingYn" class="form-control">
										<option value=""><spring:message code="common.all" /></option>
										<option value="Y" <c:if test="${sensorDataTaggingEntity.taggingYn eq 'Y'}"> selected</c:if> ><spring:message code="admin.image.imageTagggingY" /></option>
										<option value="N" <c:if test="${sensorDataTaggingEntity.taggingYn eq 'N'}"> selected</c:if> ><spring:message code="admin.image.imageTagggingN" /></option>
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


	<%-- image list start --%>
	<div style="margin-top: 10px;">
		<c:if test="${not empty list}">
			<ul id="data_list" class="photo_list">
				<c:forEach items="${list}" var="info">
					<li>
						<div class="img">
							<div class="bg_cover"></div>
							<img data-object='${info}' src="/uploadHome${info.sensorDataFilePath}" />
						</div>
						<p class="tit">
							<span>${info.sensorDataFileNm}</span>
						</p>
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${empty list}">
			<div style="height: 80px; width: 100%; text-align: center; padding-top: 25px;">
				<spring:message code="common.dataIsEmpty"/>
			</div>
		</c:if>
		<jsp:include page="/include/paging.jsp" flush="true" />
	</div>
	<%-- image list end --%>

	<%-- audio list start --%>
	<div class="row">
		<c:forEach var="item" items="${listAudio}" varStatus="status">
			<input type="hidden" id="audioCount" value="${fn:length(listAudio)} " />
			<input type="hidden" id="audioPath${status.count}" value="${item.sensorDataFilePath}" />
			<input type="hidden" id="audioNm${status.count}" value="${item.sensorDataFileNm}" />
			<div class="col-sm-6">
				<!-- audio -->
				<div id="waveform${status.count}" style="background: black"></div>
				<div style="height: 10px"></div>
				<div style="text-align: center">
					<div class="row"
						style="display: flex; justify-content: center; align-items: center;">
						<div class="col-xs-2">
							<button class="btn btn-sm btn-primary" onclick="wavesurferPlay(${status.count});">
								<i class="glyphicon glyphicon-play"></i> Play
							</button>
						</div>
						<div class="col-xs-3" id="divPlayTime${status.count}">
						</div>
						<div class="col-xs-1">
							<i class="glyphicon glyphicon-zoom-in"></i>
						</div>
						<div class="col-xs-5">
							<input id="slider${status.count}" type="range" min="1" max="200" value="1" style="width: 100%" />
						</div>
						<div class="col-xs-1">
							<i class="glyphicon glyphicon-zoom-out"></i>
						</div>
					</div>
				</div>
			</div>
			<c:if test="${status.count % 2 == 0}">
				</div>
				<div style="height: 10px"></div>
				<div class="row">
			</c:if>
		</c:forEach>
	</div>
	<%-- audio list end --%>

	<%-- change image type start --%>
	<div class="top_panel" id="ui_top_panel">
		<ul>
			<li class="btn_zoom btn_del" id="toolbar_del_region">
				<div class="btn-group">
					<div style="background:#6b6b6b" class="btn-sm">영상이미지 전환</div>
				</div>
			</li>
			<li class="btn_zoom btn_del" id="toolbar_del_region">
				<div class="btn-group">
					<span id="btnRGB" type="button" onclick="changeImageData('100');"
						class="btn btn-sm btn-default" title="Delete Region">RGB</span>
				</div>
			</li>
			<li class="btn_zoom btn_del" id="toolbar_del_region">
				<div class="btn-group">
					<span id="btnDepth" type="button" onclick="changeImageData('200');"
						class="btn btn-sm btn-default" title="Delete Region">Depth</span>
				</div>
			</li>
			<li class="btn_zoom btn_del" id="toolbar_del_region">
				<div class="btn-group">
					<span id="btnNV1" type="button" onclick="changeImageData('300');"
						class="btn btn-sm btn-default" title="Delete Region">Night Vision 1</span>
				</div>
			</li>
			<li class="btn_zoom btn_del" id="toolbar_del_region">
				<div class="btn-group">
					<span id="btnNV2" type="button" onclick="changeImageData('400');"
						class="btn btn-sm btn-default" title="Delete Region">Night Vision 2</span>
				</div>
			</li>
			<li class="btn_zoom btn_del" id="toolbar_del_region">
				<div class="btn-group">
					<span id="btnThermal" type="button" onclick="changeImageData('500');"
						class="btn btn-sm btn-default" title="Delete Region">Thermal</span>
				</div>
			</li>
		</ul>
	</div>
	<div style="height:10px"></div>
	<%-- change image type end --%>
	
	<%-- image tagging start --%>
	<div class="top_panel" id="ui_top_panel">
		<input type="file" id="invisible_file_input" name="file" style="display: none">

		<ul>
			<li id="region_shape_select" class="btn_region_shape" onclick="select_region_shape('select')" title="select">
				<img src="<c:url value="/resources/images/select_on.png" />" />
			</li>
			<li id="region_shape_rect" class="btn_region_shape" onclick="select_region_shape('rect')" title="rect">
				<img src="<c:url value="/resources/images/rect_off.png" />" />
			</li>
			<li id="region_shape_polygon" class="btn_region_shape" onclick="select_region_shape('polygon')" title="Polygon">
				<img src="<c:url value="/resources/images/polygon_off.png" />" />
			</li>
			<li class="gubun"></li>
			<li class="btn_zoom btn_zoom_in" id="toolbar_zoom_in">
				<div class="btn-group">
					<span type="button" onclick="zoom_in();" class="btn btn-sm btn-default" title="Zoom In">+</span>
				</div>
			</li>
			<li class="btn_zoom btn_zoom_out" id="toolbar_zoom_out">
				<div class="btn-group">
					<span type="button" onclick="zoom_out();" class="btn btn-sm btn-default" title="Zoom Out">-</span>
				</div>
			</li>
			<li class="btn_zoom btn_zoom_reset" id="toolbar_zoom_reset">
				<div class="btn-group">
					<span type="button" onclick="reset_zoom_level();" class="btn btn-sm btn-default" title="Zoom Reset">=</span>
				</div>
			</li>
			<li class="gubun"></li>
			<li class="btn_zoom btn_del" id="toolbar_del_region">
				<div class="btn-group">
					<span type="button" onclick="del_sel_regions();" class="btn btn-sm btn-default" title="Delete Region">Del</span>
				</div>
			</li>

			<!-- <li id="toolbar_copy_region" style="margin-left: 2em;" onclick="copy_sel_regions()" title="Copy Region">c</li>
			<li id="toolbar_paste_region" onclick="paste_sel_regions()" title="Paste Region">v</li>
			<li id="toolbar_sel_all_region" onclick="sel_all_regions()" title="Select All Regions">a</li> -->
			<%--<li class="dropdown">
				<a title="Load a set of image from local disk" onclick="sel_local_images()">Load Image</a>
			</li>--%>
			<li class="btn_save">
				<%--<a title="save" onclick="save_tagging_data()">Save</a>--%>
				<div class="col-xs-12">
					<div class="btn-group">
						<button id="btn-save" type="button" onclick="save_tagging_data();" class="btn btn-sm btn-primary" role="button">
							<i class="fa fa-save"></i> <spring:message code="common.save" />
						</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
	<!-- endof #top_panel -->



	<!-- Middle Panel contains a left-sidebar and image display areas -->
	<div class="middle_panel">
		<!-- Main display area: contains image canvas, ... -->
		<div id="display_area">
			<div id="canvas_panel">
				<canvas id="image_canvas"></canvas>
				<canvas id="region_canvas">
					Sorry, your browser does not support HTML5 Canvas functionality which is required for this application.
				</canvas>
			</div>
		</div>

		<!-- <button class="leftsidebar_accordion">Keyboard Shortcuts</button>
		<div class="leftsidebar_accordion_panel">
			<table style="padding: 2em 0em;">
				<tr>
					<td>+&nbsp;/&nbsp;-&nbsp;/&nbsp;=</td>
					<td>Zoom in/out/reset</td>
				</tr>
				<tr>
					<td>Ctrl + c</td>
					<td>Copy sel. regions</td>
				</tr>
				<tr>
					<td>Ctrl + v</td>
					<td>Paste sel. regions</td>
				</tr>
				<tr>
					<td>Ctrl + a</td>
					<td>Select all regions</td>
				</tr>
				<tr>
					<td>Del, Bkspc</td>
					<td>Delete image region</td>
				</tr>
				<tr>
					<td>Esc</td>
					<td>Cancel operation</td>
				</tr>
			</table>
		</div> -->

		<!-- region and file attributes input panel -->
		<div id="attributes_panel" style="display: none;">
			<%--<div id="attributes_panel_toolbar">
            <div onclick="toggle_attributes_input_panel()" class="attributes_panel_button">&times;</div>
            </div>--%>
			<div id="attributes_panel_meta_toolbar">
				이미지 프로퍼티
				<hr />
			</div>
			<table id="attributes_panel_meta_table">
				<tr>
					<th>파일명</th>
					<td id="sensorDataFileNmDesc" style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"></td>
				</tr>
				<tr>
					<th>파일타입</th>
					<td id="sensorDataFileType"></td>
				</tr>
				<!-- 
				<tr>
					<th>수집경로</th>
					<td id="sensorDataFileDownloadPath"></td>
				</tr>
				 -->
				<tr>
					<th>크기</th>
					<td id="sensorDataFileScale"></td>
				</tr>
				<tr>
					<th>용량</th>
					<td id="sensorDataFileSize"></td>
				</tr>

			</table>
			<div id="attributes_panel_toolbar">
				태깅 프로퍼티
				<hr />
			</div>
			<table id="attributes_panel_table">
				<tr>
					<th>분류</th>
					<td>
						<select id="tagging_dic_1_depth" class="select_label">
							<option value="">선택</option>
						
							<c:forEach var="info" items="${attributeList}">
								<option value="${info.imageTaggingDataDicIdSq}">${info.imageTaggingDataDicNm}</option>
							</c:forEach>
							
						</select><br/>
						<select id="tagging_dic_2_depth" class="select_label">
							<option value="">선택</option>
						</select><br/>
						<select id="tagging_dic_3_depth" class="select_label">
							<option value="">선택</option>
						</select>
					</td>
				</tr>
				<%--<tr>
					<th>라벨</th>
					<td><input type="text" id="label" /></td>
				</tr>--%>
				<tr>
					<th>타입</th>
					<td id="shape"></td>
				</tr>
				<tr>
					<th>좌표</th>
					<td>
						<div id="shape_info">
						</div>
					</td>
				</tr>

			</table>
		</div>
    </div>

    <!-- to show status messages -->
    <div id="message_panel"></div>

    <!-- this vertical spacer is needed to allow scrollbar to show items like Keyboard Shortcut hidden under the attributes panel -->
    <div style="width: 100%;" id="vertical_space"></div>
	<%-- image tagging end --%>
</div>
<script type="text/javascript">
	var attribute_map = ${attributeMap};
</script>
<script src="${REQUEST_CONTEXT_PATH}/resources/js/via.js?1.0.5" type="text/javascript"></script>
<script type="text/javascript">
var arrWavesurfer = new Array();
var audioCount = ${fn:length(listAudio)}; // audio file 갯수

for(var i=1; i<=audioCount; i++) {
	wavesurferCreate(i); // file 갯수만큼 wavesufer 객체 생성
}
	
function wavesurferPlay(index) {
	arrWavesurfer[index-1].playPause();
}

function wavesurferCreate(index) {
	var wavesurfer = WaveSurfer.create({
		  container: '#waveform'+index,
		  //waveColor: 'violet',
		  waveColor: 'green',
		  //progressColor: 'purple'
		  progressColor: 'skyblue',
		  //splitChannels: true,
		  height: 100
	});
	var audioPath = document.getElementById("audioPath"+index).value;
	wavesurfer.load('/uploadHome/'+audioPath);
	
	// TODO: 파일명 변경 필요
	// OLD - FX01_SOUND_20180830_131829_ch01.wav
	// NEW - FX01_SOUND_20180830_131829_131839_ch01.wav
	var audioNm = document.getElementById("audioNm"+index).value;
	var audioNmArr = audioNm.split("_");
	//console.log('date', audioNmArr[2]);
	//console.log('start', audioNmArr[3]);
	//console.log('end', audioNmArr[4]);
	//console.log('len: '+audioNmArr[3].length+', '+audioNmArr[4].length);
		
	if(audioNmArr[3].length === 6 && audioNmArr[4].length === 6) {

		var startTime = audioNmArr[2]+audioNmArr[3];
		var endTime = audioNmArr[2]+audioNmArr[4];

		// 시작일시
		var startDate = new Date(
		  parseInt(startTime.substring(0, 4), 10),
		  parseInt(startTime.substring(4, 6), 10) - 1,
		  parseInt(startTime.substring(6, 8), 10),
		  parseInt(startTime.substring(8, 10), 10),
		  parseInt(startTime.substring(10, 12), 10),
		  parseInt(startTime.substring(12, 14), 10)
		);

		// 종료일시
		var endDate = new Date(
		  parseInt(endTime.substring(0, 4), 10),
		  parseInt(endTime.substring(4, 6), 10) - 1,
		  parseInt(endTime.substring(6, 8), 10),
		  parseInt(endTime.substring(8, 10), 10),
		  parseInt(endTime.substring(10, 12), 10),
		  parseInt(endTime.substring(12, 14), 10)
		);

		// 두 일자 차이
		var dateGap = endDate.getTime() - startDate.getTime();
		var timeGap = new Date(0, 0, 0, 0, 0, 0, endDate - startDate);
		var diffDay = Math.floor(dateGap / (1000 * 60 * 60 * 24)); // 일
		var diffHour = timeGap.getHours(); // 시간
		var diffMin = timeGap.getMinutes(); // 분
		var diffSec = timeGap.getSeconds(); // 초

		//console.log(diffDay + "일 " + diffHour + "시간 " + diffMin + "분 " + diffSec + "초 ");
		   
		var startFmt = audioNmArr[3].substr(0,2)+":"+audioNmArr[3].substr(2,2)+":"+audioNmArr[3].substr(4,2);
		var endFmt = audioNmArr[4].substr(0,2)+":"+audioNmArr[4].substr(2,2)+":"+audioNmArr[4].substr(4,2);
		$('#divPlayTime'+index).html(startFmt+" ~ "+endFmt + ' ('+diffSec+' sec)');	
	} else {
		var startTime = audioNmArr[3].substr(0,2)+":"+audioNmArr[3].substr(2,2)+":"+audioNmArr[3].substr(4,2);
		$('#divPlayTime'+index).html(startTime);
	}
	
	var slider = document.querySelector('#slider'+index);
	
	slider.oninput = function () {
		var zoomLevel = Number(slider.value);
		wavesurfer.zoom(zoomLevel);
	};
	
	arrWavesurfer[index-1] = wavesurfer;
}

/*
var wavesurfer1 = WaveSurfer.create({
  container: '#waveform1',
  //waveColor: 'violet',
  waveColor: 'green',
  //progressColor: 'purple'
  progressColor: 'skyblue',
  //splitChannels: true,
  height: 100
});
var wavesurfer2 = WaveSurfer.create({
  container: '#waveform2',
  waveColor: 'green',
  progressColor: 'skyblue',
  height: 100
});
var wavesurfer3 = WaveSurfer.create({
  container: '#waveform3',
  waveColor: 'green',
  progressColor: 'skyblue',
  height: 100
});
var wavesurfer4 = WaveSurfer.create({
  container: '#waveform4',
  waveColor: 'green',
  progressColor: 'skyblue',
  height: 100
});

//wavesurfer1.load('/uploadHome/sensorData/upload/ea5fcb902282dd8cb1d2323afaf8dd5b/sound/FX01_SOUND_20180830_131829_ch01.wav')
//wavesurfer2.load('/uploadHome/sensorData/upload/ea5fcb902282dd8cb1d2323afaf8dd5b/sound/FX01_SOUND_20180830_131829_ch02.wav')
//wavesurfer3.load('/uploadHome/sensorData/upload/ea5fcb902282dd8cb1d2323afaf8dd5b/sound/FX01_SOUND_20180830_131829_ch03.wav')
//wavesurfer4.load('/uploadHome/sensorData/upload/ea5fcb902282dd8cb1d2323afaf8dd5b/sound/FX01_SOUND_20180830_131829_ch04.wav')

var audioPath1 = document.getElementById("audioPath1").value;
var audioPath2 = document.getElementById("audioPath2").value;
var audioPath3 = document.getElementById("audioPath3").value;
var audioPath4 = document.getElementById("audioPath4").value;

wavesurfer1.load('/uploadHome/'+audioPath1);
wavesurfer2.load('/uploadHome/'+audioPath2);
wavesurfer3.load('/uploadHome/'+audioPath3);
wavesurfer4.load('/uploadHome/'+audioPath4);

var slider1 = document.querySelector('#slider1');
var slider2 = document.querySelector('#slider2');
var slider3 = document.querySelector('#slider3');
var slider4 = document.querySelector('#slider4');

slider1.oninput = function () {
  var zoomLevel = Number(slider1.value);
  wavesurfer1.zoom(zoomLevel);
};
slider2.oninput = function () {
  var zoomLevel = Number(slider2.value);
  wavesurfer2.zoom(zoomLevel);
};
slider3.oninput = function () {
  var zoomLevel = Number(slider3.value);
  wavesurfer3.zoom(zoomLevel);
};
slider4.oninput = function () {
  var zoomLevel = Number(slider4.value);
  wavesurfer4.zoom(zoomLevel);
};
*/
</script>
<script type="text/javascript">
	var selectImage = ${sensorDataTaggingEntity.sensorDataFileTypeCd};

	function changeButtonColor() {
		// 3x3 matrix 변환을 RGB값을 기준으로 임의 시뮬레이션 값으로 적용하여, 일단 RGB에서만 저장하도록 막음. 
		// TODO: 나중에 삭제할 것
		if(selectImage == "100") {
			$('#btnRGB').css('background-color', 'red');
			$('#btnRGB').css('color', 'white');
			$('#btn-save').attr("disabled",false);
		} else if(selectImage == "200") {
			$('#btnDepth').css('background-color', 'red');
			$('#btnDepth').css('color', 'white');
			$('#btn-save').attr("disabled",true);
		} else if(selectImage == "300") {
			$('#btnNV1').css('background-color', 'red');
			$('#btnNV1').css('color', 'white');
			$('#btn-save').attr("disabled",true);
		} else if(selectImage == "400") {
			$('#btnNV2').css('background-color', 'red');
			$('#btnNV2').css('color', 'white');
			$('#btn-save').attr("disabled",true);
		} else if(selectImage == "500") {
			$('#btnThermal').css('background-color', 'red');
			$('#btnThermal').css('color', 'white');
			$('#btn-save').attr("disabled",true);
		} else {
			$('#btnRGB').css('background-color', 'red');
			$('#btnRGB').css('color', 'white');
			$('#btn-save').attr("disabled",true);
		}
	}
	
	changeButtonColor();

	function changeImageData(code) {
		var frm = document.taggingForm;
		frm.sensorDataFileTypeCd.value = code;
		frm.submit();
	}

	$(function() {
	    $('#data_list .img').click(function() {
	        // 저장하지 않고 다른 이미지 누른경우 유져 확인
            if($('#data_list .on').length > 0
				&& localStorage.getItem('_via_img_metadata')
				&& localStorage.getItem('_is_save') == 'false' ) {
				if(!confirm('수정사항을 저장하지 않았습니다 계속하시겠습니까?')) {
					return;
				}
			}

			// 이미지 태깅 로드
	        var _this = $(this);
	        $('#data_list .img').removeClass('on');
	        _this.addClass('on');
            var object = _this.find('img').data('object');
            
            // object: TA_SD_FILE_INFO 테이블과 TA_SD_JSON_FILE_INFO 테이블을 조인하여, 태깅정보를 가져옴
            //console.log('object: '+JSON.stringify(object));
            //console.log(object.sensorDataJsonFileDesc);
            
            $('#sensorDataFileNmDesc').text(object.sensorDataFileNm);
            $('#sensorDataFileScale').text(object.sensorDataFileScaleX + ' x ' + object.sensorDataFileScaleY);
            $('#sensorDataFileSize').text(object.sensorDataFileSize + ' byte');
            $('#sensorDataFileType').text(object.sensorDataFileTypeCdNm);
            //$('#imageFileDownloadPath').text(object.imageFileDownloadPathCdNm);
            var url = _this.find('img').attr('src');
            var filename = url.substring(url.lastIndexOf('/') + 1, url.length);

            var request = new XMLHttpRequest();
            request.open("GET", url);
            request.responseType = "blob";
            request.onload = function() {
                var data = this.response;
                var file = new File([data], filename, {type: data.type});
                store_url_img_ref(file, object);
            }
            request.send();
            
            if( object.sensorDataJsonFileDesc !== null && object.sensorDataJsonFileDesc !== undefined ) {
            	var jsonDesc = JSON.parse(object.sensorDataJsonFileDesc);
            	var regions = jsonDesc["image"]["regions"];
            	
                //console.log("dept_1_nm: "+regions["0"]["region_attributes"]["tagging_dic_1_depth_nm"]);
                //console.log("dept_2_nm: "+regions["0"]["region_attributes"]["tagging_dic_2_depth_nm"]);
                //console.log("dept_3_nm: "+regions["0"]["region_attributes"]["tagging_dic_3_depth_nm"]);
                //console.log("pos_x: "+regions["0"]["shape_attributes"]["x"]);
                //console.log("pos_y: "+regions["0"]["shape_attributes"]["y"]);
                //console.log("width: "+regions["0"]["shape_attributes"]["width"]);
                //console.log("height: "+regions["0"]["shape_attributes"]["height"]);
                
                var x = regions["0"]["shape_attributes"]["x"];
                var y = regions["0"]["shape_attributes"]["y"];
                var w = regions["0"]["shape_attributes"]["width"];
                var h = regions["0"]["shape_attributes"]["height"];
                
                //drawRect(x, y, w, h);
            }
            
		});

	    //drawRect(10, 10, 50, 50);
	    //drawRect(100, 100, 500, 50);
	    //drawRect(1106, 259, 118, 247);
	    //drawRect(100, 100, 118, 247);
	    
        _via_init();
	});
	
	function drawRect(x, y, width, height) {
		//var canvas = document.getElementById('image_canvas');
		var canvas = document.getElementById('region_canvas');

		if (canvas.getContext) {
			var ctx = canvas.getContext('2d');
			var color = "#ff0000";
			
			ctx.strokeStyle = color;
			ctx.strokeRect(x, y, width, height);
			//ctx.fillRect(x, y, width, height);
		} 
	}

	function save_tagging_data() {
        if( $('#data_list .on').length == 0) { return; }
        if( confirm('저장하시겠습니까?') ) {
            //var all_data = get_save_sensor_data();
            var all_data = get_save_data();
            cfShowBlock(true);
			$.ajax({
				url: 'form',
				method: 'POST',
				data: all_data,
				success: function(data) {
					//console.log(data);
					if( data.result == 'success' ) {
					    //console.log(data.json);
                        alert('저장 되었습니다.');
                        $('#data_list .on').parent().find('.tit img').attr('src', '<c:url value="/resources/images/icon_saved.png"/>');
                        $('#data_list .on img').attr('data-object', JSON.stringify(all_data));
                        localStorage.setItem('_is_save', 'true');
                        //window.location.reload();
                        //toggle_all_regions_selection(false);
                        //update_attributes_panel();
					} else {
                        alert('처리에 실패했습니다.');
					}
                    cfHideBlock();
				}
			});
		}
	}
</script>