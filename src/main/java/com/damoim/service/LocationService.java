package com.damoim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoim.model.vo.LocationCategory;

import mapper.locationCategoryMapper;

@Service
public class LocationService {
	@Autowired
	private locationCategoryMapper locationLargeMapper;
	
	//소분류 찾아내기
	
	public List<LocationCategory> LocationCategoryLarge(String locLargeName){
		return locationLargeMapper.allLocationSmall(locLargeName);
	}
	
	// 대분류 리스트 뽑아내기
	public List<LocationCategory> allLocation(){
		return locationLargeMapper.allLocationLarge();
	}
}
