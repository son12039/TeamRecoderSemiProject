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
	private String locationSName;
	private String typeLaName;
	private String typeSName;
	
	private List<String> locationSNameList;
	private List<String> typeSNameList;
	

    // 인덱스 페이징 처리
    private int page = 1; 	//현재 페이지
	private int offset = 0;	//시작위치
	private int limit = 12; //레코드 수
	
}

