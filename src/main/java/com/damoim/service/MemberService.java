package com.damoim.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.vo.Member;


import mapper.MemberMapper;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
