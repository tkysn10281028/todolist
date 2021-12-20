package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userrepository;
	
	public User findByEmailAddress(String emailaddress) {
		Optional<User> optuser =  userrepository.findByEmailaddress(emailaddress);
		if(optuser.isEmpty()) {
			return null;
		}
		return optuser.get();

	}
}
