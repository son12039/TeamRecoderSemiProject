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

	 /* 
	  * 
	  * */
	@GetMapping("/")
	public String index(Model model ) {
		
		List<Integer> countList = new ArrayList(); // count 계산용 인덱스 번호담는 배열
		model.addAttribute("list", service.allMembership()); // 현재 존재하는 모든 맴버쉽 정보가있는 배열		
		for(int i = 0; i < service.allMembership().size(); i++) {
		int j = service.allMembership().get(i).getMembership().getMembershipCode();
		countList.add(service.membershipUserCount(j)); // 각각 클럽의 인원수 (신청자는 제외)
		}	
		model.addAttribute("countList", countList); // 카운트 정보 출력용
		
		return "index";
	}
	 
	// 회원가입 페이지 이동
	@GetMapping("/signUp")
	public String signUp() {
		return "signUp/signUp";
	}
	
	// 마이페이지 이동
	@GetMapping("/mypage")
	public String mypage() {
		
		return "mypage/mypage";
	}
	// 내가 가입한 맴버쉽 페이지 이동


  // 로그인 페이지로 이동 
	 @GetMapping("/loginPage")
	 public String loginPage() {
	 	return "login/loginPage";
	 }
	 
	
		
	 
}
