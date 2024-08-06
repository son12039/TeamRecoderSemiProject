package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.damoim.model.vo.Membership;
import com.damoim.service.MembershipService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class MembershipController {
       
	   @Autowired
	   private MembershipService service;
	
	   
	   @GetMapping("/makeMembership")
	   public String makeMembership(  ) {
	   	return  "/mypage/makeMembership";
	   }
	   
	   @PostMapping("/makeMembership")
	   public String makeMembership(Membership membership  ) {
	   	service.makeMembership(membership);
	   	
	   	return "redirect:/";
	   }
	   
	   
	   
	
}
