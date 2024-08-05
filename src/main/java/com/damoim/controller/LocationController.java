package com.damoim.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.damoim.model.vo.LocationCategoryLarge;
import com.damoim.service.LocationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LocationController {
	
	@Autowired
	private LocationService service;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	// 화면에 보이게 만들기	 
	 //화면 대분류 코드 추가
	 @GetMapping("location")
	 public String location( Model model) {
		model.addAttribute("allLocation",service.allLocation());
		return "/location";
	 }
	
//	 @GetMapping("/location")
//	    public String location(@RequestParam(value = "location", defaultValue = "unknown") String location) {
//		 System.out.println(location);
//		 return "Location";
//	 }
	
	
}
