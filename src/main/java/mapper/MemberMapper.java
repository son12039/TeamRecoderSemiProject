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
import com.damoim.model.dto.RankDTO;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.Membership;




@Mapper
public interface MemberMapper {
	
	// 로그인 관련 ===========================================
	Member login(String id); // 로그인 	
	
	// 회원가입 + 다양한 재사용 가능 쿼리 ==========================================
	void signUp(Member member); // 회원가입 
	
	Member idCheck(Member member); // 회원가입시 ID 확인 + 각종 ID로 회원정보
	Member nicknameCheck(Member member); // 회원가입시 닉네임 확인
	Member emailCheck(Member member); // 회원가입시 이메일 확인
	Member memberInfo(Member member); // 회원 정보 수정때 사용하는거 같은데 확인 중복임 !!!
	
	// 회원정보 업데이트 ===============================
	void updateMember(Member vo); // 회원정보(중요)업데이트
	void updateMemberInfo(Member member); // 회원정보(비중요) 업데이트
	void defualtFile(String id); // 기본 사진으로 변경 2024/09/05 DM
	
	// 회원탈퇴 ======================================
	boolean memberStatus(Member member); // 회원 탈퇴시 해당 회원의 스테이터스를 false로 변경
	void memberActBlock(MemberInfoDTO dto); //  XML에 존재 X 사용처 ? ?????????????
	Member membershipSelect(Member member); //XML에 존재 X 사용처 ? ?????????????
	
	
	// 아이디 , 비밀번호 찾기 ===================================
	Member findMemberId(Member member); // 회원 ID 찾기 이름, 이메일로
	
	Member memberEmailIdcheck(Member member); // ID와 이메일 맞게 넣으면 이메일로 임시비밀번호 발송
	void updatePassword(Member member); // 임시 비밀번호로 변경
	
	// 회원 추천기능 ==========================================
	void memberManner(MemberMannerDTO dto);
	void updateLastRecommendationTime(String id);
	
	// 랭킹 ============================
	List<Member> top5();
	List<RankDTO> veteran();
	
	// 아래 2개 더미 비밀번호 암호화용도 ========================
	ArrayList<Member> dummyMember();
	void dummyUpdate(Member member);


	

}
