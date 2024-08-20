package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class LocationTypePaging {

	private int page = 1; 	//현재 페이지
	
	private int offset = 0;	//시작위치
	private int limit = 12; //레코드 수
	
	private int pageSize = 5; // 한 페이지 당 페이지 개수
	private int endPage = this.pageSize; // 한 페이지의 마지막 페이지 수
	private int startPage = this.page; 
	
	private boolean prev;
	private boolean next;
	
	public LocationTypePaging(int page, int total) {
		/*
		 * page : 1 ~ 10 -> endPage : 10
		 * page : 11 ~ 20 -> endPage : 20
		 * page : 21 ~ 30 -> endPage : 30
		 * */
		this.page = page;
		this.endPage = (int) Math.ceil((double) this.page /this.pageSize)*this.pageSize;
		this.startPage = this.endPage - this.pageSize + 1;
		
		int lastPage=(int) Math.ceil((double) total / limit);
		
		if(lastPage < this.endPage) {
			this.endPage = lastPage;
		}
		
		// 전체 개수를 통해서 마지막 페이지
		this.prev = this.startPage > 1;
		this.next = this.endPage < lastPage;
	}
	
	
}
