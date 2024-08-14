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
	private LocationTypeMapper Mapper;
	
	// 0814 - 전체 멤버십 리스트
	public List<MemberLocTypeDTO> memberLocTypeList(SearchDTO dto) {
		return Mapper.memberLocTypeList(dto);
	}
	
	public List<LocationCategory> locationList(int code) {
		return Mapper.locationList(code);
	}
	
	public List<TypeCategory> typeList(int code){
		return Mapper.typeList(code);
	}
	
	public List<String> locLaNameList() {
		return Mapper.locLaNameList();
	}
	
	public List<Integer> searchLocLaNameList(String location) {
		return Mapper.searchLocLaNameList(location);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//새로 만든 테이블
	
	// 대분류 위치 보여주기
	public List<Membership> AllMembershipLocationLaname(){
		return Mapper.AllMembershipLocationLaname();
	}
	// 소뷴류 위치 보여주기
	public List<Membership> AllMembershipLocationSname(String locLaName){
		return Mapper.AllMembershipLocationSname(locLaName);
	}
	// 대분류 타입 가져오기
	public List<Membership> AllMembershipTypeLaname(){
		return Mapper.AllMembershipTypeLaname();
	}
	
	public List<Membership> AllMembershipTypeSname(String typeLaName){
		return Mapper.AllMembershipTypeSname(typeLaName);
	}
	
	// 조건에 따라 리스트 보여주기 (DB 왔다갔다하면서 비교하기)
	public List<Membership> classification(LocationTypeDTO dto){
		return Mapper.classification(dto);	
	}
	
	
	
	//맴버쉽 모든거 보여주기
	public List<Membership> AllMembership(){
		return Mapper.AllMembership();
	}
	
	
	
}
