package com.damoim.service;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.dto.MemberLocTypeDTO;
import com.damoim.model.vo.BasicRoomListVo;
import com.damoim.model.vo.LocationCategory;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.MembershipLocation;
import com.damoim.model.vo.MembershipType;
import com.damoim.model.vo.MembershipUserList;
import com.damoim.model.vo.Paging;
import com.damoim.model.vo.TypeCategory;
import com.damoim.model.vo.UserInfoPaging;

import mapper.MembershipMapper;
import mapper.RemoveMemberShipMapper;
@Service
public class MembershipService {
	
	@Autowired
	private MembershipMapper mapper;
	
	@Autowired
	private RemoveMemberShipMapper removerMapper;
	
	public boolean updateMembership(Membership membership) {
		removerMapper.deleteLocation(membership.getMembershipCode());// 지역 정보 삭제
		removerMapper.deleteType(membership.getMembershipCode()); // 타입 정보 삭제
		
		mapper.updateMembership(membership); // 실제 업데이트 과정
		return true;
	}
	
	public List<MembershipUserList> allMembership(){
		// 08-21 14:30 채승훈 지움 (Paging paging)
		// paging.setOffset(paging.getLimit() * (paging.getPage() - 1));
		return mapper.allMembership();
	}
	
	public List<MembershipUserList> MembershipAllInfo(int membershipCode){
		
		return mapper.MembershipAllInfo(membershipCode);
	}
	
	public List<MembershipUserList> MembershipAllRegular(int membershipCode){
		
		return mapper.MembershipAllRegular(membershipCode);
	}

	
   public MembershipUserList main(Integer membershipCode){
		
		return mapper.main(membershipCode);
	}
   
   public int membershipUserCount(int count){
		return mapper.membershipUserCount(count);
 	}
   public void membershipApply(MemberListDTO member) {
	   mapper.membershipApply(member);
	  
   }
	
	public void membershipImg(Membership membership) {
		mapper.membershipImg(membership);
	}
	
	public void host(MemberListDTO list) {
		mapper.host(list);
	}
	// 로그인 회원 가입한 클럽처리
	public List<MemberListDTO> grade(Member member){
		return mapper.grade(member);
	}
	// 가입 취소, 탈퇴
	public void deleteList(MemberListDTO member) {
		mapper.deleteList(member);
	}
	
	public void agreeMemeber(MemberListDTO member) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem =  (Member)authentication.getPrincipal();
		// 로그인한 정보 가져와서 
		// 해당 멤버의 memberListDTO 리스트를 선언 
		ArrayList<MemberListDTO> list =  (ArrayList<MemberListDTO>) mem.getMemberListDTO();
	
		// 버튼을 누를떄 바뀌는 MemberListDTO를 최신화 시켜줘야함  
		// MemberListDTO member에서 보낸값이 로그인한 본인일경우 최신화가 되어야함 
		// 보내줄때 본인이 바뀔만한것은 호스트 양도 밖에 없음 
		
		
		if(member.getListGrade().equals("delete")) {
			mapper.expelMember(member);
			
			
			
		} else if (member.getListGrade().equals("host")) {
			
			// 보내준 값이 호스트인경우에 
			// 우선 해당 멤버쉽의 현제 호스트를 admin으로 체인지 
			// 그런데 무조건 본인에 영향이 가기 때문에 MemberListDTO에서 해당멤버쉽의 host인것을 admin으로 바꿔주면될듯? 
			// 만약 바꿔주지 않으면 호스트를 양도했음에도 불구하고 호스트 권한을 가지고 있는 페이지가 출력됨 
			mapper.hostChange(member.getMembershipCode());
			mapper.agreeMemeber(member);
			
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).getMembershipCode() == member.getMembershipCode()) {
					list.get(i).setListGrade("admin"); 
					break;
					
				}
				
			}
			
		}		
		 else if(member.getListGrade().equals("admin")){
		mapper.agreeMemeber(member);
		
	
		
		
		
		}	 else if(member.getListGrade().equals("regular")){
			mapper.agreeMemeber(member);
			
			
			
			
			} else {
				
				mapper.agreeMemeber(member);
				
				list.add(member);
				
			}
		

		
		
		SecurityContextHolder.getContext().setAuthentication(authentication);

		
		
		
		
		
	}
	// 체팅 서비스 ===================================
	public List<BasicRoomListVo> roomlist() {
		return mapper.roomlist();
	}
	
	public List<Integer> enterChattingroomCode(String id) {
		return mapper.enterChattingroomCode(id);
	}
	

	


	
	public void updateMembershipInfo(Membership membershipInfo) {
		mapper.updateMembershipInfo(membershipInfo);
	}
	
	public Membership selectMembership(int membershipCode) {
		return	mapper.selectMembership(membershipCode);
	}
	
	public int meetCount(String id) {
		return mapper.meetCount(id);
	}
	
	public List<MembershipUserList> selectMemberUserList(String id){
		
		return mapper.selectMemberUserList(id);
	}
	
	
	public List<MemberListDTO> adminUser(int membershipCode){
		
		return mapper.adminUser(membershipCode);
	}
	
	public MemberListDTO ifHost(String id) {
		
		return mapper.ifHost(id);
		
		}
	
	// 클럽 생성
	public void makeMembership(Membership membership) {
		mapper.makeMembership(membership);
	}
	
	// 지역 추가
	public void makeLocationMembership(MembershipLocation membershipLocation) {
		mapper.makeLocationMembership(membershipLocation);
	}

	// 유형 추가
	public void makeTypeMembership(MembershipType membershipType) {
		mapper.makeTypeMembership(membershipType);
	}
			
	// 지역- 이름으로 찾기
	public int findLocationCode(LocationCategory locationCategory) {
		return mapper.findLocationCode(locationCategory);	
	}
			
	// 유형- 이름으로 찾기
	public int findTypeCode(TypeCategory typeCategory) {
		return mapper.findTypeCode(typeCategory);	
	}
	
	// 클럽명 중복 체크
	public Membership membershipNameCheck(Membership membership) {
		return mapper.membershipNameCheck(membership);
	}

	
	// 클럽수정시 지역타입 버튼눌린 상태로 만들기
			public List<LocationCategory> locButton(int membershipCode){
				 return mapper.locButton(membershipCode);
			 }
			public List<TypeCategory> typeButton(int membershipCode){
				 return mapper.typeButton(membershipCode);
			 }

	
	
}



