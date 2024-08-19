package com.damoim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoim.model.dto.LocationTypeDTO;
import com.damoim.model.dto.MemberLocTypeDTO;
import com.damoim.model.dto.SearchDTO;
import com.damoim.model.vo.LocationCategory;
import com.damoim.model.vo.Member;
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
	/* 타입 영역 */
	public List<String> typeLaNameList(){
		return mapper.typeLaNameList();
	}
	public List<String> typeSNameList(String typeLaName){
		return mapper.typeSNameList(typeLaName);
	}
	
	
	
	
	// 3. 파라미터 값을 받고 code 숫자 뽑아오기
	public List<Integer> searchList(SearchDTO dto) {
		return mapper.searchList(dto);
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
	

	// 맴버닉네임 뽑아내기
	public Member selectMemberNickName(int code) {
		return mapper.selectMemberNickName(code);
	}
	
}
