package com.damoim.controller;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.tags.shaded.org.apache.bcel.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.dto.MembershipDTO;
import com.damoim.model.dto.SearchDTO;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.MembershipUserList;
import com.damoim.model.vo.Paging;
import com.damoim.service.LocationTypeService;
import com.damoim.service.MembershipMeetingService;
import com.damoim.service.MembershipService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
@Controller
public class PageController {
	

	@Autowired
	private MembershipService infoService; // 맴버쉽 서비스
	
	@Autowired
	private MembershipMeetingService meetService; // 미팅 관련 서비스
	
	@Autowired
	private LocationTypeService locTypeService; // 로케이션 타입
	/*
	 * 성일
	 * 인덱스에 현재 호스트가 존재하는 모든 클럽들 모두 출력
	 * */
	// 08-20 이식을 위한 잠금

	/*
	 * 성철
	 * 회원가입 페이지 이동 
	 * (나중에 추가 가능하면 휴대전화 api랑 나이 생년월일 선택으로 자동계산 반환, 승인버튼 버튼색 조건되야 변경기능)
	 * */
	
	@GetMapping("/signUp")
	public String signUp() {
		return "signUp/signUp";
	}

	
	/*
	 * 성철 내가 가입한 클럽을 가입된, 관리자or호스트, 가입대기중 클럽 조회가능한 페이지이동
	 */
	
    // 기본 정보 수정
	@GetMapping("/mypage")
	public String mypage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member member = (Member) authentication.getPrincipal();
		
		
		String id = member.getId();
		
		model.addAttribute("meetings", meetService.allMeetings1(id));
		
		
		
		
		ArrayList<MembershipUserList> membershipList = (ArrayList<MembershipUserList>) infoService.selectMemberUserList(id);
		for(MembershipUserList li : membershipList) {
			 li.setCount(infoService.membershipUserCount(li.getMembership().getMembershipCode()));
		}
		model.addAttribute("mypage", membershipList);
		
		return "mypage/mypage";
	}
	
	
	
	
	
	
	
	
	

	
	// 내 중요 정보 수정
	@GetMapping("/updateMemberInfo")
	public String updateCheck() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member member = (Member) authentication.getPrincipal();
		if(member == null) {
			return "error";
		}
		return "mypage/updateMemberInfo";
	}

	// 멤버쉽 정보 수정 ????????????????
	@GetMapping("/updateMembership")
	public String updateMembership(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member member = (Member) authentication.getPrincipal();
	
		
		int code = -1;
		for(int i=0; i<member.getMemberListDTO().size(); i++) {
			if(member.getMemberListDTO().get(i).getListGrade().equals("host")) {
				 code = member.getMemberListDTO().get(i).getMembershipCode();
				 break;
			}
		}
		model.addAttribute("membership", infoService.selectMembership(code));
		model.addAttribute("count", infoService.membershipUserCount(code));
		model.addAttribute("locLaNameList", locTypeService.locLaNameList());
		model.addAttribute("typeLaNameList", locTypeService.typeLaNameList());
		model.addAttribute("locButton", infoService.locButton(code));
		model.addAttribute("typeButton", infoService.typeButton(code));
		return "membership/updateMembership";
	}

	
	// 회원탈퇴 페이지 이동
	@GetMapping("/memberDelete")
	public String memberDelete(Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem = (Member) authentication.getPrincipal();

		if(mem == null) {
			return "error";
		}
		int num = 0;
		for(MemberListDTO m : mem.getMemberListDTO()) {
			if(m.getListGrade().equals("host"))
				num = m.getMembershipCode();
		}
		// 호스트인 클럽을 담음 호스트인게 없으면 null담김
		model.addAttribute("list", infoService.selectMembership(num));
		return "mypage/memberDelete";
	}
	
	// 내가 가입한 맴버쉽 페이지 이동
	/*
	 * 성일
	 * 로그인 페이지
	 * */
	 @GetMapping("/loginPage")
	 public String loginPage() {
	 	return "login/loginPage";
	 }
	 /* 성철
	  * 아이디 , 비밀번호 찾기 기능
	  * 아이디 찾기 미구현
	  * 비밀번호는 이메일로 임시비밀번호 발송
	  * */
	 @GetMapping("/findPassword")
	 public String findPassword() {
	 	return "login/findPassword";
	 }
	 
	 @GetMapping("/findId")
	 public String findId() {
	 	return "login/findId";
	 }
	 

	 
	 @GetMapping("/loginFail") 
	 public String loginFail() {
		 return "login/loginFail";
	 }


	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}









