package com.damoim.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.damoim.service.MembershipService;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

@Controller
public class PageController {
	
	@Autowired
	private MembershipService service;

	@GetMapping("/")
	public String index(Model model) {
//		System.out.println(1);
//
//		System.out.println(service.allMembership().get(0).getMember().getMemberImg());
//		System.out.println(service.allMembership().get(3).getMembership().getMembershipCode());
		model.addAttribute("list", service.allMembership());
		model.addAttribute("count", service.count(1));
//		System.out.println("카운트");
//		
		Integer i = service.allMembership().get(3).getMembership().getMembershipCode();
		System.out.println("카운트 대상 코드 : "+ i);
        System.out.println(service.count(i));
        
        List<Integer> codeList = new ArrayList<>();
        
        for(int j=0; j<service.allMembership().size(); j++) {
        Integer k= service.allMembership().get(j).getMembership().getMembershipCode();
        System.out.println("맴버쉽코드 : " +  k +"인원수  " + service.count(k));
      
        //model.addAttribute( "code", service.count(k));
        codeList.add(service.count(k));
      
        }
        model.addAttribute("codeList",codeList);
        System.out.println("사이즈" + service.allMembership().size());	
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
	
	


 
	 
	 @GetMapping("/{membershipCode}")
		public String main(@PathVariable("membershipCode") Integer membershipCode, Model model) {
			model.addAttribute("main",service.main(membershipCode));
			System.out.println(service.main(membershipCode));
			System.out.println(11111);
			return "mypage/main";
		}
		
	 
}
