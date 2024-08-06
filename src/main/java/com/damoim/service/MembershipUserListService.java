package com.damoim.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.MembershipUserListMapper;
import com.damoim.model.vo.MembershipUserList;



@Service
public class MembershipUserListService {
	
	@Autowired
	private MembershipUserListMapper listMapper;
	
	public List<MembershipUserList> membershipUserList() {
		
		return listMapper.membershipUserList();
	}

}







