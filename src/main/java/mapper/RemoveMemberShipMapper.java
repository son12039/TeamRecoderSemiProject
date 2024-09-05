package mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RemoveMemberShipMapper {
	
	void deleteMembershipList(int membershipCode); // 해당클럽 가입회원 제거
	void deleteMainComment(int membershipCode); // 해당클럽 댓글 제거
	void deleteLocation(int membershipCode); // 해당클럽 지역정보 제거
	void deleteType (int membershipCode); // 해당클럽 타입 제거
	ArrayList<Integer>findMeetCode(int membershipCode); // 해당클럽 meetCode 가져오기
	void deleteMeetingUpdatePlus(int meetCode); // 미팅 삭제
	void deleteMembership(int membershipCode); // 최종 맴버쉽 삭제
}
