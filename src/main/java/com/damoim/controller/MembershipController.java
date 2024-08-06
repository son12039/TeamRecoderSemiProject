package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.dto.MembershipDTO;
import com.damoim.service.MembershipService;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.MembershipUserList;
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
	   public String makeMembership(MembershipDTO dto) {
		 	System.out.println(dto.getListGrade());
		 	
		 Membership membership = Membership.builder()
	 		.membershipName(dto.getMembershipName())
	 		.membershipInfo(dto.getMembershipInfo())
	 		.membershipMax(Integer.parseInt(dto.getMembershipMax())).build();
		 
		  service.makeMembership(membership);
		  
		  System.out.println(membership.getMembershipCode());
		  

		  
		 MembershipUserList list = MembershipUserList.builder()
				 							.listGrade(dto.getListGrade())
				 							.id(dto.getId())
				 							.membershipCode(membership.getMembershipCode())
				 							.build();
	   	service.host(list);
	  
	   	
	   	return "redirect:/";
	   }
	   
	   
	   
	   @PostMapping("/membershipApply")
	public String membershipApply(MemberListDTO member) {
		
		return "redirect:/";	
	}
	   
	   
	   
	
}
