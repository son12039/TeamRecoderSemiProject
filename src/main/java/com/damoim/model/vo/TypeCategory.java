package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TypeCategory {
    private int typeSmallCode; // 소분류코드
    private String typeSmallName; // 소분류이름
    private String typeLargeName; // 대분류이름
}


