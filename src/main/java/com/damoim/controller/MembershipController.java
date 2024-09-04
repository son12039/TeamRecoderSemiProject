package com.damoim.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.apache.jasper.tagplugins.jstl.core.Remove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.damoim.model.vo.Membership;
import org.springframework.web.bind.annotation.PostMapping;

import com.damoim.model.dto.CommentDTO;
import com.damoim.model.dto.GradeDTO;
import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.dto.MemberLocTypeDTO;
import com.damoim.model.dto.MembershipDTO;
import com.damoim.model.dto.MembershipTypeDTO;
import com.damoim.service.MembershipMeetingService;
import com.damoim.model.dto.SearchDTO;
import com.damoim.service.LocationTypeService;
import com.damoim.service.MainCommentService;
import com.damoim.service.MembershipService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.damoim.model.vo.MainComment;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.MembershipUserList;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MembershipController {
	// 클럽 생성 관련 컨트롤
	@Autowired
	private MembershipService service;
	
	// 댓글관련 서비스
	@Autowired
	private MainCommentService commentService;

	// 08-22 채승훈 클럽메인페이지에 지역과 타입 추가
	@Autowired
	private LocationTypeService locationTypeService;
	
	
	
	

	@Autowired
	private  MembershipMeetingService meetingService;
	/*
	 * 
	 * */
	/*
	 * 
	 * */
	@GetMapping("/createclub")
	public String createclub() {
		return "mypage/createclub";

	}
	
	@PostMapping("/createclub")
	public String createclub(Membership membership) {
		membership.setMembershipInfo(null);
		return "redirect:/"; // 클럽 생성 후 인덱스 페이지로 리다이렉션
	}

	/*
	 * 성일 카운트 관련 VO에 합쳐버림 성철 댓글 대댓글 글 관련 로직 추가
	 */

	@GetMapping("/{membershipCode}") // 클럽 홍보 페이지 각각 맞춰 갈수있는거
	public String main(@PathVariable("membershipCode") Integer membershipCode, MemberListDTO memberListDTO,
			Model model) {
		// 홍보페이지에 membership 관련 정보 + 호스트 정보
		System.out.println(membershipCode);
		MembershipUserList list = service.main(membershipCode);
		list.setCount((service.membershipUserCount(membershipCode)));

		model.addAttribute("main", list);  
		model.addAttribute("allMember", service.MembershipAllRegular(membershipCode));

		ArrayList<MainComment> commList = commentService.allMainComment(membershipCode); // 일반댓글
		ArrayList<CommentDTO> dtoList = new ArrayList<CommentDTO>(); // 합칠예정
		

		for (int i = 0; i < commList.size(); i++) {
			CommentDTO commentDTO = new CommentDTO().builder().mainCommentCode(commList.get(i).getMainCommentCode())
					.mainCommentText(commList.get(i).getMainCommentText())
					.mainCommentDate(commList.get(i).getMainCommentDate()).id(commList.get(i).getMember().getId())
					.nickname(commList.get(i).getMember().getNickname())
					.memberImg(commList.get(i).getMember().getMemberImg())
					.membershipCode(commList.get(i).getMembershipCode()).recoment(new ArrayList<>()).build();

			dtoList.add(commentDTO);
			ArrayList<MainComment> recommList = commentService.mainReComment(membershipCode,
					commentDTO.getMainCommentCode());
			if (recommList.size() > 0) {
				for (int j = 0; j < recommList.size(); j++) {
					CommentDTO recommentDTO = new CommentDTO().builder()
							.mainCommentCode(recommList.get(j).getMainCommentCode())
							.mainCommentText(recommList.get(j).getMainCommentText())
							.mainCommentDate(recommList.get(j).getMainCommentDate())
							.id(recommList.get(j).getMember().getId())
							.nickname(recommList.get(j).getMember().getNickname())
							.memberImg(recommList.get(j).getMember().getMemberImg())
							.membershipCode(recommList.get(j).getMembershipCode())
							.mainParentsCommentCode(commList.get(i).getMainCommentCode()).build();

					commentDTO.getRecoment().add(recommentDTO);
				}

			}
		}

		model.addAttribute("comment", dtoList);
		System.out.println("커맨트 dio " + dtoList);
		// 08-22 채승훈 클럽페이지 에 로케이션 타입 정보 추가
		model.addAttribute("location", locationTypeService.locationList(membershipCode));
		model.addAttribute("type", locationTypeService.typeList(membershipCode));

		return "mainboard/main";
	}

	/*
	 * 성철 해당 클럽에 가입된 회원이 그클럽에 정보와 클럽 가입 현황 볼수있는 페이지 이동
	 */
	@GetMapping("/club/{membershipCode}") // 클럽 페이지 이동
	public String membershipPage(@PathVariable("membershipCode") Integer membershipCode, MemberListDTO memberListDTO,
			Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Member mem = (Member) authentication.getPrincipal();
		
		boolean ck = false;
		
		for(MemberListDTO dto: mem.getMemberListDTO()) {
			if(dto.getMembershipCode() == membershipCode && !dto.getListGrade().equals("guest")) {
				ck = true;
			}
		}
		if(!ck) {
			System.out.println("일단함 확인");
			return "error";
		}

		
		System.out.println("로그인 정보 " + mem);
		// 해당클럽 정보 다담음
		System.out.println(membershipCode);
		MembershipUserList list = service.main(membershipCode);
		list.setCount((service.membershipUserCount(membershipCode)));
		// 호스트정보
		model.addAttribute("main", list);
		// 해당클럽에 가입신청된 모든 유저정보

		// 0903 성일 어차피 승인은 멤버관리 페이지에서 하기 때문에 모든 인원을 알려주는거 보다 regular 이상 회원만 jsp로 전달
		// model.addAttribute("allMember" , service.MembershipAllInfo(membershipCode));
		
		model.addAttribute("allMember", service.MembershipAllRegular(membershipCode));
		model.addAttribute("adminList", service.adminUser(membershipCode));

		model.addAttribute("allmeet", meetingService.allMeetings(membershipCode));
		System.out.println(meetingService.allMeetings(membershipCode));

		// 08-22 채승훈 클럽페이지 에 로케이션 타입 정보 추가
		model.addAttribute("location", locationTypeService.locationList(membershipCode));
		model.addAttribute("type", locationTypeService.typeList(membershipCode));

		return "membership/membershipPage";
	}

	/*
	 * 성철 일단 클럽 호스트가 가입 승인대기인원 -> 일반 회원으로 바꾸는기능
	 */
	@ResponseBody
	@PostMapping("/agreeMember") // 클럽 회원가입 승인
	public void agreeMemeber(MemberListDTO member) {
		// 일단은 호스트일때만 클럽 회원 승인기능
		System.out.println("어그리멤버");
		service.agreeMemeber(member);
	}

	/*
	 * 
	 * */
	@GetMapping("/makeMembership") // 클럽 생성페이지로 이동
	public String makeMembership() {
		return "mypage/makeMembership";
	}

	/*
	 * ???
	 * 
	 * 
	 * 
	 * 성철 만들어진거에 사진첨부만 추가
	 */
	@PostMapping("/makeMembership") // 클럽 생성
	public String makeMembership(MembershipDTO dto, MultipartFile file) throws IOException {
		Membership membership = Membership.builder().membershipName(dto.getMembershipName())
				.membershipInfo(dto.getMembershipInfo()).membershipMax(Integer.parseInt(dto.getMembershipMax()))
				.build();
		// 클럽생성?
		service.makeMembership(membership);
		Path directoryPath = Paths.get("\\\\\\\\192.168.10.51\\\\damoim\\\\membership\\"
				+ Integer.toString(membership.getMembershipCode()) + "\\");
		Files.createDirectories(directoryPath);
		Membership m = Membership.builder().membershipCode(membership.getMembershipCode())
				.membershipImg(fileUpload(file, membership.getMembershipCode())).build();
		System.out.println("해당 맴버쉽 코드 : " + m.getMembershipCode());
		System.out.println("이미지 URL 테스트 " + m.getMembershipImg());
		service.membershipImg(m);
		// 멤버쉽 유저 리스트에 등록 절차
		MemberListDTO list = new MemberListDTO();
		list.setId(dto.getId());
		list.setListGrade(dto.getListGrade());
		list.setMembershipCode(membership.getMembershipCode());
		// 호스트로 보유중인 클럽 유무 확인
		service.host(list);
		return "redirect:/";
	}

	/*
	 * 성철 세션에 맴버가 해당 클럽에 가입 X 상황일시 신청가능한 메서드
	 */
	@PostMapping("/membershipApply") // 클럽 회원가입 신청
	public String membershipApply(MemberListDTO member) {
		// 클럽 가입 신청
		service.membershipApply(member);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem = (Member) authentication.getPrincipal();
		ArrayList<MemberListDTO> list = (ArrayList<MemberListDTO>) mem.getMemberListDTO();
		list.add(member);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return "redirect:/" + member.getMembershipCode();
	}
	
	/*
	 * 성철
	 * 가입 탈퇴 , 신청취소
	 * */
	@ResponseBody
	@PostMapping("/deleteList")
	public void deleteList(int membershipCode) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem = (Member) authentication.getPrincipal();
		service.deleteList(new MemberListDTO().builder()
				.id(mem.getId()).membershipCode(membershipCode).build());
		
		ArrayList<MemberListDTO> list = (ArrayList<MemberListDTO>) mem.getMemberListDTO();
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getMembershipCode() == membershipCode) {
				System.out.println("삭제될 DTO : " + list.get(i));
				list.remove(i);
				break;
				
			}
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("세션변경도 완료");
	}

	/*
	 * 성철 : 클럽 홍보글 작성 페이지
	 */

	@GetMapping("/club/{membershipCode}/membershipPromotionDetail")
	public String membershipPromotionDetail(@PathVariable("membershipCode") Integer membershipCode, Model model) {
	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member m1 = (Member) authentication.getPrincipal();
	
		boolean check = false;
		
		// 권한 체크 
 for(int i=0; i < m1.getMemberListDTO().size(); i++) {
	 if(m1.getMemberListDTO().get(i).getMembershipCode() == membershipCode && !(m1.getMemberListDTO().get(i).getListGrade().equals("guest") || m1.getMemberListDTO().get(i).getListGrade().equals("regular"))) {
		 
		 check = true;
	 }
	 
 }

		if(!check) {
			
			return "error";
		}
		
		
		
		
		model.addAttribute("memInfo", service.selectMembership(membershipCode));
		model.addAttribute("code", membershipCode);
		return "membership/membershipPromotionDetail";
	}

	/*
	 * 성철 : 클럽 홍보글 작성 페이지
	 */
	@ResponseBody
	@PostMapping("/membershipInfoUpdate")
	public void test(int membershipCode, String test) {
		System.out.println("맴버쉽 코드 : " + membershipCode);
		System.out.println("테스트 : " + test);
		Membership membership = new Membership().builder().membershipCode(membershipCode).membershipInfo(test).build();
		service.updateMembershipInfo(membership);
		System.out.println("DB 통과");
	}

	/*
	 * 성철 파일 삽입 메서드 해당맴버쉽 프로필사진 !!
	 * 
	 */
	public String fileUpload(MultipartFile file, int code) throws IllegalStateException, IOException {
		if (file.getOriginalFilename() == "") {
			System.out.println("NULL 리턴");
			return null;
		}
		UUID uuid = UUID.randomUUID(); // 랜덤 파일명 부여
		String fileName = uuid.toString() + "_" + file.getOriginalFilename();
		File copyFile = new File("\\\\192.168.10.51\\damoim\\membership\\" + Integer.toString(code) + "\\" + fileName);
		file.transferTo(copyFile);
		System.out.println("파일1개 추가!");
		return fileName;
	}

	/*
	 * 성철 파일 삭제 메서드 해당유저 프로필사진 변경시 사용!! 실 사용때는 조건에 만약 보내준 링크가 null이면 변하지 않도록
	 */
	public void fileDelete(String file, int code) throws IllegalStateException, IOException {
		if (file == null) {
			System.out.println("삭제할 파일이 없습니다");
		} else {
			System.out.println("삭제될 URL : " + file);
			File f = new File("\\\\192.168.10.51\\damoim\\membership\\" + Integer.toString(code) + "\\" + file);
			f.delete();

		}

	}
	
	
	/* 성일
	 * 어드민이나 호스트이냐 따라서 서로다른 맴버쉽 관리 페이지 이동처리
	 * */

	/*
	 * 멤버관리 페이지 호스트와 관리자만 접속 가능 등급 설정 및 회원 강퇴 기능 구현
	 * 
	 */
	@GetMapping("/management")
	public String management(Integer membershipCode, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem = (Member) authentication.getPrincipal();
		
		
	boolean check = false;
		
		for (int i=0; i<mem.getMemberListDTO().size(); i++) {
			if(mem.getMemberListDTO().get(i).getMembershipCode() == membershipCode && !(mem.getMemberListDTO().get(i).getListGrade().equals("guest") || mem.getMemberListDTO().get(i).getListGrade().equals("regular")) ) {
				check = true;
				
			}
			
		}

		if (!check) {
			
			return "error";
		}

		model.addAttribute("allMember", service.MembershipAllInfo(membershipCode));
		model.addAttribute("host", service.main(membershipCode));


		List<String> hosts = new ArrayList<String>();

		for (int i = 0; i < service.adminUser(membershipCode).size(); i++) {

			if (service.ifHost(service.adminUser(membershipCode).get(i).getId()) != null) {

				String id = service.ifHost(service.adminUser(membershipCode).get(i).getId()).getId();
				hosts.add(id);

			}

		}
	
		model.addAttribute("otherHost", hosts);
	
		System.out.println("접속");

		return "membership/management";
	}
	

	/* 
	 * 성철
	 * 일단 클럽 호스트가 가입 승인대기인원 -> 일반 회원으로 바꾸는기능 
	 * 성일
	 * 클럽 회원 관리 페이지에서 승인, 강퇴,호스트업그레이드 등등처리
	 * */
	@ResponseBody
	@PostMapping("/gradeUpdate")
	public int gradeUpdate(MemberListDTO member) {
		System.out.println("ajax 호출 ");

		System.out.println(member);

		int code = member.getMembershipCode();

		service.agreeMemeber(member);

		return code;

	}

}
