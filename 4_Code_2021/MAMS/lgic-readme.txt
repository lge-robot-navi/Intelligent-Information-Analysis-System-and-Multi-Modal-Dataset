


# typescript 설치. 
npm i -g typescript 



# 데이터베이스 변경. 
tb_event_info : abnormal_id ==> varchar(50) 변경함. 


# udp server
=============
UdpServer

위치정보 포항(ORG)
{ lat : 36.119262 , lon : 129.416367 }

실증단지
{ lat : 36.119204 , lon : 129.416038 }




# 구글 맵 overlay
=================
https://developers-dot-devsite-v2-prod.appspot.com/maps/documentation/javascript/examples/maptype-image-overlay?hl=ko

# custom overlays
https://developers-dot-devsite-v2-prod.appspot.com/maps/documentation/javascript/examples/overlay-simple

# ground overlays
https://developers-dot-devsite-v2-prod.appspot.com/maps/documentation/javascript/groundoverlays

# ground overlays options.
https://developers.google.com/android/reference/com/google/android/gms/maps/model/GroundOverlay

# 포항실증단지 주소. 
주소 : 경상북도 포항시 북구 흥해읍 용한리 894-15


# 실행오류.
- 프로파일이 다른것으로 설정되어 있는 경우, 오류 발생가능함. 
- target 폴더 자체를 초기화 시키고 실행해볼 필요 있음. 

# 프로파일
==========
mvn package -P 프로파일명 으로 패키징

local   : default 
dev     : 
release : 

클라우드 서버 릴리즈시에는 
mvn package -P release 사용하면됨.
mvn clean package -P release


# 클라우드 실행. 
===============


# 실시간 모니터링 이미지 재생 방법
===================================
1. 메인 화면에서 모니터링 재생 화면으로 이동한다. 

2. 지역선택, 시간을 선택하고, 재생 버튼을 클릭한다. 

3. 분단위로 재생정보를 요청한다. 
- 재생정보는 파일명, 시간으로 되어 있다. 

4. 각 에이전트 별로 시간에 따라서 이미지를 요청한다.

5. 모든 에이전트가 플레이가 완료되거나, 시간이 1분이 지나면 새로운 시간에 대해서 
 파일 정보를 요청한다.  




# 설정정보 
==========
- 실시간 모니터링 Off / ON  기능
 . agent 에서 조회하여 설정정보를 모니터링 하는 방식으로 처리함.
 
 # 포항, 광주 조회.
 http://localhost:52000//monitoring/api/codeInfo?cdgrpCd=TA008
 
 # 포항 에이전트
 http://localhost:52000//monitoring/api/codeInfo?cdgrpCd=TA009
 
 # 광주 에이전트.
 http://localhost:52000//monitoring/api/codeInfo?cdgrpCd=TA010 
 
 # 실시간 저장정보 설정. 
 http://localhost:52000/monitoring/api/settings/gwangju
 http://localhost:52000/monitoring/api/settings/pohang
 
 # json test.
 http://localhost:52000/monitoring/test/json
 
 http://localhost:52000/monitoring/api/agents/get/pohang
 http://localhost:52000/monitoring/api/agents/get/gwangju
 
 http://localhost:52000/monitoring/api/agents/get/pohang/2
 http://localhost:52000/monitoring/api/agents/get/gwangju/3
 
 http://localhost:52000/monitoring/api/agents/set/pohang/2/true
 http://localhost:52000/monitoring/api/agents/set/gwangju/3/true
 http://localhost:52000/monitoring/api/agents/set/pohang/2/false
 http://localhost:52000/monitoring/api/agents/set/gwangju/3/false
  

# 라이다 테스트.
http://112.171.39.141:1234/

# MMS, SDMS
http://112.171.39.141:52000/monitoring/test/map
http://112.171.39.141:52000/admin




# 작업내용 ( 2019.07.16 화 )
============================
1. 이미지 저장 기능 구현할 것. 

2. 이벤트발생 시에 이미지 저장기능. 

3. 이미지 저장 설정기능. 








# 음성 플레이어 참고 사항. 
https://github.com/staskobzar/vue-audio-visual


# 문의사항. 
1. 이벤트, 상태정보에 지역구분코드 필요함.(포항 : P, 광주 : G, areaCode 의 항목으로 추가) 
2. 이벤트정보의 eventPosX, eventPosY 가 위경도로 인식하면 되는지, 아니라면, 이를 위경도로 표시하는 방법은 무엇인지?






<script src="https://unpkg.com/vue"></script>



http://175.197.51.165:52000/monitoring/test/player2
http://175.197.51.165:52000/monitoring/test/map


http://127.0.0.1:52000/monitoring/test/player2
http://127.0.0.1:52000/monitoring/test/map



