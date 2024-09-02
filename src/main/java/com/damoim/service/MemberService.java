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

	public ArrayList<MemberListDTO> loginMemberMembership(Member member) {
		return mapper.loginMemberMembership(member);

	}
	
	
	
	

	@Transactional
	public void signUp(Member member) {
		// 비밀번호 암호화
		member.setPwd(bcpe.encode(member.getPwd()));

		mapper.signUp(member);

	}

	public Member idCheck(Member member) {
		 
		return mapper.idCheck(member);

	}

	public Member nicknameCheck(Member member) {
		return mapper.nicknameCheck(member);

	}

	public Member pwdCheck(Member member) {

		return mapper.pwdCheck(member);
	}

	// 업데이트 관련 서비스 =======================================

	// 중요도 떨어지는 정보 수정 메서드
	// 일단 프로필이랑 info 정보만 수정
	public void updateMember(Member vo) {
		mapper.updateMember(vo);
	}
	


	// 2차인증
	public boolean updateCheck(Member vo, String pwdCheck) {

		if (bcpe.matches(pwdCheck, vo.getPwd())) {
			System.out.println("서비스에서 true 리턴!!!");
			return true;
		} else {
			System.out.println("서비스에서 false 리턴!!!");
			return false;
		}
	}

	// 비밀번호 재설정시 암호화
	public void updateMemberInfo(Member member) {
		member.setPwd(bcpe.encode(member.getPwd()));
		mapper.updateMemberInfo(member);
	}

	// 주소 업데이트
	public void addrUpdate(Member member) {
		mapper.addrUpdate(member);
	}

	// 닉네임 중복 체크
	public boolean nicknameDupCheck(Member vo) {
		return mapper.nicknameDupCheck(vo);
	}

	// 이미지 선택
	public Member selectMember(String string) {
		return mapper.selectMember(string);
	}

	// 회원탈퇴 서비스 ==================================================
	// 탈퇴시 status 조정
	public boolean memberStatus(Member member) {
		return mapper.memberStatus(member);
	}
	
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
	
	// ===============================================================
	public ArrayList<Member> dummyMember() {
		return mapper.dummyMember();

	}

	public void dummyUpdate() {
		ArrayList<Member> list = dummyMember();
		System.out.println(list);
		for (Member m : list) {
			m.setPwd(bcpe.encode(m.getPwd()));
			mapper.dummyUpdate(m);

		}
	}

	@Override // 로그인하면 일로옴
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
