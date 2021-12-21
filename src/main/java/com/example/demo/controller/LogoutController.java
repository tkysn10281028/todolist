package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogoutController {
// "/home"のgetを受け取って、index.htmlのビューを返す。/logoutだとsecurityconfigの設定と被るので設定
	@RequestMapping(value = "/home",method = RequestMethod.GET)
	public String home() {
		return "index";
	}
}
