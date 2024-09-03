package com.damoim.service;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoim.model.vo.MainComment;
import com.damoim.model.vo.MeetingsComment;

import mapper.RemoveMemberCommentMapper;

@Service
public class RemoveMemberCommentService {

	@Autowired
	private RemoveMemberCommentMapper mapper;
	
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
	
}
