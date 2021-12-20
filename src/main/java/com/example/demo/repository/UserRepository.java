package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;



//saveメソッド実行してcreate tableを実行する
//findbyusernameでnameからユーザー名を探す処理

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
		List<User> findAll();
		Optional<User> findByEmailaddress(String emailaddress);
		


	  
}
