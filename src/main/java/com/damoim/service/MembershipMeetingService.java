package com.damoim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.damoim.model.vo.MeetingsAgree;
import com.damoim.model.vo.MembershipMeetings;

import mapper.MembershipMeetingMapper;
import mapper.RemoveMemberMapper;
import mapper.RemoveMemberShipMapper;



@Service
public class MembershipMeetingService {
 
	
	@Autowired
	private MembershipMeetingMapper mapper;
	
	@Autowired
	private RemoveMemberMapper removeMapper;
	
	@Autowired
	private RemoveMemberShipMapper removeMemMapper;
	
	public void addMeeting(MembershipMeetings meetings) {
		
		mapper.addMeeting(meetings);
	}
	
public List<MembershipMeetings> allMeetings( int membershipCode) {
		
	 return mapper.allMeetings(membershipCode);
	}

public MembershipMeetings meetSelect (int meetCode) {
	
	return mapper.meetSelect(meetCode);
}
public void yesOrNo(MeetingsAgree ma) {
	
	mapper.yesOrNo(ma);
}

public List<MeetingsAgree> meetMember(int meetCode){
	
	return mapper.meetMember(meetCode);
}

public void participation (MeetingsAgree ma) {
	
	mapper.participation(ma);
}


public void participationCancle (MeetingsAgree ma) {
	
	mapper.participationCancle(ma);
}



public void meetingDelete(MembershipMeetings meetings) {
	int count = removeMapper.selectMeetingAgreeMemberCount(meetings.getMeetCode()); 
	removeMapper.deleteAllMeetComment(meetings.getMeetCode()); // 게시판 댓글 전부삭제
		if(count == 0) { // 진행 전이거나 참가자가 없는경우	
			removeMapper.deleteMeeting(meetings.getMeetCode()); // 미팅 정보 삭제
		}else { // 참가자가 남아있는 경우(유저별 모임 참여 횟수 저장용)
			removeMemMapper.deleteMeetingUpdatePlus(meetings.getMeetCode()); // 미팅 삭제 X 정보만 null처리		
		}
	
}

	 public void meetingUpdate(MembershipMeetings meetings) {
		 mapper.meetingUpdate(meetings);
	 }
	 
	 public List<MembershipMeetings> allMeetings1(String id){
		 
		return  mapper.allMeetings1(id);
	 }
	 
}
