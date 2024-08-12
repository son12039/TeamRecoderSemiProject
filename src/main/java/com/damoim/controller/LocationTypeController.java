package com.damoim.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.damoim.model.vo.LocationCategory;
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
//		model.addAttribute("allLocationLarge",service.allLocationLarge());
//		//위치 더미데이터
//		model.addAttribute("allLocation",service.allLocation());
//		//타입
//		model.addAttribute("allType", service.allType());
		
		System.out.println("왜 안나오냐고"+service.AllMembership()); // 안나오는 이유 프로펄티스 설정안해서 재대로 안나왔음
		return "location/LocationType";
	}
	
	
	// Ajax
	//위치
	@ResponseBody
	@GetMapping("locationClub")
	public List<String> locationClub(String locationDistinction) {
		List<String> list= new ArrayList<String>();
		List<LocationCategory> Location = service.locationDistinction(locationDistinction);
		for(LocationCategory small : Location) {
			list.add(small.getLocSName());
		}
		return list;
	}
	
	//타입
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
