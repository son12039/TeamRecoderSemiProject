package com.damoim.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MessageDAO {
	private String img;
    private String message;
    private String nickname;
    private Date date;
    private String memimg;
    MessageDAO(){ // 쓸 때마다 현재시각으로 변경
        date = new Date();
    }
}
