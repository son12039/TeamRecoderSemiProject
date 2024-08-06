package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class MembershipUserList {
    private int listCode; // 클럽가입한 멤버코드
    private String listGrade; // 멤버등급
  //  private int userCode ; // 유저코드
 //   private int membershipCode; // 클럽코드

    
    private Member member; // 멤버 연결 
    private Membership membership; // 멤버십 연결 
}

