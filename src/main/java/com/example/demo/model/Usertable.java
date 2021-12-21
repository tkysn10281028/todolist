package com.example.demo.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
//ユーザーデータを格納するテーブルを作成する処理を記述するエンティティクラス


@Entity
@Data
public class Usertable implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int id;
	
	
	@Column(name = "user_name")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email_address")
	private String emailaddress;
	
	
	@Column(name = "role")
	private String role;
	

	@Column(name = "activate_code")
	private String activateCode;
	
	@OneToMany(mappedBy = "usertable",cascade = CascadeType.ALL)
	private List<Tweet> tweets;
	
	
	
}
