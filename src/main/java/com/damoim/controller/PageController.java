package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.damoim.service.MembershipService;

@Controller
public class PageController {
	
	@Autowired
	private MembershipService service;

	
	@GetMapping("/")
	public String index(Model model ) {
		
		model.addAttribute("list", service.allMembership());
//		int i = service.allMembership().get(0).getMembership().getMembershipCode();
//		model.addAttribute("Count", service.membershipUserCount(i));

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
	
	


 
	 
	
		
	 
}
