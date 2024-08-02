package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Image {
    private int imgCode; // 사진코드
    private String imgUrl; // 사진 URL
    private int mainCode; // 홍보게시판코드
    private int meetCode; // 모임게시판코드
    private Main main; // 메인
    private MembershipMeetings membership_meetings;
    
}

