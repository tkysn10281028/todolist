package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.constants.ActivateCode;
import com.example.demo.form.SignUpForm;
import com.example.demo.service.SignUpUserService;
import com.example.demo.service.UserService;

@Controller
public class SignUpController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	SignUpUserService signupuserservice;
	
	@Autowired
	UserService userservice;
	
	
	
	
	@RequestMapping(value = "/signup",method = RequestMethod.GET)
	public String signUpGet(@ModelAttribute SignUpForm signUpForm) {		
		return "signup/signup";
	}
	
	@RequestMapping(value = "/signup",method = RequestMethod.POST)
	public String signUpPost(@Validated @ModelAttribute SignUpForm signUpForm,BindingResult bindingresult,Model model) {
		if(bindingresult.hasErrors()) {
			return	"signup/signup";
		}
		if(userservice.findByEmailAddress(signUpForm.getEmailaddress()) != null) {
			model.addAttribute("message", "SIGNUP ERROR! EMAIL ADDRESS ALREADY EXISTS!");
			return "index";
		}
		
		
		signUpForm.setActivatecode(ActivateCode.createActivateCode());
		signupuserservice.sendmail(signUpForm);
		model.addAttribute("signUpForm", signUpForm);
		session.setAttribute("signUpForm", signUpForm);
		
		return "signup/activate";
	}
	
	@RequestMapping(value = "/activate",method = RequestMethod.POST)
	public String activatePost(@RequestParam(name = "code")String activatecode,Model model) {		
		SignUpForm signUpForm = (SignUpForm)session.getAttribute("signUpForm");
		
		
		boolean isActivate = signupuserservice.verifyActivateCode(activatecode, signUpForm);
		if(isActivate) {
			signupuserservice.signup(signUpForm);
		}else {
			model.addAttribute("message", "SIGN UP ERROR!! We Couldn't find Verification code!");
			
			
		}
		return "index";
	}
	
	@RequestMapping(value = "/activate",method = RequestMethod.GET)
	public String activateGet(@ModelAttribute SignUpForm signUpForm) {		
		return "signup/signup";
	}

}
