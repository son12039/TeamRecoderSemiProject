package com.damoim.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import com.damoim.model.dto.ChattingRoomDAO;

@Controller
public class ChattingRoomController {

	/*
	 * 정배
	 * */
	@Autowired
	private ChattingController main; 

	// 여기 모든 요청은 chatting.jsp에 연결되어 있는 js를 통해서 옴
	
	// 방 들어가기 어떤 사용자가 클럽채팅방에 들어오려고 시도하면 입장하려는 방번호로 방을 찾고, 회원닉네임을 가져와 쿠키에 저장함
	@GetMapping("/chattingRoom-enter")
	public ResponseEntity<?> EnterChattingRoom(String roomNumber, String nickname) {
		// 방 번호로 방 찾기
		ChattingRoomDAO chattingRoom = main.findRoom(roomNumber);

		if (chattingRoom == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 없는 방이라고 클라이언트한테 알려줌
		} else {
			main.enterChattingRoom(chattingRoom, nickname); // 방이 있다면 방입장 메서드호출후
			return new ResponseEntity<>(chattingRoom, HttpStatus.OK); // 클라이언트에게 방정보와 요청성공이라고 전송
		}
	}

	// 방 나가기
	@PatchMapping("/chattingRoom-exit")
	public ResponseEntity<?> ExitChattingRoom() {

		Map<String, String> map = main.findCookie(); // 방입장 시 쿠키에 방 정보가 저장되므로 나가기 메서드 호출 시 그 정보를 가져옴

		if (map == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 없으면 오류코드전송
		}

		String roomNumber = map.get("roomNumber");
		String nickname = map.get("nickname");
		
		// 방목록에서 방번호에 맞는 유저목록 가져오기
		ChattingRoomDAO chattingRoom = main.findRoom(roomNumber);
		List<String> users = chattingRoom.getUsers();

		// 닉네임 삭제
		users.remove(nickname);

		// 쿠키에서 닉네임과 방번호 삭제
		main.deleteCookie();

		return new ResponseEntity<>(chattingRoom, HttpStatus.OK);
	}

	// 참가 중이었던 대화방
	@GetMapping("/chattingRoom")
	public ResponseEntity<?> chattingRoom() {
		// 쿠키에 닉네임과 방번호가 있다면 대화중이던 방이 있던것
		// 방 제대로 안 나가고 브라우저 종료 시에 채팅서버 접속 시 입장했었던 방으로 입장
		Map<String, String> map = main.findCookie();

		if (map == null) {
			return new ResponseEntity<>(HttpStatus.OK);
		}

		String roomNumber = map.get("roomNumber");
		String nickname = map.get("nickname");

		ChattingRoomDAO chattingRoom = main.findRoom(roomNumber);

		if (chattingRoom == null) {
			main.deleteCookie();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			Map<String, Object> map2 = new HashMap<>();
			map2.put("chattingRoom", chattingRoom);
			map2.put("myNickname", nickname);

			return new ResponseEntity<>(map2, HttpStatus.OK);
		}
	}
}
