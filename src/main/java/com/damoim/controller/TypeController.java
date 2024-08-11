package com.damoim.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.damoim.model.vo.LocationCategory;
import com.damoim.model.vo.TypeCategory;
import com.damoim.service.TypeService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TypeController {
	
	@Autowired
	private TypeService service;
	
	@GetMapping("/type")
	public String alltype(Model model) {
		model.addAttribute("allType", service.allType());
		return "location/type";
	}
	// Ajax
//	@ResponseBody
//	@GetMapping("/locationtype")
//	public List<String> locationSmall(String type,Model model) {
//	     List<String> list= new ArrayList<String>();
//	     List<TypeCategory> TypeLarge = service.TypeCategoryLarge(type);
//	     for( TypeCategory Large : TypeLarge) {
//	    	 list.add(Large.getTypeSName());
//	     }
//	     return list;
//	 }
}
