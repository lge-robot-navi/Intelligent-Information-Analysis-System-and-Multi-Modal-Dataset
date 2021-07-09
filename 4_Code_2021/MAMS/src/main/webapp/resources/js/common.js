//Date Format
/* 객체 맴버 추가 변경 시작 */
Number.prototype.to2 = function() { return (this > 9 ? "" : "0") + this };
Date.MONTHS = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
//Date.DAYS   = ["Sun", "Mon", "Tue", "Wed", "Tur", "Fri", "Sat"];
Date.DAYS   = ["일", "월", "화", "수", "목", "금", "토"];
Date.prototype.getDateString = function(dateFormat) {
	var result = "";

	dateFormat = dateFormat == 8 && "YYYY-MM-DD"
				|| dateFormat == 6 && "hh:mm:ss"
				|| dateFormat == 14 && "YYYY-MM-DD hh:mm:ss"
				|| dateFormat
				"YYYY-MM-DD hh:mm:ss";

	for (var i = 0; i < dateFormat.length; i++) {
		result += dateFormat.indexOf("YYYY", i) == i ? (i+=3, this.getFullYear()                     ) :
	    		  dateFormat.indexOf("YY",   i) == i ? (i+=1, String(this.getFullYear()).substring(2)) :
	              dateFormat.indexOf("MMM",  i) == i ? (i+=2, Date.MONTHS[this.getMonth()]           ) :
	              dateFormat.indexOf("MM",   i) == i ? (i+=1, (this.getMonth()+1).to2()              ) :
	              dateFormat.indexOf("M",    i) == i ? (      this.getMonth()+1                      ) :
	              dateFormat.indexOf("DDD",  i) == i ? (i+=2, Date.DAYS[this.getDay()]               ) :
	              dateFormat.indexOf("DD",   i) == i ? (i+=1, this.getDate().to2()                   ) :
	              dateFormat.indexOf("D"   , i) == i ? (      this.getDate()                         ) :
	              dateFormat.indexOf("hh",   i) == i ? (i+=1, this.getHours().to2()                  ) :
	              dateFormat.indexOf("h",    i) == i ? (      this.getHours()                        ) :
	              dateFormat.indexOf("mm",   i) == i ? (i+=1, this.getMinutes().to2()                ) :
	              dateFormat.indexOf("m",    i) == i ? (      this.getMinutes()                      ) :
	              dateFormat.indexOf("ss",   i) == i ? (i+=1, this.getSeconds().to2()                ) :
	              dateFormat.indexOf("s",    i) == i ? (      this.getSeconds()                      ) :
	                                                   (dateFormat.charAt(i)                         ) ;
	}
	return result;
};
/* 객체 멤버 추가 변경 끝 */

String.prototype.trim = function() {
    return this.replace(/^\s+|\s+$/g,"");
};

String.prototype.ltrim = function() {
    return this.replace(/^\s+/,"");
};

String.prototype.rtrim = function() {
    return this.replace(/\s+$/,"");
};

/*
 * String 을 Date 형식으로 변환
 */
function fnStringToDate(vDate) {

	if (vDate == "" || vDate == null || vDate == undefined)
		return false;

	vDate = vDate.replace(/\D/gi, '').toString();

	var yyyy = vDate.substr(0, 4),
		mm = vDate.substr(4, 2) - 1,
		dd = vDate.substr(6, 2),
		hh = 0,
		mi = 0,
		ss = 0;

	var vLen = vDate.length;
	if (vLen >= 9)
		hh = vDate.substr(8, 2);

	if (vLen >= 11)
		mi = vDate.substr(10, 2);

	if (vLen >= 13)
		ss = vDate.substr(12, 2);

	return new Date(yyyy, mm, dd, hh, mi, ss);
}

/*
 * 입력받은 날짜를 요청한 형식의 날짜로 변환
 */
function fnStringToDateFmt(vDate, fmt) {

	if (!vDate) {
		return "";
	}

	if (!fmt) {
		fmt = 'YYYY-MM-DD';
	}

	return fnStringToDate(vDate).getDateString(fmt);
}

/*
 * 입력받은 날짜를 요청한 형식의 날짜로 변환
 */
function fnStringToDateFmt2(vDate, fmt) {

	if (!vDate) {
		return "";
	}

	if (!fmt) {
		fmt = 'YYYY-MM-DD hh:mm:ss';
	}

	return fnStringToDate(vDate).getDateString(fmt);
}

/**
 * 해당월의 시작일자를 가져온다.
 */
function getFirstDay(vDate) {

	// 빈 문자열 처리
	if (isEmpty(vDate)) {
		vDate = new Date();
	}

	// 문자열의 경우 처리
	if ( typeof(vDate) == 'string' ) {
		vDate = fnStringToDate(vDate);
	}

	vDate.setDate(1);

	return vDate.getDateString("YYYY-MM-DD");
}

/**
 * 해당월의 마지막일자를 가져온다.
 */
function getLastDay(vDate) {

	// 빈 문자열 처리
	if (isEmpty(vDate)) {
		vDate = new Date();
	}

	// 문자열의 경우 처리
	if ( typeof(vDate) == 'string' ) {
		vDate = fnStringToDate(vDate);
	}

	vDate.setMonth(vDate.getMonth() + 1);
	vDate.setDate(0);

	return vDate.getDateString("YYYY-MM-DD");
}

