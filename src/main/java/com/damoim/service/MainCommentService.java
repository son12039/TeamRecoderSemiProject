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
		if(reCommentCount == 0) {
			mapper.deleteComment(mainCommentCode);
		} else {
			mapper.deleteUpdateComment(mainCommentCode);
		}
	}
	public void updateComment(MainComment mainComment) {
		mapper.updateComment(mainComment);
	}	
	
	
	// 탈퇴한 회원 댓글 비공개
	public ArrayList<MainComment> resignedMember(MainComment mainComment){
		// 멤버정보DTO
		MainComment member = new MainComment();
		member.setMember(mainComment.getMember());
		
		System.out.println("resignedMember : " + member);
		
		ArrayList<MainComment> comments = mapper.resignedMember(mainComment);
		
		String text = "비공개된 댓글입니다";
		
		// status가 false면 text로 댓글 덮어씌우기
		for (MainComment comment : comments) {
			if(comment.getMember().isStatus() == false) {
				
				comment.setMainCommentText(text);
				
			}
		}
		
		return mapper.resignedMember(mainComment);
	}
	
	
	

}
