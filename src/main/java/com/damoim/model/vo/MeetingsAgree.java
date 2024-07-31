package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MeetingsAgree {
    private Integer meetAgreeCode; // 참여여부 코드
    private Boolean meetAgreeYn; // 참여 여부
    private Integer listCode; // 클럽멤버 코드
}


