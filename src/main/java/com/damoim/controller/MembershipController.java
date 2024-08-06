package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.damoim.model.dto.MemberListDTO;
import com.damoim.service.MembershipService;

@Controller
public class MembershipController {

	@Autowired MembershipService service;
	
	@PostMapping("/membershipApply")
	public String membershipApply(MemberListDTO member) {
		System.out.println(member);
		service.membershipApply(member);
		
		return "redirect:/";	
	}
}
