package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MeetingsComment {
    private Integer meetCommentCode; // 댓글코드
    private String meetCommentText; // 댓글 내용
    private java.sql.Date meetCommentDate; // 댓글 게시시간
    private Integer userCode; // 유저 코드
    private Integer meetCode; // 모임게시판 코드
    private Integer meetParentsCommentCode; // 대댓글 코드
}

