package com.damoim.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.damoim.model.vo.MeetingsComment;

import mapper.MeetingsCommentMapper;

@Service
public class MeetingsCommentService {

	@Autowired
	private MeetingsCommentMapper mapper;
	
	public void insertComment(MeetingsComment meetingsComment) {
		mapper.insertComment(meetingsComment);
	}
	public ArrayList<MeetingsComment> allMeetingsComment(int meetCode){
		return mapper.allMeetingsComment(meetCode);
	}
	public ArrayList<MeetingsComment> MeetingsReComment(int meetCode, int meetCommentCode){
		return mapper.meetingsReComment(meetCode, meetCommentCode);	
	}
	
	public void deleteComment(int meetCommentCode) {
		int reCommentCount = mapper.reCommentCount(meetCommentCode);
		// 해당 댓글의 대댓글 수
		if(reCommentCount == 0) { // 해당 댓글의 대댓글이 없는 경우
			MeetingsComment commentChild = mapper.selectComment(meetCommentCode);
			MeetingsComment commentParents = mapper.selectComment(commentChild.getMeetParentsCommentCode());
			if(mapper.reCommentCount(commentChild.getMeetParentsCommentCode()) == 1 && commentParents.getMeetCommentText() == null) { // 부모 댓글 코드의 대댓글이 나만 남아있는 경우
				mapper.deleteComment(meetCommentCode); // 본인 삭제후
				mapper.deleteComment(commentChild.getMeetParentsCommentCode()); // 부모도 삭제

			}
			else {	
			mapper.deleteComment(meetCommentCode); // 본인 삭제
			}
		} else {
			mapper.deleteUpdateComment(meetCommentCode); // 삭제된 댓글입니다.!
			
			
		}
	}
	public void updateComment(MeetingsComment meetingsComment) {
		mapper.updateComment(meetingsComment);
	}	
	
	
	

}