function isEmpty(obj) {

	if (obj == "" || obj == null || obj == undefined) {
		return true;
	}

	return false;
}

function isNotEmpty(obj) {
	return !isEmpty(obj);
}

//load css
function loadCss(url) {
	var link = document.createElement("link");
    link.type = "text/css";
    link.rel = "stylesheet";
    link.href = url;
    document.getElementsByTagName("head")[0].appendChild(link);
}

//set pivot
function pivot(recordSet) {
	var pivoted = {}
	if (recordSet == null) return pivoted
	recordSet.forEach(function (record) {
		Object.keys(record).forEach(function (key) {
			if (!pivoted[key]) pivoted[key] = []
			pivoted[key].push(record[key])
		})
	})
	return pivoted
}

function setLocalStorage(key, data) {
	localStorage.setItem(key, JSON.stringify(data));
}

function getLocalStorage(key) {
	return JSON.parse(localStorage.getItem(key));
}

/**
 * @desc object 의 내용을 출력한다.
 * @param data
 * @param depth
 */
function printObject(data, depth) {

	var str = '';
	for ( var key in data ) {

		value = data[key];

		for (var i = 0; i < depth; i++) {
			str += ' ';
		}

		if ( typeof value == 'object' && value instanceof Array == false ) {
			console.log(str + 'type: [' + typeof value + '], key:[' + key + ']---->');
			this.printObject(value, depth + 1);
			continue;
		}

		console.log(str + 'type:[' + typeof value + '], key:[' + key + '], value:[' + value + ']');
	}
}


function fnAjaxCommonHandler(jqXHR, response, callBackHandler){

	//--

	if(jqXHR.status == 401){
		response = JSON.parse(jqXHR.responseText);
		location.href = response.redirectUrl;
	}


	//-- callBack
	if(callBackHandler)
		callBackHandler(response);
}

/*
 * 가장 최근에 접근한 페이지로 이동
 * 사용방법:
 *
 * 1. 접속 이력을 남길 페이지의 스크립트 선언 부분에
 *    fnSetLatestMainPage();
 *    이용하여 가장 마지막에 접근한 주소 남김.
 *
 * 2. 검색 form은 Get방식을 이용하여 처리.
 *    <form method="get"></form>
 *
 * 3. 상세정보 등에서
 *    이전 목록으로 돌아갈 필요가 있을 경우 아래와 같이 사용
 *
 *    ex) <button type="button" onclick="fnLatestMainPage();">목록</button>
 *        버튼 방식에 제약은 없음(a, span, input 등 사용에 제약을 두지 않음)
 */

// set page
function fnSetLatestPage() {
	//top.latestMainPage = location.href;
	//$.cookie('latestMainPage', window.location.href);
	fnCookieLatesPage('latestMainPage', window.location.href);
}

// page call
function fnLatestPage(url) {
//	var latestMainPage = $.cookie('latestMainPage');
//	if (latestMainPage != null && latestMainPage != '') {
//		window.location.href = latestMainPage;
//	}
	fnCookieLatestPage('latestMainPage', url);
}

// set page (cookie)
function fnCookieLatesPage(key, value) {
	$.cookie(key, value);
}

// page call (Cookie)
function fnCookieLatestPage(key, url) {
	var page;
	try {
		page = $.cookie(key);
	} catch(e) {
	}
	if (isNotEmpty(page)) {
		window.location.href = page;
	} else {
		window.location.href = url;
	}
}

// Popup - default option
function fnOpen(url, name, width, height) {

	var x = (screen.availWidth - width) / 2;
	var y = (screen.availHeight - height) / 2;

	var option = 'width=' + width + ',height=' + height
					+ ',left=' + x + ',top=' + y + ',resizable=no,scrollbars=no';

	return window.open(url, name, option);
}

// Popup - option
function fnOpen(url, name, width, height, option) {

	var x = (screen.availWidth - width) / 2;
	var y = (screen.availHeight - height) / 2;

	option += ',width=' + width + ',height=' + height
					+ ',left=' + x + ',top=' + y;

	return window.open(url, name, option);
}

// URL 접속 문자열 설정
function concatURL(url, param) {

	var concat = '&';

	if ( isNotEmpty( url ) ) {
		if ( url.indexOf('?') == -1 ) {
			concat = '?';
		}
	}

	if ( isNotEmpty( param ) ) {
		url += concat + param;
	}

	return url;
}

//문자열 채우기
function pad(src, digits, ch) {
	var fill = "";
	var str = "";

	if (src != null) {
		str = src.toString();
		if (str.length < digits) {
		    var i = 0;
			for (i = 0; i < (digits - str.length); i++)
				fill += ch;
		}
	}

	return fill;
}

// 좌측 문자열 채우기
function lpad(src, digits, ch) {
	return pad(src, digits, ch) + src;
}

// 우측 문자열 채우기
function rpad(src, digits, ch) {
    return src + pad(src, digits, ch);
}

//날짜 콤보박스
//년도 설정 (-10년)
function fnSetYear(obj) {

	obj.empty();  // 초기화

	var year = new Date().getDateString("YYYY");
	for (var yy = Number(year) - 5; yy <= (Number(year) + 5); yy++)
		obj.append( $('<option>', { text: yy + " 년", val: yy }) );
}

//월 추가
function fnAddMonth(obj) {

	obj.empty(); // 초기화

	for (var i = 1; i <= 12; i++) {
		var mm = lpad(i, 2, "0");
		obj.append( $('<option>', { text: mm + " 월", val: mm }) );
	}
}

