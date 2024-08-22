package com.damoim.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class LocationCategory {
    private int locCode;
    private String locLaName;
    private String locSName; 
}


//loc_code, loc_la_name, loc_s_name