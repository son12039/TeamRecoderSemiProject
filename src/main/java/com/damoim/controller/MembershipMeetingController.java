package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.damoim.model.vo.MembershipMeetings;
import com.damoim.service.MembershipMeetingService;

@Controller
public class MembershipMeetingController {

	
	@Autowired
	private  MembershipMeetingService service;
	
	
	@GetMapping("/write")
	public String write(int membershipCode, Model model) {
		System.out.println("모임 컨트롤러 매핑 " + membershipCode);
		model.addAttribute("membershipCode",membershipCode );
		return "membershipMeeting/meetingWrite";
	}
	
	@PostMapping("/write")
	public String write1(int membershipCode, MembershipMeetings meeting, String title,String content ) {
		System.out.println("작성 완료 컨트롤러 매핑 ");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("인증인가? : " + authentication.getPrincipal());
		System.out.println("아이디인가? " + authentication.getName());
		System.out.println("제목 " + title);
		System.out.println("내용 " + content);
		
			
		meeting.setId(authentication.getName());
		// 받아온 meeting을 DB에 insert하면 될듯 
		service.addMeeting(meeting);
		
	System.out.println(meeting);
	   
	  
	   
		return "redirect:/club/"+membershipCode;
	}
	
	
}
