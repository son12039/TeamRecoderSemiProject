package com.damoim.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.damoim.model.vo.TypeCategory;
import com.damoim.service.LocationTypeService;

@Controller
public class LocationTypeController {
	
	@Autowired
	private LocationTypeService service;
	
	
	//위치
	//대분류 보야주기
	@GetMapping("LocationType")
	public String LocationTpye(Model model) {
		//위치
		model.addAttribute("allLocationLarge",service.allLocationLarge());
		model.addAttribute("allLocation",service.allLocation());
		//타입
		model.addAttribute("allType", service.allType());
		return "location/LocationType";
	}
	
	
	
	
	
	
	//타입
	// Ajax
	@ResponseBody
	@GetMapping("/locationtype")
	public List<String> locationSmall(String type,Model model) {
	     List<String> list= new ArrayList<String>();
	     List<TypeCategory> TypeLarge = service.TypeCategoryLarge(type);
	     for( TypeCategory Large : TypeLarge) {
	    	 list.add(Large.getTypeSName());
	     }
	     return list;
	 }
	
	
}