// 일자 추가
function fnAddDate(obj) {
	// Combobox 초기화
	obj.options.length = 0;

	for (var d = 1; d <= 31; d++) {
		obj.add(new Option(lpad(d, 2, "0") + "일", d));
	}
}

// 시간 추가
function fnAddHour(obj) {
	// Combobox 초기화
	obj.options.length = 0;

	for (var h = 0; h <= 23; h++) {
		obj.add(new Option(lpad(h, 2, "0") + "시", h));
	}
}

// 분 추가
function fnAddMinute(obj) {
	// Combobox 초기화
	obj.options.length = 0;

	for (var m = 0; m <= 59; m++) {
		obj.add(new Option(lpad(m, 2, "0") + "분", m));
	}
}

//Validate Time
function isValidateHH24MM(hh24mm) {
	var regex = /^[0-2][0-9][0-5][0-9]$/i;

	if (regex.test(hh24mm)) {
		if (parseInt(hh24mm) > 2359) return false;
		return true;
	} else {
		return false;
	}
}

// 시작일자 종료일자 비교함수
// 종료일자가 같거나 이후면 true
// 종료일자가 시작일자보다 앞서 있으면 false
function compareDate(str, end) {

	var date1 = str.replace(/\D/gi, '');
	if (date1.length != 8) return false;

	var date2 = end.replace(/\D/gi, '');
	if (date2.length != 8) return false;

	var date_str = new Date(date1.substr(0, 4),
			date1.substr(4, 2),
			date1.substr(6, 2));

	var date_end = new Date(date2.substr(0, 4),
			date2.substr(4, 2),
			date2.substr(6, 2));

	return ((date_end - date_str) < 0);
}

/**
 * @desc 날짜 비교함수
 *        ex) betweenDate( today, startDay, endDay )
 * @param cDate (비교시간)
 * @param fDAte (최초시간)
 * @param lDate (최종시간)
 * @returns {Boolean}
 */
function betweenDate( fDate, lDate, cDate ) {

	if ( isEmpty( cDate ) ) {
		cDate = new Date();
	}

	if ( !(cDate instanceof Date) ) {
		cDate = fnStringToDate( cDate );
	}

	if ( !(fDate instanceof Date) ) {
		fDate = fnStringToDate( fDate );
	}

	if ( !(lDate instanceof Date) ) {
		lDate = fnStringToDate( lDate );
	}

    if( (cDate <= lDate && cDate >= fDate) ) {
    	return true;
    }

    return false;
}

// IP ADDRESS VALIDATE
function isValidIPAddress(ipaddr) {
   var re = /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/;
   if (re.test(ipaddr)) {
      var parts = ipaddr.split(".");
      if (parseInt(parseFloat(parts[0])) == 0) { return false; }
      for (var i=0; i<parts.length; i++) {
         if (parseInt(parseFloat(parts[i])) > 255) { return false; }
      }
      return true;
   } else {
      return false;
   }
}

// cellphone validate
function isValidNoPhone(no_phone) {
	var re = /^01\d-\d{3,4}-\d{4}$/;
	return re.test(no_phone);
}

// getByteSize
function getByteSize(str) {
	var i = 0;
	var intByte = 0;
	var strEncoding = "";

	for (i = 0; i < str.length; i++) {
		strEncode = escape(str.slice(i, (i+1)));

		// 1: 영어, 숫자
		// 3: 키보드상의 특수문자
		// 6: 한글
		if ((strEncode.length == 1) || (strEncode.length == 3)) {
			intByte += 1;
		} else {
			intByte += 2;
		}
	}

	return intByte;
}

// getUtf8ByteSize
function getUtf8ByteSizeBak(str) {
	var i = 0;
	var intByte = 0;
	var strEncoding = "";

	for (i = 0; i < str.length; i++) {
		strEncode = escape(str.slice(i, (i+1)));

		// 1: 영어, 숫자
		// 3: 키보드상의 특수문자
		// 6: 한글
		if ((strEncode.length == 1) || (strEncode.length == 3)) {
			intByte += 1;
		} else {
			intByte += 3;
		}
	}

	return intByte;
}

function getUtf8ByteSize(string) {
    var utf8length = 0;

    for (var n = 0; n < string.length; n++) {
        var c = string.charCodeAt(n);
        if (c < 128) {
        	utf8length++;
        	if ( c == 10 ) {
        		utf8length++;
        	}
        } else if((c > 127) && (c < 2048)) {
        	utf8length = utf8length+2;
        } else {
        	utf8length = utf8length+3;
        }
    }

    return utf8length;
}

/**
 * 문자열에 한글이 있는지 판단
 */
function isKorean(str) {
	regexp = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
	return regexp.test(str);
}

/* 숫자 3자리 마다 콤마(,) 추가 */
function commify(n) {
	var reg = /(^[+-]?\d+)(\d{3})/;   // 정규식
	n += '';                          // 숫자를 문자열로 변환

	while (reg.test(n))
		n = n.replace(reg, '$1' + ',' + '$2');

	return n;
}

/* Context Path */
function getContextPath(){
    //var offset=location.href.indexOf(location.host)+location.host.length;
    //var ctxPath=location.href.substring(offset,location.href.indexOf('/',offset+1));
    //return ctxPath;
	return '';
}

