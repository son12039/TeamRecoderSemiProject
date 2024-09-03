package mapper;

import java.util.ArrayList;
import java.util.List;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.damoim.model.dto.SearchDTO;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.dto.MemberInfoDTO;
import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.dto.MemberMannerDTO;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.Membership;




@Mapper
public interface MemberMapper {
	
	Member login(String id);
	
	ArrayList<MemberListDTO> loginMemberMembership(Member member);
	
	void signUp(Member member);
	
	Member idCheck(Member member);
	Member nicknameCheck(Member member);
	Member emailCheck(Member member);
	Member pwdCheck(Member member);	
	Member memberInfo(Member member);
	String grade(Member member);
	Member selectMember(Member member);
	
	// 회원정보 업데이트 ===============================
	void updateMemberInfo(Member member); // 기본정보 업데이트
	void updateMember(Member vo); // 중요정보 업데이트
	Member updateCheck(Member vo);
	
	boolean nicknameDupCheck(Member member);
	List<MemberListDTO>resignSelect(String id); 
	
	
	
	void fileDelete(String string);
	Member selectMember(String string);
	
	// 회원탈퇴 ======================================
	boolean memberStatus(Member member);
	void memberActBlock(MemberInfoDTO dto);
	Member membershipSelect(Member member);
	
	Member findMemberId(Member member);
	// id 이메일 맞나 체크 =============================
	Member memberEmailIdcheck(Member member);
	
	// 업데이트
	void updatePassword(Member member);
	// 매너 업데이트
	void memberManner(MemberMannerDTO dto);
	void updateLastRecommendationTime(String id);
	// 아래 2개 더미 비밀번호 암호화용도
	ArrayList<Member> dummyMember();
	void dummyUpdate(Member member);

	

}
