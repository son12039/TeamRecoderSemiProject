package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MembershipType {
    private Integer memTypeCode; // 클럽 유형 리스트 코드
    private Integer typeSCode; // 소분류이름
    private Integer membershipCode; // 클럽
}


