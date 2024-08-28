package com.damoim.model.dto;

import com.damoim.model.vo.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @NoArgsConstructor @AllArgsConstructor @Data
public class ResignedDTO {
	
	private Member member;
	private int mainCommentCode;
	private String mainCommentText;
	
}
