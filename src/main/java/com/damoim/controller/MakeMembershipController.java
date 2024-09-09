package com.damoim.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.damoim.service.LocationTypeService;

@Controller
public class MakeMembershipController {

	@Autowired
	private LocationTypeService locationTypeservice;
	
	@ResponseBody
	@PostMapping("/memLocation")
	public List<String> memLocation(String locationLaName) {		
		return locationTypeservice.locSNameList(locationLaName);
	}
	
	
	@ResponseBody
	@PostMapping("/memType")
	public List<String> memType(String typeLaName) {
		return locationTypeservice.typeSNameList(typeLaName);
	}
	
	
	
	
}
