package com.damoim.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembershipDTO {

	private String membershipName;
	private String membershipInfo;
	private String membershipMax;
	private String id;
	private String listGrade;

}
