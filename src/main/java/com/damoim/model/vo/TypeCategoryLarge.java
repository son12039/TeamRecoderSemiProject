package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TypeCategoryLarge {
    private Integer typeLaCode; // 대분류코드
    private String typeLaName; // 대분류이름
}
