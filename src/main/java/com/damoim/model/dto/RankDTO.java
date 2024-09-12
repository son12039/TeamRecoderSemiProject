package com.damoim.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data @Builder
public class RankDTO {
	
	private String nickname;
	private double memberManner;
	private int meetCount;
	private int rank;
	
}
