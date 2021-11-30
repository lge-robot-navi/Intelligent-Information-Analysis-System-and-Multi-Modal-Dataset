


// 2020.08.16 
==============
typescript 기능 추가함. 
ts와 js파일이 있는 경우는 ts 파일을 수정하여 js 파일을 생성시키는 방식으로 처리할 것. 
yarn watch 
를 수행하고 나서 수정하면, 자동 컴파일을 수행함. 
( 좀더 개발을 편리하게 하기 위하여 typescript를 사용하도록 함.)
yarn build 
를 빌드 처리.

// 2020.07.20 
rgb array to image 


// 2020.06.30
//---------------------------------------------------------------
java connectionLost 에러가 계속 발생해서,

connOpts.setKeepAliveInterval(1);
연결옵션을 위와 같이 처리하고 나서 에러 발생 없음.
==> setKeepAliveInterval을 늘리는 것이 에러 현상을 없애는데는 유리한 것으로 보임. 
( 확실한 것은 아님. ) 

mqtt 서버 기동시의 옵션도 존재하는지 확인이 필요함.

문제는 위가 아니라. clientId생성시, 동일 ID로 생성하기 때문임. 아래의 
방식을 사용하면 문제 없음. 

		long time = new Date().getTime();
		String clientId = "lg-cloud" + time;
접속 클라이언트 ID를 계속 유일하게 변경함. 

//--------------------------------------------------------------- 
PC에서 개발은 환경변수 
DEV_TYPE=PC 
설정이 필요함. ( 참고 : LgicConfig.java ) 

