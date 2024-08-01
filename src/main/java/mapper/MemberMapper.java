package mapper;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.vo.Member;
import com.damoim.model.vo.User;

@Mapper
public interface MemberMapper {
	
	Member login(Member member);
	void signUp2(User user);
	void signUp(Member member);
	
}
