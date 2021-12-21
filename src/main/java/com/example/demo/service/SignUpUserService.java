package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.form.SignUpForm;
import com.example.demo.model.Usertable;
import com.example.demo.repository.UserRepository;

@Service
public class SignUpUserService {
	@Autowired
	private MailSender sender;

	@Autowired
	UserRepository userrepository;

	@Autowired
	PasswordEncoder encoder;

	public void sendmail(SignUpForm signUpForm) {
		String activatecode = signUpForm.getActivatecode();
		String emailaddress = signUpForm.getEmailaddress();

		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setFrom("test@idea-edge.com");
		msg.setTo(emailaddress);
		msg.setSubject("verification from sample application!!");
		String text = "Your Verification Code Is : \n\n" + "※※※※※※※※※※※※※※※※※※※※※※※※\n" + activatecode
				+ "\nPlease Sign In With This Code.\n Thank You.";

		msg.setText(text); // 本文の設定
		this.sender.send(msg);
	}

	public boolean verifyActivateCode(String activatecode, SignUpForm signUpForm) {
		if (activatecode.equals(signUpForm.getActivatecode())) {
			return true;
		}
		return false;
	}

	public void signup(SignUpForm signUpForm) {
		Usertable user = new Usertable();
		user.setUsername(signUpForm.getUsername());
		user.setEmailaddress(signUpForm.getEmailaddress());
		user.setPassword(encoder.encode(signUpForm.getPassword()));
		user.setRole("USER");
		user.setActivateCode(signUpForm.getActivatecode());

		userrepository.save(user);

	}

}
