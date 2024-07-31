package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Channel {
    private int chanCode; // 클럽채널코드
    private String chanName; // 채널명
    private String chanImg; // 채널 사진
    private int userCode; // 유저코드
    private int membershipCode; // 클럽코드
}

