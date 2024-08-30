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
import com.damoim.model.vo.Member;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.MembershipType;
import com.damoim.model.vo.MembershipUserList;
import com.damoim.model.vo.Paging;
import com.damoim.model.vo.TypeCategory;

import mapper.MembershipMapper;
@Service
public class MembershipService {
	
	@Autowired
	private MembershipMapper mapper;
	
	
	
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

	
	public void makeMembership(Membership membership) {
		mapper.makeMembership(membership);
	}
	
	public void membershipImg(Membership membership) {
		mapper.membershipImg(membership);
	}
	
	public void host(MemberListDTO list) {
		mapper.host(list);
	}
	
	public List<MemberListDTO> grade(Member member){
		return mapper.grade(member);
	}

	
	public void agreeMemeber(MemberListDTO member) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member mem =  (Member)authentication.getPrincipal();
		
		ArrayList<MemberListDTO> list =  (ArrayList<MemberListDTO>) mem.getMemberListDTO();
		
		
		
		if(member.getListGrade().equals("delete")) {
			mapper.expelMember(member);
			
			for(int i =0; i<list.size(); i++) {
				if(list.get(i).getMembershipCode() == member.getMembershipCode()) {
					list.remove(i);
				}
				
			}
			
		} else if (member.getListGrade().equals("host")) {
			
			mapper.hostChange(member.getMembershipCode());
			mapper.agreeMemeber(member);
			
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).getMembershipCode() == member.getMembershipCode() ) {
					list.get(i).setListGrade("admin"); 
					
				}
				
			}
			
		}		
		 else if(member.getListGrade().equals("admin")){
		mapper.agreeMemeber(member);
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getMembershipCode() == member.getMembershipCode() ) {
				list.get(i).setListGrade("admin"); 
				
			}
			
		}
		
		
		
		}	 else if(member.getListGrade().equals("regular")){
			mapper.agreeMemeber(member);
			
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).getMembershipCode() == member.getMembershipCode() ) {
					list.get(i).setListGrade("regular"); 
					
				}
				
			}
			
			
			
			} else {
				
				mapper.agreeMemeber(member);
				
				list.add(member);
				
			}
		

		
		
		SecurityContextHolder.getContext().setAuthentication(authentication);

		
		
		
		
		
	}
	public List<Integer> membershipCodeList(String id){
		
		return mapper.membershipCodeList(id);
	}

	public List<BasicRoomListVo> roomlist() {
		return mapper.roomlist();
	}
	
	
	
	public void updateMembership(Membership membership) {
		mapper.updateMembership(membership);
	}
	
	
	public void updateMembershipInfo(Membership membershipInfo) {
		mapper.updateMembershipInfo(membershipInfo);
	}
	
	public Membership selectMembership(int membershipCode) {
		return	mapper.selectMembership(membershipCode);
	}
	
//	public List<MembershipUserList> list(Paging paging) {
//		
//		return mapper.allMembership(paging);
//	}
	
	public List<MemberListDTO> adminUser(int membershipCode){
		
		return mapper.adminUser(membershipCode);
	}
	
	public MemberListDTO ifHost(String id) {
		
		return mapper.ifHost(id);
		
		}
	
	
}




