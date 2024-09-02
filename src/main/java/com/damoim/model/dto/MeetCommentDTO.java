package com.damoim.model.dto;

import java.sql.Date;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MeetCommentDTO {
	    private int meetCommentCode; // 댓글코드
	    private String meetCommentText; // 댓글 내용
	    private Date meetCommentDate; // 댓글 게시시간
	    private String nickname; // 유저 닉네임
	    private String id;
	    private String memberImg; // 작성한 유저 프로필
	    private int meetCode; // 홍보게시판 코드
	    private int meetParentsCommentCode; // 대댓글 코드
	    private ArrayList<MeetCommentDTO> recoment;
	    
	
}
