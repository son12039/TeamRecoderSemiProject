package com.damoim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.MembershipUserList;

import mapper.MembershipMapper;

@Service
public class MembershipService {
	
	@Autowired
	private MembershipMapper Mapper;
	
	public List<MembershipUserList> allMembership(){
		
		return Mapper.allMembership();
	}
	
   public MembershipUserList main(Integer membershipCode){
		
		return Mapper.main(membershipCode);
	}
   public int membershipUserCount(int count){
		return Mapper.membershipUserCount(count);
 	}
   public void membershipApply(MemberListDTO member) {
	   Mapper.membershipApply(member);
	   
   }
   public MemberListDTO checkMember(MemberListDTO member) {
	   return Mapper.checkMember(member);
   }
	

	public void makeMembership(Membership membership) {

		Mapper.makeMembership(membership);
	}
	
	public void host(MemberListDTO list) {
		Mapper.host(list);
	}
	

}