/*
 * 우편번호 검색
 * 사용방법:
 * 1. findPost()를 이용하여 우편번호 검색
 * 2. 우편번호를 호출한 jsp에
 *    fnSetPost(postObject); 를 필수 구현.
 *
 *    ex) function fnSetPost(obj) {
 *    		$('noPost').val(obj.noPost);
 *        }
 */
function findPost() {
	fnOpen(getContextPath() + '/system/post/search', 'post', 640, 600);
}

/**
 * 에너지팩 검색 팝업
 * 1. findEpack() 검색
 * 2. 호출한 화면에서 fnSetEpack(data); 필수 구현
 */
function findEpack( param ) {

	var url = getContextPath() + '/hcportal/epack/epack-info/epack-search';

	if ( isNotEmpty( param ) ) {
		url += '?' + param;
	}

	return fnOpen(url, 'epackSearch', 1000, 650);
}

/**
 * 에너지팩 멀티 검색 팝업
 * 1. findMultiEpack() 검색
 * 2. 호출한 화면에서 fnSetEpack(data); 필수 구현
 */
function findMultiEpack( param ) {

	var url = getContextPath() + '/hcportal/epack/epack-info/epack-multi-search';

	if ( isNotEmpty( param ) ) {
		url += '?' + param;
	}

	return fnOpen(url, 'epackMultiSearch', 1000, 650);
}

/**
 * 유투브 동영상 검색 팝업
 * 1. findYoutubePopup
 */
function findYoutube() {

	var url = getContextPath() + '/hcportal/epack/epack-info/youtube-search';

	return fnOpen(url, 'youtubeSearch', 800, 650);
}

/**
 * 사용자정보조회 팝업
 * 1. findUserInfoPopup
 */
function findUserInfoPopup(userSq) {

	var url = '/hcportal/user/info/view-popup';

	if (isNotEmpty(userSq)) {
		url += '?userSq=' + userSq;
	} else {
		return null;
	}

	return fnOpen(url, 'userInfoViewPopup', 800, 520);
}

/*
 * 시작, 종료일 이벤트 설정 Datepicker
 * @param str : 시작일자 object
 * @param end : 종료일자 object
 * setDatepickerStartEnd(str, end)
 */
function setDatepickerStartEnd(str, end) {

	var strVal = str.val();
	if (strVal && strVal != '') {
		strVal = fnStringToDate(strVal).getDateString(8);
		str.val(strVal);
	}

	var endVal = end.val();
	if (endVal && endVal != '') {
		endVal = fnStringToDate(endVal).getDateString(8);
		end.val(endVal);
	}

	var currentYear = new Date().getFullYear();
	// 시작일자 설정
	str.datepicker({
		changeYear: true,
		dateFormat: 'yy-mm-dd',
		yearRange: (currentYear - 10 ) + ':' + (currentYear + 20),
        prevText: '<i class="fa fa-chevron-left"></i>',
        nextText: '<i class="fa fa-chevron-right"></i>',
		onSelect: function(dateText, inst) {
			end.datepicker('option', 'minDate', fnStringToDate(dateText));
		}
	});

	// 종료일자 설정
	end.datepicker({
		changeYear: true,
		dateFormat: 'yy-mm-dd',
		yearRange: (currentYear - 10 ) + ':' + (currentYear + 20),
        prevText: '<i class="fa fa-chevron-left"></i>',
        nextText: '<i class="fa fa-chevron-right"></i>',
		onSelect: function(dateText, inst) {
			str.datepicker('option', 'maxDate', fnStringToDate(dateText));
		}
	});

	if (strVal != '' && endVal != '') {
		str.datepicker('option', 'maxDate', endVal);
		end.datepicker('option', 'minDate', strVal);
	}
}

function setDatepickerStartEndChgM(str, end) {

	var strVal = str.val();
	if (strVal && strVal != '') {
		strVal = fnStringToDate(strVal).getDateString(8);
		str.val(strVal);
	}

	var endVal = end.val();
	if (endVal && endVal != '') {
		endVal = fnStringToDate(endVal).getDateString(8);
		end.val(endVal);
	}

	var currentYear = new Date().getFullYear();
	// 시작일자 설정
	str.datepicker({
		changeMonth: true,
		changeYear: true,
		dateFormat: 'yy-mm-dd',
		yearRange: (currentYear - 10 ) + ':' + (currentYear + 20),
		prevText: '<i class="fa fa-chevron-left"></i>',
		nextText: '<i class="fa fa-chevron-right"></i>',
		onSelect: function(dateText, inst) {
			end.datepicker('option', 'minDate', fnStringToDate(dateText));
		}
	});

	// 종료일자 설정
	end.datepicker({
		changeMonth: true,
		changeYear: true,
		dateFormat: 'yy-mm-dd',
		yearRange: (currentYear - 10 ) + ':' + (currentYear + 20),
		prevText: '<i class="fa fa-chevron-left"></i>',
		nextText: '<i class="fa fa-chevron-right"></i>',
		onSelect: function(dateText, inst) {
			str.datepicker('option', 'maxDate', fnStringToDate(dateText));
		}
	});

	if (strVal != '' && endVal != '') {
		str.datepicker('option', 'maxDate', endVal);
		end.datepicker('option', 'minDate', strVal);
	}
}

/*
 * 시작, 종료일 이벤트 설정 Timepicker
 * @param str : 시작일자 object
 * @param end : 종료일자 object
 * setDatepickerStartEnd(str, end)
 */
