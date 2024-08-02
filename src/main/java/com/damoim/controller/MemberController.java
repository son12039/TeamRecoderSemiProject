package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
		session.setAttribute("member", service.login(member));
		return "redirect:/";
		
	}

	@PostMapping("/signUp")
	public String signUp(Member member) {
		System.out.println(member);
		
		service.signUp(member);
		
		
		return "redirect:mypage/mypage.jsp";
		
	}
//	회원가입
	
	
	
}
