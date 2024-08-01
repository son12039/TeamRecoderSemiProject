package com.damoim.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoim.model.vo.Member;


import mapper.TestMapper;



@Service
public class MemberService {
	
	@Autowired
	private TestMapper Mapper;
	
	public void register(Member member) {
		Mapper.register(member);        
		
	}
	
	public Member login(Member member) {
		
		return Mapper.login(member);
	}
	

}
