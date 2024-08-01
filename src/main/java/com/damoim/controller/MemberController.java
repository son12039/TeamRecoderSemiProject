package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.damoim.service.MemberService;


@Controller
public class MemberController {
	
//	회원가입
	@Autowired
	private MemberService service;
	
	
	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register/register";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login/login";
	}
	
	
	
	
}
