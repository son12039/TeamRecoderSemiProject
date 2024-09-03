package com.damoim.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class UserInfoPaging {
	
	private int page = 1; // 현재 페이지

	private int offset = 0; // 시작 위치
	private int limit = 4; // 레코드 수
	
	private int pageSize = 4; // 한 페이지 당 페이지 개수
	private int endPage = this.pageSize; // 한 페이지의 마지막 페이지 수
	private int startPage = this.page; // 한 페이지의 첫 페이지 수

	private boolean prev; // 이전 페이지가 있는지
	private boolean next;
	
	// 생성자일때
	public UserInfoPaging(int page, int total) {

		/*
		 * endPage 부터 계산 page : 1 ~ 4 => endPage : 4 page : 5 ~ 9 => endPage : 9
		 * page : 10 ~ 14 => endPage : 14
		 */
		this.page = page;
		this.endPage = (int) Math.ceil((double) this.page / this.pageSize) * this.pageSize;
		this.startPage = this.endPage - this.pageSize + 1;
		
		// 전체 개수를 통해서 마지막 페이지
		int lastPage = ((int) Math.ceil((double) total / this.limit ));
		
		if(lastPage < this.endPage) {
			this.endPage = lastPage;
		}
		
		
		this.prev = this.startPage > 1; // 1 이상인 경우만
		this.next = this.endPage < lastPage;
		
		
	}
	
	
	
	
	
	

}
