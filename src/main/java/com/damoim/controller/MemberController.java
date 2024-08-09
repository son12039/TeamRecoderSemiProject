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
	@Autowired
	private MemberService service;
	
	@Autowired
	private MembershipService infoService;
	
	
	
 // 일단 확정 08 09 
// 로그인 , 해당 회원 정보 , 가입 클럽 코드 및 등급을 세션에 
		@ResponseBody
		@PostMapping("/login")
		public boolean login(Member member, HttpServletRequest request, Model model) {
			boolean check = true;
			HttpSession session = request.getSession();
			// 로그인 성공 !
			if (service.login(member) != null) {
				
				session.setAttribute("mem", service.login(member)); // 로그인 정보 세션에
				// 내가 가입한 클럽 정보 체크용
				ArrayList<MemberListDTO> membershipList = service.loginMemberMembership(member);

				// 위정보 세션추가
				session.setAttribute("membershipList", membershipList);

				return true;

				// 로그인 실패!
			} 
				return false;			

		}
	
	// 삭제예정
// 회원가입 관련 아이디 중복 체크용 
	@PostMapping("/idCheck")
	public String idCheck(Member member,Model model) {
		boolean idResult = false;
		Member mem = service.idCheck(member);
		if(mem == null) {
			idResult = true;
			model.addAttribute("idResult", idResult);
		}else {
			model.addAttribute("idResult", idResult);
		}
		return "signUp/signUp";
		
	}
	
	
	// 삭제예정
	// 회원가입 관련 닉네임 중복 체크용 
	@PostMapping("/nicknameCheck")
	public String nicknameCheck(Member member,Model model) {
		boolean nicknameResult = false;
		Member mem = service.idCheck(member);
		if(mem == null) {
			nicknameResult = true;
			model.addAttribute("idResult", nicknameResult);
		}else {
			model.addAttribute("idResult", nicknameResult);
		}
		return "signUp/signUp";
	}

	
	@PostMapping("/signUp")
	public String signUp(Member member) {
		
		System.out.println(member);
		
		service.signUp(member);
		
		
		return "redirect:/";
		
	}

	@GetMapping("/logout")
    public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}
	
	
	@PostMapping("/pwdCheck")
	public String pwdCheck(Member member, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.setAttribute("check", service.pwdCheck(member));
		
		
		return "/mypage/mypage";
	}
	
	@GetMapping("/pwdCheck")
	public String page() {
		
		return  "/mypage/mypage";
	}

	@PostMapping("/update")
	public String update(Member member, HttpServletRequest request) {
		
       HttpSession session = request.getSession();
		 
		Member member2 = (Member) session.getAttribute("mem"); // 2차 인증시 생성된 세션 
		

		 if(member.getId() == null )  member.setId(member2.getId());	
		 
		 
		 service.update(member);
	  System.out.println(member);
	  
	  session.setAttribute("mem",member);
		
		return "redirect:/pwdCheck";
	}
	
	@GetMapping("/myMembership")
	public String myMembership(MemberInfoDTO info, Model model) {
		Member member = new Member();
		member.setId(info.getId());
		System.out.println(info.getId());
		System.out.println(member);
		
		
		model.addAttribute("membership", infoService.grade(member));
		
		return "/mypage/myMembership";
	}
	
	
	
		
	
	}
	


