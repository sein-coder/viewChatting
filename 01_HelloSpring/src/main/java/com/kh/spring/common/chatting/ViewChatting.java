package com.kh.spring.common.chatting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ViewChatting extends BinaryWebSocketHandler {

	//소켓에 접속한 세션관리 객체 등록
	private static Map<String,WebSocketSession> clients = new HashMap();
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) {
		ObjectMapper mapper = new ObjectMapper();
		//TextMessage객체의 payload라는 멤버변수에 데이터가 들어가 있다.		
		
		try {
			RtcMessage msg = mapper.readValue(message.getPayload(), RtcMessage.class);
			session.getAttributes().put("msg", msg);
			clients.put(msg.getToken(), session); //세션관리를 위해 static변수에 값을 넣어준다..?
			sessionChecking(); //접속이 종료된 session을 확인해서 지우기
			adminBroadcast(); //현재접속자 접속session에 보내주기
			
			for(Map.Entry<String, WebSocketSession> client : clients.entrySet()) {
				WebSocketSession s = client.getValue();
				if(!client.getKey().equals(msg.getToken())) {
					try {
						s.sendMessage(new TextMessage(mapper.writeValueAsString(msg)));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //각각 명칭에 맞게 자바 멤버변수에 넣어줌
		
		
		super.handleTextMessage(session, message);
	}
	
	//현재 접속자 전송
	private void adminBroadcast() {
		ObjectMapper mapper = new ObjectMapper();
		RtcMessage msg = new RtcMessage();
		msg.setToken("admin");
		msg.setMembers(new ArrayList(clients.keySet()));
		msg.setType("member");
		
		try {
			for(Map.Entry<String, WebSocketSession> client : clients.entrySet()) {
				client.getValue().sendMessage(new TextMessage(mapper.writeValueAsString(msg)));
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//동시성에러때문에 Iterator를 이용해서 삭제하도록 한다!!
	private void sessionChecking() {
		Iterator<Map.Entry<String,WebSocketSession>> it = clients.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, WebSocketSession> client = it.next();
			if(!client.getValue().isOpen()) {
				it.remove();
			}
		}
	}
	
}
