package com.damoim.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.damoim.model.vo.Member;
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
     * 성일
     * 로그인 시큐리티 처리
     * */

	/*
	  * 성철
	  * 회원가입 할때 아이디(프라이머리키 제약조건) 중복회원 체크 
	  * */	
	@ResponseBody
	@PostMapping("/idCheck")
	public boolean idCheck(Member member) {
		Member mem = service.idCheck(member);
		return mem == null;
		
	}
	/*
	  * 성철
	  * 회원가입 할때 닉네임(유니크 제약조건) 중복회원 체크 
	  * */
	@ResponseBody
	@PostMapping("/nicknameCheck") // 회원가입시 닉네임 중복 체크
	public boolean nicknameCheck(Member member) {
		Member mem = service.nicknameCheck(member);
		return mem == null;
			
	}
	/*
	  * 성철
	  * 회원가입 관련 재약조건문들 O 
	  * (나중에 추가가능 기능 -> 생년월일 받아서 나이 반환)
	  * (휴대폰 인증 관련 API? )
	  * */
	@PostMapping("/signUp") // 회원가입 메서드
	public String signUp(Member member ,String addrDetail , MultipartFile imgFile ) throws IOException {
		
		System.out.println("이미지 체크 : " + imgFile.getOriginalFilename());
		Member mem = member;
		String addr = mem.getAddr();
		addr += "#"+ addrDetail;
		mem.setAddr(addr);
		// 해당 id 이름의 회원 폴더 생성
		Path directoryPath = Paths.get("\\\\\\\\192.168.10.51\\\\damoim\\\\member\\"+ mem.getId()+"\\");  
		Files.createDirectories(directoryPath);
		member.setMemberImg(FileUpload(imgFile, mem.getId()));
		System.out.println("회원가입전 맴버 변수 체크 " + member);
		service.signUp(member);	
		
		System.out.println(member);
		return "redirect:/";
		
	}
	/*
	 * 성일
	 * 로그아웃 시큐리티
	 * */

	/*
	  * 성철
	  * 단순히 더미데이터 비밀번호 처리
	  * */
	@GetMapping("/dummyUpdate")
	public String dummyUpdate() {
		service.dummyUpdate();
		return "redirect:/";
	}
	/*
	 * 성철
	 * 내가 가입한 클럽을 가입된, 관리자or호스트, 가입대기중 클럽 조회가능한 페이지이동 
	 * */
	@GetMapping("/myMembership") // 내가 가입한 클럽확인
	public String myMembership(Member member, Model model) {
		
		// 내 등급별 클럽
		model.addAttribute("membership", infoService.listGrade(member));
		
		return "mypage/myMembership";
	}
	

	/*
	  * 성철
	  * 로그인 X 한 상태에서 유저에게 ID랑 이메일 정보를 받아서
	  * 일치할시에 그 유저가 가입할때 넣은 이메일주소에 임시 비밀번호 발송 -> 암호화 -> DB변경 (이메일서비스)
	  * 
	  * */

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
   
	/* 성철
	 * 파일 업로드 각각 mamber의 id 폴더에 저장후 URL 리턴
	 * */
	public String FileUpload(MultipartFile file,String id) throws IllegalStateException, IOException {
		if(file.getOriginalFilename() == "") {
			System.out.println("NULL 리턴");
			return null;
		}
		UUID uuid = UUID.randomUUID(); // 랜덤 파일명 부여
		String fileName = uuid.toString()+"_" + file.getOriginalFilename();
		File copyFile = new File("\\\\192.168.10.51\\damoim\\member\\"+ id + "\\" + fileName);
		file.transferTo(copyFile);
		System.out.println("파일1개 추가!");
		System.out.println("파일 이름 : " + fileName);
		return fileName;
	}
	/* 성철
	 * 파일 삭제 메서드 해당유저 프로필사진 변경시 사용!!
	 * 실 사용때는 조건에 만약 보내준 링크가 null이면 변하지 않도록
	 * */ 
	public void FileDelete(String file, String id) throws IllegalStateException, IOException {
		if(file == null) {
			System.out.println("삭제할 파일이 없습니다");
		}
		else {
			System.out.println("삭제될 URL : "  + file);
		File f = new File("\\\\192.168.10.51\\damoim\\seongchul\\"+ id + "\\" + file);
		f.delete();
		
		}
	
	}
}
	
		
	
	
	


