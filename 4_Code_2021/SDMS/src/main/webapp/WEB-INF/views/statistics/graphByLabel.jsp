<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom" uri="/tags/custom-taglib" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
<!--
	//최근 접속 페이지 값 설정
	fnSetLatestPage();

	$(function() {

		$('#download-excel-file').click(function(e) {
			e.preventDefault();
			return downloadFile('download-excel-file?'+$('form:first').serialize());
		});

		$('.tagsinput').tagsinput({
		  allowDuplicates: false,
		    itemValue: 'id',  // this will be used to set id of tag
		    itemText: 'label' // this will be used to set text of tag
		});

		// 이미지 태깅 사전 초기데이터
		taggingDicselectBoxSet('',1);

		// 검색 태깅 데이터 세팅
		<c:forEach var="item" items="${imageTaggingDataDicMap}">
			var id = "${item.key}";
			var text = "${item.value}";
			console.log("id:"+id);
			console.log("text:"+text);
			$(".tagsinput").tagsinput('add', {id:id, label:text});
		</c:forEach>

		// 이미지 태깅 콤보박스 선택 이벤트
		$("#firstImageTaggingData").change(function(){
			var taggingData = $("#imageTaggingData").val().split(",");
			var upperImageTaggingDataDicId = this.value;
			taggingDicselectBoxSet(upperImageTaggingDataDicId,2)

			console.log(taggingData);
			var selectedText = $("#firstImageTaggingData option:selected").text();
			var selectedValue = $("#firstImageTaggingData option:selected").val();
			if (taggingData.length > 10) {
				alert("Maximum count 10.");
				return;
			}
			if (selectedText != "" && selectedValue != "") {
				$(".tagsinput").tagsinput('add', {id:selectedValue, label:selectedText});
			}
		});
		$("#secondImageTaggingData").change(function(){
			var taggingData = $("#imageTaggingData").val().split(",");
			var upperImageTaggingDataDicId = this.value;
			taggingDicselectBoxSet(upperImageTaggingDataDicId,3)

			var selectedText = $("#secondImageTaggingData option:selected").text();
			var selectedValue = $("#secondImageTaggingData option:selected").val();
			if (taggingData.length > 10) {
				alert("Selectable Taggging Dic Label Number is 10.");
				return;
			}
			if (selectedText != "" && selectedValue != "") {
				$(".tagsinput").tagsinput('add', {id:selectedValue, label:selectedText});
			}
		});
		$("#thirdImageTaggingData").change(function(){
			var taggingData = $("#imageTaggingData").val().split(",");
			var selectedText = $("#thirdImageTaggingData option:selected").text();
			var selectedValue = $("#thirdImageTaggingData option:selected").val();
			if (taggingData.length > 10) {
				alert("Selectable Taggging Dic Label Number is 10.");
				return;
			}
			if (selectedText != "" && selectedValue != "") {
				$(".tagsinput").tagsinput('add', {id:selectedValue, label:selectedText});
			}
		});

		// 검색
		$('#btnSearch').click(function() { search(); });

		// 사용여부 이벤트
		$('select[name=useYn]').change(function() { search(); });

	 	// Chart Data Setting
		var taggingCnt = new Array();
		var labels = new Array();
		var datasets = new Array();

		<c:forEach var="item1" items="${barAllList}">
			var taggingDicNm = new Array();
			var taggingCnts = new Array();
			labels.push("${item1.imageFileRegistDt}");
			<c:forEach var="item2" items="${item1.imageTaggingDataDicNmList}">
			taggingDicNm.push("${item2}");
			</c:forEach>
			<c:forEach var="item3" items="${item1.taggingCntList}">
			taggingCnts.push("${item3}");
			</c:forEach>
			taggingCnt.push(taggingCnts);
		</c:forEach>

		for(var j = 0; j < taggingDicNm.length; j++) {
			var datas = new Array();
			for(var i = 0; i < taggingCnt.length; i++) {
				datas.push(taggingCnt[i][j]);
			}
			var dataset = {
				label: taggingDicNm[j],
	            backgroundColor: dynamicColors(),
	            data: datas
			};
			datasets.push(dataset);
		}

	    // BAR CHART
		var barOptions = {
			//Boolean - Whether the scale should start at zero, or an order of magnitude down from the lowest value
		    scaleBeginAtZero : true,
		    //Boolean - Whether grid lines are shown across the chart
		    scaleShowGridLines : true,
		    //String - Colour of the grid lines
		    scaleGridLineColor : "rgba(0,0,0,.05)",
		    //Number - Width of the grid lines
		    scaleGridLineWidth : 1,
		    //Boolean - If there is a stroke on each bar
		    barShowStroke : true,
		    //Number - Pixel width of the bar stroke
		    barStrokeWidth : 1,
		    //Number - Spacing between each of the X value sets
		    barValueSpacing : 5,
		    //Number - Spacing between data sets within X values
		    barDatasetSpacing : 1,
		    //Boolean - Re-draw chart on page resize
	        responsive: true,
	        tooltips: {
	            mode : 'label',
	            bodyFontSize: 20
	        },
			scales: {
	            xAxes: [{
	            	type: "time",
	            	distribution: 'series',
                    time: {
                    	parser: "YYYY-MM-DD",
                    	displayFormats: {
                    		'millisecond': 'YYYY-MM-DD',
                            'second': 'YYYY-MM-DD',
                            'minute': 'YYYY-MM-DD',
                            'hour': 'YYYY-MM-DD',
                            'day': 'YYYY-MM-DD',
                            'month': 'YYYY-MM-DD',
                            'quarter': 'YYYY-MM-DD',
                            'year': 'YYYY-MM-DD',
                        },
                        tooltipFormat: 'YYYY-MM-DD',
                        min:labels[0],
                        max:labels[labels.length-1]
                    },
                    scaleLabel: {
                        display: true,
                        labelString: 'Date'
                    },
	            	ticks: {
	            		/* source:'data', */
	            		maxRotation: 45,
	                    minRotation: 45,
	                    // Create scientific notation labels
	                    callback: function(value, index, values) {
	                        return value.replace(/(\d{4})(\d{2})(\d{2})/g, '$1-$2-$3');
	                    }
	           		},
                    stacked: true
	            }],
	            yAxes: [{
	                ticks: {
	                    beginAtZero: true
	           		},
                    stacked: true
	            }]
	        },
			zoom: {
				enabled: true,
				mode: 'x'
			}
		};

	    var barData = {
	        labels: labels,
	         datasets: datasets
	    };

	    // render chart
	    var ctx = document.getElementById("barChart").getContext("2d");
	    var myBarChart = new Chart(ctx, {
	        type: 'bar',
	        data: barData,
	        options: barOptions
	    });

	    // END BAR CHART

	    // PIE CHART

	    var pieOptions = {
	    	//Boolean - Whether we should show a stroke on each segment
	        segmentShowStroke: true,
	        //String - The colour of each segment stroke
	        segmentStrokeColor: "#fff",
	        //Number - The width of each segment stroke
	        segmentStrokeWidth: 2,
	        //Number - Amount of animation steps
	        animationSteps: 100,
	        //String - types of animation
	        animationEasing: "easeOutBounce",
	        //Boolean - Whether we animate the rotation of the Doughnut
	        animateRotate: true,
	        //Boolean - Whether we animate scaling the Doughnut from the centre
	        animateScale: false,
	        //Boolean - Re-draw chart on page resize
	        responsive: true,
	        //String - A legend template
	        tooltips: {
	        	bodyFontSize: 20,
	            callbacks: {
	                label: function(tooltipItem, data) {
	                    var allData = data.datasets[tooltipItem.datasetIndex].data;
	                    var tooltipLabel = data.labels[tooltipItem.index];
	                    var tooltipData = allData[tooltipItem.index];
	                    var total = 0;
	                    for (var i in allData) {
	                        total += parseInt(allData[i]);
	                    }
	                    var tooltipPercentage = ((tooltipData / total) * 100).toFixed(1);
	                    return tooltipLabel + ': ' + tooltipData + ' (' + tooltipPercentage + '%)';
	                }
	            }
	        }
	    };

	 	// Chart Data Setting
		var taggingCnt = new Array();
		var labels = new Array();
		var datasets = new Array();

		<c:forEach var="item1" items="${pieList}">
			var taggingCnts = new Array();
			<c:forEach var="item2" items="${item1.imageTaggingDataDicNmList}">
			labels.push("${item2}");
			</c:forEach>
			<c:forEach var="item3" items="${item1.taggingCntList}">
			taggingCnt.push("${item3}");
			</c:forEach>
		</c:forEach>

		var backgroundColors = new Array();
		for(var j = 0; j < labels.length; j++) {
			backgroundColors.push(dynamicColors());
		}

	    var pieData = {
   	    	labels: labels,
   	    	datasets: [{
                data: taggingCnt,
                backgroundColor: backgroundColors,
                label: 'Image Crawling Info'
            }]
   	    };

	    // render chart
	    var ctx = document.getElementById("pieChart").getContext("2d");
	    var myNewChart = new Chart(ctx,{
	        type: 'pie',
	        data: pieData,
	        options: pieOptions
	    });

	    // END PIE CHART

	    $(".bootstrap-tagsinput > input").prop("size","180");
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

	var dynamicColors = function() {
	    var r = Math.floor(Math.random() * 130)+100;
	    var g = Math.floor(Math.random() * 130)+100;
	    var b = Math.floor(Math.random() * 130)+100;
	    return "rgb(" + r + "," + g + "," + b + ")";
	}

	// 태깅사전 select box depth별 조회
	function taggingDicselectBoxSet(idSq, depth) {

		var sendData = {upperImageTaggingDataDicId : idSq, imageTaggingDataDicLevel : depth};

		$.ajax({
			type: 'POST',
			url: '${REQUEST_CONTEXT_PATH}/statistics/graphByLabel/taggingDataSearch',
			data: JSON.stringify(sendData),
			contentType : "application/json",
			dataType : 'json',
			success: function(data) {
				if (data.resultCd == 'success') { // 성공
				 	var comboId = "firstImageTaggingData";
				 	var dataList = data.taggingDicDataList;

					if (depth == '1') {
						comboId = "firstImageTaggingData";
					} else if (depth == '2') {
						comboId = "secondImageTaggingData";
					} else if (depth == '3') {
						comboId = "thirdImageTaggingData";
					}

					$('#'+comboId).empty();
					$('<option value=""><spring:message code="selectbox.all" /></option>').appendTo('#'+comboId);
					for(var i=0; i<dataList.length; i++){
						$('<option value="'+ dataList[i].imageTaggingDataDicIdSq +'">' + dataList[i].imageTaggingDataDicNm + '</option>').appendTo('#'+comboId);
					}
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
//-->
</script>

<!-- button area -->
<div class="row button-wrap">
	<div class="col-xs-12 text-align-right">
		<custom:auth type="CREATE">
			<div class="btn-group">
				<a id="download-excel-file" class="btn btn-sm btn-info" role="button">
					<i class="fa fa-download"></i> <spring:message code="common.excelDownload" /></a>
			</div>
		</custom:auth>
	</div>
</div>
<!-- // button area -->

<p></p>

<!-- search -->
<div class="well search-wrap">
	<form:form method="get" action="list" modelAttribute="imageFileEntity" cssClass="form-inline search-form" role="form" autocomplete="off">
		<fieldset>
			<div class="row">
				<div class="col-sm-10">
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group form-inline">
								<label class="col-md-2 control-label text-center" for="startDt">기간</label>
								<div class="col-md-3 input-group" style="padding-left: 13px;">
									<form:input path="startDt" cssClass="form-control datepicker" data-dateformat="yy-mm-dd" readonly="true" />
									<span class="input-group-addon"><i class="icon-append fa fa-calendar"></i></span>
								</div>
								<span class="display-inline">&nbsp;~&nbsp;</span>
								<div class="col-md-3 input-group" style="padding-right: 13px;">
									<form:input path="endDt" cssClass="form-control datepicker" data-dateformat="yy-mm-dd" readonly="true" />
									<span class="input-group-addon"><i class="icon-append fa fa-calendar"></i></span>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-10">
							<div class="form-group form-inline">
								<label class="text-center" for="firstImageTaggingData"><spring:message code="admin.statistics.imageTaggingDataSelect" /></label>
								<select id="firstImageTaggingData" name="firstImageTaggingData" class="form-control">
									<option value=""><spring:message code="selectbox.all" /></option>
								</select>
								<select id="secondImageTaggingData" name="secondImageTaggingData" class="form-control">
									<option value=""><spring:message code="selectbox.all" /></option>
								</select>
								<select id="thirdImageTaggingData" name="thirdImageTaggingData" class="form-control">
									<option value=""><spring:message code="selectbox.all" /></option>
								</select>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<input id="imageTaggingData" name="imageTaggingDataDicIdSqList" class="form-control tagsinput" value="" data-role="tagsinput">
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

<ul id="myTab1" class="nav nav-tabs bordered">
	<li class="active">
		<a href="#s1" data-toggle="tab">Bar Chart</a>
	</li>
	<li>
		<a href="#s2" data-toggle="tab">Pie Chart</a>
	</li>
</ul>

<div id="myTabContent1" class="tab-content padding-10">
	<div class="tab-pane fade in active" id="s1">
		<div class="jarviswidget" id="wid-id-1" data-widget-colorbutton="false" data-widget-fullscreenbutton="false" data-widget-editbutton="false" data-widget-sortable="false">
			<div class="widget-body">
				<canvas id="barChart" height="120"></canvas>
				<!-- list -->
				<div class="table-responsive smart-form">
					<table class="table table-striped table-bordered table-hover">
						<colgroup>
							<col width="50%" />
						</colgroup>

						<thead>
							<c:if test="${not empty barList[0].imageTaggingDataDicNmList}">
							<tr>
								<th><spring:message code="admin.statistics.imageFileRegistDt" /></th>
								<c:forEach var="tagNm" items="${barList[0].imageTaggingDataDicNmList}" varStatus="status">
								<th>${tagNm}</th>
								</c:forEach>
							</tr>
							</c:if>
						</thead>

						<tbody>
							<c:if test="${empty barList[0].imageTaggingDataDicNmList}">
								<tr><td><spring:message code="common.dataIsEmpty" /></td></tr>
							</c:if>
							<c:if test="${not empty barList[0].imageTaggingDataDicNmList}">
							<c:forEach var="item" items="${barList}">
								<tr>
									<spring:eval var="imageFileRegistDt" expression="T(com.lge.crawling.admin.common.util.DateUtil).format(item.imageFileRegistDt,'yyyy-MM-dd')" />
									<td class="le ellipsis" title="${imageFileRegistDt}">${imageFileRegistDt}</td>
									<c:forEach var="tagNm" items="${item.imageTaggingDataDicNmList}" varStatus="status">
									<td title="${item.taggingCntList[status.index]}">${item.taggingCntList[status.index]}</td>
									</c:forEach>
								</tr>
							</c:forEach>
							</c:if>
						</tbody>
					</table>
				</div>
				<jsp:include page="/include/paging.jsp" flush="true" />
			</div>
		</div>
	</div>
	<div class="tab-pane fade form-group form-inline" id="s2">
		<div class="jarviswidget col-sm-6" id="wid-id-2" data-widget-colorbutton="false" data-widget-fullscreenbutton="false" data-widget-editbutton="false" data-widget-sortable="false">
			<div class="widget-body">
				<canvas id="pieChart" height="220"></canvas>
			</div>
		</div>

		<div class="jarviswidget col-sm-6" id="wid-id-3" data-widget-colorbutton="false" data-widget-fullscreenbutton="false" data-widget-editbutton="false" data-widget-sortable="false">
			<!-- list -->
			<div class="table-responsive">
				<table class="table table-striped table-bordered table-hover">
					<colgroup>
						<col width="33%" />
						<col width="33%" />
						<col width="33%" />
					</colgroup>

					<thead>
						<tr class="danger">
							<th><spring:message code="admin.statistics.siteName" /></th>
							<th><spring:message code="admin.statistics.count" /></th>
							<th>%</th>
						</tr>
					</thead>

					<tbody>
						<c:if test="${empty pieList}">
							<tr><td colspan="5">데이터가 존재하지 않습니다.</td></tr>
						</c:if>
						<c:forEach var="item" items="${pieList}">
							<c:forEach var="tagNm" items="${item.imageTaggingDataDicNmList}" varStatus="status">
							<tr>
								<td title="${tagNm}">${tagNm}</td>
								<td title="${item.taggingCntList[status.index]}">${item.taggingCntList[status.index]}</td>
								<td title="${item.taggingCntPerList[status.index]}%">${item.taggingCntPerList[status.index]}%</td>
							</tr>
							</c:forEach>
							<tr class="info">
								<td title="합계">합계</td>
								<c:set var="totalAvg" value="${item.googleAvg+item.flickrAvg+item.tumblrAvg+item.twitterAvg}" />
								<td title="${item.totalCnt}">${item.totalCnt}</td>
								<td title="${item.totalPercentage}%">${item.totalPercentage}%</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
