package com.damoim.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.MembershipUserList;
import com.damoim.model.vo.Paging;
import com.damoim.service.EmailService;
import com.damoim.service.MemberService;
import com.damoim.service.MembershipService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

	@Autowired
	private MemberService service; // 맴버 서비스

	@Autowired
	private MembershipService infoService; // 맴버쉽 서비스

	@Autowired
	private EmailService emailService; // 이메일 서비스

	/*
	 * 성일 로그인 시큐리티 처리
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
	 * 성철 회원가입 관련 재약조건문들 O (나중에 추가가능 기능 -> 생년월일 받아서 나이 반환) (휴대폰 인증 관련 API? )
	 */
	@PostMapping("/signUp") // 회원가입 메서드
	public String signUp(Member member, String addrDetail, MultipartFile imgFile) throws IOException {
		Member mem = member;
		String addr = mem.getAddr();
		addr += "#" + addrDetail;
		mem.setAddr(addr);
		// 해당 id 이름의 회원 폴더 생성
		Path directoryPath = Paths.get("\\\\\\\\192.168.10.51\\\\damoim\\\\member\\"+ mem.getId()+"\\");
		Files.createDirectories(directoryPath);
		member.setMemberImg(fileUpload(imgFile, mem.getId()));
		service.signUp(member);
		return "redirect:/";

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
	 * 성철 내가 가입한 클럽을 가입된, 관리자or호스트, 가입대기중 클럽 조회가능한 페이지이동
	 */
	@GetMapping("/myMembership") // 내가 가입한 클럽확인
	public String myMembership(Model model) {
		
 		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member member = (Member) authentication.getPrincipal();
		
		List<MembershipUserList> list =	new ArrayList<MembershipUserList>();
		for(MemberListDTO m : member.getMemberListDTO()) {
			list.add((MembershipUserList) infoService.main(m.getMembershipCode()));	
		}
		for(int i =0; i < list.size(); i++) {
			list.get(i).setCount(list.get(i).getListCode());
		}

		
		// 내 등급별 클럽
		model.addAttribute("membership", list);

	
		return "mypage/myMembership";
	}

	// (동문) 비밀번호 확인후 update 페이지 이동
	@PostMapping("/updateCheck")
	public String updateCheck(String pwdCheck) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem = (Member) authentication.getPrincipal();

		boolean check = service.updateCheck(mem, pwdCheck);
		System.out.println("체크 확인" + check);
		if (check) {
			System.out.println("비밀번호 확인 완료!!!");
			return "mypage/updateMemberInfo";
		} else {
			System.out.println("비밀번호 오류!!!!");
			return "redirect:/updateCheck";
		}
		
		
	}
	
	/*
	 * 성철 로그인 X 한 상태에서 유저에게 ID랑 이메일 정보를 받아서 일치할시에 그 유저가 가입할때 넣은 이메일주소에 임시 비밀번호 발송 ->
	 * 암호화 -> DB변경 (이메일서비스)
	 */

	@ResponseBody
	@PostMapping("/updateMemberInfo")
	public boolean updateMemberInfo(Member vo, Model model, String addrDetail, String nickname) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem = (Member) authentication.getPrincipal();
		
		
		
		vo.setId(mem.getId());

		String addr = vo.getAddr();
		addr += "#" + addrDetail;
		vo.setAddr(addr);
		vo.setNickname(nickname);
		System.out.println("vo.getNickname : " + vo.getNickname()); // 닉네임 받아온거 확인

		// 닉네임 중복확인
		if (service.nicknameDupCheck(vo)) {
			System.out.println("닉네임 중복");
			return false;
		}
		
		service.addrUpdate(vo);
		service.updateMemberInfo(vo);
		
		System.out.println("updateMemberInfo" + vo); // 수정된 값 들어옴
		mem.setNickname(vo.getNickname());
		mem.setPwd(vo.getPwd());
		mem.setName(vo.getName());
		mem.setPhone(vo.getPhone());
		mem.setAddr(vo.getAddr());
		mem.setEmail(vo.getEmail());
		mem.setAge(vo.getAge());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return true;
	}
	
	@PostMapping("/memberDelete")
	public void memberDelete(Member member) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem = (Member) authentication.getPrincipal();
		
		System.out.println("memberDelete : " + mem); // mem 정보 받음
		
		
		
	}

	
	// 프로필, info 업데이트
	@ResponseBody
	@PostMapping("/updateMember")
	public String updateMember(String memberInfo, MultipartFile file)
			throws IllegalStateException, IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem = (Member) authentication.getPrincipal();
		
		System.out.println("멤버 파일 정보 : " + mem.getFile());
		System.out.println("업데이트멤버 멤버 정보: " + mem); // 멤버정보 확인
		// mem에 member정보 다 담겨져서 옴
		System.out.println("파일을 보냈나? : " + file.getOriginalFilename());
		System.out.println("null임? : " + file.getOriginalFilename().isEmpty());
		
		// 조건에 만약 보내준 링크가 null이면 변하지 않도록
		if (!file.getOriginalFilename().isEmpty()) {
			
			fileDelete(mem.getMemberImg(), mem.getId());
			String url = fileUpload(file, mem.getId());
			mem.setMemberImg(url);
			System.out.println("updateMember 삭제 : " + mem.getMemberImg() + mem.getId());
		}
		
		// 새 이미지 업데이트
		System.out.println("수정후 member 정보 : " + mem);
		service.updateMember(mem);
		
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "redirect:/mypage";
	}

	
	// // 성철 파일 업로드 각각 mamber의 id 폴더에 저장후 URL 리턴
	// public String fileUpload(MultipartFile file, String id) throws IllegalStateException, IOException {
	// 	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	// 	Member mem = (Member) authentication.getPrincipal();
		
	// 	if (file.getOriginalFilename() == "") {
    //     return "redirect:/"; // 인덱스 페이지로 리다이렉트
    // }
   
	/* 성철
	 * 파일 업로드 각각 mamber의 id 폴더에 저장후 URL 리턴
	 * */
	public String fileUpload(MultipartFile file,String id) throws IllegalStateException, IOException {
		if(file.getOriginalFilename() == "") {
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
	public String fileDelete(String fileName, String id) throws IllegalStateException, IOException {

		
		if (fileName == null || fileName.isEmpty()) {
			System.out.println("삭제할 파일이 없습니다");
		} else {
			System.out.println("삭제될 URL : " + fileName);
			File file = new File("\\\\192.168.10.51\\damoim\\member\\" + id + "\\" + fileName);
			file.delete();
		}
		service.selectImg(id);
		return "redirect:/mypage";
	}
	
	

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
			model.addAttribute("message", "임시 비밀번호가 이메일로 전송되었습니다.");
			System.out.println("비밀번호 변경 완료");
		} catch (Exception e) {
			model.addAttribute("message", "비밀번호 재설정에 실패했습니다.");
			System.out.println("비밀번호 변경 실패");
		}

		return "redirect:/"; // 인덱스 페이지로 리다이렉트
	}
}

/*
 * // kakao로그인 요청을 처리한다.
 * 
 * @PostMapping("/kakao-login") public String loginWithKakao(KakaoLoginForm
 * form){ log.info("카카오 로그인 인증정보:"+ form);
 * 
 * User user = User.builder() .email(form.getEmail()) .name(form.getName())
 * .img(form.getImg()) .loginType(KAKAO_LOGIN_TYPE) .build();
 * 
 * User savedUser = userService.loginWithKakao(user);
 * 
 * // 저장된 회원정보가 없으면 전달받은 회원정보를 세션에 저장, 있으면 기존 정보 저장. if(savedUser != null) {
 * SessionUtils.addAttribute("LOGIN_USER", savedUser); }else {
 * SessionUtils.addAttribute("LOGIN_USER", user); }
 * 
 * return "redirect:/"; }
 */