function setDatetimepickerRange(startDateTextBox, endDateTextBox) {
	startDateTextBox.datetimepicker({
		timeFormat: 'HH:mm:ss',
		onClose: function(dateText, inst) {
			if (endDateTextBox.val() != '') {
				var testStartDate = startDateTextBox.datetimepicker('getDate');
				var testEndDate = endDateTextBox.datetimepicker('getDate');
				if (testStartDate > testEndDate)
					endDateTextBox.datetimepicker('setDate', testStartDate);
			} else {
				endDateTextBox.val(dateText);
			}
		},
		onSelect: function (selectedDateTime){
			endDateTextBox.datetimepicker('option', 'minDate', startDateTextBox.datetimepicker('getDate') );
		}
	});

	endDateTextBox.datetimepicker({
		timeFormat: 'HH:mm:ss',
		onClose: function(dateText, inst) {
			if (startDateTextBox.val() != '') {
				var testStartDate = startDateTextBox.datetimepicker('getDate');
				var testEndDate = endDateTextBox.datetimepicker('getDate');
				if (testStartDate > testEndDate)
					startDateTextBox.datetimepicker('setDate', testEndDate);
			} else {
				startDateTextBox.val(dateText);
			}
		},
		onSelect: function (selectedDateTime){
			startDateTextBox.datetimepicker('option', 'maxDate', endDateTextBox.datetimepicker('getDate') );
		}
	});

	// Set Date
	startDateTextBox.datetimepicker('setDate', fnStringToDate(startDateTextBox.val()) );
	endDateTextBox.datetimepicker('setDate', fnStringToDate(endDateTextBox.val()) );
}

/*
param date : Date Objeet
return string "YYYY-MM-DD"
usage : getDateObjToStr(new Date());
*/
function getDateObjToStr(date){
var str = new Array();

var _year = date.getFullYear();
str[str.length] = _year;

var _month = date.getMonth()+1;
if(_month < 10) _month = "0"+_month;
str[str.length] = _month;

var _day = date.getDate();
if(_day < 10) _day = "0"+_day;
str[str.length] = _day
var getDateObjToStr = str.join("");

return _year+"-"+_month+"-"+_day;
}

/*
getDateObjToStr 함수 필요
return Today "YYYY-MM-DD"
*/
function getToday(){
var d = new Date();
var getToday = getDateObjToStr(d);
return getToday;
}

/*
데이트 계산 함수
param date : string "yyyy-mm-dd"
param period : int
param period_kind : string "Y","M","D"
param gt_today : boolean
usage : calcDate("20080205",30,"D",false);
*/
function calcDate(date,period, period_kind,gt_today){
date = date.replace(/-/g, '');
var today = getToday();

var in_year = date.substr(0,4);
var in_month = date.substr(4,2);
var in_day = date.substr(6,2);

var nd = new Date(in_year, in_month-1, in_day);
if(period_kind == "D"){
 nd.setDate(nd.getDate()+period);
}
if(period_kind == "M"){
 nd.setMonth(nd.getMonth()+period);
}
if(period_kind == "Y"){
 nd.setFullYear(nd.getFullYear()+period);
}
var new_date = new Date(nd);
var calcDate = getDateObjToStr(new_date);
if(gt_today){ // 금일보다 큰 날짜 반환한다면
 if(calcDate > today){
  calcDate = today;
 }
}
return calcDate;
}


/*
콤마제거 함수
param str : 000,000,000
*/
var oldText = "";
function replaceComma(str) { // 콤마 없애기
 while(str.indexOf(",") > -1) {
  str = str.replace(",", "");
 }
 return str;
}
/*
콤마연산 함수
param str : 000000000
return : 000,000,000
*/
function numChk(num){

 var rightchar = parseInt(replaceComma(num.value))+'';
 var moneychar = "";

 for(index = rightchar.length-1; index>=0; index--){
  splitchar = rightchar.charAt(index);
  if (isNaN(splitchar)) {
   //alert(splitchar +"는 숫자가 아닙니다. \n다시 입력해주세요");
   num.value = "";      //num.value = oldText; 이전text반환
   num.focus();
   return;
  }
  moneychar = splitchar+moneychar;
  if(index%3==rightchar.length%3&&index!=0){ moneychar=','+moneychar; }
 }
 oldText = moneychar;
 num.value = moneychar;
}

/**
 * stripTags 메서드는 문자열에서 XML 혹은 HTML 태그를 제거한다.
 * @return {$S} XML 혹은 HTML 태그를 제거한 새로운 $S 객체
 * @example
 * var str = "Meeting <b>people</b> is easy.";
 * document.write( $S(str).stripTags() );
 *
 * // 결과 :
 * // Meeting people is easy.
 */
function stripTags(str) {
	return str.replace(/<\/?(?:h[1-5]|[a-z]+(?:\:[a-z]+)?)[^>]*>/ig, '');
};

/**
 * HTML 태그에 해당하는 글자가 먹히지 않도록 바꿔주기
 *
 * 동작) & 를 &amp; 로, < 를 &lt; 로, > 를 &gt; 로 바꿔준다
 *
 * @param {String} sText
 * @return {String}
 */
function htmlSpecialChars(sText) {
	return sText.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/ /g, '&nbsp;');
}

