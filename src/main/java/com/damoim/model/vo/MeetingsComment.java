package com.damoim.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MeetingsComment {
    private int meetCommentCode; // 댓글코드
    private String meetCommentText; // 댓글 내용
    private Date meetCommentDate; // 댓글 게시시간
    private int userCode; // 유저 코드
    private int meetCode; // 모임게시판 코드
    private int meetParentsCommentCode; // 대댓글 코드
}

