package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class SignUp {
    private String id; // 아이디
    private String pwd; // 비밀번호
    private String addr; // 주소
    private String phone; // 전화번호
    private String email; // 이메일
    private String name; // 이름
    private int age; // 나이
    private char gender; // 성별
    private boolean status; // 유저 가입,탈퇴 여부
}
