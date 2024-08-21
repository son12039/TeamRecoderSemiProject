package com.damoim.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotations.Search;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.damoim.model.dto.LocationTypeDTO;
import com.damoim.model.dto.MemberLocTypeDTO;
import com.damoim.model.dto.MembershipDTO;
import com.damoim.model.dto.SearchDTO;
import com.damoim.model.vo.LocationCategory;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.TypeCategory;
import com.damoim.service.LocationTypeService;
import com.damoim.service.MembershipService;

// 승훈 - 0814 수정

@Controller
public class LocationTypeController {
	
	@Autowired
	private LocationTypeService locationTypeservice;

	@Autowired
	private MembershipService memberService;
	
	public List<MemberLocTypeDTO> locationTypeList(SearchDTO search) {
		// '세종' 안에 null 이 아니면 '세종시' 넣고
		if(search.getLocationSName()!=null) {
			search.setLocationSNameList(new ArrayList<>(Arrays.asList(search.getLocationSName().split(","))));	
		}
		if(search.getTypeSName()!=null) {
			search.setTypeSNameList(new ArrayList<>(Arrays.asList(search.getTypeSName().split(","))));
		}


		List<Integer> membershipCodes = locationTypeservice.searchList(search);		
		
		List<MemberLocTypeDTO> list = new ArrayList<MemberLocTypeDTO>();
		
		//성일님이 만든거
		List<Integer> countList = new ArrayList(); // count 계산용 인덱스 번호담는 배열	
		for(int i = 0; i < memberService.allMembership().size(); i++) {
		int j = memberService.allMembership().get(i).getMembership().getMembershipCode();
		countList.add(memberService.membershipUserCount(j)); // 각각 클럽의 인원수 (신청자는 제외)
		}	
		
		
		// 모든 정보 합쳐서 리스트로 뿌리기
		if(membershipCodes.size()!=0) {
			search.setMembershipCodes(membershipCodes);
			list = locationTypeservice.memberLocTypeList(search);
			for(MemberLocTypeDTO dto : list) {
				//그리고 dto에 만든 리스트에 또 넣기
				List<LocationCategory> locations = locationTypeservice.locationList(dto.getMembershipCode());
				List<TypeCategory> types = locationTypeservice.typeList(dto.getMembershipCode());
				Member member = locationTypeservice.selectMemberNickName(dto.getMembershipCode());
				dto.setLocations(locations);
				dto.setTypes(types);
				
				dto.setNickname(member.getNickname());
				dto.setMemberImg(member.getMemberImg());
				dto.setId(member.getId());
				dto.setMemberLocation(member.getMemberLocation());
				dto.setMemberType(member.getMemberType());
				dto.setCountList(countList);
			}
		}
		return list;
		
	}
	
	
	// 화면단에 뿌리기
	@GetMapping("/")
	public String locationType(Model model, SearchDTO search) {
		
		model.addAttribute("list", locationTypeList(search));
		// 화면 상단바
		model.addAttribute("locLaNameList", locationTypeservice.locLaNameList());
		model.addAttribute("locSNameList",locationTypeservice.locSNameList(search.getLocationLaName()));
		model.addAttribute("typeLaNameList", locationTypeservice.typeLaNameList());
		model.addAttribute("typeSNameList",locationTypeservice.typeSNameList(search.getTypeLaName()));
		
		return "index";
	}
	
	@ResponseBody
	@GetMapping("list")
	public List<MemberLocTypeDTO> list(SearchDTO search) {	
		return locationTypeList(search);
	}
}
