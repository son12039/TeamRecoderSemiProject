package com.damoim.model.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class LocationCategoryLarge {
    private int locLargeCode; // 위치대분류코드
    private String locLargeName; // 위치대분류이름
}