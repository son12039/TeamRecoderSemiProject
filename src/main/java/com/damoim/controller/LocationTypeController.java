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

	
	// 08_14
	//새로 시작
	//대분류 리스트 보여주기
	@GetMapping("LocationType")
	public String LocationTpye(Model model, String laName,String typeName) {	
		/* 위치 영역 */
		SearchDTO search = new SearchDTO();
		System.out.println(laName);
		//<!-- 3. 로케이션 및 타입 리스트 뿌리기 -->
		if(laName!=null) {
			// 파라미터 값을 넣어서 지역에 관련된 code 뽑기
			List<Integer> searchLocLaNameList = service.searchLocLaNameList(laName);
			// 뽑아온 code 숫자들을 DTO list에 넣기
			search.setLocations(searchLocLaNameList);
		}
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
//		model.addAttribute("locSNameList",service.locSNameList(laName));
		System.out.println(service.locSNameList(laName));
		
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
