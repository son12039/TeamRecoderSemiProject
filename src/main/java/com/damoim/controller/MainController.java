package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.damoim.model.vo.Main;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.MembershipUserList;
import com.damoim.service.MainService;

@Controller
public class MainController {
	
	@Autowired
	private MainService service;
	
//	main 생성 post 받음
	@PostMapping("/mainCreate")
	public String mainCreate(MembershipUserList vo, Main main) {
		if(vo.getListGrade().equals("host") || vo.getListGrade().equals("admin")) {
			service.mainCreate(main);
			System.out.println("유저 등급 : " + vo.getListGrade());
		}
		
		
		return "redirect:/";
	}
	
	
}









