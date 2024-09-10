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
	private MemberService memberService;
	

	@Autowired
	private  MembershipMeetingService meetingService;
	
	@Autowired
	private RemoveMembershipService removeService;
	
	@Autowired
	private RemoveMembershipService removeMembership;
	/*
	 * 
	 * */
	/*
	 * 
	 * */
	@GetMapping("/makeMembership") // 클럽 생성페이지로 이동
	public String makeMembership(SearchDTO search, Model model) {
		model.addAttribute("locLaNameList", locationTypeService.locLaNameList());
		model.addAttribute("typeLaNameList", locationTypeService.typeLaNameList());
		return "mypage/makeMembership";
	}

	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	/*
	 * ???
	 * 
	 * 
	 * 영민 클럽 생성기능 추가
	 *
	 * 성철 만들어진거에 사진첨부만 추가
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
	 * 
	 */
	@ResponseBody
	@PostMapping("/membershipNameCheck")
	public boolean membershipNameCheck(Membership membership) {
		int code2 = membership.getMembershipCode(); // JSP에서 온코드 OR 0
	 // 이름으로 멤버쉽을 조회 !
	if(service.membershipNameCheck(membership) == null) { // 중복이 아닌 상황임  중복인데 0이 아님  make 중복인데 1 이 아님 update
		return true;                    // 무조건 바로 그냥 트루
	}else if(code2 != 0) {       // 중복이지만 업데이트 상황임
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
		Member mem = (Member) authentication.getPrincipal();
		if(memberService.updateCheck(mem, pwdCheck)) {
				// 삭제전 비밀번호 확인
		int membershipCode = -1;
		for(MemberListDTO dto: mem.getMemberListDTO()) {
			if(dto.getListGrade().equals("host")) {
				System.out.println("이사람이 삭제하려는 클럽 코드는 : "  + dto.getMembershipCode());
				membershipCode = dto.getMembershipCode();
			}
		}
		Membership membership = service.selectMembership(membershipCode);
		boolean ck = removeService.allDeleteMembership(membershipCode);
		if(ck && membership != null) {
			// 파일도 삭제
			try {
//				fileDelete(membership.getMembershipImg(), membershipCode);
				folderDelete(membershipCode);
			} catch (Exception e) {
				return false;
			}
			System.out.println("파일 삭제 완료");
			ArrayList<MemberListDTO> list = (ArrayList<MemberListDTO>) mem.getMemberListDTO();
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).getMembershipCode() == membershipCode) {
					System.out.println("삭제될 DTO : " + list.get(i));
					list.remove(i);
					break;
					
				}
				
		}
			return true;
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("업데이트 완료");
	 	return ck;
		} 
		return false;
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
		// 08-22 채승훈 클럽페이지 에 로케이션 타입 정보 추가
		model.addAttribute("location", locationTypeService.locationList(membershipCode));
		model.addAttribute("type", locationTypeService.typeList(membershipCode));

		return "mainboard/main";
	}

	/*
	 * 성철 해당 클럽에 가입된 회원이 그클럽에 정보와 클럽 가입 현황 볼수있는 페이지 이동
	 */
	@GetMapping("/club/{membershipCode}") // 클럽 페이지 이동
	public String membershipPage(@PathVariable("membershipCode") Integer membershipCode,
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
	 * 성철 : 클럽 홍보글 작성 페이지 이동
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
	 * 성철 : 클럽 홍보글 작성
	 */
	@ResponseBody
	@PostMapping("/membershipInfoUpdate")
	public void test(int membershipCode, String test) {

		Membership membership = new Membership().builder().membershipCode(membershipCode).membershipInfo(test).build();
		service.updateMembershipInfo(membership);

	}
	
	
	@ResponseBody
	@PostMapping("/deleteMembership")
	public boolean deleteMembership(int membershipCode) {
		
		return true;
	}
	
	
	
	/*
	 * 성철 파일 삽입 메서드 해당맴버쉽 프로필사진 !!
	 * 
	 */
	public String fileUpload(MultipartFile file, int code) throws IllegalStateException, IOException {
		if (file == null || file.getOriginalFilename() == "") {
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
			String decodedString = URLDecoder.decode(file, StandardCharsets.UTF_8.name());
			File f = new File("\\\\192.168.10.51\\damoim\\membership\\" + Integer.toString(code) + "\\" + decodedString);
			f.delete();

		}

	}
	/*
	 * 성철
	 * 폴더 내의 파일 삭제
	 * */
	public boolean folderDelete(int code) {
		 String path = "\\\\\\\\192.168.10.51\\\\damoim\\\\membership\\\\" + Integer.toString(code); 
         File folder = new File(path); //
         try {
             if (folder.exists()) { // 폴더가 존재한다면
                 File[] listFiles = folder.listFiles();

                 for (File file : listFiles) { // 폴더 내 파일을 반복시켜서 삭제
                     file.delete();
                 }

//                 if (listFiles.length == 0 && folder.isDirectory()) { // 하위 파일이 없는지와 폴더인지 확인 후 폴더 삭제
                     folder.delete();
//                 }
             
        
             }
         }
        catch (Exception e) {
        	return false;
		}
        return true;
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
	 * 성철 일단 클럽 호스트가 가입 승인대기인원 -> 일반 회원으로 바꾸는기능 성일 클럽 회원 관리 페이지에서 승인, 강퇴,호스트업그레이드
	 * 등등처리
	 */
	@ResponseBody
	@PostMapping("/gradeUpdate")
	public int gradeUpdate(MemberListDTO member) {
		System.out.println("ajax 호출 ");

		System.out.println(member);

		int code = member.getMembershipCode();

		service.agreeMemeber(member);

		return code;

	}

	@ResponseBody
	@PostMapping("/updateMembership") // 클럽 수정
	public Integer updateMembership(Membership vo, MultipartFile file, String LB, String TB,int zIndex) throws Exception {
		// 맴버쉽 코드 사용
		System.out.println("지역 확인 : " + LB); // 인천 = 중구, 미추홀구, 남동구
		System.out.println("유형 확인 : " + TB); // 스터디 = 코딩, 자격증, 토론
		System.out.println(zIndex);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		// 맴버쉽 코드 사용
		int code = vo.getMembershipCode();
		Membership oldMembership = service.selectMembership(vo.getMembershipCode());
		
		String imgUrl = oldMembership.getMembershipImg(); // 기존 맴버쉽 정보의 이미지 URL
		// 파일 업로드를 안했을 경우 ! >> 수정전 멤버쉽의 사진으로 
		// 파일 업로드를 했을 경우 ! >> 기존 멤버쉽 폴더의 사진 삭제후 재 업로드 
		System.out.println("보내는 정보에서 사진 정보 제외하고 + " + vo);
		if(vo.getFile() == null  ) { // 사진 변경을 안함(기존 그대로인 imgURL을 사용해야함)
			if( zIndex == -1) {
				System.out.println("이사람 프사 고르다가 취소하고 원래 프사 쓰기로함 ");
				vo.setMembershipImg(imgUrl);
			} else {
				System.out.println("이사람 프사 고르다가 취소하고 사이트 기본 프사 쓰기로함  ");
				vo.setMembershipImg(null);
			}
		}else { // 사진이 바뀜 먼가 바낌
			System.out.println("이사람 직접 사진을 골랐음");
			fileDelete(imgUrl, vo.getMembershipCode()); // 실 파일 삭제	
			vo.setMembershipImg(fileUpload(vo.getFile(), vo.getMembershipCode())); // 파일 업로드 + DB에 URL추가
		}
		
		// 타입이랑 로케이션 삭제
		// 만들어놓은 membership update 돌리기
		service.updateMembership(vo);
		// 로케이션
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
		// 타입
		String typeAll = TB.substring(1, (TB.length() - 1));
		String typeAllstr = typeAll.replaceAll("\"", "");
		String[] typeList = typeAllstr.split(","); // 대분류 이름 소분류 이름 분리
		String typeLaName = typeList[0];
		;
		TypeCategory tc = TypeCategory.builder().typeLaName(typeLaName).build();
		System.out.println(tc);
		for (String ss : typeList) {
			if (!ss.equals(typeLaName)) {
				tc.setTypeSName(ss);
				int typeCode = service.findTypeCode(tc);
				MembershipType type = MembershipType.builder().typeSmallCode(typeCode)
						.membershipCode(vo.getMembershipCode()).build();
				 service.makeTypeMembership(type);
			}
		}
		return vo.getMembershipCode();
	}

}
