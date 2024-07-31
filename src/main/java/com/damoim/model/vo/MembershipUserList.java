package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MembershipUserList {
    private Integer listCode; // 클럽가입한 멤버코드
    private String listGrade; // 멤버등급
    private Integer userCode; // 유저코드
    private Integer membershipCode; // 클럽코드
}

