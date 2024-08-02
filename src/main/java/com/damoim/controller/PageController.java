package com.damoim.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.damoim.model.vo.Membership;
import com.damoim.service.MembershipService;

@Controller
public class PageController {

    @Autowired
    private MembershipService service;

	 @GetMapping("/")
	 public String index(Model model) {
		 System.out.println(1);
		
		 
		 System.out.println(service.allMembership());
		 model.addAttribute("list",service.allMembership());
		
		 
		 return "index";
	 }
	 
	 @GetMapping("/register")
	 public String register() {
		 return "mypage/register";
	 }
	 
	 @GetMapping("/mypage")
	 public String mypage() {
		 
		 return "mypage/mypage1";
	 }
	 
}
