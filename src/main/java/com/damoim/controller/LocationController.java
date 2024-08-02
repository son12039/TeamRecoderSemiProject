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
	
	 @GetMapping("/location")
	 public String location(Model model) {
		 model.addAttribute("allLocation",service.allLocation());
	 return "Location";
	 }
	
	 @GetMapping("/locations")
	 public String location(@RequestParam("location") String locLargeCode, Model model) {
		System.out.println(locLargeCode);
		model.addAttribute("LocationCategoryLarge", service.LocationCategoryLarge());
		return "Location";
	 }
	
//	 @GetMapping("/location")
//	    public String location(@RequestParam(value = "location", defaultValue = "unknown") String location) {
//		 System.out.println(location);
//		 return "Location";
//	 }
	
	
}
