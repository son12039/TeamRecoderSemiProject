package com.damoim.controller;

import java.util.ArrayList;
import java.util.List;

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
		
		List<Integer> countList = new ArrayList();
		model.addAttribute("list", service.allMembership());
		for(int i = 0; i < service.allMembership().size(); i++) {
		int j = service.allMembership().get(i).getMembership().getMembershipCode();
		countList.add(service.membershipUserCount(j));
		
		}
		
		model.addAttribute("countList", countList);
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
