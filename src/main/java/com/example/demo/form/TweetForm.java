package com.example.demo.form;

import com.example.demo.model.Usertable;

import lombok.Data;

@Data
public class TweetForm {
	private int id;
	private String tweet;
	private String date;
	private Usertable user;
	private int realtweetId;
	private String whentodo;
	
	
}