/**
 * htmlSpecialChars 의 반대 기능의 함수
 *
 * 동작) &amp, &lt, &gt, &nbsp 를 각각 &, <, >, 빈칸으로 바꿔준다
 *
 * @param {String} sText
 * @return {String}
 */
function unhtmlSpecialChars(sText) {
	return sText.replace(/&lt;/g, '<').replace(/&gt;/g, '>').replace(/&nbsp;/g, ' ').replace(/&amp;/g, '&');
}

/**
 * 스마트에디터에 있는 태그를 제거한다.
 *
 * @param sHtml
 * @returns
 */
var sRxConverter = '@[0-9]+@';
function smarteditorRemoveHTML(sHtml) {

	var sContent = sHtml, nIdx = 0;
	var aTemp = sContent.match(new RegExp(this.sRxConverter)); // applyConverter에서 추가한 sTmpStr를 잠시 제거해준다.
	if (aTemp !== null) {
		sContent = sContent.replace(new RegExp(this.sRxConverter), "");
	}

	//0.안보이는 값들에 대한 정리. (에디터 모드에 view와 text모드의 view를 동일하게 해주기 위해서)
	sContent = sContent.replace(/\r/g, '');// MS엑셀 테이블에서 tr별로 분리해주는 역할이\r이기 때문에  text모드로 변경시에 가독성을 위해 \r 제거하는 것은 임시 보류. - 11.01.28 by cielo
	sContent = sContent.replace(/[\n|\t]/g, ''); // 개행문자, 안보이는 공백 제거
	sContent = sContent.replace(/[\v|\f]/g, ''); // 개행문자, 안보이는 공백 제거
	//1. 먼저, 빈 라인 처리 .
	sContent = sContent.replace(/<p><br><\/p>/gi, '\n');
	sContent = sContent.replace(/<P>&nbsp;<\/P>/gi, '\n');

	//2. 빈 라인 이외에 linebreak 처리.
	sContent = sContent.replace(/<br(\s)*\/?>/gi, '\n'); // br 태그를 개행문자로
	sContent = sContent.replace(/<br(\s[^\/]*)?>/gi, '\n'); // br 태그를 개행문자로
	sContent = sContent.replace(/<\/p(\s[^\/]*)?>/gi, '\n'); // p 태그를 개행문자로

	sContent = sContent.replace(/<\/li(\s[^\/]*)?>/gi, '\n'); // li 태그를 개행문자로 [SMARTEDITORSUS-107]개행 추가
	sContent = sContent.replace(/<\/tr(\s[^\/]*)?>/gi, '\n'); // tr 태그를 개행문자로 [SMARTEDITORSUS-107]개행 추가

	// 마지막 \n은 로직상 불필요한 linebreak를 제공하므로 제거해준다.
	nIdx = sContent.lastIndexOf('\n');
	if (nIdx > -1 && sContent.substring(nIdx) == '\n') {
		sContent = sContent.substring(0, nIdx);
	}

	//sContent = jindo.$S(sContent).stripTags().toString();
	sContent = stripTags(sContent);

	//sContent = this.unhtmlSpecialChars(sContent);
	sContent = unhtmlSpecialChars(sContent);

	if (aTemp !== null) { // 제거했던sTmpStr를 추가해준다.
		sContent = aTemp[0] + sContent;
	}

	return sContent;
}




/**
 * AjaxForm 을 이용한 파일 업로드
 *
 * @param objData
 * 	url 				: UpLoad URL
 * 	data 				: UpLoad param Data
 * 	beforSendFunc 	: UpLoad 이전 실행될 Function
 * 	completeFunc 	: UpLoad 완료 이후 실행될 Function
 * 	successFunc 		: UpLoad 성공 이후 실행될 Function
 * 	errorFunc 		: UpLoad 실패 시 실행될 Function
 */
