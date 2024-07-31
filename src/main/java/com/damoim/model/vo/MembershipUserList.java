package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MembershipUserList {
    private int listCode; // 클럽가입한 멤버코드
    private String listGrade; // 멤버등급
    private int userCode; // 유저코드
    private int membershipCode; // 클럽코드
}

