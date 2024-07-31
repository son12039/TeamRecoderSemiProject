package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Main {
    private Integer mainCode; // 홍보게시판코드
    private Integer membershipCode; // 클럽코드
    private String mainName; // 홍보글제목
    private String mainText; // 홍보내용
    private Integer mainViews; // 조회수
    private java.sql.Date mainDate; // 홍보글게시날짜
    private Integer userCode; // 유저 코드
}

