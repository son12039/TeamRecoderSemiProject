package com.damoim.service;

import org.springframework.stereotype.Service;

import com.damoim.model.vo.UserInfo;

import mapper.UserInfoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//xml -> Mapper -> service -> controller

@Service
public class UserInfoService {
	
	@Autowired
	private UserInfoMapper mapper;
	
	public void signUp(UserInfo signUp) {
		mapper.register(signUp);
	}
		

	
	
}
