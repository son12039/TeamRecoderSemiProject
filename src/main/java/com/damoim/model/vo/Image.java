package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Image {
    private Integer imgCode; // 사진코드
    private String imgUrl; // 사진 URL
    private Integer mainCode; // 홍보게시판코드
    private Integer meetCode; // 모임게시판코드
}

