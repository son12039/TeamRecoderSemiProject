package mapper;

import org.apache.ibatis.annotations.Mapper;


import com.damoim.model.vo.Main;

@Mapper
public interface MainMapper {
	
	void mainCreate(Main main);
}
