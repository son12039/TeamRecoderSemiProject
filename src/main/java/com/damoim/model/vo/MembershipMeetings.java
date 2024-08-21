package com.damoim.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipMeetings {
	private int meetCode; // 모임게시판코드 1 자동생성 
	private int membershipCode; // 클럽코드 2 
	private Date meetDateStart; // 모임 시작일
	private Date meetDateEnd; // 모임 종료일
//	private int meetAgreeCode; // 참여여부 테이블 연결
	private String meetInfo; // 모임관련 정보 구분자로 지정해서 캘린더에 제목만 표시해야할듯?
//	private Date meetCreatDate; // 모임 생성일
	private String id; // 모임 작성글 작성자 = id
}
