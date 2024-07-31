package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class LocationCategoryLarge {
    private Integer locLaCode; // 위치대분류코드
    private String locLaName; // 위치대분류이름
}