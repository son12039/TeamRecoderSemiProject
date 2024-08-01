package mapper;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.vo.Member;

@Mapper
public interface MemberMapper {
	
	void register(Member signUp);
	
	
}
