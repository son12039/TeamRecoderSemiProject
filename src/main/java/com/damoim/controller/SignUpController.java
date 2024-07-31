package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.damoim.model.vo.SignUp;
import com.damoim.service.SignUpService;


@Controller
public class SignUpController {

	@Autowired
	 private SignUpService service;
	
	@PostMapping("/")
	public String index() {
		
		
		return "index";
	}
	
	
	
	
	
	@PostMapping("/signup")
	public String register(SignUp signup) {
		
		service.register(signup);
		System.out.println(signup);
		return "redirect:/";
	}
	
	
}
