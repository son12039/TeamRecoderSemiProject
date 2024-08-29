package com.damoim.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoim.model.vo.MainComment;

import mapper.MainCommentMapper;

@Service
public class MainCommentService {
	@Autowired
	private MainCommentMapper mapper;

	public void insertComment(MainComment mainComment) {
		mapper.insertComment(mainComment);
	}
	public ArrayList<MainComment>allMainComment(int membershipCode){
		return mapper.allMainComment(membershipCode);
	}
	public ArrayList<MainComment>mainReComment(int membershipCode, int mainCommentCode){
		return mapper.mainReComment(membershipCode, mainCommentCode);	
	}
	
	public void deleteComment(int mainCommentCode) {
		int reCommentCount = mapper.reCommentCount(mainCommentCode);
		
		if(reCommentCount == 0) { // 해당 댓글의 대댓글이 없는 경우
			MainComment m = mapper.selectComment(mainCommentCode);

			if(mapper.reCommentCount(m.getMainParentsCommentCode()) == 1) { // 부모 댓글 코드의 대댓글이 나만 남아있는 경우
				mapper.deleteComment(mainCommentCode); // 자식 삭제후
				mapper.deleteComment(m.getMainParentsCommentCode()); // 부모도 삭제

			}
			else {	
			mapper.deleteComment(mainCommentCode); // 자식만 삭제
			}
		} else {
			mapper.deleteUpdateComment(mainCommentCode);
			
			
		}
	}
	public void updateComment(MainComment mainComment) {
		mapper.updateComment(mainComment);
	}	
	
}
