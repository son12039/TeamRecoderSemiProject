package mapper;

import java.util.ArrayList;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.dto.MemberLocTypeDTO;
import com.damoim.model.vo.BasicRoomListVo;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.MembershipType;
import com.damoim.model.vo.MembershipUserList;
import com.damoim.model.vo.Paging;
import com.damoim.model.vo.TypeCategory;

@Mapper
public interface MembershipMapper {
	// 로그인 ==================
	List<MemberListDTO> grade(Member member); // 로그인시 그 회원의 가입 클럽정보 전부 DTO로 담아서 넣어둠??
	// 화면단 출력 용도 ===================
	MembershipUserList main(int membershipCode); // 메인 화면에 맴버쉽, 그 맴버쉽의 호스트 정보 반환
	int membershipUserCount(int membershipCode); // 해당 클럽의 가입유저수(신청만 한사람 제외) 의 카운트를 반환
	List<MembershipUserList> allMembership();// 08-21 14:30 채승훈 지움 (Paging paging)
	
	List<MembershipUserList> MembershipAllInfo(int membershipCode); // 클럽 페이지 클릭 -> 맴버쉽 코드로 그 맴버쉽 정보 + 가입 회원정보 까지
	
	List<MembershipUserList> MembershipAllRegular(int membershipCode); // 맴버쉽 코드로 해당 클럽의 게스트 제외 인원
	// 단일 클럽 선택 용도 ========================
	Membership selectMembership(int membershipCode); // 맴버쉽 코드로 맴버쉽 정보만 반환

	// 클럽 가입 신청 =========================================
	void membershipApply(MemberListDTO member); // 가입 신청 누르면 자동으로 해당 클럽의 게스트로 추가
	void deleteList(MemberListDTO member); // 가입 취소, 탈퇴
	// 클럽 생성 ============================================
	void membershipImg(Membership membership); // 클럽 창설때 IMG 추가 업데이트 형식으로 추가
	void host(MemberListDTO list); // 클럽 생성시 해당 클럽 코드 + 호스트 추가
	
	// 맴버쉽 관리 =========================================
	void agreeMemeber(MemberListDTO member); // 해당 맴버쉽 코드 , 유저ID로 유저 등급 변경
	List<MemberListDTO> adminUser(int membershipCode); // 해당 클럽의 관리자인 유저 반환
	MemberListDTO ifHost(String id); // 호스트를 넘겨주기 전에 해당 관리자가 이미 호스트인 클럽이 있나 확인
	void hostChange(int membershipCode); // 호스트가 관리자에게 호스트권환 넘겨주고 본인은 관리자로
	void expelMember(MemberListDTO member); // 해당클럽에서 유저 강퇴
	
	// 회원 페이지 용도 ==============================
	int meetCount (String string);// 해당 유저의 ID로  해당 맴버의 모임 참여 횟수
	List<MembershipUserList> selectMemberUserList(String id);// 해당 맴버 ID로 그 회원의 모든 클럽(상세정보까지 반환)	
	
	
	// 채팅 ===================================
	List<BasicRoomListVo> roomlist(); // 체팅?
	
	// 멤버쉽 업데이트 ============================	
	void updateMembershipInfo(Membership membershipInfo);// 아마 맴버쉽 정보 업데이트
	
	// ??????????????????????????
	void makeMembership(Membership membership); // 클럽 만들기????
	

	

	void resignUserList(String id); // 아이디 받아서 membership_user_list 테이블 삭제
	
}










