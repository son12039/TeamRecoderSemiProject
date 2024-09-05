package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class MembershipLocation {
    private int memLocCode; // 클럽 지역 리스트 코드
    private int locSmallCode; // 위치소분류코드
    private int membershipCode; // 클럽
}
