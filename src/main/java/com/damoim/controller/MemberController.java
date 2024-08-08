package com.damoim.controller;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.damoim.model.dto.SearchDTO;
import com.damoim.model.vo.Member;
import com.damoim.service.MemberService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@PostMapping("/login")
	public String login(Member member,HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("mem", service.login(member));
		System.out.println(member);
		return "redirect:/";
	}
	
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
		
		return "/mypage/update";
	}
	
	
	// 회원정보 수정
	@PostMapping("/update")
	public String update(Member member, HttpServletRequest request) {

		HttpSession session = request.getSession();
		Member member2 = (Member) session.getAttribute("mem"); // 2차 인증시 생성된 세션
		
		if (member.getId() == null)
			member.setId(member2.getId());
		
		Member memCheck = (Member) session.getAttribute("mem");
		session.setAttribute("mem", member);
		service.update(member);
		
		System.out.println(member.getPwd()); // 1234 (수정할 비밀번호)
		System.out.println(member2.getPwd()); // 123 (원래 비밀번호)
		
		if (member.getPwd().equals(member2.getPwd())) {
			// 수정할 비밀번호가 같은경우
			return "/mypage/update_fail";
		} else {
			// 수정할 비밀번호가 다른경우 수정
			service.update(member);
			memCheck = (Member) session.getAttribute("mem");
			session.setAttribute("mem", member);
			return "/mypage/update_ok";
		}
		
		
	}
	

}












