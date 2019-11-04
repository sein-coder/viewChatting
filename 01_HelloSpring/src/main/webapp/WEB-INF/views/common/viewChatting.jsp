<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>화상 1대1 채팅하기</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<!-- 
	WebRTC통신을 위한 준비하기 
	브라우저에서 지원하는 webRTC관련 객체/메소드 이용하기	
-->
<script>
	var webrtc_capable = true;
	//현재 접속브라우저가 WebRTC기술이 적용이 되었는지 확인하는 기준값
	var rtc_peer_connection=null;
	//peer연결을 위한 각 정보를 가지고 있는 객체
	/* 표준이 적용이 되어있지만 구버전 브라우저에서는 브라우저별 생성방식이 상이하여
	분기하여 변수에 넣어줌 
		표준 : RCTPeerConnection
		크롬 : webkitRTCPeerConnection
		파이어폭스 : mozRTCPeerConnection	
	*/
	var rtc_session_description=null;
	//RTC가 부여하는 Session 정보를 저장하는 변수
	var get_user_media=null
	//peer가 가지고 있는 video, sound의 정보를 가져오기
	/* 표준 : navigator.getUserMedia()
	      크롬 : navigator.webkitGetUserMedia()
	   firefox : navigator.mozGetUserMedia()
	*/
	var connect_stream_to_src=null;
	//peer가 보내는 mediaStream정보를 페이지의 video태그에 넣어주는
	//함수
	var stun_server="stun.l.google.com:19302";
	//firefox stun_server : 74.125.31.127:19302;
	
	//위에서 선언한 변수에 값넣기!!
	//표준이 되면? 표준
	//firefox되면 firefox / 크롬되면 크롬
	if(navigator.getUserMedia){//표준방식으로 필요객체생성
		rtc_peer_connection=RTCPeerConnection;
		rtc_session_description=RTCSessionDescription;
		get_user_media=navigator.getUserMedia.bind(navigator);
		connect_stream_to_src=function(media_stream,media_element){
			media_element.srcObject=media_stream;
			media_element.play();//video태그 실행!
		}
	}//표준으로 구현끝
	else if(navigator.mozGetUserMedia){//firefox
		rtc_peer_connection=mozRTCPeerConnection;
		rtc_session_description=mozRTCSessionDescription;
		get_user_media=navigator.mozGetUserMedia.bind(navigator);
		connect_stream_to_src=function(media_stream,media_element){
			media_element.mozSrcObject=media_stream;
			media_element.play();
			}
		stun_server="74.125.31.127:19302";
	}else if(navigator.webkitGetUserMedia){
		rtc_peer_connection=webkitRTCPeerConnection;
		rtc_session_description=webkitRTCSessionDescription;
		get_user_media=navigator.webkitGetUserMedia.bind(navigator);
		connect_stream_to_src=function(media_stream,media_element){
			media_element.src=webkitURL.createObjectURL(media_stream);
			}
	}else{
		alert("지원하지 않는 브라우저입니다. 크롬브라우저나 파이어폭스 브라우저를 이용하세요");
		webrtc_capable=false;
	}
