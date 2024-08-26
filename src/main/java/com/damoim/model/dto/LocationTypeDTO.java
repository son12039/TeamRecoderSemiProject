package com.damoim.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class LocationTypeDTO {
	
    private String locLaName;
    private String locSName; 
	private String typeLaName; 
    private String typeSName;
}
