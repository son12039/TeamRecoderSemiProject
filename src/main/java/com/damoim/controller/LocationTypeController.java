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
import com.damoim.model.vo.Member;
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
	public String LocationType(Model model, String locationLaName,String locationSName,String typeLaName,String typeSName) {	
		//틀 만들고
		SearchDTO search = new SearchDTO();
		// null 이 아니면 "세종" 넣고
		if(locationLaName!=null) {
			search.setLocationLaName(locationLaName);
		}
		if(typeLaName!=null) {
			search.setTypeLaName(typeLaName);
		}
		// '세종' 안에 null 이 아니면 '세종시' 넣고
		if(locationSName!=null) {
			search.setLocationSNameList(new ArrayList<>(Arrays.asList(locationSName.split(","))));
		}
		if(typeSName!=null) {
			search.setTypeSNameList(new ArrayList<>(Arrays.asList(typeSName.split(","))));
		}

//		search.setNickname(service.selectMemberNickName());
		List<Integer> membershipCodes = service.searchList(search);
		
		List<MemberLocTypeDTO> list = null;
		if(membershipCodes.size()!=0) {
			search.setMembershipCodes(membershipCodes);
			list = service.memberLocTypeList(search);
			for(MemberLocTypeDTO dto : list) {
				//그리고 dto에 만든 리스트에 또 넣기
				List<LocationCategory> locations = service.locationList(dto.getMembershipCode());
				List<TypeCategory> types = service.typeList(dto.getMembershipCode());
				Member member = service.selectMemberNickName(dto.getMembershipCode());
				dto.setLocations(locations);
				dto.setTypes(types);
				dto.setNickname(member.getNickname());
				dto.setMemberImg(member.getMemberImg());
			}
		}
		
		// <!-- 1.화면 옵션에 도시 이름 전체 리스트 보여주기 -->

		model.addAttribute("list",list);
		model.addAttribute("locLaNameList", service.locLaNameList());
		model.addAttribute("locSNameList",service.locSNameList(locationLaName));
		model.addAttribute("typeLaNameList", service.typeLaNameList());
		model.addAttribute("typeSNameList",service.typeSNameList(typeLaName));

		return "location/LocationType";
	}

}
