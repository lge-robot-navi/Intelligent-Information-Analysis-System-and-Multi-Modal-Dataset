<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom" uri="/tags/custom-taglib" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<style>
<!--
/* .chartWrapper {
    position: relative;
}

.chartWrapper > canvas {
    position: absolute;
    left: 0;
    top: 0;
    pointer-events:none;
}

.chartAreaWrapper {
    width: 1500px;
    overflow-x: scroll;
} */
-->
</style>

<script type="text/javascript">
<!--
	//최근 접속 페이지 값 설정
	fnSetLatestPage();

	$(function() {

		$('#download-excel-file').click(function(e) {
			e.preventDefault();
			return downloadFile('download-excel-file?'+$('form:first').serialize());
		});

		// 검색
		$('#btnSearch').click(function() { search(); });

		// 사용여부 이벤트
		$('select[name=useYn]').change(function() { search(); });

	 	// Chart Data Setting
		var googleCnt = new Array();
		var flickrCnt = new Array();
		var tumblrCnt =  new Array();
		var twitterCnt =  new Array();
		var labels = new Array();

		<c:forEach var="item" items="${barAllList}">
			var registDt = fnStringToDate("${item.imageFileRegistDt}");
			labels.push(registDt);
			googleCnt.push(parseInt("${item.googleCnt}"));
			flickrCnt.push(parseInt("${item.flickrCnt}"));
			tumblrCnt.push(parseInt("${item.tumblrCnt}"));
			twitterCnt.push(parseInt("${item.twitterCnt}"));
		</c:forEach>

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
	         datasets: [
		        {
		            label: "Google",
		            backgroundColor: "rgba(255, 0, 30, 0.5)",
		            data: googleCnt
		        },
		        {
		            label: "Flickr",
		            backgroundColor: "rgba(255, 0, 98, 0.5)",
		            data: flickrCnt
		        },
		        {
		            label: "Tumblr",
		            backgroundColor: "rgba(62, 58, 126, 0.5)",
		            data: tumblrCnt
		        },
		        {
		            label: "Twitter",
		            backgroundColor: "rgba(0, 174, 255, 0.5)",
		            data: twitterCnt
		        }
		    ]
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
	                        total += allData[i];
	                    }
	                    var tooltipPercentage = Math.round((tooltipData / total) * 100);
	                    return tooltipLabel + ': ' + tooltipData + ' (' + tooltipPercentage + '%)';
	                }
	            }
	        }
	    };

	 	// Chart Data Setting
		var googleTotalCnt = 0;
		var flickrTotalCnt = 0;
		var tumblrTotalCnt =  0;
		var twitterTotalCnt =  0;
		var labels = new Array();

		<c:forEach var="item" items="${pieList}">
			googleTotalCnt += parseInt("${item.googleCnt}");
			flickrTotalCnt += parseInt("${item.flickrCnt}");
			tumblrTotalCnt += parseInt("${item.tumblrCnt}");
			twitterTotalCnt += parseInt("${item.twitterCnt}");
		</c:forEach>

	    var pieData = {
   	    	labels: [
   	             "Google",
   	             "Plickr",
   	             "Tumblr",
   	             "Twitter"
   	         ],
   	         datasets: [{
                 data: [googleTotalCnt, flickrTotalCnt, tumblrTotalCnt, twitterTotalCnt],
                 backgroundColor: [
                     "rgba(255, 0, 30, 0.5)",
                     "rgba(255, 0, 98, 0.5)",
                     "rgba(62, 58, 126, 0.5)",
                     "rgba(0, 174, 255, 0.5)"
                 ],
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

	function addData(numData, chart){
		for (var i = 0; i < numData; i++){
    		chart.data.datasets[0].data.push(Math.random() * 100);
    		chart.data.labels.push("Label" + i);
    		var newwidth = $('.chartAreaWrapper2').width() +60;
    		$('.chartAreaWrapper2').width(newwidth);
	    }
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

					<%-- <div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label class="text-center" for="adminStatusCd"><spring:message code="admin.statistics.imageFileDownloadPathCd" /></label>
								<tags:select id="adminStatusCd" name="adminStatusCd" group="TA005" cssClass="form-control" value="${adminEntity.adminStatusCd}">
									<option value=""><spring:message code="selectbox.all" /></option>
								</tags:select>
							</div>
						</div>
					</div> --%>
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
		<div class="jarviswidget chartWrapper" id="wid-id-1" data-widget-colorbutton="false" data-widget-fullscreenbutton="false" data-widget-editbutton="false" data-widget-sortable="false">
			<div class="widget-body chartAreaWrapper">
				<div class="chartAreaWrapper2">
					<canvas id="barChart" height="500" width="1200"></canvas>
				</div>
				<%-- <div class="chartWrapper">
				  <div class="chartAreaWrapper">
				  <div class="chartAreaWrapper2">
				      <canvas id="barChart" height="350" width="1200"></canvas>
				  </div>
				  </div>
				  <canvas id="axis-FuelSpend" height="350" width="0"></canvas>
				</div> --%>
			</div>
			<div>
				<!-- list -->
				<div class="table-responsive smart-form">
					<table class="table table-striped table-bordered table-hover">
						<colgroup>
							<col width="50%" />
							<col width="10%" />
							<col width="10%" />
							<col width="10%" />
							<col width="10%" />
							<col width="10%" />
						</colgroup>

						<thead>
							<tr>
								<th><spring:message code="admin.statistics.imageFileRegistDt" /></th>
								<th><spring:message code="admin.statistics.googleCnt" /></th>
								<th><spring:message code="admin.statistics.flickrCnt" /></th>
								<th><spring:message code="admin.statistics.tumblrCnt" /></th>
								<th><spring:message code="admin.statistics.twitterCnt" /></th>
								<th><spring:message code="admin.statistics.totalCnt" /></th>
							</tr>
						</thead>

						<tbody>
							<c:if test="${empty barList}">
								<tr><td colspan="6"><spring:message code="common.dataIsEmpty" /></td></tr>
							</c:if>
							<c:forEach var="item" items="${barList}">
								<tr>
									<spring:eval var="imageFileRegistDt" expression="T(com.lge.crawling.admin.common.util.DateUtil).format(item.imageFileRegistDt,'yyyy-MM-dd')" />
									<td class="le ellipsis" title="${imageFileRegistDt}">${imageFileRegistDt}</td>
									<td class="le ellipsis" title="${item.googleCnt}">${item.googleCnt}</td>
									<td class="le ellipsis" title="${item.flickrCnt}">${item.flickrCnt}</td>
									<td class="le ellipsis" title="${item.tumblrCnt}">${item.tumblrCnt}</td>
									<td class="le ellipsis" title="${item.twitterCnt}">${item.twitterCnt}</td>
									<td class="le ellipsis" title="${item.totalCnt}">${item.totalCnt}</td>
								</tr>
							</c:forEach>
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
							<tr>
								<td title="Google">Google</td>
								<td title="${item.googleCnt}">${item.googleCnt}</td>
								<td title="${item.googleAvg}%">${item.googleAvg}%</td>
							</tr>
							<tr>
								<td title="Flickr">Flickr</td>
								<td title="${item.flickrCnt}">${item.flickrCnt}</td>
								<td title="${item.flickrAvg}%">${item.flickrAvg}%</td>
							</tr>
							<tr>
								<td title="Tumblr">Tumblr</td>
								<td title="${item.tumblrCnt}">${item.tumblrCnt}</td>
								<td title="${item.tumblrAvg}%">${item.tumblrAvg}%</td>
							</tr>
							<tr>
								<td title="Twitter">Twitter</td>
								<td title="${item.twitterCnt}">${item.twitterCnt}</td>
								<td title="${item.twitterAvg}%">${item.twitterAvg}%</td>
							</tr>
							<tr class="info">
								<td title="합계">합계</td>
								<c:set var="totalAvg" value="${item.googleAvg+item.flickrAvg+item.tumblrAvg+item.twitterAvg}" />
								<td title="${item.totalCnt}">${item.totalCnt}</td>
								<td title="${totalAvg}%">${totalAvg}%</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
