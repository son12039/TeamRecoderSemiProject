package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class User {
    private Integer userCode; // 유저코드
    private String id; // 유저아이디
    private Double userManner; // 유저온도
    private String userName; // 닉네임
    private String userImg; // 유저프로필사진
    private String userHobby; // 유저 관심사
    private String userInfo; // 유저 간단한 자기소개
    private String userLocation; // 유저선호지역
    private String userType; // 유저 선호만남유형
}
