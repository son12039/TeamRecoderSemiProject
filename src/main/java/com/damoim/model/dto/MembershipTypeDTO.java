package com.damoim.model.dto;

import com.damoim.model.vo.Member;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.MembershipUserList;
import com.damoim.model.vo.TypeCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MembershipTypeDTO {
	   private int memTypeCode; // 클럽 유형 리스트 코드
	    private int typeSmallCode; // 소분류이름
	    private int membershipCode; // 클럽
	 
	   
	    
	    
	    private TypeCategory typeCategory; // 관계성 
	    private Membership membership; // 관계성 
	        
	    
	    private Member member; //관계성 
	    
	    private int count;
}
