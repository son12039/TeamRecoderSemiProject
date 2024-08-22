package com.damoim.controller;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.MembershipUserList;
import com.damoim.model.vo.Paging;
import com.damoim.service.MembershipService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
@Controller
public class PageController {
	
	@Autowired
	private MembershipService service;

	/*
	 * 성일
	 * 인덱스에 현재 호스트가 존재하는 모든 클럽들 모두 출력
	 * */
	@GetMapping("/")
	public String index(Model model, Paging paging) {
		

	
	
	 
 		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("인증인가된 유저? : " + authentication.getPrincipal());
		List<Integer> countList = new ArrayList(); // count 계산용 인덱스 번호담는 배열
		model.addAttribute("list", service.allMembership(paging)); // 현재 존재하는 모든 맴버쉽 정보가있는 배열		
		for(int i = 0; i < service.allMembership(paging).size(); i++) {
		int j = service.allMembership(paging).get(i).getMembership().getMembershipCode();
		countList.add(service.membershipUserCount(j)); // 각각 클럽의 인원수 (신청자는 제외)
		}	
		model.addAttribute("countList", countList); // 카운트 정보 출력용
		
		List<MembershipUserList> list =	new ArrayList<MembershipUserList>();
		
		
		for(int i =0; i < service.allMembership(paging).size(); i++) {
			
			list.add(service.allMembership(paging).get(i));
			list.get(i).setCount(service.membershipUserCount(service.allMembership(paging).get(i).getMembership().getMembershipCode()));
		}
		
		model.addAttribute("list", list);


		return "index";
	}
	@ResponseBody
	@GetMapping("/list")
	public List<MembershipUserList> list(Paging paging){
		
		List<MembershipUserList> list =	new ArrayList<MembershipUserList>();
		
	for(int i =0; i < service.allMembership(paging).size(); i++) {
			
		list.add(service.allMembership(paging).get(i));
		list.get(i).setCount(service.membershipUserCount(service.allMembership(paging).get(i).getMembership().getMembershipCode()));
		}
		
		
		
		
		return list;
	}
	/*
	 * 성철
	 * 회원가입 페이지 이동 
	 * (나중에 추가 가능하면 휴대전화 api랑 나이 생년월일 선택으로 자동계산 반환, 승인버튼 버튼색 조건되야 변경기능)
	 * */
	@GetMapping("/signUp")
	public String signUp() {
		return "signUp/signUp";
	}

	
    // 기본 정보 수정
	@GetMapping("/update")
	public String mypage(Model model) {
		return "mypage/mypage";
	}
	
	// 내 정보 열람 비밀번호 체크
	@GetMapping("/updateCheck")
	public String updateCheck() {
		return "mypage/updateCheck";
	}
	
	// 중요 회원정보 수정
	@GetMapping("/updateMemberInfo")
	public String updateMemberInfo() {
		return "mypage/updateMemberInfo";
	}
	
	// 멤버쉽 정보 수정
	@GetMapping("/updateMembership")
	public String updateMembership() {
		return "membership/updateMembership";
	}
	
	// 회원탈퇴
	@GetMapping("/memberDelete")
	public String memberDelete(Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem = (Member) authentication.getPrincipal();
		System.out.println("memberDelete : " + mem);
		ArrayList<MembershipUserList> membershipList = service.selectName(mem.getId());
		model.addAttribute("list", membershipList);
		return "mypage/memberDelete";
	}

	// 내가 가입한 맴버쉽 페이지 이동
	/*
	 * 성일
	 * 로그인 페이지
	 * */
	 @GetMapping("/loginPage")
	 public String loginPage() {
	 	return "login/loginPage";
	 }
	 /* 성철
	  * 아이디 , 비밀번호 찾기 기능
	  * 아이디 찾기 미구현
	  * 비밀번호는 이메일로 임시비밀번호 발송
	  * */
	 @GetMapping("/findMember")
	 public String findMember() {
	 	return "login/findMember";
	 }
	 
  // 카카오맵 이동
	 @GetMapping("/kakaoMap")
	 public String kakaoMap() {
		 
		 return "kakaoMap";
	 }
	 
	 @GetMapping("/loginFail") 
	 public String loginFail() {
		 return "login/loginFail";
	 }

}









