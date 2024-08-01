package mapper;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.vo.UserInfo;

@Mapper
public interface UserInfoMapper {
	
	void register(UserInfo signUp);
	
	
}
