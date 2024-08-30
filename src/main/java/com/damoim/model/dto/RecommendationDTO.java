package com.damoim.model.dto;

import com.damoim.model.vo.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor @NoArgsConstructor @Builder @Data
public class RecommendationDTO {
	private Member targetMember;
	private Member loginMember;
	private boolean plusMinus;
}
