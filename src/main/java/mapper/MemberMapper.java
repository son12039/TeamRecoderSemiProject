package mapper;

import java.util.ArrayList;
import java.util.List;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.damoim.model.dto.SearchDTO;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.Membership;




@Mapper
public interface MemberMapper {
	
	Member login(String id);
	ArrayList<MemberListDTO> loginMemberMembership(Member member);
	void signUp(Member member);
	Member idCheck(Member member);
	Member nicknameCheck(Member member);
	Member pwdCheck(Member member);	
	Member memberInfo(Member member);
	String grade(Member member);
	
	// 업데이트
	Member updateCheck(Member vo, Member mem);
	void updateMemberInfo(Member member);
	void addrUpdate(Member member);
	
	
	
	
	// id 이메일 맞나 체크
	Member memberEmailIdcheck(Member member);
	
	// 업데이트
	void updatePassword(Member member);
	
	ArrayList<Member> dummyMember();
	void dummyUpdate(Member member);

}
