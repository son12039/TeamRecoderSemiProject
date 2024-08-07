package com.damoim.model.dto;

import java.sql.Date;

import com.damoim.model.vo.Member;
import com.damoim.model.vo.Membership;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MemberInfoDTO {

	 private String id; // 아이디
	    private String pwd; // 비밀번호
	    private String addr; // 주소
	    private String phone; // 전화번호
	    private String email; // 이메일
	    private String name; // 이름
	    private int age; // 나이
	    private char gender; // 성별
	    private boolean status; // 유저 가입,탈퇴 여부
	    private double MemberManner; // 유저온도
	    private String nickname; // 닉네임
	    private String MemberImg; // 유저프로필사진
	    private String MemberHobby; // 유저 관심사
	    private String MemberInfo; // 유저 간단한 자기소개
	    private String MemberLocation; // 유저선호지역
	    private String MemberType; // 유저 선호만남유형
	    
	    private int membershipCode; // 클럽코드
	    private String membershipName; // 클럽이름
	    private String membershipImg; // 클럽메인사진
	    private String membershipInfo; // 클럽소개
	    private Date membershipDate; // 클럽생성날짜
	    private double membershipGrade; // 클럽 별점
	    private int membershipMax; // 클럽최대인원
	    
	    
	    private int listCode; // 클럽가입한 멤버코드
	    private String listGrade; // 멤버등급
	
	    private Member member; // 멤버 연결 
	    private Membership membership; // 멤버십 연결 
	
	
	
}
