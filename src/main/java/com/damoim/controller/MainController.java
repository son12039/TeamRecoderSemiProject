package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.vo.Member;
import com.damoim.service.MemberService;
import com.damoim.service.MembershipService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	@Autowired MembershipService service;

	 @GetMapping("/{membershipCode}")
		public String main(@PathVariable("membershipCode") Integer membershipCode,MemberListDTO member, Model model,HttpServletRequest request) {
			model.addAttribute("main",service.main(membershipCode));
			model.addAttribute("membershipUserCount", service.membershipUserCount(membershipCode));
			HttpSession session = request.getSession();
			Member mem = (Member) session.getAttribute("mem");
			if(mem != null) {
				System.out.println("시작" + member);
				member.setId(mem.getId());
				System.out.println("삽입후" +member);
			
			model.addAttribute("checkMember" , service.checkMember(member));
				
			}
			
			
			return "mainboard/main";
		}
}
