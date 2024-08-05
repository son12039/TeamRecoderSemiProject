package mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.vo.MembershipUserList;

@Mapper
public interface MembershipUserListMapper {
	
//	회원등급 받아오기
	List<MembershipUserList> memberList();
	
	
}
