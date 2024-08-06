package com.damoim.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoim.model.dto.SearchDTO;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.MembershipUserList;


import mapper.MembershipMapper;

@Service
public class MembershipService {
	
	@Autowired
	private MembershipMapper mapper;
	
	public List<MembershipUserList> allMembership(){
		
		return mapper.allMembership();
	}

	public Member search(SearchDTO dto) {
		return mapper.search(dto);
	}
	
	

}
