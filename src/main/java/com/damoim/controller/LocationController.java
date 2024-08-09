package com.damoim.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.damoim.model.vo.LocationCategory;
import com.damoim.service.LocationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LocationController {
	
	@Autowired
	private LocationService service;
	
	
	// 전체 테이블 보이는 기능
	// 화면에 보이게 만들기	 
	 //화면 대분류 코드 추가
	 @GetMapping("location")
	 public String location( Model model) {
		model.addAttribute("allLocation",service.allLocation());
		return "location/location";
	 }
	 // Ajax
	 @ResponseBody
	 @GetMapping("/locationCategory")
	 public List<String> locationSmall(String location,Model model) {
	     List<String> list= new ArrayList<String>();
	     List<LocationCategory> locationLarge = service.LocationCategoryLarge(location);
	     for( LocationCategory Large : locationLarge) {
	    	 list.add(Large.getLocSName());
	     }
	     return list;
	 }

	 
	 
	 

	@GetMapping("/location2")
	public String location2(Model model) {
		model.addAttribute("allLocation",service.allLocation());
		return "location/location2";
	}
	 
}
