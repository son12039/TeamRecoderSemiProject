package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@Autowired
	private MembershipService infoService;
	
	
	

	// 로그인 , 해당 회원 정보 , 가입 클럽 코드 및 등급을 세션에 
		@ResponseBody
		@PostMapping("/login")
		public boolean login(Member member, HttpServletRequest request, Model model) {
			HttpSession session = request.getSession();
			// 로그인 성공 !
			if (service.login(member) != null) {
				
				session.setAttribute("mem", service.login(member)); 
				// 로그인 정보 세션에
				// 내가 가입한 클럽 정보 체크용
		

				// 해당 id를 가진 맴버의 맴버쉽 의 모든정보 + 맴버, 등급 등등
				System.out.println(infoService.grade(member));
				session.setAttribute("membership", infoService.grade(member)); 

				return true;
				// 로그인 실패!
			} 
				return false;			

		}
	
	// *** 회원가입 관련
		
	// 회원가입 관련 아이디 중복 체크용 
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
		service.signUp(member);	
		return "redirect:/";
		
	}
	
	@GetMapping("/logout") // 로그아웃 메서드
    public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/myMembership") // 내가 가입한 클럽확인
	public String myMembership(Member member, Model model) {
		
		// 내 등급별 클럽
		model.addAttribute("membership", infoService.grade(member));
		
		return "/mypage/myMembership";
	}
	
	@PostMapping("/update")
	public String update(Member vo, Model model, HttpServletRequest request, String beforeAddr, String addrDetail) {
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("mem");
		// 현재 저장된 주소
		String addr = "";
		String con = "#";
//		String beforeAddr = vo.getAddr();
//		System.out.println("현재 저장된 주소 : " + beforeAddr);
		if(addrDetail != "") {
			Member memberAddr = new Member();
//			memberAddr += beforeAddr();

			
			
		}


////		 #을 기준으로 앞쪽 addr이랑 뒤쪽 addr를 나누고
		String[] addr = beforeAddr.split("#");
		for(String a : addr) {
			System.out.println("addr값" + a);
		}

		

		// 만약에 앞뒤 따로 비교하는데 앞 addr이 변경된 값이 x고 뒤만 
		
		
		// 변경되면 기존에 member에 앞 addr + 새로받은 addrD를 추가해서 셋하고
//		if (addrDetail != "") {
//			vo.setAddr(member.getAddr());
//			vo.setAddr(addrDetail);
//			service.update(vo);
//			addr += '#' + (vo.getAddr());
//			
//		}
		
//		System.out.println(vo.getAddr() + vo.getId() + vo.getPwd());
		
		return "redirect:/";
	}
	
	
	
	
	
	}
	




