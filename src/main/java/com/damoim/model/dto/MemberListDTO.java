package com.damoim.model.dto;

import com.damoim.model.vo.Member;
import com.damoim.model.vo.MembershipUserList;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class MemberListDTO {
	
	
	    private String membershipCode;
	    private String membershipName;
	    private String membershipImg;
	    private String membershipInfo;
	    private String membershipDate;
	    private String membershipGrade;
	    private int membershipMax;
	    private int count;
	    private Member host;

		
	}

