package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MembershipLocation {
    private Integer memLocCode; // 클럽 지역 리스트 코드
    private Integer locSCode; // 소분류
    private Integer membershipCode; // 클럽
}
