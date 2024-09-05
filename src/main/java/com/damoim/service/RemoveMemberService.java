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
	
	public void deleteMembershipUserList(String id) {
		mapper.deleteMembershipUserList(id);
	}
	
	public void deleteAllComment(String id) {
	
		ArrayList<MainComment> mainList = mapper.selectMain(id);	
		for(MainComment main : mainList) {
			int count = mapper.mainReCommentCount(main.getMainCommentCode());
				if(count == 0) { // 대댓글이 없으면
					
					MainComment commentParents = mapper.selectMainComment(main.getMainParentsCommentCode());
					if(mapper.mainReCommentCount(main.getMainParentsCommentCode()) == 1 && commentParents.getMainCommentText() == null) {
						mapper.deleteMainComment(main.getMainCommentCode());
						mapper.deleteMainComment(main.getMainParentsCommentCode());
					}else {
						mapper.deleteMainComment(main.getMainCommentCode());
					}
				}else {
					mapper.deleteUpdateMainComment(main.getMainCommentCode());
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