ajaxFormFileUpLoad = function(objData){

	var obj={
			jqObj:"",
			url:"",
			data: {},
			timeout : 120000,//30000,
			beforSendFunc:function(){ },
			completeFunc:function(){ },
			successFunc:function(){ },
			errorFunc:function(){ },
			getDataFunc:function(objData,eventObj){ return objData; }
	};

	//ObjData Check
	if(objData != "" && objData != null){
		if(objData.jqObj != "" && objData.jqObj != null){									//Jquery Object
			obj.jqObj = objData.jqObj;
		}
		if(objData.url != "" && objData.url != null){										//URL
			obj.url = objData.url;
		}
		if(objData.data != "" && objData.data != null){									//Data
			obj.data = objData.data;
		}
		if(objData.beforSendFunc != "" && objData.beforSendFunc != null){			//Befor Function
			obj.beforSendFunc = objData.beforSendFunc;
		}
		if(objData.completeFunc != "" && objData.completeFunc != null){				//Complete Function
			obj.completeFunc = objData.completeFunc;
		}
		if(objData.successFunc != "" && objData.successFunc != null){					//Success Function
			obj.successFunc = objData.successFunc;
		}
		if(objData.errorFunc != "" && objData.errorFunc != null){							//Error Function
			obj.errorFunc = objData.errorFunc;
		}
		if(objData.getDataFunc != "" && objData.getDataFunc != null){
			obj.getDataFunc = objData.getDataFunc;
		}
		if(objData.timeout != "" && objData.timeout != null){
			obj.timeout = objData.timeout;
		}

		if(objData.container){
			obj.container = objData.container;
		}
	}

	function mouseOverEvent(e, eventObj){

		var exts = eventObj.attr("exts");

		var accept = "";
		var tmp = exts.split(',');
		if( exts != undefined && exts != '' ) {
			$.each(tmp, function(v) {
				if( accept == ""){
					accept = "."+tmp[v];
				}else{
					accept += ",."+tmp[v];
				}
			});
		}

		var strHtml = "";
		strHtml += '<div id="ajaxFormDiv" style="position: absolute; top:0px; left:0px; overflow: hidden; width: 100px; height: 33px; display: block; z-index: 999;">';
		strHtml += '	<form id="ajaxForm" name="dataFileForm" method="post" enctype="multipart/form-data" action="' + obj.url + '">';
		if( accept != ""){
			strHtml += '		<input type="file" name="fileData" id="ajaxFormFileData" style="width:100px; height:33px; filter: alpha(opacity=0); opacity:0; z-index: 999;" accept="' + accept +'"/>';
		}else{
			strHtml += '		<input type="file" name="fileData" id="ajaxFormFileData" style="width:100px; height:33px; filter: alpha(opacity=0); opacity:0;z-index: 999;" />';
		}

		strHtml += '	</form>';
		strHtml += '</div>';
		$("#ajaxFormDiv").remove();

		var container = objData.container;
		if(!container){
			container="body";
		}
		$(container).append(strHtml);

		if (navigator.appVersion.indexOf("MSIE") > 0) {		//IE 하위버전을 위한 구문...
			//Change Event 추가....
			$("#ajaxFormFileData").unbind("change");
			$("#ajaxFormFileData").bind("change", function(){
				//alert("bind change");
				//console.log("bind Cahnge!!!!!!");
			});

			//ajaxFormDiv 위치 설정....
			if($('#ajaxFormDiv')){
				$('#ajaxFormDiv').css('top', e.pageY-e.offsetY-5);
				$('#ajaxFormDiv').css('left', e.pageX-e.offsetX-8);
				$('#ajaxFormDiv').show();
			}else{
				//console.log("Jquery Object 가 없습니다.[ajaxFormDiv]");
			}

			//mouseOut 이벤트 발생시 ajaxFormDiv 삭제
			$("#ajaxFormDiv").mouseout(function(){
				//console.log("ajaxFormFileData out");
				$('#ajaxFormDiv').hide();
				//$('#ajaxFormDiv').remove();
			});
		}

		$("#ajaxFormFileData").change(function(){
			//console.log($(this));
			//obj.data 속성값 추가.
			if(undefined != eventObj.attr("fileType") || '' != eventObj.attr("fileType")) {
				obj.data.fileType = eventObj.attr("fileType");
			}
			if(undefined != eventObj.attr("deviceType") || '' != eventObj.attr("deviceType")) {
				obj.data.deviceType = eventObj.attr("deviceType");
			}
			if(undefined != eventObj.attr("fileName") || '' != eventObj.attr("fileName")) {
				obj.data.fileName = eventObj.attr("fileName");
			}
			if(undefined != eventObj.attr("imgWidth") || '' != eventObj.attr("imgWidth")) {
				obj.data.imgWidth = eventObj.attr("imgWidth");
			}
			if(undefined != eventObj.attr("imgHeight") || '' != eventObj.attr("imgHeight")) {
				obj.data.imgHeight = eventObj.attr("imgHeight");
			}
			if(undefined != eventObj.attr("exts") || '' != eventObj.attr("exts")) {
				obj.data.exts = eventObj.attr("exts");
			}
			if(undefined != eventObj.attr("maxLength") || '' != eventObj.attr("maxLength")) {
				obj.data.maxLength = eventObj.attr("maxLength");
			}
			if(undefined != eventObj.attr("fileSeq") || '' != eventObj.attr("fileSeq")) {
				obj.data.fileSeq = eventObj.attr("fileSeq");
			}

			var uesChangeEvent = eventObj.attr("useChangeEvent");

			obj.data = obj.getDataFunc(obj.data,eventObj);

			if(uesChangeEvent == "N" || uesChangeEvent == "n"){
				obj.successFunc("", eventObj);
				return ;
			}

			ajaxForm(eventObj);
		});

	}

	function ajaxForm(eventObj){
//		// ajax 기본 옵션 정의
//		var ajaxOption = {
//			timeout: obj.timeout,
//			type: 'POST',
//			cache: false
//		};
//
//		// ajax 기본 셋팅
//		$.ajaxSetup(ajaxOption);

		$("#ajaxForm").ajaxForm({
			data:obj.data	,
			timeout: obj.timeout,
			type: 'POST',
			cache: false,
			beforeSend: function(xmlHttpRequest) {
				cfShowBlock(true);

				console.log("before");
				obj.beforSendFunc();
			},
			complete: function(xhr, textStatus) {
				obj.completeFunc();
				$("#ajaxFormDiv").remove();

				cfHideBlock();
			},
			success:function(data){
				console.log("data");
				obj.successFunc(data, eventObj);
	    	},
	    	error:function(error){
	    		console.log("error");
	    		obj.errorFunc(error);
	    	},
			dataType:"json"
		}).submit();
	};


	//Event 추가....
	obj.jqObj.bind({
		mouseover : function(e){
			var eventObj = $(this);
			mouseOverEvent(e, eventObj);
		},
		click : function(){
			$("#ajaxFormFileData").click();
		}
	});
};

