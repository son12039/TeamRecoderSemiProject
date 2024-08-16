package com.damoim.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.damoim.model.dto.MemberLocTypeDTO;
import com.damoim.model.dto.SearchDTO;
import com.damoim.model.vo.LocationCategory;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.TypeCategory;
import com.damoim.service.LocationTypeService;

// 승훈 - 0814 수정

@Controller
public class LocationTypeController {
	
	@Autowired
	private LocationTypeService service;

	
	// 08_14
	//새로 시작
	//대분류 리스트 보여주기
	@GetMapping("LocationType")
	public String LocationType(Model model, String locationLaName,String locationSName) {	
		/* 위치 영역 */
		SearchDTO search = new SearchDTO();
		
		if(locationLaName!=null) {
			search.setLocationLaName(locationLaName);
		}
		
		if(locationSName!=null) {
			search.setLocationSNameList(new ArrayList<>(Arrays.asList(locationSName.split(","))));
		}
		
		List<Integer> membershipCodes = service.searchList(search);
		search.setMembershipCodes(membershipCodes);
		
		// 뽑아온 숫자를 보내서 code에 관련된 member code 뽑아서 저장
		List<MemberLocTypeDTO> list = service.memberLocTypeList(search);
		
		for(MemberLocTypeDTO dto : list) {
			//그리고 dto에 만든 리스트에 또 넣기
			List<LocationCategory> locations = service.locationList(dto.getMembershipCode());
			List<TypeCategory> types = service.typeList(dto.getMembershipCode());
			dto.setLocations(locations);
			dto.setTypes(types);
		}
		
		//System.out.println(list);
		
		// <!-- 1.화면 옵션에 도시 이름 전체 리스트 보여주기 -->
		model.addAttribute("list",list);
		model.addAttribute("locLaNameList", service.locLaNameList());
		model.addAttribute("locSNameList",service.locSNameList(locationLaName));
		
		//model.addAttribute("allMember",service.AllMembership());
		//System.out.println("size : " + service.AllMembership().size());
		
		// 대분류 위치값 ex)서울,부산,경기 ...
		//List<LocationCategory> laLocation = new ArrayList();
		//List<Membership> laLocationMember = service.AllMembershipLocationLaname();
		//for(Membership m : laLocationMember) {
			
			//laLocation.add(m.getMembershipLocation().getLocationCategory());
		//}
	//	model.addAttribute("allMemberLaList",laLocation);
		
		// 대분류 타입값 ex)액티비티,취미 ...
		//List<TypeCategory> laTypeList = new ArrayList();
	//	List<Membership> laTypeMember = service.AllMembershipTypeLaname();
	//	for(Membership m : laTypeMember) {
			//laTypeList.add(m.getMembershipType().getTypeCategory());
	//	}
	//	model.addAttribute("allMemberLaType",laTypeList);
		/* 타입 영역 */
		model.addAttribute("typeLaNameList", service.typeLaNameList());
		
		
		

		return "location/LocationType";
	}

}
