package com.damoim.model.vo;

import java.sql.Date;

import com.damoim.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MainComment {
    private int mainCommentCode; // 댓글코드
    private String mainCommentText; // 댓글 내용
    private Date mainCommentDate; // 댓글 게시시간
    private String id; // 유저 아이디
    private int membershipCode; // 홍보게시판 코드
    private int mainParentsCommentCode; // 대댓글 코드
    private Member member; // 작성한 유저
    
  
}

