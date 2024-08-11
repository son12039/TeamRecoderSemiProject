package com.damoim.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.damoim.model.dto.MemberInfoDTO;
import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.vo.Member;
import com.damoim.service.MemberService;
import com.damoim.service.MembershipService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	int count = 0;

	@Autowired
	private MemberService service;
	private MembershipService infoService;

	
	
	

	// 로그인 , 해당 회원 정보 , 가입 클럽 코드 및 등급을 세션에 
		@ResponseBody
		@PostMapping("/login")
		public boolean login(Member member, HttpServletRequest request, Model model) {
			HttpSession session = request.getSession();
			// 로그인 성공 !
			if (service.login(member) != null) {
				
				session.setAttribute("mem", service.login(member)); // 로그인 정보 세션에
				// 내가 가입한 클럽 정보 체크용
		

				// 해당 id를 가진 맴버의 맴버쉽 의 모든정보 + 맴버, 등급 등등
				System.out.println(infoService.grade(member));
				session.setAttribute("membership", infoService.grade(member)); 

				return true;
				// 로그인 실패!
			} 
				return false;			

		}
	
		// 로그인 성공 !
	if(service.login(member) != null) {
		
		// service에는 listGrade 정보 없음
//		System.out.println(service.login(member));
		// infoService는 listGrade 정보 담음
//		System.out.println(infoService.grade(member));
			
			session.setAttribute("mem", service.login(member));
//			session.setAttribute("info", infoService.grade(member));
			
	        ArrayList<MemberListDTO> membershipList = service.loginMemberMembership(member);
	
	   
	        for (MemberListDTO i : membershipList) {
	            System.out.println("리스트 그레이드 : " + i);
        }
              
        count =0;
        session.setAttribute("membershipList", membershipList);
        
        session.setAttribute("loginCheck", check);
    
        
        return "redirect:/";
        
   // 로그인 실패!     
   } else {
		/*
		 * if(count < 5) count++; check =false; session.setAttribute("loginCheck",
		 * check); session.setAttribute("count", count);
		 */
	  check= false; 
	session.setAttribute("result", check);
	
	return "login/login";
}




	}
	
	@ResponseBody
	@PostMapping("/idCheck")
	public boolean idCheck(Member member) {
		Member mem = service.idCheck(member);
		return mem == null;
		
	}
	@ResponseBody
	@PostMapping("/nicknameCheck") // 회원가입시 닉네임 중복 체크 
	public boolean nicknameCheck(Member member) {
		Member mem = service.nicknameCheck(member);
		return mem == null;
			
	}

	@PostMapping("/signUp") // 회원가입 메서드
	public String signUp(Member member) {
		
		System.out.println(member);
		
		service.signUp(member);
		
		return "redirect:/";
		
	}

	@GetMapping("/logout") // 로그아웃 메서드
    public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}
	
	
	@PostMapping("/pwdCheck") // 아마 비밀번호 체크용 미리?
	public String pwdCheck(Member member, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.setAttribute("check", service.pwdCheck(member));
		
		return "/mypage/update";
	}
	
	
	@ResponseBody
	@PostMapping("/update") 
	public String update(Member member, HttpServletRequest request) {

		HttpSession session = request.getSession();
		Member member2 = (Member) session.getAttribute("mem"); // 2차 인증시 생성된 세션
		
		if (member.getId() == null)
			member.setId(member2.getId());
		
		Member memCheck = (Member) session.getAttribute("mem");
		session.setAttribute("mem", member);
		service.update(member);
		
		System.out.println(member.getPwd()); // 1234 (수정할 비밀번호)
		System.out.println(member2.getPwd()); // 123 (원래 비밀번호)
		
		if (member.getPwd().equals(member2.getPwd())) {
			// 수정할 비밀번호가 같은경우
			return "/mypage/update_fail";
		} else {
			// 수정할 비밀번호가 다른경우 수정
			service.update(member);
			memCheck = (Member) session.getAttribute("mem");
			session.setAttribute("mem", member);
			return "/mypage/update_ok";
		}


	}
	
	@GetMapping("/myMembership") // 내가 가입한 클럽확인
	public String myMembership(Member member, Model model) {
		
		// 내 등급별 클럽
		model.addAttribute("membership", infoService.grade(member));
		
		return "/mypage/myMembership";
	}
	
	
	
		
	
	}
	


