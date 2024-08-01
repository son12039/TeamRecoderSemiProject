package com.damoim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	@GetMapping("/mypage")
	public String mypage() {
		return "/mypage/mypage";
	}
	
	@GetMapping("/login")
	public String login() {
		return "/login/login";
	}
	
	@GetMapping("/")
	public String register() {
		return "/register/register";
	}

	
}
