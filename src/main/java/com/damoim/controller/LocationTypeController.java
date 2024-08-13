package com.damoim.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.damoim.model.dto.LocationTypeDTO;
import com.damoim.model.vo.LocationCategory;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.TypeCategory;
import com.damoim.service.LocationTypeService;

@Controller
public class LocationTypeController {
	
	@Autowired
	private LocationTypeService service;
	
	
	//새로 시작
	//대분류 리스트 보여주기
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
	// 소분류 뽑아내서 옵션에 집어넣기
	@ResponseBody
	@GetMapping("locationSList")
	public ResponseEntity<Map<String, Object>> LocationSList(LocationTypeDTO dto){

		// 소분류 위치값 ex)서울 -> 강남,왕십리, ..
		//상단 위치 리스트 보여주기
		List<String> sLocation = new ArrayList();
		List<Membership> sLocationMember = service.AllMembershipLocationSname(dto.getLocLaName());
		for(Membership m : sLocationMember) {
			sLocation.add(m.getMembershipLocation().getLocationCategory().getLocSName());
		}
		
		List<LocationTypeDTO> LaLocation = new ArrayList();
		List<Membership> LaLocationMember = service.classification(dto);
		System.out.println(LaLocationMember);
		for(Membership m : LaLocationMember) {
			LocationTypeDTO typeDTO = LocationTypeDTO.builder()
					.locLaName(m.getMembershipLocation().getLocationCategory().getLocLaName())
					.locSName(m.getMembershipLocation().getLocationCategory().getLocSName())
					.typeLaName(m.getMembershipType().getTypeCategory().getTypeLaName())
					.typeSName(m.getMembershipType().getTypeCategory().getTypeSName())
					.build();
			LaLocation.add(typeDTO);
		}
		System.out.println(LaLocation);
		
		
		Map<String, Object> response = new HashMap<>();
		response.put("sLocation", sLocation);
		response.put("LaLocation", LaLocation);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 대분류 뽑아내서 옵션에 집어넣기
	@ResponseBody
	@GetMapping("typeSList")
	public List<String> TypeSList(LocationTypeDTO dto) {
		//상단 위치 보여주기
		List<String> sType = new ArrayList();
		List<Membership> sTypeMember = service.AllMembershipTypeSname(dto.getTypeLaName()); 
		for(Membership m : sTypeMember) {
			sType.add(m.getMembershipType().getTypeCategory().getTypeSName());
		}
		
		List<String> LaLocation = new ArrayList();
		List<Membership> LaLocationMember = service.classification(dto);
		System.out.println(LaLocationMember);
		for(Membership m : LaLocationMember) {
			LaLocation.add(m.getMembershipLocation().getLocationCategory().getLocLaName());
			LaLocation.add(m.getMembershipType().getTypeCategory().getTypeLaName());
		}
		System.out.println(LaLocation);
		return sType;
		//return null;
	}
	
	
	// 이제 클릭시 분류 해놓기
	@ResponseBody
	@GetMapping("classificationLaLocation")
	public void classificationLa(String locationLaName) {
		
	}
	
	
//	@ResponseBody
//	@GetMapping("classificationSLocation")
//	public void classificationS(String typeLaName) {
//		dto.setTypeLaName(typeLaName);
//		service.classification(dto);
//		System.out.println(dto);
//		List<String> LaLocation = new ArrayList();
//		List<Membership> LaLocationMember = service.classification(dto);
//		for(Membership m : LaLocationMember) {
//			LaLocation.add(m.getMembershipLocation().getLocationCategory().getLocLaName());
//			LaLocation.add(m.getMembershipType().getTypeCategory().getTypeLaName());
//		}
//		System.out.println(LaLocation);
//	}
	
	
	
	
	
	
	
	
	
	
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
