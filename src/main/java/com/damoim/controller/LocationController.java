package com.damoim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LocationController {
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	 @GetMapping("location")
	    public String location(@RequestParam(value = "location", required = false, defaultValue = "Unknown") String location) {
		 	
		 	int a = 0;
		 	
	        if ("서울".equals(location)) {
	            a = 1;
	        } else if ("경기".equals(location)) {
	            System.out.println("경기가 선택되었습니다.");
	        } else if ("인천".equals(location)) {
	            System.out.println("인천이 선택되었습니다.");
	        } else {
	            System.out.println("알 수 없는 지역이 선택되었습니다.");
	        }
	        return "Location"; // "Location"은 반환할 뷰의 이름입니다.
	    }
}
