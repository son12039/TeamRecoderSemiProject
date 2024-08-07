package com.damoim.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.damoim.model.dto.MemberListDTO;
import com.damoim.service.MembershipService;
import com.mysql.cj.Session;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {
	
	@Autowired
	private MembershipService service;

	
	@GetMapping("/")
	public String index(Model model ) {
		
		List<Integer> countList = new ArrayList();
		model.addAttribute("list", service.allMembership());
		for(int i = 0; i < service.allMembership().size(); i++) {
		int j = service.allMembership().get(i).getMembership().getMembershipCode();
		countList.add(service.membershipUserCount(j));
		
		}
		
		model.addAttribute("countList", countList);
		return "index";
	}
	 

	@GetMapping("/signUp")
	public String signUp() {
		return "signUp/signUp";
	}
	
	@GetMapping("/mypage")
	public String mypage() {
		return "mypage/mypage";
	}
	@GetMapping("/myMembershipPage")
	public String myMembershipPage(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ArrayList<MemberListDTO> membershipList =(ArrayList<MemberListDTO>) session.getAttribute("membershipList");
		List<Integer> guestList = new ArrayList();
		List<Integer> regularList = new ArrayList();
		List<Integer> adminList = new ArrayList();
		List<Integer> hostList = new ArrayList();
		
		for(MemberListDTO code : membershipList) {
			
			if(code.getListGrade().equals("guest")) {
				guestList.add(code.getMembershipCode());	
			}else if(code.getListGrade().equals("regular")) {
				regularList.add(code.getMembershipCode());
			}else if(code.getListGrade().equals("admin")) {
				adminList.add(code.getMembershipCode());
			}else if(code.getListGrade().equals("host")) {
				hostList.add(code.getMembershipCode());
			}
		}
		System.out.println("가입 승인대기중인 클럽 :" + guestList);
		System.out.println("정회원 :" + regularList);
		System.out.println("관리자 :" + adminList);
		System.out.println("호스트 :" + hostList);
			if(guestList.get(0) != null) {
				
			}
		
		return "mypage/myMembershipPage";
	}
	
	


 
	 
	
		
	 
}
