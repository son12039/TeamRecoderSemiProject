package mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.vo.MeetingsComment;


@Mapper
public interface MeetingsCommentMapper {

	void insertComment(MeetingsComment meetingsComment);
	ArrayList<MeetingsComment>allMeetingsComment(int meetCode);
	ArrayList<MeetingsComment>meetingsReComment(int meetCode, int meetCommentCode);
	void deleteUpdateComment(int meetingCommentCode);
	void deleteComment(int meetingCommentCode);
	int reCommentCount(int meetingCommentCode);
	void updateComment(MeetingsComment meetingsComment);

	MeetingsComment selectComment(int meetingCommentCode);
}
