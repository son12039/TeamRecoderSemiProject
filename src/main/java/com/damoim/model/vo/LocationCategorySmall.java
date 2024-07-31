package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class LocationCategorySmall {
    private Integer locSCode; // 위치소분류코드
    private String locSName; // 위치소분류이름
    private Integer locLaCode; // 위치대분류코드
}
