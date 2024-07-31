package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class LocationCategorySmall {
    private int locSmallCode; // 위치소분류코드
    private String locSmallName; // 위치소분류이름
    private int locLargeCode; // 위치대분류코드
}
