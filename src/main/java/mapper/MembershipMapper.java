package mapper;

import java.util.ArrayList;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.MembershipUserList;

@Mapper
public interface MembershipMapper {
	
	
	MembershipUserList main(int membershipCode);
	int membershipUserCount(int membershipCode);
	List<MembershipUserList>allMembership();
	MemberListDTO checkMember(MemberListDTO member);
	void makeMembership(Membership membership);
	void host(MemberListDTO list);
	ArrayList<Member> allMember();
	List<MembershipUserList> MembershipAllInfo(int membershipCode);
	void membershipApply(MemberListDTO member);
	
	List<MembershipUserList> grade(Member member);
	void agreeMemeber(MemberListDTO member);
	void membershipImg(Membership membership);
}










