package mapper;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.vo.Member;

@Mapper
public interface TestMapper {
	
	void register(Member member);

	Member login(Member member);
	
	
	
	Member pwdCheck(Member member);
	
	void update(Member member);
}