var opts = {
	lines: 13 // The number of lines to draw
	, length: 28 // The length of each line
	, width: 14 // The line thickness
	, radius: 42 // The radius of the inner circle
	, scale: 0.5 // Scales overall size of the spinner
	, corners: 1 // Corner roundness (0..1)
	//, color: '#000' // #rgb or #rrggbb or array of colors
	, opacity: 0.25 // Opacity of the lines
	, rotate: 0 // The rotation offset
	, direction: 1 // 1: clockwise, -1: counterclockwise
	, speed: 1.0 // Rounds per second
	, trail: 60 // Afterglow percentage
	, fps: 20 // Frames per second when using setTimeout() as a fallback for CSS
	, zIndex: 2e9 // The z-index (defaults to 2000000000)
	, className: 'spinner' // The CSS class to assign to the spinner
	, top: '45%' // Top position relative to parent
	, left: '50%' // Left position relative to parent
	, shadow: false // Whether to render a shadow
	, hwaccel: false // Whether to use hardware acceleration
	, position: 'absolute' // Element positioning
};
var spinner = null;
/***************************************************************
 * 화면을 회색 레이어로 블록 처리하는 메소드
 *
 * @param boolean isLoading : 로딩 이미지를 띄울지 여부(true/false)
 * @return void
****************************************************************/
function cfShowBlock(isLoading) {
	if(isLoading) {
		if(spinner == null) {
			spinner = new Spinner(opts).spin();
		}
		$('body').append(spinner.el);
	}
	$.blockUI({message: null});
}
function cfShowBlockMsg(isLoading,msg) {
	if(isLoading) {
		if(spinner == null) {
			spinner = new Spinner(opts).spin();
		}
		$('body').append(spinner.el);
	}
	$.blockUI({message: msg, baseZ:2000});
}

/***************************************************************
 * 화면을 회색 레이어로 블록 처리하는 메소드
 *
 * @param void
 * @return void
****************************************************************/
function cfHideBlock() {
	if(spinner != null) {
		spinner.stop();
		spinner = null;
	}
	$.unblockUI();
}

/***************************************************************
 * 이미지 사이즈 체크 및 프리뷰
 ****************************************************************/
function readURL(input, viewer, maxWidth, maxHeight) {
	if(input.files[0].type.indexOf("image") == -1) {
		alert('이미지 파일만 지원합니다.');
		$(input).val('');
		return false;
	}
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function (e) {
			var image = new Image();
			image.src = e.target.result;
			if( ( maxWidth > 0 && image.width > maxWidth )
				|| ( maxHeight > 0 && image.height > maxHeight ) ) {
				alert('사용할 수 없는 이미지 크기 입니다.');
				$(input).val('');
			} else {
				if( viewer ) {
					viewer.attr('src', image.src);
				}
			}
		};
		reader.readAsDataURL(input.files[0]);
	}
}

function chkImageSize(input, maxWidth, maxHeight, callback) {
	if(input.files[0].type.indexOf("image") == -1) {
		alert('이미지 파일만 지원합니다.');
		$(input).val('');
		return false;
	}
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function (e) {
			var image = new Image();
			image.src = e.target.result;
			if( ( maxWidth > 0 && image.width > maxWidth )
				|| ( maxHeight > 0 && image.height > maxHeight ) ) {
				alert('사용할 수 없는 이미지 크기 입니다.');
				$(input).val('');
			} else {
				if( callback ) {
					callback();
				}
			}
		};
		reader.readAsDataURL(input.files[0]);
	}
}

function downloadFile(url) {
	cfShowBlock(true);
	$.fileDownload(url, {
		successCallback: function(url) {
			cfHideBlock();
		},
		failCallback: function(responseHtml, url) {
			cfHideBlock();
			alert('File download failed!');
		}
	});
	return false;
}

function formatXML( xmlString , indent )
{
  indent = indent || "\t"; //can be specified by second argument of the function

  var tabs = "";  //store the current indentation

  var result = xmlString.replace(
    /\s*<[^>\/]*>[^<>]*<\/[^>]*>|\s*<.+?>|\s*[^<]+/g , //pattern to match nodes (angled brackets or text)
    function(m,i)
    {
      m = m.replace(/^\s+|\s+$/g, "");  //trim the match just in case

      if(i<38)
       if (/^<[?]xml/.test(m))  return m+"\n";  //if the match is a header, ignore it

      if (/^<[/]/.test(m))  //if the match is a closing tag
       {
          tabs = tabs.replace(indent, "");  //remove one indent from the store
          m = tabs + m;  //add the tabs at the beginning of the match
       }
       else if (/<.*>.*<\/.*>|<.*[^>]\/>/.test(m))  //if the match contains an entire node
       {
        //leave the store as is or
        m = m.replace(/(<[^\/>]*)><[\/][^>]*>/g, "$1 />");  //join opening with closing tags of the same node to one entire node if no content is between them
        m = tabs + m; //add the tabs at the beginning of the match
       }
       else if (/<.*>/.test(m)) //if the match starts with an opening tag and does not contain an entire node
       {
        m = tabs + m;  //add the tabs at the beginning of the match
        tabs += indent;  //and add one indent to the store
       }
       else  //if the match contain a text node
       {
        m = tabs + m;  // add the tabs at the beginning of the match
       }

      //return m+"\n";
      return "\n"+m; //content has additional space(match) from header
    }//anonymous function
  );//replace

  return result;
}



