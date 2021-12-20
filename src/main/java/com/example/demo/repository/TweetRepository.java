package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Integer>{
	
	List<Tweet> findAll();
	
//	Optional<User> findByEmailaddress(String emailaddress);
	
	Optional<Tweet> findByTweetid(int tweetid);
	
}
