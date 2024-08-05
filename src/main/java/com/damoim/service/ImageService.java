package com.damoim.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoim.model.vo.Image;

import mapper.ImageMapper;

@Service
public class ImageService {
	
	@Autowired
	private ImageMapper image;
	
	public List<Image> profile() {
		return image.profile();
	}
	

}
