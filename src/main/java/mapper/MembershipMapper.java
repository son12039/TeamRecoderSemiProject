package mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.dto.SearchDTO;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.MembershipUserList;

@Mapper
public interface MembershipMapper {
	
	List<MembershipUserList> allMembership();
	Member search (SearchDTO dto);
	
	
}
