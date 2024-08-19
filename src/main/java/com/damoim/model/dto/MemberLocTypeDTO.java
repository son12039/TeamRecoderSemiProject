package com.damoim.model.dto;

import java.sql.Date;
import java.util.List;

import com.damoim.model.vo.LocationCategory;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.MembershipLocation;
import com.damoim.model.vo.MembershipType;
import com.damoim.model.vo.TypeCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author : 채승훈
 * date : 2024.08.14
 */

@Builder @Data 
@NoArgsConstructor @AllArgsConstructor
public class MemberLocTypeDTO {

	private int membershipCode; // 클럽코드
    private String membershipName; // 클럽이름
    private String membershipImg; // 클럽메인사진
    private String membershipInfo; // 클럽소개
    private Date membershipDate; // 클럽생성날짜
    private double membershipGrade; // 클럽 별점
    private int membershipMax; // 클럽최대인원
    
    private String nickname;
    private String memberImg;
	
    /*join*/
    private MembershipLocation membershipLocation;
    private MembershipType membershipType;
    
    private List<LocationCategory> locations;
    private List<TypeCategory> types;
}
