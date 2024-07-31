package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MainComment {
    private Integer mainCommentCode; // 댓글코드
    private String mainCommentText; // 댓글 내용
    private java.sql.Date mainCommentDate; // 댓글 게시시간
    private Integer userCode; // 유저 코드
    private Integer mainCode; // 홍보게시판 코드
    private Integer mainParentsCommentCode; // 대댓글 코드
}

