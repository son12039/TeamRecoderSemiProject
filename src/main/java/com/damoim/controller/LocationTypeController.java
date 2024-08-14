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
	
	/*
	 * 완성했다 생각했지만 반대로 문제가 하나씩 보이기 시작함
	 * 
	 * 2. 아무것도 클릭 안하게 되면 이상하게 더 중복된? 아니면 더 생성된? 애들이 나와서 더 길어짐
	 * 		ㄴ 이거는 방식을 화면에서 하는게 아니라 제이쿼리에서 뿌려주면 뭔가 해결될거같음
	 * 			ㄴ 그냥 통으로 잡아서 조건문 걸면 끝날거같음
	 * 
	 * 3. 만약에 아무것도 정보 검색이 안된다면 그대로 없어짐
	 * 		ㄴ 상관은 없지만 정보검색이 없다면 화면 중아에서 "검색결과 없음" 뜨게 만들면 좋을거같음 // 심각함 **
	 * 			ㄴ 이것도 2번 조건 걸때 같이 하면 해결 될거같음
	 * 
	 * 4. 나중에 옵션이 아니라 div로 만들고 click이벤트로 넣을때 배열로 받으면서
	 * 	  [서울, 부산] 이렇게 출력이 되면 DB에서 조건 걸면 될거같음 
	 * 		ㄴ 이것도 쉬울거같음 
	 * 			ㄴ 문제는 div로 리스트 형식 만들고 css 꾸미가 빡셀거같음 :(
	 * 
	 * 
	 * */
	
	
	
	//새로 시작
	//대분류 리스트 보여주기
	@GetMapping("LocationType")
	public String LocationTpye(Model model, String laName) {	
		
		SearchDTO search = new SearchDTO();
		
		if(laName!=null) {
			List<Integer> searchLocLaNameList = service.searchLocLaNameList(laName);
			search.setLocations(searchLocLaNameList);
		}
		
		List<MemberLocTypeDTO> list = service.memberLocTypeList(search);
		
		for(MemberLocTypeDTO dto : list) {
			List<LocationCategory> locations = service.locationList(dto.getMembershipCode());
			List<TypeCategory> types = service.typeList(dto.getMembershipCode());
			dto.setLocations(locations);
			dto.setTypes(types);
		}
		
		//System.out.println(list);
		
		model.addAttribute("list", list);
		model.addAttribute("locLaNameList", service.locLaNameList());
		
		
		
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
		

		return "location/LocationType";
	}
	
	
	// Ajax 
	// 소분류 뽑아내서 옵션에 집어넣기 and 대분류 클럽 분류하기
	@ResponseBody
	@GetMapping("locationLaList")
	public ResponseEntity<Map<String, Object>> locationLaList(LocationTypeDTO dto){
		 
		if(dto.getLocLaName().equals("")) {
			dto.setLocSName("");
		}
		System.out.println(dto+" 대");
		// 소분류 위치값 ex)서울 -> 강남,왕십리, ..
		//상단 위치 리스트 보여주기
		List<String> sLocation = new ArrayList();
		List<Membership> sLocationMember = service.AllMembershipLocationSname(dto.getLocLaName());
		for(Membership m : sLocationMember) {
			//sLocation.add(m.getMembershipLocation().getLocationCategory().getLocSName());
		}
		
		//분류 타입 나누기
		//System.out.println(dto); //  LocationTypeDTO(locLaName=부산, locSName=, typeLaName=, typeSName=) 방식으로 DB까지 와서
		List<LocationTypeDTO> LaLocation = new ArrayList();
		List<Membership> LaLocationMember = service.classification(dto);
		for(Membership m : LaLocationMember) {
			//LocationTypeDTO LocationLaDTO = LocationTypeDTO.builder()
				//	.locLaName(m.getMembershipLocation().getLocationCategory().getLocLaName())
					//.locSName(m.getMembershipLocation().getLocationCategory().getLocSName())
				//	.typeLaName(m.getMembershipType().getTypeCategory().getTypeLaName())
				//	.typeSName(m.getMembershipType().getTypeCategory().getTypeSName())
				//	.build();
			//LaLocation.add(LocationLaDTO);
		}
//		System.out.println(LaLocation); [LocationTypeDTO(locLaName=서울, locSName=강동구, typeLaName=스터디, typeSName=코딩)..

		Map<String, Object> response = new HashMap<>();
		response.put("sLocation", sLocation);
		response.put("LaLocation", LaLocation);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	// 소분류 클럽 분류하기
	@ResponseBody
	@GetMapping("locationSList")
	public List<LocationTypeDTO> locationSList(LocationTypeDTO dto) {
		System.out.println(dto+" 소");
		List<LocationTypeDTO> sLocation = new ArrayList();
		List<Membership> sLocationMember = service.classification(dto);
		for(Membership m : sLocationMember) {
			//LocationTypeDTO LocationSDTO = LocationTypeDTO.builder()
			//		.locLaName(m.getMembershipLocation().getLocationCategory().getLocLaName())
			//		.locSName(m.getMembershipLocation().getLocationCategory().getLocSName())
				//	.typeLaName(m.getMembershipType().getTypeCategory().getTypeLaName())
				//	.typeSName(m.getMembershipType().getTypeCategory().getTypeSName())
			//		.build();
			//sLocation.add(LocationSDTO);
		}
		return sLocation;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	// 소분류 뽑아내서 옵션에 집어넣기 클럽 분류하기
	@ResponseBody
	@GetMapping("typeLaList")
	public ResponseEntity<Map<String,Object>> typeLaList(LocationTypeDTO dto) {
		if(dto.getTypeLaName().equals("")) {
			dto.setTypeSName("");
		}
		System.out.println(dto);
		System.out.println(dto);
		//상단 위치 보여주기
		List<String> sType = new ArrayList();
		List<Membership> sTypeMember = service.AllMembershipTypeSname(dto.getTypeLaName()); 
		for(Membership m : sTypeMember) {
			//sType.add(m.getMembershipType().getTypeCategory().getTypeSName());
		}
		
		//분류 타입 나누기
		List<LocationTypeDTO> LaType = new ArrayList();
		List<Membership> LaTypeMember = service.classification(dto);
		for(Membership m : LaTypeMember) {
			LocationTypeDTO typeLaDTO = LocationTypeDTO.builder()
				//	.locLaName(m.getMembershipLocation().getLocationCategory().getLocLaName())
				//	.locSName(m.getMembershipLocation().getLocationCategory().getLocSName())
				//	.typeLaName(m.getMembershipType().getTypeCategory().getTypeLaName())
				//	.typeSName(m.getMembershipType().getTypeCategory().getTypeSName())
					.build();
			LaType.add(typeLaDTO);
		}
		Map<String,Object> response = new HashMap<>();
		response.put("sType", sType);
		response.put("LaType", LaType);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping("typeSList")
	public List<LocationTypeDTO> typeSList(LocationTypeDTO dto) {
		System.out.println(dto);
		List<LocationTypeDTO> sType = new ArrayList();
		List<Membership> sTypeMember = service.classification(dto);
		for(Membership m : sTypeMember) {
			//LocationTypeDTO sTypeDTO = LocationTypeDTO.builder()
					//.locLaName(m.getMembershipLocation().getLocationCategory().getLocLaName())
					//.locSName(m.getMembershipLocation().getLocationCategory().getLocSName())
				//	.typeLaName(m.getMembershipType().getTypeCategory().getTypeLaName())
					//.typeSName(m.getMembershipType().getTypeCategory().getTypeSName())
				//	.build();
			//sType.add(sTypeDTO);
		}
		return sType;
	}
	
	


	
	
}
