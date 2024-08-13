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
	
	
	// 업데이트 관련 서비스 =====================
	public Member updateCheck(Member member) {
		return mapper.updateCheck(member);
	}
	public void update(Member member) {
		 mapper.update(member);
	}
	public void addrUpdate(Member member) {
		 mapper.addrUpdate(member);
	}
	// ======================================
	
	
	
	
	/*
	// 카카오 로그인
	public User loginWithKakao(User user) {
		User savedUser = userMapper.getUserByEmail(user.getEmail());
		if(savedUser == null) {
			userMapper.addUser(user);
		}
		return savedUser;
	}
	*/
	
}
