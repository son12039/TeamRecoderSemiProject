package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.damoim.model.dto.MemberInfoDTO;
import com.damoim.model.vo.Member;
import com.damoim.service.MemberService;
import com.damoim.service.MembershipService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	@Autowired
	private MemberService service;
	
	@Autowired
	private MembershipService infoService;
	
	@PostMapping("/login")
	public String login(MemberInfoDTO info,HttpServletRequest request) {
		HttpSession session = request.getSession();
		Member member = new Member();
		member.setId(info.getId());		
		member.setPwd(info.getPwd());
		
		
		System.out.println(service.login(member));
		
		System.out.println( infoService.grade(member));
		
		session.setAttribute("info", infoService.grade(member));
		
		session.setAttribute("mem", service.login(member));
		return "redirect:/";
		
	}
	@PostMapping("/idCheck")
	public String idCheck(Member member,Model model) {
		boolean idResult = false;
		Member mem = service.idCheck(member);
		if(mem == null) {
			idResult = true;
			model.addAttribute("idResult", idResult);
		}else {
			model.addAttribute("idResult", idResult);
		}
		return "signUp/signUp";
		
	}
	@PostMapping("/nicknameCheck")
	public String nicknameCheck(Member member,Model model) {
		boolean nicknameResult = false;
		Member mem = service.idCheck(member);
		if(mem == null) {
			nicknameResult = true;
			model.addAttribute("idResult", nicknameResult);
		}else {
			model.addAttribute("idResult", nicknameResult);
		}
		return "signUp/signUp";
	}

	@PostMapping("/signUp")
	public String signUp(Member member) {
		
		System.out.println(member);
		
		service.signUp(member);
		
		
		return "redirect:/";
		
	}

	@GetMapping("/logout")
    public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}
	
	
	@PostMapping("/pwdCheck")
	public String pwdCheck(Member member, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.setAttribute("check", service.pwdCheck(member));
		
		
		return "/mypage/mypage";
	}
	
	@GetMapping("/pwdCheck")
	public String page() {
		
		return  "/mypage/mypage";
	}

	@PostMapping("/update")
	public String update(Member member, HttpServletRequest request) {
		
       HttpSession session = request.getSession();
		 
		Member member2 = (Member) session.getAttribute("mem"); // 2차 인증시 생성된 세션 
		

		 if(member.getId() == null )  member.setId(member2.getId());	
		 
		 
		 service.update(member);
	  System.out.println(member);
	  
	  session.setAttribute("mem",member);
		
		return "redirect:/pwdCheck";
	}
	
	@GetMapping("/myMembership")
	public String myMembership(MemberInfoDTO info, Model model) {
		Member member = new Member();
		member.setId(info.getId());
		System.out.println(info.getId());
		System.out.println(member);
		
		
		model.addAttribute("membership", infoService.grade(member));
		
		return "/mypage/myMembership";
	}
	

}
