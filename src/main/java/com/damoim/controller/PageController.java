package com.damoim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
	
//	마이페이지
	@GetMapping("/mypage")
	public String mypage() {
		return "/mypage/mypage";
	}
	
//	로그인 페이지
	@GetMapping("/login")
	public String login() {
		return "/mypage/login";
	}
//	회원가입 페이지
	@GetMapping("/register")
	public String register() {
		return "/mypage/register";
	}
//	main 생성 페이지
	@GetMapping("/mainCreate")
	public String mainCreate() {
		return "/mypage/mainCreate";
	}
	
	
}
