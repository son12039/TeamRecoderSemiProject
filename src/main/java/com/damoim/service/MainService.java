package com.damoim.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.damoim.model.vo.Main;

import mapper.MainMapper;
@Service
public class MainService {
	
	@Autowired
	private MainMapper mapper;
	
	public void mainCreate(Main main) {
		mapper.mainCreate(main);
	}
}
