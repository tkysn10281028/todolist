package com.example.demo.form;

import com.example.demo.model.User;

import lombok.Data;

@Data
public class TweetForm {
	private int id;
	private String tweet;
	private String date;
	private User user;
	private int realtweetId;
	private String whentodo;
	
	
}
