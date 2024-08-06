package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.damoim.model.dto.SearchDTO;
import com.damoim.model.vo.Member;
import com.damoim.service.MembershipService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {
	


	@Autowired
	private MembershipService service;
	

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("list", service.allMembership());
		return "index";
	}
	
	@GetMapping("/signUp")
	public String signUp() {
		return "signUp/signUp";
	}
	
	@GetMapping("/mypage")
	public String mypage() {
		return "mypage/mypage";
	}
	@GetMapping("/mainCreate")
	public String mainCreate() {
		return "/mypage/mainCreate";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("vo");
		if(member != null)session.invalidate();
		return "redirect:/";
	
	
	}
	
	
	 

}
