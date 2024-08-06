package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.damoim.service.TypeService;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class TypeController {
	
	@Autowired
	private TypeService service;
	
	@GetMapping("/type")
	public String alltype(Model model) {
		model.addAttribute("allType", service.allType());
		return "location/type";
	}
	
	@GetMapping("/locationtype")
	public String allTypeSmall(@RequestParam("type") String typeSName, Model model) {
		model.addAttribute("typeSmallList", service.allTypesmall(typeSName));
		return "location/typeSmall";
	}
}
