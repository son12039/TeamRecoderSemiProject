package com.damoim.service;

import org.springframework.stereotype.Service;

import com.damoim.model.vo.SignUp;

import mapper.SignUpMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//xml -> Mapper -> service -> controller

@Service
public class SignUpService {
	
	@Autowired
	private SignUpMapper mapper;
	
	public void signUp(SignUp signUp) {
		mapper.register(signUp);
	}
		

	
	
}
