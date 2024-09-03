package com.damoim.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class MemberUserDTO {
	
    private String id;
    private String nickname;
    private int membershipCode;
    private String membershipName;
}
