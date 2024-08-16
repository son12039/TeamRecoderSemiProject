package com.damoim.controller;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.damoim.model.vo.Membership;
import org.springframework.web.bind.annotation.PostMapping;
import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.dto.MembershipDTO;
import com.damoim.model.dto.MembershipTypeDTO;
import com.damoim.service.MembershipService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.MembershipUserList;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.RequestBody;
@Controller
public class MembershipController {
	// 클럽 생성 관련 컨트롤
	@Autowired
	private MembershipService service;
	/*
	 * 
	 * */
	@GetMapping("/createclub")
	public String createclub(){
		return "mypage/createclub";
		
	}
	/*
	 * 
	 * */
	@PostMapping("/createclub")
	public String createclub(Membership membership) {
		System.out.println(membership);
		membership.setMembershipInfo(null);
     return "redirect:/"; // 클럽 생성 후 인덱스 페이지로 리다이렉션
}	
	
	
	
	

	/*
	 * 성일
	 * 
	 * 
	 * */
	@GetMapping("/{membershipCode}") // 클럽 홍보 페이지 각각 맞춰 갈수있는거
	public String main(@PathVariable("membershipCode") Integer membershipCode, MemberListDTO memberListDTO, Model model,
			HttpServletRequest request) {
		System.out.println(service.main(membershipCode).getListCode());
		// 홍보페이지에 membership 관련 정보 + 호스트 정보
		model.addAttribute("main", service.main(membershipCode));
		// 현재 가입된 인원수
		model.addAttribute("membershipUserCount", service.membershipUserCount(membershipCode));
		HttpSession session = request.getSession();
		// 로그인한 회원의 id 정보 가져오기 위함
		Member mem = (Member) session.getAttribute("mem");
		if (mem != null) { // 로그인 유무 확인 . 널포인트 에러 방지
			// 가입한 클럽 인지 확인을 위한 아이디 정보 가져오기
			memberListDTO.setId(mem.getId());
			// 해당클럽 안에서의 등급 가져오기
			System.out.println("checkMember : " + service.checkMember(memberListDTO));
			model.addAttribute("checkMember", service.checkMember(memberListDTO));
		}
		return "mainboard/main";
	}
	/*
	  * 성철
	  * 해당 클럽에 가입된 회원이 그클럽에 정보와 클럽 가입 현황 볼수있는 페이지 이동
	  * */
	 @GetMapping("/club/{membershipCode}") // 클럽 페이지 이동
		public String membershipPage(@PathVariable("membershipCode") Integer membershipCode,MemberListDTO memberListDTO, Model model,HttpServletRequest request) {
		 	// 클럽 페이지에 membership 관련 정보 + 호스트 정보
		 	model.addAttribute("main",service.main(membershipCode));
		 	// 현재 가입된 인원수
			model.addAttribute("membershipUserCount", service.membershipUserCount(membershipCode));
			// 로그인된 회원 정보		
			List<MembershipUserList> list = service.MembershipAllInfo(membershipCode);
			// 해당클럽 모든 유저 정보 불러오기
			model.addAttribute("allMember" , service.MembershipAllInfo(membershipCode));
			return "membership/membershipPage";
		}
	 /*
	  * 성철
	  * 일단 클럽 호스트가 가입 승인대기인원 -> 일반 회원으로 바꾸는기능 
	  * */
	 @ResponseBody
	 @PostMapping("/agreeMember") // 클럽 회원가입 승인
	 public void agreeMemeber(MemberListDTO member) {
		 // 일단은 호스트일때만 클럽 회원 승인기능
		 System.out.println("맴버 잘왔나? : " + member);
		 service.agreeMemeber(member);
		System.out.println("승인");
		
		
	 }
	
	/*
	 * 
	 * */
	@GetMapping("/makeMembership") // 클럽 생성페이지로 이동
	public String makeMembership() {
		return "mypage/makeMembership";
	}
	/*
	 * 
	 * */
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
	 /*
	  * 성철
	  * 세션에 맴버가 해당 클럽에 가입 X 상황일시 신청가능한 메서드
	  * */
	@PostMapping("/membershipApply") // 클럽 회원가입 신청
	public String membershipApply(MemberListDTO member) {
		// 클럽 가입 신청
		service.membershipApply(member);
		return "redirect:/";
	}
	
	@ResponseBody
	@GetMapping("/type")
	public List<MembershipTypeDTO> typeCheck(@RequestParam String value) {

	   System.out.println(value);
		
		List<MembershipTypeDTO> dtoList = new ArrayList();
		
		
		service.typeCheck(value);
		System.out.println(service.typeCheck(value));
		// 출력된 결과를 DTO에 추가함 
		// 각 멤버쉽 코드를 추출해 반복문으로 카운트를 돌린후 DTO에 추가함 
		// 각 DTO들을 DTO 리스트에 넣어서 리턴 
		for(int i=0; i<service.typeCheck(value).size(); i++) {
			MembershipTypeDTO dto = new MembershipTypeDTO();
		 int asd =	service.typeCheck(value).get(i).getMembership().getMembershipCode();
	            dto.setMembership(service.typeCheck(value).get(i).getMembership());
	            dto.setCount(service.typeCount(asd));
	            dto.setMember(service.typeHost(asd).getMember());
	            dtoList.add(dto);
	            System.out.println("typeHost : "+ service.typeHost(asd).getMember().getName());
	            
		}
		
		
		System.out.println(dtoList);
	//	System.out.println(service.typeCheck(value).get(0).getMembership().getMembershipCode());
		
		return dtoList;
	}
	
	
	

}
