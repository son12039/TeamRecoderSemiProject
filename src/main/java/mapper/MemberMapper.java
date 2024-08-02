package mapper;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.vo.Member;


@Mapper
public interface MemberMapper {
	
	Member login(Member member);
	void signUp(Member member);
	
}
