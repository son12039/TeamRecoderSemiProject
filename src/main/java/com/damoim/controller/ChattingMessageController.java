package com.damoim.controller;

import java.util.Map;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.damoim.model.dto.MessageDAO;

@Controller
public class ChattingMessageController {
	// chatting.js에서 호출되는 메서드들

	// 채팅방 목록 업데이트용
	// getmapping("/뭐시기") 리턴 "/"하는 얘랑 똑같음!
	@MessageMapping("/socket/roomList")
	@SendTo("/topic/roomList")
	public void roomList() {
	}

	// 클럽코드가 roomNumber인 클럽채팅방에서 메세지를 보내면 그 클럽코드이름으로된 메세지가 오고, 
	// 그 메세지를 딸려온 클럽코드이름의 채팅방에 있는 다른 사용자에게 뿌리는 기능
	@MessageMapping("/socket/sendMessage/{roomNumber}")
	@SendTo("/topic/message/{roomNumber}")
	public MessageDAO sendMessage(@DestinationVariable String roomNumber, MessageDAO message) {
		return message;
	}

	// 위와 같이 출입시 요청되는 메세지를 클럽코드에 따라 같은 방 사용자들에게 출입키워드를 뿌리는 기능
	@MessageMapping("/socket/notification/{roomNumber}")
	@SendTo("/topic/notification/{roomNumber}")
	public Map<String, Object> notification(@DestinationVariable String roomNumber, Map<String, Object> chattingRoom) {
		return chattingRoom;
	}
}
