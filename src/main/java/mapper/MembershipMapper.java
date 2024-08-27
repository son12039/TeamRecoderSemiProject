package mapper;

import java.util.ArrayList;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.dto.MemberLocTypeDTO;
import com.damoim.model.vo.BasicRoomListVo;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.MembershipType;
import com.damoim.model.vo.MembershipUserList;
import com.damoim.model.vo.Paging;
import com.damoim.model.vo.TypeCategory;

@Mapper
public interface MembershipMapper {
	
	
	MembershipUserList main(int membershipCode);
	int membershipUserCount(int membershipCode);
	List<MembershipUserList> allMembership();// 08-21 14:30 채승훈 지움 (Paging paging)
	Membership selectMembership(int membershipCode);
	void makeMembership(Membership membership);
	void host(MemberListDTO list);
	ArrayList<Member> allMember();
	List<MembershipUserList> MembershipAllInfo(int membershipCode);
	void membershipApply(MemberListDTO member);
	
	List<MemberListDTO> grade(Member member);
	
	void agreeMemeber(MemberListDTO member);
	
	 
	List<Integer> membershipCodeList(String id);
	List<BasicRoomListVo> roomlist();
	
	void membershipImg(Membership membership);
	
	
	
	void updateMembership(Membership membership);
	
	void updateMembershipInfo(Membership membershipInfo);
}










