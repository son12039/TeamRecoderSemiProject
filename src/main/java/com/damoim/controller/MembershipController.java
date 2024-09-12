package com.damoim.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
import com.damoim.model.vo.MembershipLocation;
import com.damoim.model.vo.MembershipType;

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
import com.damoim.service.MemberService;
import com.damoim.service.MembershipService;
import com.damoim.service.RemoveMembershipService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.damoim.model.vo.LocationCategory;
import com.damoim.model.vo.MainComment;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.MembershipUserList;
import com.damoim.model.vo.TypeCategory;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MembershipController {
	// 클럽 관련 서비스
	@Autowired
	private MembershipService service;
	// 댓글관련 서비스
	@Autowired
	private MainCommentService commentService;
	// 클럽 타입, 지역 서비스
	@Autowired
	private LocationTypeService locationTypeService;
	// 회원 서비스
	@Autowired
	private MemberService memberService;
	// 클럽내 모임 서비스
	@Autowired
	private  MembershipMeetingService meetingService;
	// 클럽 삭제 서비스
	@Autowired
	private RemoveMembershipService removeService;

	
	@GetMapping("/makeMembership") // 클럽 생성페이지로 이동
	public String makeMembership(SearchDTO search, Model model) {
		// 생성 페이지에 DB에 있는 타입, 지역 정보 
		model.addAttribute("locLaNameList", locationTypeService.locLaNameList());
		model.addAttribute("typeLaNameList", locationTypeService.typeLaNameList());
		return "membership/makeMembership";
	}

	/*  
	 * 영민 클럽 생성기능 추가
	 */
	@ResponseBody
	@PostMapping("/makeMembership") // 클럽 생성
	public int makeMembership(Membership vo, MultipartFile file, String LB, String TB) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Membership membership = Membership.builder().membershipName(vo.getMembershipName())
				.membershipMax((vo.getMembershipMax())).membershipAccessionText(vo.getMembershipAccessionText())
				.membershipSimpleText(vo.getMembershipSimpleText()).build();
		service.makeMembership(membership);
		// 맴버쉽 코드 사용
		int code = membership.getMembershipCode();
		// 폴더 생성
		Path directoryPath = Paths.get("\\\\192.168.10.51\\damoim\\membership\\" + code + "\\");
		Files.createDirectories(directoryPath);
		// 파일 업로드
		Membership m = Membership.builder().membershipCode(membership.getMembershipCode())
				.membershipImg(fileUpload(file, code)).build();
		service.membershipImg(m);
		// 로케이션
		String locAll = LB.substring(1, (LB.length() - 1));
		String locAllStr = locAll.replaceAll("\"", "");
		String[] locList = locAllStr.split(","); // 대분류 이름 소분류 이름 분리
		String locLaName = locList[0];
		LocationCategory lc = LocationCategory.builder().locLaName(locLaName).build();
		
		for (String s : locList) {
			if (!s.equals(locLaName)) {
				lc.setLocSName(s);
				int locationCode = service.findLocationCode(lc);
				MembershipLocation location = MembershipLocation.builder().locSmallCode(locationCode)
						.membershipCode(code).build();
				service.makeLocationMembership(location);
			}
			
		}
		
		
		String typeAll = TB.substring(1, (TB.length() - 1));
		String typeAllStr = typeAll.replaceAll("\"", "");
		String[] typeList = typeAllStr.split(","); // 대분류 이름 소분류 이름 분리
		String typeLaName = typeList[0];;
		TypeCategory tc = TypeCategory.builder().typeLaName(typeLaName).build();
		
		
		for (String s : typeList) {
			if (!s.equals(typeLaName)) {
				tc.setTypeSName(s);
				int typeCode = service.findTypeCode(tc);
				MembershipType type = MembershipType.builder().typeSmallCode(typeCode)
						.membershipCode(code).build();
				service.makeTypeMembership(type);
			}		
		}
		Member mem = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// 호스트 담기용 DTO 생성
		MemberListDTO list = new MemberListDTO();
		list.setId(mem.getId());
		list.setListGrade("host");
		list.setMembershipCode(code);
		ArrayList<MemberListDTO> dtolist = (ArrayList<MemberListDTO>) mem.getMemberListDTO();
		// 호스트로 추가
		service.host(list);
//		// 세션에 호스트 정보 추가
		dtolist.add(list);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("생성 및 호스트 추가 완료");
		return code;
	}

	/*
	 * 영민 클럽명 중복 체크용 Ajax
	 * 성일 수정때도 체크 가능하게 변경
	 */
	@ResponseBody
	@PostMapping("/membershipNameCheck")
	public boolean membershipNameCheck(Membership membership) {
		int code2 = membership.getMembershipCode(); // JSP에서 온코드 OR 0
	 // 이름으로 멤버쉽을 조회 !
	if(service.membershipNameCheck(membership) == null) { // 중복이 아닌 상황임  중복인데 0이 아님  make 중복인데 1 이 아님 update
		return true;   // 무조건 바로 그냥 트루
	}else if(code2 != 0) {  // 중복이지만 업데이트 상황임
		if(code2 == service.membershipNameCheck(membership).getMembershipCode()) {
			return true;
		}
			
	}
	
	return false;
	
		
	}
	/*
	 * 성철
	 * 클럽 삭제 
	 * */
	@ResponseBody
	@PostMapping("/allDeleteMembership")	
	public boolean allDeleteMembership(String pwdCheck){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	
		Member mem = (Member) authentication.getPrincipal(); // 로그인 회원 정보
		if(memberService.updateCheck(mem, pwdCheck)) { // 삭제전 비밀번호 확인			
		int membershipCode = -1;
		for(MemberListDTO dto: mem.getMemberListDTO()) {
			if(dto.getListGrade().equals("host")) { // 해당 회원이 호스트인 클럽 찾기
				membershipCode = dto.getMembershipCode(); // 코드 저장함
			}
		}
		boolean ck = removeService.allDeleteMembership(membershipCode); // 삭제 성공 실패 유무
		if(ck) { // 삭제 로직 성공
			try {
				folderDelete(membershipCode); // 폴더와 하위 파일 삭제
			} catch (Exception e) {
				 // 파일&폴더 삭제 실패
			}
			ArrayList<MemberListDTO> list = (ArrayList<MemberListDTO>) mem.getMemberListDTO(); // 로그인 회원 정보
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).getMembershipCode() == membershipCode) {
					list.remove(i); // 로그인 세션에서 삭제한 DTO 제거
					break;	//반복문 빠져나감
				}		
			}	
		}
		SecurityContextHolder.getContext().setAuthentication(authentication); // 세션 업데이트
	 	return ck;
		} 
		return false; // 비밀번호 틀림!
	}
	
	/*
	 * 성일 카운트 관련 VO에 합쳐버림 성철 댓글 대댓글 글 관련 로직 추가
	 */

	@GetMapping("/{membershipCode}") // 클럽 홍보 페이지 각각 맞춰 갈수있는거
	public String main(@PathVariable("membershipCode") Integer membershipCode, MemberListDTO memberListDTO, Model model) {
		MembershipUserList list = service.main(membershipCode);
		list.setCount((service.membershipUserCount(membershipCode)));

		model.addAttribute("main", list);  // 해당 클럽의 정보 + 호스트 정보 
		model.addAttribute("allMember", service.MembershipAllRegular(membershipCode)); // 해당 클럽의 가입된 회원 정보(위가 배열이라 편의성위해)
		
		// 댓글 출력 
		ArrayList<MainComment> commList = commentService.allMainComment(membershipCode); // 일반댓글(대댓글이 아닌 댓글)
		ArrayList<CommentDTO> dtoList = new ArrayList<CommentDTO>(); // 화면단에 필요한 정보만 모아둔 DTO
		for (int i = 0; i < commList.size(); i++) { // 해당 클럽 홍보게시판의 댓글 2중 for문
			CommentDTO commentDTO = new CommentDTO().builder().mainCommentCode(commList.get(i).getMainCommentCode())
					.mainCommentText(commList.get(i).getMainCommentText())
					.mainCommentDate(commList.get(i).getMainCommentDate()).id(commList.get(i).getMember().getId())
					.nickname(commList.get(i).getMember().getNickname())
					.memberImg(commList.get(i).getMember().getMemberImg())
					.membershipCode(commList.get(i).getMembershipCode()).recoment(new ArrayList<>()).build();
			dtoList.add(commentDTO);
			ArrayList<MainComment> recommList = commentService.mainReComment(commentDTO.getMainCommentCode());
			if (recommList.size() > 0) { // 해당 댓글을 부모로 가진 대댓글이 있는 경우에
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
					commentDTO.getRecoment().add(recommentDTO); // 그 댓글의 대댓글로 추가 
				}
			}
		}
		model.addAttribute("comment", dtoList); // 댓글 모델에 추가
		model.addAttribute("location", locationTypeService.locationList(membershipCode));
		model.addAttribute("type", locationTypeService.typeList(membershipCode));

		return "mainboard/main";
	}

	/*
	 * 성철 해당 클럽에 가입된 회원이 그클럽에 정보와 클럽 가입 현황 볼수있는 페이지 이동
	 */
	@GetMapping("/club/{membershipCode}") // 클럽 페이지 이동 클릭시 membershipCode를 같이보냄
	public String membershipPage(@PathVariable("membershipCode") Integer membershipCode,Model model) {	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	
		Member mem = (Member) authentication.getPrincipal();	
		boolean ck = false;
		// 성일 접근권한
		for(MemberListDTO dto: mem.getMemberListDTO()) { // 해당 회원이 해당 클럽 페이지에 접근권한이 있는가?
			if(dto.getMembershipCode() == membershipCode && !dto.getListGrade().equals("guest")) {
				ck = true;
			}
		}
		if(!ck) { // 접근 권한이 없을시 에러페이지로
			return "error";
		}
		MembershipUserList list = service.main(membershipCode); // 해당 클럽의 호스트,클럽 정보등 모델에 추가
		list.setCount((service.membershipUserCount(membershipCode))); // 해당클럽의 인원 현황 추가
		model.addAttribute("main", list);
		// 해당클럽에 가입신청된 모든 유저정보
	
		model.addAttribute("allMember", service.MembershipAllRegular(membershipCode));
		model.addAttribute("adminList", service.adminUser(membershipCode));

		model.addAttribute("allmeet", meetingService.allMeetings(membershipCode));

		// 08-22 채승훈 클럽페이지 에 로케이션 타입 정보 추가
		model.addAttribute("location", locationTypeService.locationList(membershipCode));
		model.addAttribute("type", locationTypeService.typeList(membershipCode));

		return "membership/membershipPage";
	}


	/*
	 * 성철 세션에 맴버가 해당 클럽에 가입 X 상황일시 신청가능한 메서드
	 */
	@PostMapping("/membershipApply") // 클럽 회원가입 신청
	public String membershipApply(MemberListDTO member) {
		service.membershipApply(member); // 클릭시 id, membershipCode를 보내서 게스트 등급으로 추가
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem = (Member) authentication.getPrincipal();
		ArrayList<MemberListDTO> list = (ArrayList<MemberListDTO>) mem.getMemberListDTO();
		list.add(member); // 해당 정보를 로그인 세션에도 추가
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
		service.deleteList(new MemberListDTO().builder() // 로그인정보의 Id와 탈퇴,취소 할 클럽의 코드를 보내줌
				.id(mem.getId()).membershipCode(membershipCode).build());
		
		ArrayList<MemberListDTO> list = (ArrayList<MemberListDTO>) mem.getMemberListDTO();
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getMembershipCode() == membershipCode) {
				list.remove(i);
				break; // 로그인 세션도 업데이트			
			}
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	/*
	 * 성철 : 클럽 홍보글 작성 페이지 이동
	 */

	@GetMapping("/club/{membershipCode}/membershipPromotionDetail")
	public String membershipPromotionDetail(@PathVariable("membershipCode") Integer membershipCode, Model model) {
	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member m1 = (Member) authentication.getPrincipal();
		boolean check = false;
		
		// 성일 권한 체크 
	 for(int i=0; i < m1.getMemberListDTO().size(); i++) {
		 if(m1.getMemberListDTO().get(i).getMembershipCode() == membershipCode && !(m1.getMemberListDTO().get(i).getListGrade().equals("guest") || m1.getMemberListDTO().get(i).getListGrade().equals("regular"))) { 
			 check = true;
		 } // 해당 클럽의 가입자인가 확인, 가입한 회원이라면 호스트나 관리자 권한인지 확인	 
 }
		if(!check) { // 권한이 없을시 에러페이지로
			return "error";
		}
		model.addAttribute("memInfo", service.selectMembership(membershipCode));// 해당 클럽이전 홍보글, 코드 담기용도
		return "membership/membershipPromotionDetail";
	}

	/*
	 * 성철 : 클럽 홍보글 작성
	 */
	@ResponseBody
	@PostMapping("/membershipInfoUpdate")
	public void test(int membershipCode, String text) {
		// 입력받은 글과 코드로 홍보글 수정
		Membership membership = new Membership().builder().membershipCode(membershipCode).membershipInfo(text).build();
		service.updateMembershipInfo(membership);

	}
	
	
	
	
	
	 //  성철 파일 삽입 메서드 !!
	public String fileUpload(MultipartFile file, int code) throws IllegalStateException, IOException {
		if (file == null || file.getOriginalFilename() == "") {
			return null; // 파일이 오지 않은경우 파일명(DB에 넣어줄 정보)null
		}
		UUID uuid = UUID.randomUUID(); // 랜덤 파일명 부여
		String fileName = uuid.toString() + "_" + file.getOriginalFilename();
		File copyFile = new File("\\\\192.168.10.51\\damoim\\membership\\" + Integer.toString(code) + "\\" + fileName);
		file.transferTo(copyFile);
		return fileName; // 파일 실 서버에 추가후 DB에는 경로 다빼고 이름만 추가
	}

	 // 성철 파일 삭제 메서드 사진 변경시 사용!! 조건에 만약 보내준 링크가 null이면 변하지 않도록
	public void fileDelete(String file, int code) throws IllegalStateException, IOException {
		if (file != null) { // 보내준 DB저장된 파일명이 null이 아니면
			String decodedString = URLDecoder.decode(file, StandardCharsets.UTF_8.name()); // 한글 디코딩 처리
			File f = new File("\\\\192.168.10.51\\damoim\\membership\\" + Integer.toString(code) + "\\" + decodedString);
			f.delete(); // 파일 삭제
		}
	}

	 // 성철 폴더 내의 파일 삭제(클럽 삭제시)
	public boolean folderDelete(int code) {
		 String path = "\\\\\\\\192.168.10.51\\\\damoim\\\\membership\\\\" + Integer.toString(code); 
         File folder = new File(path); // 해당 폴더 경로 지정후
         try {
             if (folder.exists()) { // 폴더가 존재한다면
                 File[] listFiles = folder.listFiles(); // 폴더안의 파일을 리스트로
                 for (File file : listFiles) { // 폴더 내 파일을 반복시켜서 삭제
                     file.delete();
                 }
                     folder.delete(); // 파일 삭제후 폴더도 삭제      
             }
         }
        catch (Exception e) {
        	return false; // 삭제 실패
		}
        return true;
	}
	

	/*
	 * 멤버관리 페이지 호스트와 관리자만 접속 가능 등급 설정 및 회원 강퇴 기능 구현
	 * 클럽 구성원 관리 페이지 이동
	 */
	@GetMapping("/management")
	public String management(Integer membershipCode, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem = (Member) authentication.getPrincipal();
		
		// 성일 접근 권한 처리	
		boolean check = false;
		for (int i=0; i<mem.getMemberListDTO().size(); i++) {
			if(mem.getMemberListDTO().get(i).getMembershipCode() == membershipCode && !(mem.getMemberListDTO().get(i).getListGrade().equals("guest") || mem.getMemberListDTO().get(i).getListGrade().equals("regular")) ) {
				check = true;		
			}		
		}
		if (!check) {// 가입했으면서, 호스트나 관리자가 아니라면 에러페이지로		
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
		return "membership/management";
	}

	/*
	 * 성철 가입 승인 로직 
	 * 성일 클럽 회원 관리 페이지에서 승인, 강퇴, 호스트양도 
	 * 
	 */
	@ResponseBody
	@PostMapping("/gradeUpdate")
	public int gradeUpdate(MemberListDTO member) {
		int code = member.getMembershipCode();
		service.agreeMemeber(member);
		return code;
	}

	@ResponseBody
	@PostMapping("/updateMembership") // 클럽 수정
	public Integer updateMembership(Membership vo, MultipartFile file, String LB, String TB,int zIndex) throws Exception {
		
		Membership oldMembership = service.selectMembership(vo.getMembershipCode()); // 수정전 클럽 정보 객체 생성	
		String imgUrl = oldMembership.getMembershipImg(); // 기존 맴버쉽 정보의 이미지 URL
		
		if(vo.getFile() == null  ) { // 사진 변경을 안함(기존 그대로인 imgURL을 사용해야함)
			if( zIndex == -1) { // 프로필 사진을 변경하지 않음 < 기존 프로필 사진 사용
				vo.setMembershipImg(imgUrl);
			} else { // 프로필 사진을 변경하지 않음 < 기본 프로필로 변경 누름(기본 프로필 사진 사용)
				vo.setMembershipImg(null);
			}
		}else { // 사진이 변경됨
			fileDelete(imgUrl, vo.getMembershipCode()); // 수정 전 클럽 정보로 기존 파일 삭제
			vo.setMembershipImg(fileUpload(vo.getFile(), vo.getMembershipCode())); // 파일 업로드 + DB에 URL추가
		}		
		// 만들어놓은 membership update 돌리기 타입이랑 로케이션 삭제
		service.updateMembership(vo);
		
		// 지역 추가
		String locAll = LB.substring(1, (LB.length() - 1));
		String locAllstr = locAll.replaceAll("\"", "");
		String[] locList = locAllstr.split(","); // 대분류 이름 소분류 이름 분리
		String locLaName = locList[0];
		LocationCategory lc = LocationCategory.builder().locLaName(locLaName).build();
		for (String s : locList) {
			if (!s.equals(locLaName)) {
				lc.setLocSName(s);
				int locationCode = service.findLocationCode(lc);
				MembershipLocation location = MembershipLocation.builder().locSmallCode(locationCode)
						.membershipCode(vo.getMembershipCode()).build();
				service.makeLocationMembership(location); // MembershipLocation
			}
		}
		// 타입 추가
		String typeAll = TB.substring(1, (TB.length() - 1));
		String typeAllstr = typeAll.replaceAll("\"", "");
		String[] typeList = typeAllstr.split(","); // 대분류 이름 소분류 이름 분리
		String typeLaName = typeList[0];
		;
		TypeCategory tc = TypeCategory.builder().typeLaName(typeLaName).build();
		for (String ss : typeList) {
			if (!ss.equals(typeLaName)) {
				tc.setTypeSName(ss);
				int typeCode = service.findTypeCode(tc);
				MembershipType type = MembershipType.builder().typeSmallCode(typeCode)
						.membershipCode(vo.getMembershipCode()).build();
				 service.makeTypeMembership(type);
			}
		}
		return vo.getMembershipCode(); // 수정후 코드 반환
	}

}
