package com.damoim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



public class SignUpService {
	
	@Service
	public class MemberService {
		
		@Autowired
		private Sign_up mapper;
		
		public void register(Member member) {
			mapper.register(member);
		}
		

	
	
}