-- --------------------------------------------------------
-- 호스트:                          192.168.0.190
-- 서버 버전:                        5.7.26-0ubuntu0.16.04.1 - (Ubuntu)
-- 서버 OS:                        Linux
-- HeidiSQL 버전:                  10.1.0.5501
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 테이블 데이터 lgic.TA_CODE_INFO:~34 rows (대략적) 내보내기
/*!40000 ALTER TABLE `TA_CODE_INFO` DISABLE KEYS */;
INSERT INTO `TA_CODE_INFO` (`CODE_CD`, `CODE_NM`, `CODE_DS`, `USE_YN`, `IF_CD`, `INSERT_DT`, `INSERT_ID`, `UPDATE_DT`, `UPDATE_ID`, `ORDER_NO`, `CDGRP_CD`) VALUES
	('1', '사람감지', '{ image : siren.gif }', 'Y', '', '2019-05-30 02:21:29', 'A000000001', '2019-05-30 02:38:31', 'A000000001', '1', 'TA011'),
	('100', 'Super Admin', '', 'Y', '', '2017-09-17 15:24:09', 'A000000001', '2017-09-17 15:24:09', 'A000000001', '100', 'TA002'),
	('100', '정상', '', 'Y', '', '2017-09-17 15:24:47', 'A000000001', '2017-09-17 15:24:47', 'A000000001', '100', 'TA003'),
	('100', 'JPG', '100 : JPG', 'Y', '', '2017-10-14 19:32:ㄲ29', 'A000000001', '2017-10-14 19:32:29', 'A000000001', '100', 'TA004'),
	('100', 'Google', '100 : Google', 'Y', '', '2017-10-14 19:36:06', 'A000000001', '2017-10-14 19:36:06', 'A000000001', '100', 'TA005'),
	('100', 'Rectangle', 'Rectangle', 'Y', '', '2017-11-13 00:31:27', 'A000000003', '2017-11-13 00:31:27', 'A000000003', '100', 'TA006'),
	('100', '포항', '{lat:36.0907, lon:129.3329}', 'Y', '', '2019-05-24 14:48:55', 'A000000001', '2019-05-29 20:55:40', 'A000000001', '100', 'TA008'),
	('100', 'P : 에이전트1(고정)', '{lat:36.0910, lon:129.3332, fixed:Y, markerImage:cctv.png ,agentId:101}', 'Y', '', '2019-05-28 12:12:49', 'A000000001', '2019-05-29 23:16:50', 'A000000001', '1', 'TA009'),
	('101', 'P : 에이전트2(고정)', '{lat:36.0910, lon:129.3332, fixed:Y, markerImage:cctv.png ,agentId:102}', 'Y', '', '2019-05-28 12:13:17', 'A000000001', '2019-05-29 23:17:00', 'A000000001', '2', 'TA009'),
	('102', 'P : 에이전트3(이동)', '{lat:36.0910,lon:129.3332, fixed:Y, markerImage:robot.png ,agentId:103}', 'Y', '', '2019-05-28 12:13:41', 'A000000001', '2019-05-29 23:17:09', 'A000000001', '3', 'TA009'),
	('103', 'P : 에이전트4(이동)', '{lat:36.0910, lon:129.3332, fixed:Y, markerImage:robot.png,agentId:104 }', 'Y', '', '2019-05-28 12:14:00', 'A000000001', '2019-05-29 23:17:18', 'A000000001', '4', 'TA009'),
	('104', 'P : 에이전트5(이동)', '{lat:36.0910, lon:129.3332, fixed:Y, markerImage:robot.png ,agentId:105}', 'Y', '', '2019-05-28 12:16:07', 'A000000001', '2019-05-29 23:17:27', 'A000000001', '5', 'TA009'),
	('105', 'P : 에이전트6(고정)', '{lat:36.0910, lon:129.3332, fixed:Y, markerImage:cctv.png ,agentId:106}', 'Y', '', '2019-05-28 12:16:23', 'A000000001', '2019-05-29 23:17:36', 'A000000001', '6', 'TA009'),
	('2', '화재감지', '{ image : siren.gif }', 'Y', '', '2019-05-30 02:21:46', 'A000000001', '2019-05-30 02:38:44', 'A000000001', '2', 'TA011'),
	('200', '광주', '', 'Y', '', '2019-05-24 14:46:27', 'A000000001', '2019-05-24 14:46:27', 'A000000001', '200', 'TA0008'),
	('200', 'Worker', '200 : Worker', 'Y', '', '2017-10-20 14:28:52', 'A000000003', '2017-10-20 14:28:52', 'A000000003', '200', 'TA002'),
	('200', '일시정지', '', 'Y', '', '2017-09-17 15:24:57', 'A000000001', '2017-09-17 15:24:57', 'A000000001', '200', 'TA003'),
	('200', 'PNG', '200 : PNG', 'Y', '', '2017-10-14 19:32:50', 'A000000001', '2017-10-14 19:32:50', 'A000000001', '200', 'TA004'),
	('200', 'Flickr', '200 : Flickr', 'Y', '', '2017-10-15 01:32:31', 'A000000001', '2017-10-15 01:32:31', 'A000000001', '200', 'TA005'),
	('200', 'Ploygon', 'Ploygon', 'Y', '', '2017-11-13 00:31:40', 'A000000003', '2017-11-13 00:31:40', 'A000000003', '200', 'TA006'),
	('200', 'Depth', '200 : Depth', 'Y', '', '2018-06-28 14:12:21', 'A000000001', '2018-11-25 15:26:26', 'A000000001', '200', 'TA007'),
	('200', '광주', '{lat:35.105391, lon:126.894703}', 'Y', '', '2019-05-24 14:49:18', 'A000000001', '2019-05-29 23:45:59', 'A000000001', '200', 'TA008'),
	('200', 'G : 에이전트1(고정)', '{lat:35.1064, lon:126.8951, fixed:Y, markerImage:cctv.png ,agentId:101}', 'Y', '', '2019-05-28 12:18:10', 'A000000001', '2019-05-29 23:40:05', 'A000000001', '1', 'TA010'),
	('201', 'G : 에이전트2(고정)', '{lat:35.106918, lon:126.895787, fixed:Y, markerImage:cctv.png ,agentId:102}', 'Y', '', '2019-05-28 12:18:26', 'A000000001', '2019-05-29 23:41:38', 'A000000001', '2', 'TA010'),
	('202', 'G : 에이전트3(고정)', '{lat:35.104987, lon:126.893684, fixed:Y, markerImage:cctv.png ,agentId:103}', 'Y', '', '2019-05-28 12:18:47', 'A000000001', '2019-05-29 23:42:06', 'A000000001', '3', 'TA010'),
	('203', 'G : 에이전트4(이동)', '{lat:35.104153, lon:126.895207, fixed:Y, markerImage:cctv.png ,agentId:104}', 'Y', '', '2019-05-28 12:19:30', 'A000000001', '2019-05-29 23:43:43', 'A000000001', '4', 'TA010'),
	('204', 'G : 에이전트5(이동)', '{lat:35.104162, lon:126.894338, fixed:Y, markerImage:cctv.png ,agentId:105}', 'Y', '', '2019-05-28 12:19:46', 'A000000001', '2019-05-29 23:42:31', 'A000000001', '5', 'TA010'),
	('205', 'G : 에이전트6(이동)', '{lat:35.104355, lon:126.894317, fixed:Y, markerImage:robot.png ,agentId:106}', 'Y', '', '2019-05-28 12:20:03', 'A000000001', '2019-05-29 23:44:51', 'A000000001', '6', 'TA010'),
	('3', '인구밀도 비정상적인 증가', '{ image : siren.gif }', 'Y', '', '2019-05-30 02:22:04', 'A000000001', '2019-05-30 02:38:50', 'A000000001', '3', 'TA011'),
	('300', 'BMP', '300 : BMP', 'Y', '', '2017-10-14 19:33:10', 'A000000001', '2017-10-14 19:33:10', 'A000000001', '300', 'TA004'),
	('300', 'Tumblr', '300 : Tumblr', 'Y', '', '2017-10-15 01:32:45', 'A000000001', '2017-10-15 01:32:45', 'A000000001', '300', 'TA005'),
	('300', 'NV1', '300 : Night Vision1', 'Y', '', '2018-06-28 14:12:21', 'A000000001', '2018-11-25 15:23:07', 'A000000001', '300', 'TA007'),
	('400', 'Twitter', '400 : Twitter', 'Y', '', '2017-10-15 01:32:57', 'A000000001', '2017-10-15 01:32:57', 'A000000001', '400', 'TA005'),
	('400', 'NV2', '400 : Night Vision2', 'Y', '', '2018-06-28 14:12:21', 'A000000001', '2018-11-25 15:23:29', 'A000000001', '400', 'TA007'),
	('500', 'Thermal', '500 : Thermal', 'Y', '', '2018-06-28 14:12:21', 'A000000001', '2018-11-25 15:23:48', 'A000000001', '500', 'TA007'),
	('600', 'Sound', '600 : Sound', 'Y', NULL, '2018-06-28 14:12:21', 'A000000001', '2018-06-28 14:12:21', 'A000000001', '600', 'TA007'),
	('65535', '미분류 이상상황', '{ image : siren.gif }', 'Y', '', '2019-05-30 02:22:39', 'A000000001', '2019-05-30 02:38:38', 'A000000001', '1000', 'TA011'),
	('700', 'Lidar', '700 : Lidar', 'Y', '', '2018-11-25 15:22:12', 'A000000001', '2018-11-25 15:22:12', 'A000000001', '700', 'TA007'),
	('900', '해지', '', 'Y', '', '2017-09-17 15:25:10', 'A000000001', '2017-09-17 15:25:10', 'A000000001', '900', 'TA003'),
	('M', 'Menu', '', 'Y', '', '2017-09-17 15:23:35', 'A000000001', '2017-09-17 15:23:35', 'A000000001', '200', 'TA001'),
	('U', 'URL', '', 'Y', '', '2017-09-17 15:23:21', 'A000000001', '2017-09-17 15:23:21', 'A000000001', '100', 'TA001');
/*!40000 ALTER TABLE `TA_CODE_INFO` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
