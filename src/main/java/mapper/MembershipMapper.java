package mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.damoim.model.vo.Membership;
import com.damoim.model.vo.MembershipUserList;

@Mapper
public interface MembershipMapper {
	
	List<MembershipUserList> allMembership();
	MembershipUserList main(int membershipCode);
	int membershipUserCount(int membershipCode);
	

	void makeMembership(Membership membership);
	
	void host(MembershipUserList list);

}
