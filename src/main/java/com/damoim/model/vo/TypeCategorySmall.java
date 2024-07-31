package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TypeCategorySmall {
    private Integer typeSCode; // 소분류코드
    private String typeSName; // 소분류이름
    private Integer typeLaCode; // 대분류코드
}

