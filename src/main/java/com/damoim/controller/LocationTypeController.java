package com.damoim.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.damoim.model.vo.LocationCategory;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.TypeCategory;
import com.damoim.service.LocationTypeService;

@Controller
public class LocationTypeController {
	
	@Autowired
	private LocationTypeService service;
	
	
	//새로 시작

	@GetMapping("LocationType")
	public String LocationTpye(Model model) {		
		model.addAttribute("allMember",service.AllMembership());
		
		// 대분류 위치값 ex)서울,부산,경기 ...
		List<LocationCategory> laLocation = new ArrayList();
		List<Membership> laLocationMember = service.AllMembershipLocationLaname();
		for(Membership m : laLocationMember) {
			laLocation.add(m.getMembershipLocation().getLocationCategory());
		}
		model.addAttribute("allMemberLaList",laLocation);
		
		// 대분류 타입값 ex)액티비티,취미 ...
		List<TypeCategory> laTypeList = new ArrayList();
		List<Membership> laTypeMember = service.AllMembershipTypeLaname();
		for(Membership m : laTypeMember) {
			laTypeList.add(m.getMembershipType().getTypeCategory());
		}
		model.addAttribute("allMemberLaType",laTypeList);
		
		
		
		return "location/LocationType";
	}
	
	
	// Ajax 
	@ResponseBody
	@GetMapping("locationSList")
	public List<String> LocationSList(String locationLaName){
		// 소분류 위치값 ex)서울 -> 강남,왕십리, ..
		List<String> sLocation = new ArrayList();
		List<Membership> sLocationMember = service.AllMembershipLocationSname(locationLaName);
		for(Membership m : sLocationMember) {
			sLocation.add(m.getMembershipLocation().getLocationCategory().getLocSName());
		}
		return sLocation;
	}
	
	@ResponseBody
	@GetMapping("typeSList")
	public List<String> TypeSList(String typeLaName) {
		System.out.println(service.AllMembershipTypeSname(typeLaName));
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//위치
//	model.addAttribute("allLocationLarge",service.allLocationLarge());
//	//위치 더미데이터
//	model.addAttribute("allLocation",service.allLocation());
//	//타입
//	model.addAttribute("allType", service.allType());
	
	// Ajax
	//위치
//	@ResponseBody
//	@GetMapping("locationClub")
//	public List<String> locationClub(String locationDistinction) {
//		List<String> list= new ArrayList<String>();
//		List<LocationCategory> Location = service.locationDistinction(locationDistinction);
//		for(LocationCategory small : Location) {
//			list.add(small.getLocSName());
//		}
//		return list;
//	}
//	
//	//타입
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
//	
	
	
	
}
