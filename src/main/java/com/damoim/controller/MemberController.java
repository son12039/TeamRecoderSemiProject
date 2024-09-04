package com.damoim.controller;

import java.io.File;

import java.io.IOException;
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
import com.damoim.model.vo.MembershipUserList;
import com.damoim.model.vo.Paging;
import com.damoim.model.vo.UserInfoPaging;
import com.damoim.service.EmailService;
import com.damoim.service.MainCommentService;
import com.damoim.service.MemberService;
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


	/*
	 * 성일 로그인 시큐리티 처리(member서비스)
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
	@PostMapping("/nicknameCheck") // 회원가입시 닉네임 중복 체크
	public boolean nicknameCheck(Member member) {
		Member mem = service.nicknameCheck(member);
		return mem == null;

	}
	/*
	 * 성철 회원가입할때 이메일 (유니크) 증복체크
	 * */
	@ResponseBody
	@PostMapping("/emailCheck") // 회원가입시 닉네임 중복 체크
	public boolean emailCheck(Member member) {
		Member mem = service.emailCheck(member);
		return mem == null;

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
	 * 성철 
	 * 회원의 이메일, 이름 받아서 아이디 찾기
	 * */
	@PostMapping("/findMemberId")
	public String findMemberId(Member member, Model model) {
		
		String str = service.findMemberId(member);
		if(str == "") {
			model.addAttribute("message" , "틀림");
		}else {
			model.addAttribute("message" , str);
		}
		return "login/findId"; // 인덱스 페이지로 리다이렉트
	}

	/*
	 * 성철 
	 * 비밀번호 찾기
	 * 로그인 X 한 상태에서 유저에게 ID랑 이메일 정보를 받아서 일치할시에 그 유저가 가입할때 넣은 이메일주소에 임시 비밀번호 발송 ->
	 * 암호화 -> DB변경 (이메일서비스)
	 */

	@PostMapping("/sendEmail")
	public String sendEmail(@RequestParam("id") String id, @RequestParam("email") String email, Model model) {
		Member member = new Member();
		member.setId(id);
		member.setEmail(email);
		System.out.println("DB에 보낼 정보 : " + member);
		Member mem = emailService.memberEmailIdcheck(member);
		System.out.println("DB의 정보 : " + mem);
		try {
			System.out.println("서비스 진입전 member 정보 : " + mem);
			emailService.processPasswordReset(mem);
			System.out.println("서비스 진입성공");
			if(mem != null) {
			model.addAttribute("message", "임시 비밀번호가 이메일로 전송되었습니다.");
			System.out.println("비밀번호 변경 완료");
			}else {
				System.out.println("아이디 비밀번호 일치 X");
				model.addAttribute("message", "아이디와 이메일이 일치하지 않습니다.");
			}
		} catch (Exception e) {
			model.addAttribute("message", "비밀번호 재설정에 실패했습니다.");
			System.out.println("비밀번호 변경 실패");
		}

		return "login/findPassword"; // 인덱스 페이지로 리다이렉트
	}

	/*
	 * 성일 로그아웃 시큐리티
	 */

	/*
	 * 성철 단순히 더미데이터 비밀번호 처리
	 */
	@GetMapping("/dummyUpdate")
	public String dummyUpdate() {
		service.dummyUpdate();
		return "redirect:/";
	}
	/*
	 * 성철
	 * 업데이트시 본인꺼 OR 중복 X 확인
	 * */
	@ResponseBody
	@PostMapping("/updateNicknameCheck")
	public boolean updateNicknameCheck(String nickname){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// 로그인 회원
		Member m1 = (Member) authentication.getPrincipal();
		// 해당 닉네임 넣을시 회원이 있는가
		Member m2 = service.nicknameCheck(new Member().builder().nickname(nickname).build());
		// 해당 닉네임으로 생성된 회원정보가 없거나 
		// 해당 닉네임으로 생성된 회원 정보가 로그인한 회원가 같을시
		if (m2 == null || m2.getId().equals(m1.getId())) { 
			// 트루 리턴
			return true;
		}
		// 아니면 false 리턴
		return false;
	}
	/*
	 * 성철
	 * 업데이트시 본인꺼 OR 중복 X 확인
	 * */
	@ResponseBody
	@PostMapping("/emailUpdateCheck")
	public boolean emailUpdateCheck(String email){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// 로그인 회원
		Member m1 = (Member) authentication.getPrincipal();
		// 해당 이메일 넣을시 회원이 있는가
		Member m2 = service.emailCheck(new Member().builder().email(email).build());
		// 해당 이메일로 생성된 회원정보가 없거나 
		// 해당 이메일로 생성된 회원 정보가 로그인한 회원가 같을시
		if (m2 == null || m2.getId().equals(m1.getId())) { 
			// 트루 리턴
			return true;
		}
		// 아니면 false 리턴
		return false;
	}
	/*
	 * 
	 * 회원 중요정보 수정
	 * */
	@PostMapping("/updateMemberInfo")
	public String updateMemberInfo(Member vo, Model model, String addrDetail,String beforePwd ) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem = (Member) authentication.getPrincipal();
		
		if(!service.updateCheck(mem, beforePwd)) {
			
			System.out.println("실패함 ");
			model.addAttribute("text" , "변경 실패");
			System.out.println("실패함1 ");
			return "mypage/updateMemberInfo";
			
		}
  System.out.println("비번은 뚫음 ");
		vo.setId(mem.getId());
		String addr = vo.getAddr();
		addr += "#" + addrDetail;
		vo.setAddr(addr);

		service.updateMemberInfo(vo,beforePwd); // 수정
		
		
		
		// 실제 로그인 회원 정보 업데이트
		mem.setNickname(vo.getNickname());
		mem.setPwd(vo.getPwd());
		mem.setName(vo.getName());
		mem.setPhone(vo.getPhone());
		mem.setAddr(vo.getAddr());
		mem.setEmail(vo.getEmail());
		mem.setAge(vo.getAge());

		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		
		model.addAttribute("text" , "변경 성공");
	
		
		
	
		return "mypage/mypage";
	}

	// 회원정보 수정 비밀번호 체크
	@ResponseBody
	@PostMapping("/updateCheck")
	public boolean updateCheck(String pwdCheck) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem = (Member) authentication.getPrincipal();
		return service.updateCheck(mem, pwdCheck);
	}

	// 회원탈퇴 비밀번호 체크
	@ResponseBody
	@PostMapping("/resignCheck")
	public boolean resignCheck(String pwdCheck) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem = (Member) authentication.getPrincipal();
		return service.updateCheck(mem, pwdCheck);
	}
	
	/*
	 * 성철
	 * 탈퇴 로직에 댓글 자동삭제 추가
	 * 로직 추가필요 ================================================
	 * 
	 * */
	@ResponseBody
	@PostMapping("/memberStatus")
	public boolean memberStatus(HttpServletRequest request, HttpServletResponse response) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    Member mem = (Member) authentication.getPrincipal();
	    boolean check = false;
	    for(MemberListDTO dto : mem.getMemberListDTO()) {
	    	if(dto.getListGrade().equals("host"))
				   check = true; 
	    		}
	    if (check) { // 해당 유저가 가입된 클럽 중  호스트인게 있다면!
	        return false;
	    	}
	    
	    service.memberStatus(mem); // 멤버 상태 업데이트
	    removeService.deleteAllComment(mem.getId());
	    removeService.deleteMembershipUserList(mem.getId());
	    
	    // membershipUserList 삭제
	    
	    // 로그아웃 처리
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
	    logoutHandler.logout(request, response, authentication);

	    return true;
	}



	// 프로필, info 업데이트
	@ResponseBody
	@PostMapping("/updateMember")
	public String updateMember(Member vo, MultipartFile file) throws IllegalStateException, IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem = (Member) authentication.getPrincipal();

		// 삭제
		if (file.getOriginalFilename() != null || mem.getMemberImg() == null) {

			fileDelete(mem.getMemberImg(), mem.getId());
			String url = fileUpload(file, mem.getId());
			mem.setMemberImg(url);
		}

		mem.setMemberHobby(vo.getMemberHobby());
		mem.setMemberInfo(vo.getMemberInfo());
		service.updateMember(mem);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return "redirect:/mypage";
	}

	/* 성철
	 * 닉네임값 받아서 해당유저의 상세페이지로 이동(그유저의 가입된 클럽 여부, 추천기능)
	 */
	@GetMapping("/userInfo/{nickname}")
	public String getMethodName(@PathVariable("nickname") String nickname, Model model) {
		Member member = service.nicknameCheck(new Member().builder().nickname(nickname).build());

		MemberInfoDTO mem = new MemberInfoDTO().builder().member(member)
				.memberMeetCount(infoService.meetCount(member.getId()))
				.membershipUserList(infoService.selectMemberUserList(member.getId())).build();
		System.out.println(mem);
		model.addAttribute("mem", mem);

		return "member/userInfo";
	}

	/*
	 * 성철
	 * 유저 1명당 24시간마다 다른 유저에게 추천, 비추천 기능(온도 0.5도씩 업다운)
	 * */
	@ResponseBody
	@PostMapping("/recommendation")
	public boolean recommendation(String targetMember, String loginMember, boolean plusMinus) {
		Member m1 = new Member().builder().id(targetMember).build();
		Member m2 = new Member().builder().id(loginMember).build();
		RecommendationDTO dto = new RecommendationDTO(service.idCheck(m1), service.idCheck(m2), plusMinus);
		// 추천 성공, 실패 여부 블리언으로 반환
		boolean check = service.memberManner(dto);
		if (check) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Member mem = (Member) authentication.getPrincipal();
			mem.setLastRecommendationTime(service.idCheck(m2).getLastRecommendationTime());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		return check;
	}

	/*
	 * 성철 파일 업로드 각각 mamber의 id 폴더에 저장후 URL 리턴
	 */
	public String fileUpload(MultipartFile file, String id) throws IllegalStateException, IOException {
		if (file.getOriginalFilename() == "") {
			System.out.println("NULL 리턴");
			return null;
		}
		
		UUID uuid = UUID.randomUUID(); // 랜덤 파일명 부여
		String fileName = uuid.toString() + "_" + file.getOriginalFilename();
		File copyFile = new File("\\\\192.168.10.51\\damoim\\member\\" + id + "\\" + fileName);
		file.transferTo(copyFile);
		System.out.println("파일1개 추가!");
		System.out.println("파일 이름 : " + fileName);
		return fileName;
	}

	// 성철 파일 삭제 메서드 해당유저 프로필사진 변경시 사용!! 실 사용때는 조건에 만약 보내준 링크가 null이면 변하지 않도록
	public void fileDelete(String fileName, String id) throws IllegalStateException, IOException {

		if (fileName == null || fileName.isEmpty()) {
			System.out.println("삭제할 파일이 없습니다");
		} else {
			System.out.println("삭제될 URL : " + fileName);
			File file = new File("\\\\192.168.10.51\\damoim\\member\\" + id + "\\" + fileName);
			file.delete();
		}
		
		
	}

}
