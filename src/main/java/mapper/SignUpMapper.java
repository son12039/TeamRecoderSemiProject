package mapper;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.vo.SignUp;

@Mapper
public interface SignUpMapper {
	
	void register(SignUp signUp);
	
	
}
