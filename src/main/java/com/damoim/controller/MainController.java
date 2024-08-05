package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.damoim.service.MemberService;
import com.damoim.service.MembershipService;

@Controller
public class MainController {
	@Autowired MembershipService service;

	 @GetMapping("/{membershipCode}")
		public String main(@PathVariable("membershipCode") Integer membershipCode, Model model) {
			model.addAttribute("main",service.main(membershipCode));
			model.addAttribute("membershipUserCount", service.membershipUserCount(membershipCode));
			
			return "mainboard/main";
		}
}
