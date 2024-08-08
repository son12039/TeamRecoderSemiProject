package com.damoim.service;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.vo.Member;


import mapper.MemberMapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

//xml -> Mapper -> service -> controller

@Service
public class MemberService {
	
	@Autowired
	private MemberMapper mapper;
	
	public Member login(Member member) {
		return mapper.login(member);
		
	}
	public ArrayList<MemberListDTO> loginMemberMembership(Member member){
		return mapper.loginMemberMembership(member);
		
	}
	
	@Transactional
	public void signUp(Member member) {
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
    
	public void update(Member member) {
		
		mapper.update(member);
	}
	
	public Member memberInfo(Member member) {
		return mapper.memberInfo();
	}
	
	
	
	
	
	
	
	
//	public ArrayList<Member> search(SearchDTO dto) {
//		return mapper.search(dto);
//	}
	
//	public ArrayList<Member> allMember() {
//		return mapper.allMember();
//	}
//    <!-- 검색 시도중!!!!! -->
//    <!--   <form action="/search">
//   <c:forEach items="${mem != null ? mem : search} var="member">
//      <input type="text" name = keyword >
//      <button type="submit" value="닉네임검색">닉네임 검색</button>
//      <h1>닉네임 : ${member.nickname}</h1>
//      <h1>나이 : ${member.age}</h1>
//      	</c:forEach>
//      </form> -->
//  	<!-- 검색 시도중!!!!! -->
	
}
