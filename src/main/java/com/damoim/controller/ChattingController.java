package com.damoim.controller;

import com.damoim.model.dto.ChattingRoomDAO;
import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.dto.MessageDAO;
import com.damoim.model.vo.BasicRoomListVo;
import com.damoim.model.vo.Member;
import com.damoim.service.MemberService;
import com.damoim.service.MembershipService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // 채팅방 목록
    public static LinkedList<ChattingRoomDAO> chattingRoomList = new LinkedList<>();

    // 채팅방 목록 초기화
    public void basic() throws Exception {
        List<BasicRoomListVo> list = service.roomlist();
        for (BasicRoomListVo vo : list) {
            ChattingRoomDAO chattingRoom = ChattingRoomDAO.builder()
                .roomNumber(String.valueOf(vo.getMembershipCode())) // 방 번호를 문자열로 변환
                .users(new LinkedList<>())
                .roomName(vo.getMembershipName())
                .build();
            chattingRoomList.add(chattingRoom);
            System.out.println(vo);
        }
        System.out.println(list.size());
    }

    // 방 번호로 방 찾기
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

    // 닉네임 생성
    public void createNickname(String nickname) {
        addCookie("nickname", nickname);
    }

    // 방 입장하기
    public boolean enterChattingRoom(ChattingRoomDAO chattingRoom, String nickname) {
        createNickname(nickname);

        if (chattingRoom == null) {
            deleteCookie();
            return false;
        } else {
            LinkedList<String> users = chattingRoom.getUsers();
            users.add(nickname);

            addCookie("roomNumber", chattingRoom.getRoomNumber());
            return true;
        }
    }

    // 컨트롤러

    // 메인화면
    @GetMapping("/chatserver")
    public String chatServer(@RequestParam(value = "membershipCode", required = false) String membershipCode, Model model) {
        Integer  code = Integer.valueOf(membershipCode);;
        model.addAttribute("membershipCode", membershipCode);
                return "chatting/chatting";
//        return "chatPage"; // 적절한 JSP 페이지 반환
    }


    // 회원 정보 조회
    @GetMapping("/getMemberInfo")
    public ResponseEntity<ArrayList<Integer>> idreturn(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Member a = (Member) session.getAttribute("mem");
        ArrayList<Integer> list = (ArrayList<Integer>) service.membershipCodeList(a.getId());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 채팅방 목록
    @GetMapping("/chattingRoomList")
    public ResponseEntity<?> chattingRoomList() throws Exception {
        if (chattingRoomList.size() < 3) {
            basic();
        }
        return new ResponseEntity<>(chattingRoomList, HttpStatus.OK);
    }

    // 닉네임 조회
    @GetMapping("/nick1")
    public ResponseEntity<String> nick1(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Member nick = (Member) session.getAttribute("mem");
        return new ResponseEntity<>(nick.getNickname(), HttpStatus.OK);
    }

    // 새 채팅방 만들기
    @PostMapping("/chattingRoom")
    public ResponseEntity<?> chattingRoom(@RequestParam("roomName") String roomName, @RequestParam("nickname") String nickname) throws Exception {
        // 방을 만들고 채팅방 목록에 추가
        String roomNumber = UUID.randomUUID().toString(); // UUID를 문자열로 사용
        ChattingRoomDAO chattingRoom = ChattingRoomDAO.builder()
            .roomNumber(roomNumber)
            .users(new LinkedList<>())
            .roomName(roomName)
            .build();

        chattingRoomList.add(chattingRoom);
        // 방 입장하기
        enterChattingRoom(chattingRoom, nickname);
        return new ResponseEntity<>(chattingRoom, HttpStatus.OK);
    }
}
