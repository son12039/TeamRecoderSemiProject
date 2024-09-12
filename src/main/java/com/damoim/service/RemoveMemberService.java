package com.damoim.service;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoim.model.vo.MainComment;
import com.damoim.model.vo.MeetingsComment;
import com.damoim.model.vo.MembershipMeetings;

import mapper.RemoveMemberMapper;

@Service
public class RemoveMemberService {

	@Autowired
	private RemoveMemberMapper mapper;
	
	public void deleteMembershipUserList(String id) { // 해당 유저가 가입한 모든 클럽에서 탈퇴 처리
		mapper.deleteMembershipUserList(id);
	}
	
	public void deleteAllComment(String id) {// 해당 ID로 작성된 모든 댓글 삭제 로직
	
		ArrayList<MainComment> mainList = mapper.selectMain(id);	
		for(MainComment main : mainList) {
			int count = mapper.mainReCommentCount(main.getMainCommentCode());
				if(count == 0) { // 해당 댓글의 대댓글이 없는 경우				
					MainComment commentParents = mapper.selectMainComment(main.getMainParentsCommentCode());
					if(mapper.mainReCommentCount(main.getMainParentsCommentCode()) == 1 && commentParents.getMainCommentText() == null) {
						mapper.deleteMainComment(main.getMainCommentCode());// 본인 삭제후
						mapper.deleteMainComment(main.getMainParentsCommentCode());// 부모도 삭제
					}else {
						mapper.deleteMainComment(main.getMainCommentCode());// 본인 삭제
					}
				}else {
					mapper.deleteUpdateMainComment(main.getMainCommentCode());// 삭제된 댓글입니다.!
				}
		}	
		ArrayList<MeetingsComment> meetList = mapper.selectMeet(id);
		for(MeetingsComment meet : meetList) {
			int count = mapper.meetingReCommentCount(meet.getMeetCommentCode());
				if(count == 0) {
					
					MeetingsComment commentParents = mapper.selectMeetComment(meet.getMeetParentsCommentCode());
					if(mapper.meetingReCommentCount(meet.getMeetParentsCommentCode()) == 1 && commentParents.getMeetCommentText() == null) {
						mapper.deleteMeetingComment(meet.getMeetCommentCode());
						mapper.deleteMeetingComment(meet.getMeetParentsCommentCode());
					}else {
						mapper.deleteMeetingComment(meet.getMeetCommentCode());
					}
					
				}else {
					mapper.deleteMeetingComment(meet.getMeetCommentCode());
				}
		}
		
	}
	
	// 회원 탈퇴시 
	public void deleteAllMeeting(String id) {
		mapper.deleteMemberMeetingsAgree(id); // 동의사항 날리기(조건 X)
		ArrayList<MembershipMeetings> list = mapper.selectMeeting(id); // 해당 유저가 작성한 모든 미팅 게시판 글
		
		for(MembershipMeetings met: list) {
			int count = mapper.selectMeetingAgreeMemberCount(met.getMeetCode());
			mapper.deleteAllMeetComment(met.getMeetCode()); // 댓글 삭제
			if(count == 0) {		
				mapper.deleteMeeting(met.getMeetCode()); // 미팅 삭제
			}else {
				mapper.deleteMeetingUpdate(met.getMeetCode());
			}
			
		}

	}
	
	
	
	// 해당 미팅 글 삭제 눌렀을때 진행 로직
	public void deleteMeeting(int MeetCode) {
		mapper.deleteAllMeetComment(MeetCode); // 댓글 삭제
		int count = mapper.selectMeetingAgreeMemberCount(MeetCode);
		if(count == 0) {
			mapper.deleteMeeting(MeetCode);
		}else {
			mapper.deleteMeetingUpdate(MeetCode);
		}
		
	}
	
}
