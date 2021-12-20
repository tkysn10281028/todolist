package com.example.demo.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name = "tweet")
@Data
public class Tweet implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tweetid;
	
	@ManyToOne
	@JoinColumn(name = "email_address")
	private User user;
	
	
	@Column(name = "create_date")
	private String createdate;

	
	@Column(name = "tweet_disable_flag")
	private boolean tweetdisableflag;
	
	@Column(name = "tweet")
	private String tweet;
	
	@Column(name = "when_to_done")
	private String whentodone;
	
	
	
}
