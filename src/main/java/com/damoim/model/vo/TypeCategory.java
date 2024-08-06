package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TypeCategory {
    private int typeCode; 
    private String typeLaName; 
    private String typeSName;
}


//type_code, type_la_name, type_s_name