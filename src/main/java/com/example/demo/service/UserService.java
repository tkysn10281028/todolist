package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Usertable;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userrepository;
	
	public Usertable findByEmailAddress(String emailaddress) {
		Optional<Usertable> optuser =  userrepository.findByEmailaddress(emailaddress);
		if(optuser.isEmpty()) {
			return null;
		}
		return optuser.get();

	}
}
