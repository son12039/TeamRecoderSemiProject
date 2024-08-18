package com.damoim.model.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder @Data 
@NoArgsConstructor @AllArgsConstructor
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
	private String typeLaName;
	private List<String> typeSNameList;
}

