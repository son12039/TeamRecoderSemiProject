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
	public String index(Model model) {
		System.out.println(1);

		System.out.println(service.allMembership().get(0).getMember().getMemberImg());
		System.out.println(service.allMembership());
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
	
	


 
	 
	 @GetMapping("/{membershipCode}")
		public String main(@PathVariable("membershipCode") Integer membershipCode, Model model) {
			model.addAttribute("main",service.main(membershipCode));
			System.out.println(service.main(membershipCode));
			System.out.println(11111);
			return "mypage/main";
		}
		
	 
}
