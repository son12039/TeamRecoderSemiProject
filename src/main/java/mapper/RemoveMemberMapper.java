package mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.vo.MainComment;
import com.damoim.model.vo.MeetingsComment;

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
}
