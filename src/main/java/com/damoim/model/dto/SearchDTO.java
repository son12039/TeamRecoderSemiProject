package com.damoim.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Data @AllArgsConstructor @NoArgsConstructor
public class SearchDTO {
	
	private String keyword;
	private String select;
	private String nickname;
	private String id;
	private String name;
	//0814
	private List<Integer> membershipCodes;
	
	private String locationLaName;
	private List<String> locationSNameList;
	
}

