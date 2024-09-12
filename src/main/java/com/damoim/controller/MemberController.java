package com.damoim.controller;

import java.io.File;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.damoim.model.dto.MemberInfoDTO;
import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.dto.RecommendationDTO;
import com.damoim.model.vo.MainComment;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.MembershipMeetings;
import com.damoim.model.vo.MembershipUserList;
import com.damoim.model.vo.Paging;
import com.damoim.model.vo.UserInfoPaging;
import com.damoim.service.EmailService;
import com.damoim.service.MainCommentService;
import com.damoim.service.MemberService;
import com.damoim.service.MembershipMeetingService;
import com.damoim.service.MembershipService;
import com.damoim.service.RemoveMemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;

@Controller
public class MemberController {

	@Autowired
	private MemberService service; // 맴버 서비스

	@Autowired
	private MembershipService infoService; // 맴버쉽 서비스

	@Autowired
	private EmailService emailService; // 이메일 서비스

	@Autowired // 회원 탈퇴 댓글 삭제 서비스
	private RemoveMemberService removeService;
	
	@Autowired
	private MembershipMeetingService meetingService;

	/*
	 * 성일 로그인 시큐리티 처리(member서비스)
	 * 
	 * 
	 */

	/*
	 * 성철 회원가입 할때 아이디(프라이머리키 제약조건) 중복회원 체크
	 */
	@ResponseBody
	@PostMapping("/idCheck")
	public boolean idCheck(Member member) {
		Member mem = service.idCheck(member);
		return mem == null;

	}
	/*
	 * 성철 회원가입 할때 닉네임(유니크 제약조건) 중복회원 체크
	 */
	@ResponseBody
	@PostMapping("/nicknameCheck") // 회원가입시,정보 수정시 닉네임 중복 체크
	public boolean nicknameCheck(Member member) {
		Member mem = service.nicknameCheck(member);
		if(mem == null) { // 중복이 아닐시 
			return true;
		}else { // 중복일시  
			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				Member loginMember = (Member) authentication.getPrincipal();
				if(loginMember.getId().equals(mem.getId())) { 
					return true;	// 로그인한 회원과 중복인 회원의 id가 일치할시(변경 없이 그대로 수정)
				}
					return false; // 회원정보 수정중 단순 중복 닉네임
			} catch (Exception e) {
				return false; // 중복이지만 회원가입 상황에 에러 유도 false 리턴
			}
			
		}
	}	
	
	/*
	 * 성철 회원가입할때 이메일 (유니크) 증복체크
	 */
	@ResponseBody
	@PostMapping("/emailCheck") // 회원가입시 닉네임 중복 체크
	public boolean emailCheck(Member member) {
		Member mem = service.emailCheck(member);
		if(mem == null) { // 중복이 아닐시 
			return true;
		}else { // 중복일시  
			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				Member loginMember = (Member) authentication.getPrincipal();
				if(loginMember.getId().equals(mem.getId())) { // 로그인한 회원과 중복인 회원의 id가 일치할시
					return true;
				}
				return false;
			} catch (Exception e) {
				return false;
			}
			
		}

	}

	/*
	 * 성철 회원가입 관련 재약조건문들 O (나중에 추가? -> 생년월일 받아서 나이 반환) (휴대폰 인증 관련 API? )
	 */
	@PostMapping("/signUp") // 회원가입 메서드
	public String signUp(Member member, String addrDetail, MultipartFile imgFile) throws IOException {
		Member mem = member;
		String addr = mem.getAddr();
		addr += "#" + addrDetail;
		mem.setAddr(addr);
		// 해당 id 이름의 회원 폴더 생성
		Path directoryPath = Paths.get("\\\\\\\\192.168.10.51\\\\damoim\\\\member\\" + mem.getId() + "\\");
		Files.createDirectories(directoryPath);
		member.setMemberImg(fileUpload(imgFile, mem.getId()));
		service.signUp(member);
		return "login/loginPage";

	}

	/*
	 * 성철 회원의 이메일, 이름 받아서 아이디 찾기
	 */
	@PostMapping("/findMemberId")
	public String findMemberId(Member member, Model model) {

		String str = service.findMemberId(member);
		if (str == "") {
			model.addAttribute("message", "틀림");
		} else {
			model.addAttribute("message", str);
		}
		return "login/findId"; // 인덱스 페이지로 리다이렉트
	}

	/*
	 * 성철 비밀번호 찾기 로그인 X 한 상태에서 유저에게 ID랑 이메일 정보를 받아서 일치할시에 그 유저가 가입할때 넣은 이메일주소에 임시
	 * 비밀번호 발송 -> 암호화 -> DB변경 (이메일서비스)
	 */

	@PostMapping("/sendEmail")
	public String sendEmail(@RequestParam("id") String id, @RequestParam("email") String email, Model model) {
		Member member = new Member();
		member.setId(id);
		member.setEmail(email);
		Member mem = emailService.memberEmailIdcheck(member);
		try {	
			if (mem != null) {
				emailService.processPasswordReset(mem);
				model.addAttribute("message", "임시 비밀번호가 이메일로 전송되었습니다.");
			} else {
				model.addAttribute("message", "아이디와 이메일이 일치하지 않습니다.");
			}
		} catch (Exception e) {
			model.addAttribute("message", "비밀번호 재설정에 실패했습니다.");
		}

		return "login/findPassword"; 
	}

	/*
	 * 성일 로그아웃 시큐리티
	 */

	/*
	 * 성철 단순히 더미데이터 비밀번호 처리
	 */
	@GetMapping("/dummyUpdate")
	public String dummyUpdate() throws IOException {
		service.dummyUpdate();// 더미 비밀번호 업데이트 // 폴더생성
		return "redirect:/";
	}


	/*
	 * 
	 * 회원 중요정보 수정
	 */
	@PostMapping("/updateMemberInfo")
	public String updateMemberInfo(Member vo, Model model, String addrDetail, String beforePwd) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem = (Member) authentication.getPrincipal();

		if (!service.updateCheck(mem, beforePwd)) {

			model.addAttribute("text", "변경 실패");

			return "mypage/updateMemberInfo";

		}
		System.out.println("비번은 뚫음 ");
		vo.setId(mem.getId());
		String addr = vo.getAddr();
		addr += "#" + addrDetail;
		vo.setAddr(addr);

		service.updateMemberInfo(vo, beforePwd); // 수정

		mem.setNickname(vo.getNickname());
		mem.setPwd(vo.getPwd());
		mem.setName(vo.getName());
		mem.setPhone(vo.getPhone());
		mem.setAddr(vo.getAddr());
		mem.setEmail(vo.getEmail());
		mem.setAge(vo.getAge());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		model.addAttribute("text", "변경 성공");

		return "mypage/mypage";
	}

	/*
	 * 성철 탈퇴 로직에 댓글 자동삭제 추가 로직 추가필요 ================================================
	 * 
	 */
	@ResponseBody
	@PostMapping("/memberStatus")
	public boolean memberStatus(HttpServletRequest request, HttpServletResponse response ,String pwdCheck) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    System.out.println("입력한 비번옴? : " + pwdCheck);
	    Member mem = (Member) authentication.getPrincipal();
	    boolean check = false;
	    for(MemberListDTO dto : mem.getMemberListDTO()) {
	    	if(dto.getListGrade().equals("host"))
				   check = true; 
	    		}
	    if (check) { // 해당 유저가 가입된 클럽 중  호스트인게 있다면!
	        return false;
	    	}
	    if(!service.updateCheck(mem, pwdCheck)) { // 비밀번호 확인에서 틀렸을 경우
	    	System.out.println("비번트림 ㅠ");
	    	return false;
	    }
	    
	    service.memberStatus(mem); // 멤버 상태 업데이트
	    removeService.deleteAllComment(mem.getId());
	    removeService.deleteMembershipUserList(mem.getId());
	    removeService.deleteAllMeeting(mem.getId());
	    System.out.println("탙퇴 로직 도착");
	    // membershipUserList 삭제
	    boolean ck =  folderDelete(mem.getId());
	    	System.out.println("파일 삭제 로직은 탐");
	    // 로그아웃 처리
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
	    logoutHandler.logout(request, response, authentication);
	    if(!ck) {
	    	System.out.println("파일 삭제 실패");
	    	return false;
	    }
	    System.out.println("리턴");
	    return true;
	}

	// 기본 사진으로 변경
	@ResponseBody
	@PostMapping("/defualtFile")
	public boolean defualtFile() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem = (Member) authentication.getPrincipal();
		service.defualtFile(mem.getId());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return true;
	}

	// 프로필, info 업데이트
	@ResponseBody
	@PostMapping("/updateMember")
	public boolean updateMember(MultipartFile file,String memberInfo, String memberHobby) throws IllegalStateException, IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem = (Member) authentication.getPrincipal();
		// 1. file 이 null 인 경우 (사용자가 파일을 선택하지 않고 제출 버튼을 눌렀을 때)
		// 1-1 !file.isEmpty() (파일이 선택되었고 내용이 있는 경우)
	    if (file != null && !file.isEmpty()) {
	        if (mem.getMemberImg() != null) {
	        	// 2. 멤버가 가지고있는 파일이미지가 null이 아닐경우 기존 파일 삭제
	            fileDelete(mem.getMemberImg(), mem.getId());
	        }
	        
	        // 3. 그 뒤에 url 파일 업로드 후 멤버의 이미지에 추가
	        String url = fileUpload(file, mem.getId());
	        mem.setMemberImg(url);
        
	    } else if (file == null) {
	    	
	    	// 4. 추가한 파일이 없거나 기존 이미지로 유지할 경우 기존 이미지 유지
	        mem.setMemberImg(mem.getMemberImg());
	    }

		mem.setMemberHobby(memberHobby);
		mem.setMemberInfo(memberInfo);

		service.updateMember(mem);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return true;
	}

	/*
	 * 성철 닉네임값 받아서 해당유저의 상세페이지로 이동(그유저의 가입된 클럽 여부, 추천기능)
	 */

	@GetMapping("/userInfo/{nickname}")
	public String getMethodName(@PathVariable("nickname") String nickname, Model model) {	
		Member member = service.nicknameCheck(new Member().builder().nickname(nickname).build());
		MemberInfoDTO mem = new MemberInfoDTO().builder().member(member)
				.memberMeetCount(infoService.meetCount(member.getId()))
				.membershipUserList(infoService.selectMemberUserList(member.getId())).build();
		model.addAttribute("mem", mem);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal().equals("anonymousUser")) { // 비회원이 유저페이지로 간 경우
			return "member/userInfo";
		}
		Member loginMember = (Member) authentication.getPrincipal();
		if (loginMember.getNickname().equals(nickname)) { // 클릭한 대상이 본인인 경우
			return "redirect:/mypage";
		}
		return "member/userInfo"; // 다른 유저의 페이지로 간 경우
	}

	/*
	 * 성철 유저 1명당 24시간마다 다른 유저에게 추천, 비추천 기능(온도 0.5도씩 업다운)
	 */
	@ResponseBody
	@PostMapping("/recommendation")
	public boolean recommendation(String targetMember, String loginMember, boolean plusMinus) {
		Member target = new Member().builder().id(targetMember).build();
		Member login = new Member().builder().id(loginMember).build();
		RecommendationDTO dto = new RecommendationDTO(service.idCheck(target), service.idCheck(login), plusMinus);
		// 추천 성공, 실패 여부 블리언으로 반환
		boolean check = service.memberManner(dto);
		if (check) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Member mem = (Member) authentication.getPrincipal();
			mem.setLastRecommendationTime(service.idCheck(login).getLastRecommendationTime());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		return check;
	}

	/*
	 * 성철 파일 업로드 각각 mamber의 id 폴더에 저장후 URL 리턴
	 */
	public String fileUpload(MultipartFile file, String id) throws IllegalStateException, IOException {
		if (file == null || file.getOriginalFilename() == "") {
			return null;
		}
		UUID uuid = UUID.randomUUID(); // 랜덤 파일명 부여
		String fileName = uuid.toString() + "_" + file.getOriginalFilename();
		File copyFile = new File("\\\\192.168.10.51\\damoim\\member\\" + id + "\\" + fileName);
		file.transferTo(copyFile);
		return fileName;
	}

	// 성철 파일 삭제 메서드 해당유저 프로필사진 변경시 사용!! 실 사용때는 조건에 만약 보내준 링크가 null이면 변하지 않도록
	public void fileDelete(String file,  String id) throws IllegalStateException, IOException {
		
		if (file == null) {
			System.out.println("삭제할 파일이 없습니다");
		} else {
			System.out.println("삭제될 URL : " + file);
			String decodedString = URLDecoder.decode(file, StandardCharsets.UTF_8.name());
			File f = new File("\\\\192.168.10.51\\damoim\\member\\" + id + "\\" + decodedString);
			f.delete();

		}

	}
	// 성철 회원 탈퇴시 id 값을 받아서 폴더도 삭제
	public boolean folderDelete(String id) {
		String path = "\\\\\\\\192.168.10.51\\\\damoim\\\\member\\\\" + id; 
        File folder = new File(path); // 폴더 경로 지정   
            if (folder.exists()) { // 폴더가 존재한다면
                File[] listFiles = folder.listFiles();
                for (File file : listFiles) { // 폴더 내 파일을 반복시켜서 삭제
                    file.delete();
                	}	
                folder.delete();
                return true;
            }       
            else return false;
            
		}
       
	

}
