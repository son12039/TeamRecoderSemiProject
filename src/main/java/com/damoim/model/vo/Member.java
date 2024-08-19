package com.damoim.model.vo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import com.damoim.model.dto.MemberListDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Member implements UserDetails {
    private String id; // 아이디
    private String pwd; // 비밀번호
    private String addr; // 주소
    private String phone; // 전화번호
    private String email; // 이메일
    private String name; // 이름
    private int age; // 나이
    private char gender; // 성별
    private boolean status; // 유저 가입,탈퇴 여부
    private double memberManner; // 유저온도
    private String nickname; // 닉네임
    private String memberImg; // 유저프로필사진
    private String memberHobby; // 유저 관심사
    private String memberInfo; // 유저 간단한 자기소개
    private String memberLocation; // 유저선호지역
    private String memberType; // 유저 선호만남유형
    private MultipartFile file;
    
    private List<MemberListDTO> memberListDTO;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return Collections.emptyList();
	}

	
	@Override
	public String getUsername() {
		
		return id;
	}


	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return pwd;
	}


	



	
	
    
}
