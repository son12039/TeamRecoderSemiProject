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
    private String id; // 유저 아이디
    private int meetCode; // 모임게시판 코드
    private int meetParentsCommentCode; // 대댓글 코드
    private Member member; // 작성한 유저
}

