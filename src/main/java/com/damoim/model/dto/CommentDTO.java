package com.damoim.model.dto;

import java.sql.Date;
import java.util.ArrayList;

import com.damoim.model.vo.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CommentDTO {
    private int mainCommentCode; // 댓글코드
    private String mainCommentText; // 댓글 내용
    private Date mainCommentDate; // 댓글 게시시간
    private String nickname; // 유저 닉네임
    private String id;
    private String memberImg; // 작성한 유저 프로필
    private int membershipCode; // 홍보게시판 코드
    private int mainParentsCommentCode; // 대댓글 코드
    private ArrayList<CommentDTO> recoment;
    
}
