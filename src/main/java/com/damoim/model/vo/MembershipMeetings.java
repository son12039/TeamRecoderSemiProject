package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MembershipMeetings {
    private Integer meetCode; // 모임게시판코드
    private Integer membershipCode; // 클럽코드
    private java.sql.Date meetDateStart; // 모임 시작일
    private java.sql.Date meetDateEnd; // 모임 종료일
    private Integer meetAgreeCode; // 참여여부 테이블 연결
    private String meetInfo; // 모임관련 정보
    private java.sql.Date meetCreatDate; // 모임 생성일
}

