package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.damoim.model.vo.MeetingsAgree;
import com.damoim.model.vo.MembershipMeetings;
import com.damoim.service.MembershipMeetingService;
import com.damoim.service.MembershipService;

@Controller
public class MembershipMeetingController {

	
	@Autowired
	private  MembershipMeetingService service;
	@Autowired
	private MembershipService membershipService;
	
	@GetMapping("/write")
	public String write(int membershipCode, Model model) {
		System.out.println("모임 컨트롤러 매핑 " + membershipCode);
		model.addAttribute("membershipCode",membershipCode );
		return "membershipMeeting/meetingWrite";
	}
	
	@PostMapping("/write")
	public String write1(int membershipCode, MembershipMeetings meeting, String title,String content,MeetingsAgree ma ) {
		System.out.println("작성 완료 컨트롤러 매핑 ");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("인증인가? : " + authentication.getPrincipal());
		System.out.println("아이디인가? " + authentication.getName());
	
		meeting.setId(authentication.getName());
		System.out.println(meeting);
		service.addMeeting(meeting);
		int size = membershipService.MembershipAllInfo(membershipCode).size();
	for(int i=0; i<size; i++) {
	if(!membershipService.MembershipAllInfo(membershipCode).get(i).getListGrade().equals("guest")) {
		
		System.out.println("게스트가 아닌애들 이름 " + membershipService.MembershipAllInfo(membershipCode).get(i).getMember().getId());
		ma.setId(membershipService.MembershipAllInfo(membershipCode).get(i).getMember().getId());
		ma.setMeetCode(meeting.getMeetCode());
		service.yesOrNo(ma);	
	}
	}
		
		
		
		
		
		
	
	   
	  
	   
		return "redirect:/club/"+membershipCode;
	}
	
	@GetMapping("/meetingDetail")
	
	public String meetingDetail(int meetCode , Model model) {
		System.out.println("디테일 컨트롤러 연결 : " + meetCode);
		model.addAttribute("meet", service.meetSelect(meetCode));
		model.addAttribute("list", service.meetMember(meetCode));
		String name = service.meetMember(meetCode).get(0).getMember().getId();
		MeetingsAgree asd =(MeetingsAgree) service.meetMember(meetCode).get(0);
	  System.out.println("1차 " +asd);
	  asd.setId(name);
	  System.out.println(asd);
	
		
		
		return "membershipMeeting/meetingDetail";
	}
		
		
	
	
}
