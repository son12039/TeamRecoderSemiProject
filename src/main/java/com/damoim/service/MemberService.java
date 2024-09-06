package com.damoim.service;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.damoim.model.dto.MemberInfoDTO;
import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.dto.MemberMannerDTO;
import com.damoim.model.dto.RecommendationDTO;
import com.damoim.model.vo.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mapper.MemberMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

//xml -> Mapper -> service -> controller

@Service
public class MemberService implements UserDetailsService {

	@Autowired
	private MemberMapper mapper;

	@Autowired
	private MembershipService infoService;

	@Autowired
	private PasswordEncoder bcpe;


	
	
	
	// 회원가입 
	@Transactional
	public void signUp(Member member) {	
		member.setPwd(bcpe.encode(member.getPwd()));// 비밀번호 암호화
		mapper.signUp(member);

	}
	// ID로 맴버정보
	public Member idCheck(Member member) {
		 
		return mapper.idCheck(member);

	}
	// 닉네임 으로 맴버정보
	public Member nicknameCheck(Member member) {
		return mapper.nicknameCheck(member);

	}
	// 이메일로 맴버정보
	public Member emailCheck(Member member) {
		return mapper.emailCheck(member);
	}




	// ID 찾기(이름, 이메일로 ID 보내줌)
	public String findMemberId(Member member) {
		return mapper.findMemberId(member).getId();
	}


	

	// 탈퇴 ,업데이트 관련 서비스 =======================================
	// 수정 필!!!!!!!
	// 중요도 떨어지는 정보 수정 메서드
	// 일단 프로필이랑 info 정보만 수정
	public void updateMember(Member vo) {
		mapper.updateMember(vo);
	}
	
	// 비밀번호 맞나 틀리나
	public boolean updateCheck(Member vo, String pwdCheck) {

		if (bcpe.matches(pwdCheck, vo.getPwd())) {
			return true;
		} else {
			return false;
		}
	}

	// 비밀번호 재설정시 암호화
	public void updateMemberInfo(Member member, String beforePwd) {
		
		
		
			
			member.setPwd(bcpe.encode(member.getPwd()));
			mapper.updateMemberInfo(member);
			
		
	}


	// 탈퇴시 status 조정
	
	public boolean memberStatus(Member member) {
		return mapper.memberStatus(member);
	}
	
	
	
	
	
	
	
	
	// 회원 추천 + 해당회원에 쿨타임 업데이트 ==================================
	public boolean memberManner(RecommendationDTO dto) {
		
		System.out.println("로그인한 회원의 마지막 정보" + dto.getLoginMember());
		if(dto.getLoginMember().getLastRecommendationTime() == null) {
		// 대상맴버의 매너 업데이트
		mapper.memberManner(MemberMannerDTO.builder()
					.member(dto.getTargetMember())
					.pulsMinus(dto.isPlusMinus())
					.build());
		// 로그인 회원의 마지막 추천시간 업데이트
		mapper.updateLastRecommendationTime(dto.getLoginMember().getId());
		return true;
		}
		return false;
	}
	
	// 더미 데이터 비밀번호 업데이트용 ===============================================================
	public ArrayList<Member> dummyMember() {
		return mapper.dummyMember();

	}
	// 더미 2
	public void dummyUpdate() throws IOException {
		ArrayList<Member> list = dummyMember();
		System.out.println(list);
		for (Member m : list) {
			Path directoryPath = Paths.get("\\\\\\\\192.168.10.51\\\\damoim\\\\member\\" + m.getId() + "\\");
			Files.createDirectories(directoryPath);
			m.setPwd(bcpe.encode(m.getPwd()));
			
			mapper.dummyUpdate(m);

		}
	}

	@Override // 로그인하면 일로옴 ====================
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = mapper.login(username);
		if (member == null) {
			System.out.println("사용자 정보를 찾지 못했습니다");
		} else if (!member.isStatus()){
			System.out.println("이미 탈퇴한 회원입니다 : " + username);
			return null;
		} else {
			System.out.println("로그인 성공! : " + username);
			member.setMemberListDTO(infoService.grade(mapper.login(username)));

		}

		return member;
	}

}
