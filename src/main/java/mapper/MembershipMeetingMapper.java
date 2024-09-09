package mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.vo.MeetingsAgree;
import com.damoim.model.vo.MembershipMeetings;
import com.damoim.model.vo.Image;

@Mapper
public interface MembershipMeetingMapper {

	
	void addMeeting(MembershipMeetings meetings) ;
	
	List<MembershipMeetings> allMeetings(int membershipCode);
	
	MembershipMeetings meetSelect(int meetCode);
	
	void yesOrNo(MeetingsAgree ma);
	
	List<MeetingsAgree> meetMember (int meetCode);
	// 미팅 참가 
	void participation(MeetingsAgree ma);
	// 미팅 참가 취소 
	void participationCancle(MeetingsAgree ma);
	
	public void addImage (Image img);
	
	List<Image> viewImage (int meetCode);
	
	// 미팅 삭제 
	void meetingDelete(MembershipMeetings meeting);
	
	// 미팅 수정 
	void meetingUpdate(MembershipMeetings meetings);
	
	List<MembershipMeetings> allMeetings1(String id);
	
}
