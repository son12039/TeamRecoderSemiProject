package mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.vo.MembershipMeetings;

@Mapper
public interface MembershipMeetingMapper {

	
	void addMeeting(MembershipMeetings meetings) ;
	
	List<MembershipMeetings> allMeetings(int membershipCode);
}
