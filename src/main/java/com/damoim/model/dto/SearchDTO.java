package com.damoim.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SearchDTO {
	
	private String keyword;
	private String select;
	private String nickname;
	private String id;
	private String name;
	//0814
	private List<Integer> locations;
	
}

