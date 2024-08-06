package com.damoim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoim.model.vo.TypeCategory;

import mapper.typeCategoryMapper;

@Service
public class TypeService {
	
	@Autowired
	private typeCategoryMapper typemapper;
	
	public List<TypeCategory> allType(){
		return typemapper.allType();
	}
	
	public List<TypeCategory> allTypesmall(String typeSName){
		return typemapper.allSmallType(typeSName);
	}
}
