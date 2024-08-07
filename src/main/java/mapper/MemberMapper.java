package mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.vo.Member;


@Mapper
public interface MemberMapper {
	
	Member login(Member member);
	ArrayList<MemberListDTO> loginMemberMembership(Member member);
	void signUp(Member member);
	Member idCheck(Member member);
	Member nicknameCheck(Member member);
	Member pwdCheck(Member member);	
	void update(Member member);
	
}