</script>
<!-- 여기까지가 기본 브라우저에서 제공하는 객체,함수를 등록 -->
<script>
	var call_token="${loginMember.userId}";//사용자를 구분하기 위한 구분자, session userId값사용
	//두개의 peer한테 이벤트를 전달하여 통신을 할 수 있게 해주는 서버
	//long-polling방식, polling방식
	var signal_server;//websocket적용
	var peer_connection;
	//peer간의 연결을 세팅하는 RTCPeerConection객체를 생성
	//session정보, icecandidata설정함.
	function start(){//페이지가 로드된 후 실행
		peer_connection=new rtc_peer_connection({
			"iceServers":[{
				"url":"stun:"+stun_server	
			}]	
		});
		//다른  peer에게 icecandidate보내는 이벤트핸들러를 등록
		//p2p방식통신경로 후보군을 보내는것 *최적경로로 서로 연결!
		peer_connection.onicecandidate=function(ice_event){
			if(ice_event.candidate){
				signal_server.send(JSON.stringify({
					token:call_token,
					type:"new_ice_candidate",
					candidate:ice_event.candidate
				}))
			}
		};
		//영상을 보내주는 주소를 연결
		peer_connection.onaddstream=function(event){
			connect_stream_to_src(event.stream,
					document.getElementById("remote_video"));
			$("#loading_state").css("display","none");
			$("#open_call_state").css("display","block");		
		}
		//로컬에 있는 미디어를 출력하기
		setup_video();
		
		//signalserver세팅
		signal_server=new WebSocket("wss://192.168.20.34:8443/spring/viewCatting");
		
		//웹소켓으로 보내지는 메세지를 처리할 함수 설정
		signal_server.onopen=function(){
			//웹소켓서버에 접속한 뒤 로직 구성
			signal_server.onmessage=caller_signal_handler;
			//사용자에 대한 정보를 서버에 전송
			signal_server.send(JSON.stringify({
				token:call_token,
				type:"join"				
			}));
			document.title("연결대기중");
			$("#loading_state").html("영상통화대기 중.....");
		}
	}//서버에 대한 기본설정 끝!
		//서버에서 실시간으로 들어오는 message처리 함수등록
		
		var connected=true;//연결요청처리에 대한 flag값
		
		function caller_signal_handler(event){
			var signal=JSON.parse(event.data);
			//서버에서 보낸 자바스크립트 객체로 메세지 파싱
			//메제지 flag값을 통해서 분기처리 * type
			if(signal.type=="callee_arrived"){
				//화상연결된사용자가 있을때 화상통화요청
				//미디어연결에 대한 offer를 신호로 보냄
				//sdp객체를 생성해서 자신의 미디어정보를 전송
				//peer_connection.createOffer
				//(성공시 callback함수, 실패시 callback함수)
				peer_connection.createOffer(new_description_created,logError);
			}else if(signal.type=="new_ice_candidate"){
				peer_connection.addIceCandidate(new RTCIceCandidate(signal.candidate));
			}else if(signal.type=="new_description"){
				peer_connection.setRemoteDescription(new rtc_session_description(signal.sdp),
					function(){
					//성공에 대한 callback함수
							if(peer_connection.remoteDescription.type="answer"){
								if(connected){
									if(confirm("화상채팅요청이 들어왔습니다. 허용하시겠습니까?")){
										sendArrived();
									}
									connected=false;
								}
							}
							if(peer_connection.remoteDescription.type="offer"){
								peer_connection.createAnswer(new_description_created,function(){},logerror);
							}
					},logerror)
			}else if(signal.type="member"){//현재 서버에 접속한 멤버출력
				var membercontainer=$(".members");
				membercontainer.html("");
				for(var i=0;i<signal.members.length;i++){
					var li=$("<li>");
					var h=$("<h3>").html(signal.members[i]).css("color","gray");
					if(call_token!=sinal.members[i]){
						h.css("color","green");
						h.click(function(){
							if(confirm($(this).html()+"과 연결하시겠습니까?")){
								sendArrived();
							}
						});
						li.html(h);
						membercontainer.append(li);
					}			
				}
			}			
		}
		//생성된 미디어스트림에 대한 정보를 서버에 전송하여 상대방 peer에 전송
		//sdp
		function new_description_created(description){
			peer_connection.setLocalDescription(description,function(){
				signal_server.send(JSON.stringify({
					token:call_token,
					type:"new_description",
					sdp:description
				}))
			},logerror);
		}
		
		//전송된 스트림 처리하는 함수 미디어등록
		function setup_video(){
			get_user_media({
				"audio":true,
				"video":true
			},function(localstream){
				connection_stream_to_src(localstream,document.getElementById("local_video"));
				peer_connection.addStream(localstream);
			},logerror)
		}
		
		//에러처리함수
		function logerror(error){
			console.log(error);
		}
		
		//응답 및 전송요청 처리함수
		function sendArrived(){
			signal_server.send(JSON.stringify({
				token:call_token,
				type:"callee_arrived"
			}));
		}

</script>


</head>
<style>
	html, body {
		padding: 0px;
		margin: 0px;
		font-family: "Arial", "Helvetica", sans-serif;
	}
	
	#loading_state {
		position: absolute;
		top: 45%;
		left: 0px;
		width: 100%;
		font-size: 20px;
		text-align: center;
	}
	
	#open_call_state {
		display: none;
	}
	
	#local_video {
		position: absolute;
		top: 10px;
		left: 10px;
		width: 160px;
		height: 120px;
		background: #333333;
	}
	
	#remote_video {
		position: absolute;
		top: 0px;
		left: 0px;
		width: 1024px;
		height: 768px;
		background: #999999;
	}
	#membercontainer{
		position: absolute;
		top:20px;
		left:70%;
	}
	
	#membercontainer>ul{
		list-style-type:none;
	}
	#membercontainer>ul>li>h3{
		cursor:pointer;
	}
</style>
</head>
<body>
<!-- 화상채팅을 위한 페이지 -->

</head>
<body onload="start()">
		<div id="membercontainer">
			<ul class="members">
			</ul>
		</div>
	<div id="loading_state">
	</div>
	<div id="open_call_state">
		<video id="remote_video" controls></video>
		<video id="local_video"></video>
		<div id="membercontainer">
			<ul class="members">
			</ul>
		</div>
	</div>
</body>
</html>