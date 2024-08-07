package com.damoim.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.damoim.model.dto.SearchDTO;
import com.damoim.model.vo.Member;
import com.damoim.service.MemberService;
import com.damoim.service.MembershipService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

@Controller
public class PageController {
	


	@Autowired
	private MembershipService service;
	private MemberService memberService;

	
	@GetMapping("/")
	public String index(Model model) {
		
		List<Integer> countList = new ArrayList();
		model.addAttribute("list", service.allMembership());
//		model.addAttribute("allMember", memberService.allMember());
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
	
	@GetMapping("/mainCreate")
	public String mainCreate() {
		return "/mypage/mainCreate";
	}
	

	
	
	 
	
		
	 
}
