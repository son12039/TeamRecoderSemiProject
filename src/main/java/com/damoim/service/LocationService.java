package com.damoim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoim.model.vo.LocationCategoryLarge;

import mapper.locationCategoryLargeMapper;

@Service
public class LocationService {
	@Autowired
	private locationCategoryLargeMapper locationMapper;
	
	public List<String> LocationCategoryLarge(){
		return locationMapper.LocationCategoryLarge();
	}
	
	public List<LocationCategoryLarge> allLocation(){
		return locationMapper.allLocation();
	}
}
