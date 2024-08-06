package mapper;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.dto.SearchDTO;
import com.damoim.model.vo.Member;



@Mapper
public interface MemberMapper {
	
	Member login(Member member);
	void signUp(Member member);
	Member idCheck(Member member);
	Member nicknameCheck(Member member);
	Member pwdCheck(Member member);	
	void update(Member member);
	List<Member> search(SearchDTO dto);

	
}
