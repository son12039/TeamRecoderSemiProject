package com.damoim.controller;

import com.damoim.model.dto.ChattingRoomDAO;
import com.damoim.model.vo.BasicRoomListVo;
import com.damoim.model.vo.Member;
import com.damoim.service.MembershipService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

@Controller
public class ChattingController {

    @Autowired
    private MembershipService service;

    // 채팅방 목록 : 모든 채팅관련 메서드와 접근할 수 있음
    public static LinkedList<ChattingRoomDAO> chattingRoomList = new LinkedList<>();

    

    // 방 입출입 때 필요함!
    public ChattingRoomDAO findRoom(String roomNumber) {
        ChattingRoomDAO room = ChattingRoomDAO.builder().roomNumber(roomNumber).build();
        int index = chattingRoomList.indexOf(room);
        return (index >= 0) ? chattingRoomList.get(index) : null;
    }


    // 쿠키에 추가 
    public void addCookie(String cookieName, String value) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletResponse response = attr.getResponse();
        Cookie cookie = new Cookie(cookieName, value);
        int maxage = 60 * 60 * 24 * 7;
        cookie.setMaxAge(maxage);
        response.addCookie(cookie);
    }

    // 방 번호, 닉네임 쿠키 삭제
    public void deleteCookie() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletResponse response = attr.getResponse();

        Cookie roomCookie = new Cookie("roomNumber", null);
        Cookie nicknameCookie = new Cookie("nickname", null);

        roomCookie.setMaxAge(0);
        nicknameCookie.setMaxAge(0);

        response.addCookie(nicknameCookie);
        response.addCookie(roomCookie);
    }

    // 쿠키에서 방 번호, 닉네임 찾기
    public Map<String, String> findCookie() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();

        Cookie[] cookies = request.getCookies();
        String roomNumber = "";
        String nickname = "";

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("roomNumber".equals(cookie.getName())) {
                    roomNumber = cookie.getValue();
                }
                if ("nickname".equals(cookie.getName())) {
                    nickname = cookie.getValue();
                }
            }

            if (!roomNumber.isEmpty() && !nickname.isEmpty()) {
                Map<String, String> map = new HashMap<>();
                map.put("nickname", nickname);
                map.put("roomNumber", roomNumber);
                return map;
            }
        }
        return null;
    }

    // 방 입장하기
    public boolean enterChattingRoom(ChattingRoomDAO chattingRoom, String nickname) {

        if (chattingRoom == null) {
            deleteCookie();
            return false;
        } else {
            LinkedList<String> users = chattingRoom.getUsers();
            users.add(nickname);
            addCookie("nickname", nickname);
            addCookie("roomNumber", chattingRoom.getRoomNumber());
            return true;
        }
    }

    // 컨트롤러

    // 메인화면
    @GetMapping("/chatserver")
    public String chatServer(@RequestParam(value = "membershipCode", required = false) String membershipCode, Model model) {
        model.addAttribute("membershipCode", membershipCode);
                return "chatting/chatting";
//        return "chatPage"; // 적절한 JSP 페이지 반환
    }
    
    // 채팅방 목록
    // 모든 클럽정보를 가져와서 클럽마다 채팅서버1개씩 생성, 서버를 처음 작동했을 때만 작동함
    @GetMapping("/chattingRoomList")
    public ResponseEntity<?> chattingRoomList() throws Exception {
        if (chattingRoomList.size() < 3) { 
            for (BasicRoomListVo vo : service.roomlist()) {
                ChattingRoomDAO chattingRoom = ChattingRoomDAO.builder()
                    .roomNumber(String.valueOf(vo.getMembershipCode()))
                    .users(new LinkedList<>())
                    .roomName(vo.getMembershipName()+" (메인 채팅방)")
                    .build();
                chattingRoomList.add(chattingRoom);
            }
        }
        return new ResponseEntity<>(chattingRoomList, HttpStatus.OK);
    }

    // 닉네임 조회
    @GetMapping("/nick1")
    public ResponseEntity<Member> nick1() throws Exception {
       Member mem = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     return new ResponseEntity<>(mem, HttpStatus.OK);
    }

    // 새 채팅방 만들기
    @PostMapping("/chattingRoom")
    public ResponseEntity<?> chattingRoom(@RequestParam("roomName") String roomName) throws Exception {
    	Member mem = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	 System.out.println(roomName+" : "+mem.getNickname());
        String roomNumber = UUID.randomUUID().toString(); // UUID를 문자열로 사용
        ChattingRoomDAO chattingRoom = ChattingRoomDAO.builder()
            .roomNumber(roomNumber)
            .users(new LinkedList<>())
            .roomName(roomName)
            .build();
        chattingRoomList.add(chattingRoom);
        // 방 입장하기
        enterChattingRoom(chattingRoom, mem.getNickname());
        return new ResponseEntity<>(chattingRoom, HttpStatus.OK);
    }
}
