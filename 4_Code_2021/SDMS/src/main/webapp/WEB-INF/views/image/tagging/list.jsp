<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="custom" uri="/tags/custom-taglib"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page trimDirectiveWhitespaces="true"%>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css" media="screen" href="${REQUEST_CONTEXT_PATH}/resources/css/via.css?1.0.3">
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
	<form:form method="get" action="list" modelAttribute="imageTaggingEntity" cssClass="form-inline search-form" role="form" autocomplete="off">
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
								<label for="imageFileTypeCd"><spring:message code="admin.image.imageFileTypeCd" /></label>
								<label class="select">
									<tags:select id="imageFileTypeCd" name="imageFileTypeCd" group="TA004" cssClass="form-control" value="${imageTaggingEntity.imageFileTypeCd}">
										<option value=""><spring:message code="common.all" /></option>
									</tags:select>
									<i></i>
								</label>
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label for="imageFileDownloadPathCd"><spring:message code="admin.image.imageFileDownloadPathCd" /></label>
								<label class="select">
									<tags:select id="imageFileDownloadPathCd" name="imageFileDownloadPathCd" group="TA005" cssClass="form-control" value="${imageTaggingEntity.imageFileDownloadPathCd}">
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
								<label for="imageFileNm">${msgImageFileNm}</label>
								<form:input path="imageFileNm" cssClass="form-control" placeholder="${msgImageFileNm}" />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label class="control-label"><spring:message code="admin.image.imageTagggingYn" /></label>
								<label class="select">
									<select id="taggingYn" name="taggingYn" class="form-control">
										<option value=""><spring:message code="common.all" /></option>
										<option value="Y" <c:if test="${imageTaggingEntity.taggingYn eq 'Y'}"> selected</c:if> ><spring:message code="admin.image.imageTagggingY" /></option>
										<option value="N" <c:if test="${imageTaggingEntity.taggingYn eq 'N'}"> selected</c:if> ><spring:message code="admin.image.imageTagggingN" /></option>
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
							<img data-object='${info}' src="/uploadHome${info.imageFilePath}" />
						</div>
						<p class="tit">
							<c:if test="${not empty info.imageJsonFileDesc}">
								<img src="<c:url value="/resources/images/icon_saved.png" />" />
							</c:if>
							<c:if test="${empty info.imageJsonFileDesc}">
								<img src="<c:url value="/resources/images/icon_nonsaved.png" />" />
							</c:if>
							<span>${info.imageFileNm}</span>
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
						<button type="button" onclick="save_tagging_data();" class="btn btn-sm btn-primary" role="button">
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
					<td id="imageFileNmDesc" style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"></td>
				</tr>
				<tr>
					<th>파일타입</th>
					<td id="imageFileType"></td>
				</tr>
				<tr>
					<th>수집경로</th>
					<td id="imageFileDownloadPath"></td>
				</tr>
				<tr>
					<th>크기</th>
					<td id="imageFileScale"></td>
				</tr>
				<tr>
					<th>용량</th>
					<td id="imageFileSize"></td>
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
<script src="${REQUEST_CONTEXT_PATH}/resources/js/via.js?1.0.3" type="text/javascript"></script>
<script type="text/javascript">
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
            $('#imageFileNmDesc').text(object.imageFileNm);
            $('#imageFileScale').text(object.imageFileScaleX + ' x ' + object.imageFileScaleY);
            $('#imageFileSize').text(object.imageFileSize + ' byte');
            $('#imageFileType').text(object.imageFileTypeCdNm);
            $('#imageFileDownloadPath').text(object.imageFileDownloadPathCdNm);
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
		});

        _via_init();
	});

	function save_tagging_data() {
        if( $('#data_list .on').length == 0) { return; }
        if( confirm('저장하시겠습니까?') ) {
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