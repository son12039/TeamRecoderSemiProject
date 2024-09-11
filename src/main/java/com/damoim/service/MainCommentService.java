package com.damoim.service;

import java.util.ArrayList;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.damoim.model.vo.MainComment;
import com.damoim.model.vo.MeetingsComment;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.MembershipUserList;

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
	public ArrayList<MainComment>mainReComment(int mainCommentCode){
		return mapper.mainReComment(mainCommentCode);	
	}
	
	public void deleteComment(int mainCommentCode) { // 삭제 로직
		int reCommentCount = mapper.reCommentCount(mainCommentCode);// 해당 댓글을 부모로 가지고 있는 댓글의 숫자	
		if(reCommentCount == 0) { // 해당 댓글의 대댓글이 없는 경우(즉 대댓글이거나, 대댓글이 없는 일반 댓글)
			MainComment commentChild = mapper.selectComment(mainCommentCode); // 코드로 대상 댓글의 정보를 가져옴
			MainComment commentParents = mapper.selectComment(commentChild.getMainParentsCommentCode()); //대상 댓글의 부모 댓글의 정보를 가져옴
			// 부모 댓글 코드의 대댓글이 나만 남아있는 경우이면서 부모 댓글의 내용이 삭제된 댓글
			if(mapper.reCommentCount(commentChild.getMainParentsCommentCode()) == 1  && commentParents.getMainCommentText() == null) { 	
				mapper.deleteComment(mainCommentCode); // 본인 삭제 후
				mapper.deleteComment(commentChild.getMainParentsCommentCode()); // 부모도 삭제
			}else {	// 대댓글이면서 부모 댓글이 살아있는경우 OR 대댓글이 없는 댓글 
			mapper.deleteComment(mainCommentCode); // 삭제하려던 대상 delete
			}
		} else {// 해당 댓글에 대댓글이 남아있기 때문에 delete대신 update로 내용만 날림!
			mapper.deleteUpdateComment(mainCommentCode);
			
			
		}
	}
	
	
	public void updateComment(MainComment mainComment) {
		mapper.updateComment(mainComment);
	}	
	
	


	
	
	

	
	
	

}
