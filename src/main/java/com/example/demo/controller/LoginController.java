package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
// "/"のget要求を受け取って、index.htmlのビューを返す。	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {

		return "index";
	}

}
