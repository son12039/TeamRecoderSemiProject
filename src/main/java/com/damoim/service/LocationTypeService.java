package com.damoim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoim.model.dto.LocationTypeDTO;
import com.damoim.model.dto.MemberLocTypeDTO;
import com.damoim.model.dto.SearchDTO;
import com.damoim.model.vo.LocationCategory;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.TypeCategory;

import mapper.LocationTypeMapper;

@Service
public class LocationTypeService {

	@Autowired
	private LocationTypeMapper mapper;
	
	// 0814 - 전체 멤버십 리스트
	/* 위치 영역 */
	// 1.화면 옵션에 도시 이름 전체 리스트 보여주기
	public List<String> locLaNameList() {
		return mapper.locLaNameList();
	}
	// 도시 소분류 뽑기
	public List<String> locSNameList(String laName){
		return mapper.locSNameList(laName);
	}
	
	
	// 3. 파라미터 값을 받고 code 숫자 뽑아오기
	public List<Integer> searchLocLaNameList(String location) {
		return mapper.searchLocLaNameList(location);
	}
	
	
	public List<MemberLocTypeDTO> memberLocTypeList(SearchDTO dto) {
		return mapper.memberLocTypeList(dto);
	}
	
	public List<LocationCategory> locationList(int code) {
		return mapper.locationList(code);
	}
	
	public List<TypeCategory> typeList(int code){
		return mapper.typeList(code);
	}
	

	
	/* 타입 영역 */
	public List<String> typeLaNameList(){
		return mapper.typeLaNameList();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//새로 만든 테이블
	
	// 대분류 위치 보여주기
	public List<Membership> AllMembershipLocationLaname(){
		return mapper.AllMembershipLocationLaname();
	}
	// 소뷴류 위치 보여주기
	public List<Membership> AllMembershipLocationSname(String locLaName){
		return mapper.AllMembershipLocationSname(locLaName);
	}
	// 대분류 타입 가져오기
	public List<Membership> AllMembershipTypeLaname(){
		return mapper.AllMembershipTypeLaname();
	}
	
	public List<Membership> AllMembershipTypeSname(String typeLaName){
		return mapper.AllMembershipTypeSname(typeLaName);
	}
	
	// 조건에 따라 리스트 보여주기 (DB 왔다갔다하면서 비교하기)
	public List<Membership> classification(LocationTypeDTO dto){
		return mapper.classification(dto);	
	}
	
	
	
	//맴버쉽 모든거 보여주기
	public List<Membership> AllMembership(){
		return mapper.AllMembership();
	}
	
	
	
}
