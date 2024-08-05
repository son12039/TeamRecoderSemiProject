package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.damoim.model.vo.Main;
import com.damoim.service.MainService;

@Controller
public class MainController {
	
	@Autowired
	private MainService service;
	
//	main 생성 post 받음
	@PostMapping("/mainCreate")
	public String mainCreate(Main vo) {
		service.mainCreate(vo);
		
		return "redirect:/";
	}
	
	
}









