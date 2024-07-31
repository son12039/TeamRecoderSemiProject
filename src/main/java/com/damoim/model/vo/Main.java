package com.damoim.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Main {
    private int mainCode; // 홍보게시판코드
    private int membershipCode; // 클럽코드
    private String mainName; // 홍보글제목
    private String mainText; // 홍보내용
    private int mainViews; // 조회수
    private Date mainDate; // 홍보글게시날짜
    private int userCode; // 유저 코드
}

