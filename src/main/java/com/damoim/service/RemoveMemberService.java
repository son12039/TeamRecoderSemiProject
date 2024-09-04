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
		ArrayList<MeetingsComment> meetList = mapper.selectMeet(id);
		for(MainComment main : mainList) {
			int count = mapper.mainReCommentCount(main.getMainCommentCode());
				if(count == 0) {
					MainComment commentChild = mapper.selectMainComment(main.getMainCommentCode());
					MainComment commentParents = mapper.selectMainComment(commentChild.getMainParentsCommentCode());
					if(mapper.mainReCommentCount(commentChild.getMainParentsCommentCode()) == 1 && commentParents.getMainCommentText() == null) {
						mapper.deleteMainComment(main.getMainCommentCode());
						mapper.deleteMainComment(commentChild.getMainParentsCommentCode());
					}else {
						mapper.deleteMainComment(main.getMainCommentCode());
					}
				}else {
					mapper.deleteUpdateMainComment(main.getMainCommentCode());
				}
		}
		
		for(MeetingsComment meet : meetList) {
			int count = mapper.meetingReCommentCount(meet.getMeetCommentCode());
				if(count == 0) {
					MeetingsComment commentChild = mapper.selectMeetComment(meet.getMeetCommentCode());
					MeetingsComment commentParents = mapper.selectMeetComment(commentChild.getMeetParentsCommentCode());
					if(mapper.meetingReCommentCount(commentChild.getMeetParentsCommentCode()) == 1 && commentParents.getMeetCommentText() == null) {
						mapper.deleteMeetingComment(meet.getMeetCommentCode());
						mapper.deleteMeetingComment(commentChild.getMeetParentsCommentCode());
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
			if(count == 0) {
				mapper.deleteMeeting(met.getMeetCode());
			}else {
				mapper.deleteMeetingUpdate(met.getMeetCode());
			}
			
		}

	}
	// 해당 미팅 글 삭제 눌렀을때 진행 로직
	public void deleteMeeting(int MeetCode) {
		int count = mapper.selectMeetingAgreeMemberCount(MeetCode);
		if(count == 0) {
			mapper.deleteMeeting(MeetCode);
		}else {
			mapper.deleteMeetingUpdate(MeetCode);
		}
		
	}
	
}
