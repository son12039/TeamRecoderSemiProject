package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.damoim.model.vo.SignUp;
import com.damoim.service.SignUpService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class SignUpController {

	@Autowired
	 private SignUpService service;
	
	 

	
	
	@PostMapping("/signup")
	public String register(SignUp signup) {
		
		service.register(signup);
		System.out.println(signup);
		return "redirect:/";
	}
	
	@PostMapping("/login")
	public String login(SignUp signup, HttpServletRequest request) {
		 
		HttpSession session = request.getSession();
		session.setAttribute("mem", service.login(signup));
		
		return "redirect:/";
	}
	
	
	
}
