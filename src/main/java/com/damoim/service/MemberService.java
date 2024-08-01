package com.damoim.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.damoim.model.vo.Member;
import com.damoim.model.vo.User;

import mapper.MemberMapper;


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
	
	@Transactional
	public void signUp(User user, Member member) {
		mapper.signUp2(user);
		mapper.signUp(member);
		
	}	

	
	
}
