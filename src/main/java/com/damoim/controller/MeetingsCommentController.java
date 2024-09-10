package com.damoim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.damoim.model.vo.MainComment;
import com.damoim.model.vo.MeetingsComment;
import com.damoim.service.MeetingsCommentService;

@Controller
public class MeetingsCommentController {

	@Autowired
	private MeetingsCommentService service;
	/*
	 * 성철
	 * 댓글 대댓글 작성
	 * xml에서 대댓글일시에는 부모 댓글코드 추가해서 들어감 
	 * 일반댓글은 부모코드 null처리 
	 * 
	 * */
	@ResponseBody
	@PostMapping("/meetingsComment")
	public void insertComment (MeetingsComment meetingsComment) {
		System.out.println("댓글정보 : " + meetingsComment);
		service.insertComment(meetingsComment);
		
		
	}
	/*
	 * 성철
	 * 댓글 대댓글 삭제
	 * 자식으로 가지고 있는 댓글이 있는 댓글은 삭제 대신 업데이트문으로 서비스단에서 다르게 적용
	 * text 널처리 하면 화면단에 삭제된 댓글입니다 나오게 해둠
	 * 만약 대댓글이 없는 댓글이면 그냥 삭제
	 * 남아있던 마지막 대댓글이 삭제되면 해당 댓글도 삭제처리 ㅇㄹ 
	 * 
	 * */
	@ResponseBody
	@PostMapping("/deleteMeetingsComment")
	public void deleteComment(int meetCommentCode) {
		service.deleteComment(meetCommentCode);

	}
	/*
	 * 성철
	 * 댓글 수정 기능
	 * */
	@ResponseBody
	@PostMapping("/updateMeetingsComment")
	public void updateComment(MeetingsComment meetingsComment) {
		
		service.updateComment(meetingsComment);
	}
	
}
