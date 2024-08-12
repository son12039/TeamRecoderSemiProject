package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Image {
    private int imgCode; // 사진코드
    private String imgUrl; // 사진 URL
    private int membershipCode; // 맴버쉽코드
    private int meetCode; // 모임게시판코드
    private MembershipMeetings membership_meetings;
    
}

