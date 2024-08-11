package com.damoim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoim.model.vo.LocationCategory;
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
	public List<LocationCategory> allLocation(){
		return Mapper.allLocation();
	}
	
	
	
	//타입
	public List<TypeCategory> allType(){
		return Mapper.allType();
	}
	public List<TypeCategory> TypeCategoryLarge(String type){
		return Mapper.TypeCategoryLarge(type);
	}
}
