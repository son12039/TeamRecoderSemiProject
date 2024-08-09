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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class MembershipController {

	@Autowired
	private MembershipService service;
	
	 @GetMapping("/{membershipCode}") // 클럽 홍보 페이지 각각 맞춰 갈수있는거
		public String main(@PathVariable("membershipCode") Integer membershipCode,MemberListDTO member, Model model,HttpServletRequest request) {
		 
		 System.out.println(service.main(membershipCode).getListCode());
		 	// 홍보페이지에 membership 관련 정보 + 호스트 정보
			model.addAttribute("main",service.main(membershipCode));
			// 현재 가입된 인원수
			model.addAttribute("membershipUserCount", service.membershipUserCount(membershipCode));
			HttpSession session = request.getSession();
			
			Member mem = (Member) session.getAttribute("mem");
			
			if(mem != null) {
				System.out.println("시작" + member);
				member.setId(mem.getId());
				System.out.println("삽입후" +member);
			// 해당클럽 가입된 사람인가 아닌가 확인
			model.addAttribute("checkMember" , service.checkMember(member));
				
			}
			
			
			return "mainboard/main";
		}
	 
	 @GetMapping("/{membershipCode}club") // 클럽 페이지 이동
		public String membershipPage(@PathVariable("membershipCode") Integer membershipCode,MemberListDTO member, Model model,HttpServletRequest request) {
		 	// 클럽 페이지에 membership 관련 정보 + 호스트 정보
		 	model.addAttribute("main",service.main(membershipCode));
		 	// 현재 가입된 인원수
			model.addAttribute("membershipUserCount", service.membershipUserCount(membershipCode));
			// 로그인된 회원 정보
			HttpSession session = request.getSession();
			Member mem = (Member) session.getAttribute("mem");
			if(mem != null) {
				System.out.println("시작" + member);
				member.setId(mem.getId());
				System.out.println("삽입후" +member);
			// 해당클럽 가입여부 확인
			model.addAttribute("checkMember" , service.checkMember(member));
				
			}
			
			List<MembershipUserList> list = service.MembershipAllInfo(membershipCode);
			System.out.println(list.get(0));
			// 해당클럽 모든 유저 정보 불러오기
			model.addAttribute("allMember" , service.MembershipAllInfo(membershipCode));
			return "membership/membershipPage";
		}
	 
	 @PostMapping("/agreeMember") // 클럽 회원가입 승인
	 public void agreeMemeber(MemberListDTO member) {
		 // 일단은 호스트일때만 클럽 회원 승인기능
		 service.agreeMemeber(member);
		System.out.println("승인");
		
		
	 }
	 
	 
	@GetMapping("/makeMembership") // 클럽 생성페이지로 이동
	public String makeMembership() {
		return "/mypage/makeMembership";
	}
	
	@PostMapping("/makeMembership") // 클럽 생성
	public String makeMembership(MembershipDTO dto) {
		Membership membership = Membership.builder()
				.membershipName(dto.getMembershipName())
				.membershipInfo(dto.getMembershipInfo())
				.membershipMax(Integer.parseInt(dto.getMembershipMax())
						).build();
		// 클럽생성?
		service.makeMembership(membership);

		MemberListDTO list = new MemberListDTO();
				list.setId(dto.getId());
				list.setListGrade(dto.getListGrade());
				list.setMembershipCode(membership.getMembershipCode());
		// 호스트로 보유중인 클럽 유무 확인
		service.host(list);

		return "redirect:/";
	}

	@PostMapping("/membershipApply") // 클럽 회원가입 신청
	public String membershipApply(MemberListDTO member) {
		// 클럽 가입 신청 
		service.membershipApply(member);

		return "redirect:/";
	}

}
