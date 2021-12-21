package com.example.demo.advice;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.constants.ActivateCode;
import com.example.demo.model.Usertable;
import com.example.demo.repository.UserRepository;

@Aspect
@Component
public class ValidationAdvice {
	@Autowired
	HttpSession session;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	UserRepository userrepository;
	
	
	@Before("execution(* *..*SignUpController.signUpGet(..))")
	    public void testUserTurnfalse(JoinPoint joinpoint){


		session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION",new BadCredentialsException(""));
	 
	}
	
//テストユーザー追加用メソッド　DB内のユーザーが存在しない場合に使用する
	
	@Before("execution(* *..*LoginController.index(..))")
	public void insertuser() {
		
		Optional<Usertable> optuser =  userrepository.findByEmailaddress("test@test.com");
		
		if(optuser.isEmpty()) {
		
		Usertable user = new Usertable();
		user.setUsername("username");
		user.setPassword(encoder.encode("Pleasure1"));
		user.setEmailaddress("test@test.com");
		user.setActivateCode(ActivateCode.createActivateCode());
		user.setRole("ADMIN");
		userrepository.save(user);
		}
		
	}
	
}
