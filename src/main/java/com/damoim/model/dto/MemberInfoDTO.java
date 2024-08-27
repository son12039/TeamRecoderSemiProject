package com.damoim.model.dto;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.damoim.model.vo.Member;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.MembershipUserList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberInfoDTO {

	private Member member;
	private List<MembershipUserList> membershipUserList; // 해당맴버가 가입되어있는 맴버쉽 정보+ 등급
    private int memberMeetCount; // 해당 멤버의 모임 참여 횟수

}
