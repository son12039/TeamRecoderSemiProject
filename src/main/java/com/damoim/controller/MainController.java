package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.damoim.model.vo.Main;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.MembershipUserList;
import com.damoim.service.MainService;
import com.damoim.service.MembershipUserListService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	@Autowired
	private MainService mainService;
	private MembershipUserListService listService;
	
//	main 생성 post 받음
	@PostMapping("/mainCreate")
	public String mainCreate(MembershipUserList vo , HttpServletRequest request, Main main) {
		HttpSession session = request.getSession();
//		listService는 MembershipUserListMapper
		session.setAttribute("grade", listService.membershipUserList());
		if(vo.getListGrade().equals("host") || vo.getListGrade().equals("admin")) {
			mainService.mainCreate(main);
			System.out.println("유저 등급 : " + vo.getListGrade());
		}
		
		System.out.println("유저 등급 : " + vo.getListGrade());
		
		return "redirect:/";
	}
	
	
	
	
	
	
	
	
}









