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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Controller
public class ChattingController {

	@Autowired
	private MembershipService service;
	
	
	// 채팅방 목록
	public static LinkedList<ChattingRoomDAO> chattingRoomList = new LinkedList<>();
	
	public Connection con() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return  DriverManager.getConnection("jdbc:mysql://192.168.10.51:3306/damoim", "root", "qwer1234");
	}
	
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	public void basic() throws Exception {
		Connection conn = con();
		List<BasicRoomListVo> list = service.roomlist();
		int i =1;
		for(BasicRoomListVo a : list) {
			System.out.println(a + "테스트" +i++);
		}
		 
		String query = "SELECT membership_code, membership_name FROM membership";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
//		List<BasicRoomListVo> list = new ArrayList();
		 
		while(rs.next()) {
			ChattingRoomDAO chattingRoom = null;
			chattingRoom = ChattingRoomDAO.builder().roomNumber(rs.getInt("membership_code"))
				.users(new LinkedList<>()).roomName(rs.getString("membership_name")).build();
			chattingRoomList.add(chattingRoom);
		}
		close(rs, ps, conn);
		
	}
	public void close(PreparedStatement ps, Connection conn) throws SQLException {
		ps.close();
		conn.close();
	}
	
	public void close(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
		rs.close();
		close(ps, conn);
	}
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	// 유틸 메서드
	
	// 방 번호로 방 찾기
	public ChattingRoomDAO findRoom(String roomNumber) {
		ChattingRoomDAO room = ChattingRoomDAO.builder().roomNumber(Integer.parseInt(roomNumber)).build();
		int index = chattingRoomList.indexOf(room);
		return chattingRoomList.contains(room) ? chattingRoomList.get(index) : null;
	}

	// 쿠키에 추가
	public void addCookie(String cookieName, int i) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletResponse response = attr.getResponse();
		Cookie cookie = new Cookie(cookieName, String.valueOf(i));
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

	// 쿠키에서 방번호, 닉네임 찾기
	public Map<String, String> findCookie() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();

		Cookie[] cookies = request.getCookies();
		String roomNumber = "";
		String nickname = "";

		if (cookies == null) {
			return null;
		}

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if ("roomNumber".equals(cookies[i].getName())) {
					roomNumber = cookies[i].getValue();
				}
				if ("nickname".equals(cookies[i].getName())) {
					nickname = cookies[i].getValue();
				}
			}

			if (!"".equals(roomNumber) && !"".equals(nickname)) {
				Map<String, String> map = new HashMap<>();
				map.put("nickname", nickname);
				map.put("roomNumber", roomNumber);

				return map;
			}
		}

		return null;
	}

	// 닉네임 생성
	public void createNickname(String nickname) { // 방입장할때 닉넴 생성 후 쿠키에 저장
		addCookie("nickname", Integer.parseInt(nickname));
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

	// ----------------------------------------------------

	// 컨트롤러

	// 메인화면
	
	@GetMapping("/chatserver")
	public String main(HttpServletRequest request, String membershipName, Model model) {		
		model.addAttribute("membershipName", membershipName);
		return "chatting/chatting"; // jsp이동
	}
	@GetMapping("/getMemberInfo")
	public ResponseEntity<ArrayList<Integer>> idreturn(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		Member a = (Member) session.getAttribute("mem");
		ArrayList<Integer> list = (ArrayList<Integer>) service.membershipCodeList(a.getId());
		return new ResponseEntity<ArrayList<Integer>>(list, HttpStatus.OK);
	}
	// 채팅방 목록
	@GetMapping("/chattingRoomList")
	public ResponseEntity<?> chattingRoomList() throws Exception {
		if(chattingRoomList.size()<3)basic();
		return new ResponseEntity<LinkedList<ChattingRoomDAO>>(chattingRoomList, HttpStatus.OK);
	}
	@GetMapping("/nick1")
	public ResponseEntity<?> nick1(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		Member nick = (Member) session.getAttribute("mem");
		return new ResponseEntity<String>( nick.getNickname(), HttpStatus.OK);
	}
	// (url: "/chattingRoomList")로 호출되어 채팅리스트를 리턴한다

	// 새 채팅방 만들기 -----------------------------------------------------------

	@PostMapping("/chattingRoom")
	public ResponseEntity<?> chattingRoom(String roomName, String nickname) throws Exception {
		// 방을 만들고 채팅방목록에 추가
		 
		ChattingRoomDAO chattingRoom = null;
		String roomNumber = UUID.randomUUID().toString();
		chattingRoom = ChattingRoomDAO.builder().roomNumber(Integer.parseInt(roomNumber)).users(new LinkedList<>()).roomName(roomName)
				.build();

		chattingRoomList.add(chattingRoom);
		// 방 입장하기
		enterChattingRoom(chattingRoom, nickname);
		return new ResponseEntity<>(chattingRoom, HttpStatus.OK);
	}
	// 새 채팅방 만들기끝 -----------------------------------------------------------

	 


}