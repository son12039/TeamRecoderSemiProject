package com.damoim.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.damoim.model.vo.Main;

import mapper.MainMapper;

@Service
public class MainService {
	
	@Autowired
	private MainMapper mainMapper;

	public void mainCreate(Main main) {
		mainMapper.mainCreate(main);
	}
	

}
