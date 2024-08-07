package com.damoim.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.dto.MembershipDTO;
import com.damoim.service.MembershipService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.damoim.model.vo.Member;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.MembershipUserList;
import com.damoim.service.MembershipService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class MembershipController {

	@Autowired
	private MembershipService service;
	
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
	 
	 @GetMapping("/{membershipCode}club")
		public String membershipPage(@PathVariable("membershipCode") Integer membershipCode,MemberListDTO member, Model model,HttpServletRequest request) {
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
			List<MembershipUserList> list = service.MembershipAllInfo(membershipCode);
			System.out.println(list.get(0));
			
			model.addAttribute("allMember" , service.MembershipAllInfo(membershipCode));
			return "membership/membershipPage";
		}
	 @GetMapping("/agreeMember")
	 public String agreeMemeber(MemberListDTO member) {
		 service.agreeMemeber(member);
		return null;
		 
	 }

//	 @PostMapping("/{membershipCode}")
//		public String membershipPage(@PathVariable("membershipCode") Integer membershipCode,MemberListDTO member, Model model,HttpServletRequest request) {
//			model.addAttribute("main",service.main(membershipCode));
//			model.addAttribute("membershipUserCount", service.membershipUserCount(membershipCode));
//			HttpSession session = request.getSession();
//			Member mem = (Member) session.getAttribute("mem");
//			if(mem != null) {
//				System.out.println("시작" + member);
//				member.setId(mem.getId());
//				System.out.println("삽입후" +member);
//			
//			model.addAttribute("checkMember" , service.checkMember(member));
//				
//			}
//			
//			
//			return "membership/membershipPage";
//		}
	@GetMapping("/makeMembership")
	public String makeMembership() {
		return "/mypage/makeMembership";
	}

	@PostMapping("/makeMembership")
	public String makeMembership(MembershipDTO dto) {
		Membership membership = Membership.builder()
				.membershipName(dto.getMembershipName())
				.membershipInfo(dto.getMembershipInfo())
				.membershipMax(Integer.parseInt(dto.getMembershipMax())
						).build();
		
		service.makeMembership(membership);

		MemberListDTO list = new MemberListDTO();
				list.setId(dto.getId());
				list.setListGrade(dto.getListGrade());
				list.setMembershipCode(membership.getMembershipCode());
		
		service.host(list);

		return "redirect:/";
	}

	@PostMapping("/membershipApply")
	public String membershipApply(MemberListDTO member) {
		service.membershipApply(member);

		return "redirect:/";
	}

}
