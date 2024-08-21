package com.damoim.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damoim.model.vo.MainComment;

import mapper.MainCommentMapper;

@Service
public class MainCommentService {
	@Autowired
	private MainCommentMapper mapper;

	public void insertComment(MainComment mainComment) {
		mapper.insertComment(mainComment);
	}
	public ArrayList<MainComment>allMainComment(int membershipCode){
		System.out.println(mapper.allMainComment(membershipCode));
		return mapper.allMainComment(membershipCode);
	}
}
