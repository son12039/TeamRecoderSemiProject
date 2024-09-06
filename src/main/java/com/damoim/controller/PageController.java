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
	private MembershipMeetingService meetService;
	
	@Autowired
	private LocationTypeService locTypeService;
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
		
		
		
		
		ArrayList<MembershipUserList> membershipList = (ArrayList<MembershipUserList>) infoService.selectMemberUserList(member.getId());
		model.addAttribute("list", membershipList);
		
		List<MembershipUserList> list = new ArrayList<MembershipUserList>();
		for (MemberListDTO m : member.getMemberListDTO()) {
			list.add((MembershipUserList) infoService.main(m.getMembershipCode()));
		}
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setCount(list.get(i).getListCode());
		}
		// 내 등급별 클럽
		model.addAttribute("membership", list);
		return "mypage/mypage";
	}
	
	// 개인 유저 페이지
	@GetMapping("/user")
	public String user() {
		return "member/user";
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
	
		
		if(member == null) {
			return "error";
		};
		
		int code = -1;
		for(int i=0; i<member.getMemberListDTO().size(); i++) {
			if(member.getMemberListDTO().get(i).getListGrade().equals("host")) {
				 code = member.getMemberListDTO().get(i).getMembershipCode();
			}
			
		}
		
		locTypeService.locationList(code);
		locTypeService.typeList(code);
		
		String loc = locTypeService.locationList(code).get(0).getLocLaName()+" =";
		String type= locTypeService.typeList(code).get(0).getTypeLaName()+" ="; 
		
		
		for(int i=0; i<locTypeService.locationList(code).size(); i++) {
		    if(i != locTypeService.locationList(code).size() -1)
			loc +=" "+locTypeService.locationList(code).get(i).getLocSName()+",";
		    else {
		    	loc +=" "+locTypeService.locationList(code).get(i).getLocSName();
		    }
			
		}
		
		for(int i=0; i<locTypeService.typeList(code).size(); i++) {
		    if(i != locTypeService.typeList(code).size() -1)
			type +=" "+locTypeService.typeList(code).get(i).getTypeSName()+",";
		    else {
		    	type +=" "+locTypeService.typeList(code).get(i).getTypeSName();
		    }
			
		}
		
		model.addAttribute("locLaNameList", locTypeService.locLaNameList());
		model.addAttribute("typeLaNameList", locTypeService.typeLaNameList());
		
		model.addAttribute("type", type);
		model.addAttribute("locList", loc);
		System.out.println("이게멀까? " + locTypeService.locationList(code));
		
	   model.addAttribute("membership", infoService.selectMembership(code));
	   model.addAttribute("count" , infoService.membershipUserCount(code));
	   //model.addAllAttributes("location",infoService.);
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
	 
  // 카카오맵 이동
	 @GetMapping("/kakaoMap")
	 public String kakaoMap() {
		 
		 return "kakaoMap";
	 }
	 
	 @GetMapping("/loginFail") 
	 public String loginFail() {
		 return "login/loginFail";
	 }

	 @GetMapping("/makeTest") // 클럽 생성페이지로 이동
		public String makeMembership(SearchDTO search, Model model) {
			model.addAttribute("locLaNameList", locTypeService.locLaNameList());
			model.addAttribute("typeLaNameList", locTypeService.typeLaNameList());
			return "makeTest";
		}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}









