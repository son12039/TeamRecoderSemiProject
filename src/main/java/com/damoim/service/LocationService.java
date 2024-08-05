package com.damoim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoim.model.vo.LocationCategoryLarge;
import com.damoim.model.vo.LocationCategorySmall;

import mapper.locationCategoryMapper;

@Service
public class LocationService {
	@Autowired
	private locationCategoryMapper locationLargeMapper;
	
	//소분류 찾아내기
	
	public List<LocationCategorySmall> LocationCategoryLarge(int locLargeCode){
		return locationLargeMapper.allLocationSmall(locLargeCode);
	}
	
	// 대분류 리스트 뽑아내기
	public List<LocationCategoryLarge> allLocation(){
		return locationLargeMapper.allLocationLarge();
	}
}
