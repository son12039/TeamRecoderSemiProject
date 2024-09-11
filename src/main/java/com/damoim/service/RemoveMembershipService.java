package com.damoim.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.MembershipMapper;
import mapper.RemoveMemberMapper;
import mapper.RemoveMemberShipMapper;

@Service
public class RemoveMembershipService {
	@Autowired
	private RemoveMemberMapper removeMemberMapper;
	
	@Autowired
	private MembershipMapper membershipMapper;

	@Autowired
	private RemoveMemberShipMapper removeMembershipMapper;
	

	
	public boolean allDeleteMembership(int membershipCode) {
		int clubCount = membershipMapper.membershipUserCount(membershipCode); // 해당클럽에 남아있는 인원수 
		// 해당클럽에 1명만 남은경우 (호스트 본인)
		if(clubCount == 1) {
			removeMembershipMapper.deleteMembershipList(membershipCode); // membershipUserList에서 호스트(본인) 정보 제거
			removeMembershipMapper.deleteMainComment(membershipCode); // 해당 홍보글 댓글 삭제
			removeMembershipMapper.deleteType(membershipCode); // 타입 삭제
			removeMembershipMapper.deleteLocation(membershipCode); // 위치정보 삭제
			// 해당 클럽의  모든 membershipMeetings 추출
			ArrayList<Integer> list = removeMembershipMapper.findMeetCode(membershipCode);
			for(int code : list) {
				// 해당 클럽의 미팅 게시판에 시작날짜가 지난 미팅 정보의 참여자 수
				int count = removeMemberMapper.selectMeetingAgreeMemberCount(code); 
				removeMemberMapper.deleteAllMeetComment(code); // 게시판 댓글 전부삭제
					if(count == 0) { // 진행 전이거나 참가자가 없는경우	
						removeMemberMapper.deleteMeeting(code); // 미팅 정보 삭제
					}else { // 참가자가 남아있는 경우(유저별 모임 참여 횟수 저장용)
						removeMembershipMapper.deleteMeetingUpdatePlus(code); // 미팅 삭제 X 정보만 null처리		
					}
			}
			removeMembershipMapper.deleteMembership(membershipCode); // 최종 맴버쉽 삭제
			return true; // 삭제 성공!
		}else{
			return false; // 삭제 실패(클럽에 본인외 다른 유저가 남아있음)
		}
	
	}
}
