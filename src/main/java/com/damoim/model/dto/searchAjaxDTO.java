package com.damoim.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @Data 
@NoArgsConstructor @AllArgsConstructor
public class searchAjaxDTO {
	private String locationLaName;
	private List<String> locationSName;
	private String typeLaName;
	private List<String> typeSName;
}
