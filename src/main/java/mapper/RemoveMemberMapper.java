package mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.vo.MainComment;
import com.damoim.model.vo.MeetingsComment;
import com.damoim.model.vo.MembershipMeetings;

@Mapper
public interface RemoveMemberMapper {
	// 메인 댓글 삭제 로직
	ArrayList<MainComment> selectMain(String id);
	void deleteUpdateMainComment(int mainCommentCode);
	void deleteMainComment(int mainCommentCode);
	int mainReCommentCount(int mainCommentCode);
	MainComment selectMainComment(int mainCommentCode);
	
	// 미팅 댓글 삭제로직
	ArrayList<MeetingsComment> selectMeet(String id);
	void deleteUpdateMeetingComment(int meetingCommentCode);
	void deleteMeetingComment(int meetingCommentCode);
	int meetingReCommentCount(int meetingCommentCode);
	MeetingsComment selectMeetComment(int meetingCommentCode);
	
	// 댓글말고 유저리스트 삭제
	void deleteMembershipUserList(String id);
	
	// 미팅 게시판 관련
	void deleteMemberMeetingsAgree(String id); // 동의사항 날리기(조건 X)
	ArrayList<MembershipMeetings> selectMeeting(String id); // 해당 유저가 작성한 모든 미팅 게시판 글
	int selectMeetingAgreeMemberCount(int meetCode); // 해당 글의 참여자가 있나 확인
	void deleteMeetingUpdate(int meetCode); // 해당 글의 참여자가 있을시 업데이트로 널처리
	void deleteMeeting(int meetCode); // 참여자가 0명이면 삭제
}
