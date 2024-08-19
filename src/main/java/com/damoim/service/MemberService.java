package com.damoim.service;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.damoim.model.dto.MemberListDTO;
import com.damoim.model.vo.Member;


import mapper.MemberMapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//xml -> Mapper -> service -> controller

@Service
public class MemberService  implements UserDetailsService{
	
	@Autowired
	private MemberMapper mapper;
	
	@Autowired
	private MembershipService infoService;
	
	@Autowired
	private PasswordEncoder bcpe;
	
	public ArrayList<MemberListDTO> loginMemberMembership(Member member){
		return mapper.loginMemberMembership(member);
		
	}
	
	@Transactional
	public void signUp(Member member) {
		// 비밀번호 암호화
		member.setPwd(bcpe.encode(member.getPwd()));
		
			mapper.signUp(member);
		
	}
	
	public Member idCheck(Member member) {
		return mapper.idCheck(member);
		
	}
	
	public Member nicknameCheck(Member member) {
		return mapper.nicknameCheck(member);
		
	}

	public Member pwdCheck(Member member) {
		
		return mapper.pwdCheck(member);
	}
    
	public void update(Member member) {
		
		mapper.update(member);
	}
	
	public ArrayList<Member> dummyMember(){
		return mapper.dummyMember();
		
	}
	public void dummyUpdate() {
		ArrayList<Member> list = dummyMember();
		System.out.println(list);
		for(Member m : list) {
			m.setPwd(bcpe.encode(m.getPwd()));
		mapper.dummyUpdate(m);
		
		}
	}
	
	@Override  //로그인하면 일로옴  
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("로그인 성공!!!");
		System.out.println("유저네임 : " + username);
		Member member = mapper.login(username);
	 //  member.setMembershipList(infoService.grade(mapper.login(username)));
	   member.setMemberListDTO(infoService.grade(mapper.login(username)));
		
	  System.out.println(member);
		
		return member;
	}
}
