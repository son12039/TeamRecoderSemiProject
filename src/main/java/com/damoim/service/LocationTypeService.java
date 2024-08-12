package com.damoim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoim.model.vo.LocationCategory;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.TypeCategory;

import mapper.LocationTypeMapper;

@Service
public class LocationTypeService {

	@Autowired
	private LocationTypeMapper Mapper;
	
	//위치
	//대분류 보야주기
	public List<LocationCategory> allLocationLarge(){
		return Mapper.allLocationLarge();
	}
	//all 클럽 위치 더미
	public List<LocationCategory> allLocation(){
		return Mapper.allLocation();
	}
	//소분류 찾아주기
	public List<LocationCategory> locationDistinction(String locationDistinction){
		return Mapper.locationDistinction(locationDistinction);
	}
	
	
	//타입
	public List<TypeCategory> allType(){
		return Mapper.allType();
	}
	public List<TypeCategory> TypeCategoryLarge(String type){
		return Mapper.TypeCategoryLarge(type);
	}
	
	
	
	
	//맴버쉽 모든거 보여주기
	public List<Membership> AllMembership(){
		System.out.println(Mapper.AllMembership());
		return Mapper.AllMembership();
	}
	
}
