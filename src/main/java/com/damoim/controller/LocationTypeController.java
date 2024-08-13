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
	
	/*
	 * 완성했다 생각했지만 반대로 문제가 하나씩 보이기 시작함
	 * 
	 * 1. 대분류 애들은 바로 취소할수있어서 상관없지만
	 * 	  그렇게되면 소분류 애들은 데이터가 남아져있어서 클럽테이블에 영향이 감 // 심각함 ***
	 * 		ㄴ 이놈이 제일 악질임
	 * 			ㄴ 아까 컨트롤에서 해봤지만 아닌거같음 js파일에서 해야할거같음	
	 * 				ㄴ 이거소 조건문 달면 해결 될거같으면서도 힘들거같음
	 * 					ㄴ 아니면 ---- 이 부분을 전체보기로 만들고 DB에 조건을 다시 거는거임
	 * 						ㄴ 소분류 애들도 처음엔 ----- 이렇게 보이다가 값이 들어오면 그때 전체보기,강남,이태리,용산... 이렇게 나오면 좋을거같음
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
	// 소분류 뽑아내서 옵션에 집어넣기 and 대분류 클럽 분류하기
	@ResponseBody
	@GetMapping("locationLaList")
	public ResponseEntity<Map<String, Object>> locationLaList(LocationTypeDTO dto){
		System.out.println(dto);
		// 소분류 위치값 ex)서울 -> 강남,왕십리, ..
		//상단 위치 리스트 보여주기
		List<String> sLocation = new ArrayList();
		List<Membership> sLocationMember = service.AllMembershipLocationSname(dto.getLocLaName());
		for(Membership m : sLocationMember) {
			sLocation.add(m.getMembershipLocation().getLocationCategory().getLocSName());
		}
		
		//분류 타입 나누기
		//System.out.println(dto); //  LocationTypeDTO(locLaName=부산, locSName=, typeLaName=, typeSName=) 방식으로 DB까지 와서
		List<LocationTypeDTO> LaLocation = new ArrayList();
		List<Membership> LaLocationMember = service.classification(dto);
		for(Membership m : LaLocationMember) {
			LocationTypeDTO LocationLaDTO = LocationTypeDTO.builder()
					.locLaName(m.getMembershipLocation().getLocationCategory().getLocLaName())
					.locSName(m.getMembershipLocation().getLocationCategory().getLocSName())
					.typeLaName(m.getMembershipType().getTypeCategory().getTypeLaName())
					.typeSName(m.getMembershipType().getTypeCategory().getTypeSName())
					.build();
			LaLocation.add(LocationLaDTO);
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
		System.out.println(dto);
		List<LocationTypeDTO> sLocation = new ArrayList();
		List<Membership> sLocationMember = service.classification(dto);
		for(Membership m : sLocationMember) {
			LocationTypeDTO LocationSDTO = LocationTypeDTO.builder()
					.locLaName(m.getMembershipLocation().getLocationCategory().getLocLaName())
					.locSName(m.getMembershipLocation().getLocationCategory().getLocSName())
					.typeLaName(m.getMembershipType().getTypeCategory().getTypeLaName())
					.typeSName(m.getMembershipType().getTypeCategory().getTypeSName())
					.build();
			sLocation.add(LocationSDTO);
		}
		return sLocation;
	}
	
	
	
	// 소분류 뽑아내서 옵션에 집어넣기 클럽 분류하기
	@ResponseBody
	@GetMapping("typeLaList")
	public ResponseEntity<Map<String,Object>> typeLaList(LocationTypeDTO dto) {
		System.out.println(dto);
		//상단 위치 보여주기
		List<String> sType = new ArrayList();
		List<Membership> sTypeMember = service.AllMembershipTypeSname(dto.getTypeLaName()); 
		for(Membership m : sTypeMember) {
			sType.add(m.getMembershipType().getTypeCategory().getTypeSName());
		}
		
		//분류 타입 나누기
		List<LocationTypeDTO> LaType = new ArrayList();
		List<Membership> LaTypeMember = service.classification(dto);
		for(Membership m : LaTypeMember) {
			LocationTypeDTO typeLaDTO = LocationTypeDTO.builder()
					.locLaName(m.getMembershipLocation().getLocationCategory().getLocLaName())
					.locSName(m.getMembershipLocation().getLocationCategory().getLocSName())
					.typeLaName(m.getMembershipType().getTypeCategory().getTypeLaName())
					.typeSName(m.getMembershipType().getTypeCategory().getTypeSName())
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
			LocationTypeDTO sTypeDTO = LocationTypeDTO.builder()
					.locLaName(m.getMembershipLocation().getLocationCategory().getLocLaName())
					.locSName(m.getMembershipLocation().getLocationCategory().getLocSName())
					.typeLaName(m.getMembershipType().getTypeCategory().getTypeLaName())
					.typeSName(m.getMembershipType().getTypeCategory().getTypeSName())
					.build();
			sType.add(sTypeDTO);
		}
		return sType;
	}
	
	


	
	
}
