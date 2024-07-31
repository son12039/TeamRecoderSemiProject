package com.damoim.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoim.model.vo.SignUp;


import mapper.TestMapper;



@Service
public class SignUpService {
	
	@Autowired
	private TestMapper Mapper;
	
	public void register(SignUp signup) {
		Mapper.register(signup);        
		
	}
	
	

}
