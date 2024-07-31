package mapper;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.vo.SignUp;

@Mapper
public interface TestMapper {
	
	void register(SignUp signup);

	SignUp login(SignUp signup);
}
