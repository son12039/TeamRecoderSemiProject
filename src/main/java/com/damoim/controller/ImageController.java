package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.damoim.service.ImageService;


@Controller
public class ImageController {
	
	
	@Autowired
	private ImageService image;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("Profile", image.profile());
		return "index";
	
	}
	
	
	
}
