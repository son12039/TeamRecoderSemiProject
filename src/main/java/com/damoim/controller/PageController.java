package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.damoim.service.MembershipService;

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
	
	
	


 
	 

}
