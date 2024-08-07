package com.damoim.model.dto;

import com.damoim.model.vo.Member;
import com.damoim.model.vo.MembershipUserList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data @AllArgsConstructor @NoArgsConstructor
public class MemberListDTO {
	
		
	    private int membershipCode; // 코드
	    private String id; // 아이디
	    private String listGrade; // 등급
		
	}

