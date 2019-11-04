<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>화상채팅 1:1</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"/>
</head>
<body>
<!-- 
	WebRTC통신을 위한 준비하기
	브라우저에서 지원하는 WebRTC 관련 객체/메소드 이용하기 
-->
<script>
	var webrtc capable = true; //현재 접속브라우저가 WebRTC기술이 적용이 되었는지 확인하는 기준값 (크롬,파이어폭스는 지원)
	var rtc_peer_connection = null; 
	//peer연결을 위한 각 정보를 가지고 있는 객체
	/* 
		표준이 적용이 되어있지만 구버전 브라우저에서는 브라우저별 생성방식이 상이하기에 분기하여 변수에 넣어줌. 
			표준 : RTCPeerConnection
			크롬 : webkitRTCPeerConnection
			파이어폭스 : mozRTCPeerConnection
	*/
	var rtc_session_description = null;
	//RTC가 부여하는 Session 정보를 저장하는 변수 (stateless통신이기 때문에)
	
	var get_user_media = null
	/*
		지금 현재 peer가 가지고 있는 video, sound의 정보를 가져오기
			표준 : navigator.getUserMedia()
			크롬 : navigator.webkitGetUserMedia()
			파이어폭스 : navigator.mozGetUserMedia()
	*/
	
	var connect_stream_to_src = null;
	//peer가 보내는 mediaStream정보를 페이지의 video태그에 넣어주는 함수
	var stun_server = "stun.l.google.com:19302";
	//firefox의 경우 - stun_server : 74.125.31.127:19302;
	
	//위에서 선언한 변수에 값넣기!!
	//표준이 되면? 표준
	//firefox되면 firefox / 크롬되면 크롬
	
	if(navigator.getUserMedia){ //표준방식으로 필요객체생성
		rtc_peer_connection = RTCPeerConnection;
		rtc_session_description = RTCSessionDescription;
		get_user_media = navigator.getUserMedia.bind(navigator);
	}
	
	
</script>
</body>
</html>