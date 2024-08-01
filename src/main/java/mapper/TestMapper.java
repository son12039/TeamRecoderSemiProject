package mapper;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.vo.Member;

@Mapper
public interface TestMapper {
	
	void register(Member user);

	Member login(Member user);
}
