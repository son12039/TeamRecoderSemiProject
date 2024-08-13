package mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.MembershipType;
import com.damoim.model.vo.MembershipUserList;
import com.damoim.model.vo.TypeCategory;

@Mapper
public interface MembershipMapper {
	
	List<MembershipUserList> allMembership();
	MembershipUserList main(int membershipCode);
	int membershipUserCount(int membershipCode);
	
	List<MembershipUserList> MembershipAllInfo(int membershipCode);

	void membershipApply(MemberListDTO member);
	
	void makeMembership(Membership membership);
	
	void host(MemberListDTO list);
	
	MemberListDTO checkMember(MemberListDTO member);

	List<MembershipUserList> grade(Member member);
	
	void agreeMemeber(MemberListDTO member);
	
	List<TypeCategory> membershipType();
	

	
	List<TypeCategory> membershipType1();
	
	 List<MembershipType> typeCheck(String value);
	 
	 int typeCount(int code);
	 
	 MembershipUserList typeHost (int code);
	 
}
