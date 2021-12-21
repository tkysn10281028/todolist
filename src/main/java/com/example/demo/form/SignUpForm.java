package com.example.demo.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class SignUpForm {
	@NotBlank
	private String username;

	@NotBlank
	@Length(min = 8, max = 100)
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	private String password;

	@NotBlank
	@Email
	private String emailaddress;
	private String role;

	private String activatecode;

}
