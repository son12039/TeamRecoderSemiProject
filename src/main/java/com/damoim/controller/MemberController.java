package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.damoim.model.dto.SearchDTO;
import com.damoim.model.vo.Member;
import com.damoim.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	

	@Autowired
	private MemberService service;
	
	@PostMapping("/login")
	public String login(Member vo, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("mem",service.login(vo));
		System.out.println(1);
		service.login(vo);
		return "redirect:/";
		
	}
	
	@GetMapping("/search")
	public String search(SearchDTO dto, Model model) {
		model.addAttribute("search", service.search(dto));
		return "index";
	}
}

