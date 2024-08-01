package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.damoim.model.vo.Member;
import com.damoim.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class MemberController {

	@Autowired
	 private MemberService service;
	
	 

//	String id, String pwd, int age
	
	@PostMapping("/signup")
	public String register(Member member) {
		

		service.register(member);
		System.out.println(member);
		return "redirect:/";
	}
	
	@PostMapping("/login")
	public String login(Member member, HttpServletRequest request) {
		 
		HttpSession session = request.getSession();
		session.setAttribute("mem", service.login(member));
		
		return "redirect:/";
	}
	
	
	
	
	
}
