package mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.vo.Image;
import com.damoim.model.vo.Member;


@Mapper
public interface MemberMapper {
	
	void register(Member member);
	Member login(Member member);
	List<Image> profile();
	
	
}
