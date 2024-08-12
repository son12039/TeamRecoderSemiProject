package com.damoim.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.dto.MemberInfoDTO;
import com.damoim.model.vo.Member;
import com.damoim.service.MemberService;
import com.damoim.service.MembershipService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	int count = 0;
	@Autowired
	private MemberService service;
	
	@Autowired
	private MembershipService infoService;
	
	
	

	// 로그인 , 해당 회원 정보 , 가입 클럽 코드 및 등급을 세션에 
		@ResponseBody
		@PostMapping("/login")
		public boolean login(Member member, HttpServletRequest request, Model model) {
			HttpSession session = request.getSession();
			// 로그인 성공 !
			if (service.login(member) != null) {
				
				session.setAttribute("mem", service.login(member)); // 로그인 정보 세션에
				// 내가 가입한 클럽 정보 체크용
		

				// 해당 id를 가진 맴버의 맴버쉽 의 모든정보 + 맴버, 등급 등등
				System.out.println(infoService.grade(member));
				session.setAttribute("membership", infoService.grade(member)); 

				return true;
				// 로그인 실패!
			} 
				return false;			

		}
	
	// *** 회원가입 관련
		
	// 회원가입 관련 아이디 중복 체크용 
	@ResponseBody
	@PostMapping("/idCheck")
	public boolean idCheck(Member member) {
		Member mem = service.idCheck(member);
		return mem == null;
		
	}
	@ResponseBody
	@PostMapping("/nicknameCheck") // 회원가입시 닉네임 중복 체크 
	public boolean nicknameCheck(Member member) {
		Member mem = service.nicknameCheck(member);
		return mem == null;
			
	}

	@PostMapping("/signUp") // 회원가입 메서드
	public String signUp(Member member ,String addrDetail ) {
		

		Member mem = member;
		String addr = mem.getAddr();
		System.out.println("일반주소 : " + addr);
		System.out.println("상세 주소 : " + addrDetail);
		addr += "#"+ addrDetail;
		System.out.println("합친 주소 #이 구분자 : " + addr);
		mem.setAddr(addr);
		service.signUp(member);	
		System.out.println(member);
		return "redirect:/";
		
	}

	@GetMapping("/logout") // 로그아웃 메서드
    public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/myMembership") // 내가 가입한 클럽확인
	public String myMembership(Member member, Model model) {
		
		// 내 등급별 클럽
		model.addAttribute("membership", infoService.grade(member));
		
		return "/mypage/myMembership";
	}
	
	
	
		
	
	}
	


