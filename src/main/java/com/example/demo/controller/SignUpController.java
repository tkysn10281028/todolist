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

//　/signupのget要求を受け取る。Signupformのインスタンス化を実行
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signUpGet(@ModelAttribute SignUpForm signUpForm) {
		return "signup/signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signUpPost(@Validated @ModelAttribute SignUpForm signUpForm, BindingResult bindingresult,
			Model model) {
//バインドしたフィールドにエラーが出たらsignupにリダイレクト
		if (bindingresult.hasErrors()) {
			return "signup/signup";
		}
//メールアドレスが既に存在したらindex.htmlにリダイレクト、エラーメッセージも合わせて表示
		if (userservice.findByEmailAddress(signUpForm.getEmailaddress()) != null) {
			model.addAttribute("message", "SIGNUP ERROR! EMAIL ADDRESS ALREADY EXISTS!");
			return "index";
		}
//アクティベートコード作成してsignupformにセット
		signUpForm.setActivatecode(ActivateCode.createActivateCode());
//signupformを渡してメール送信を実行。終わったらactivateのビューを返す。
		signupuserservice.sendmail(signUpForm);

		model.addAttribute("signUpForm", signUpForm);
//signup時の情報をセッションにも入れて2段階認証時に値を使用
		session.setAttribute("signUpForm", signUpForm);

		return "signup/activate";
	}

// /activateのpost要求を受け取る。入力値を受け取る。
	@RequestMapping(value = "/activate", method = RequestMethod.POST)
	public String activatePost(@RequestParam(name = "code") String activatecode, Model model) {

//セッション情報に入っているsignupformの内容のactivatecodeの比較　verifyactivatecodeの呼び出しを行う
		SignUpForm signUpForm = (SignUpForm) session.getAttribute("signUpForm");
		boolean isActivate = signupuserservice.verifyActivateCode(activatecode, signUpForm);
		
		if (isActivate) {
//OKなら登録処理
			signupuserservice.signup(signUpForm);
		} else {
//NGならエラーメッセージをモデルに追加 ログイン画面に遷移を返す　index.htmlへ遷移
			model.addAttribute("message", "SIGN UP ERROR!! We Couldn't find Verification code!");

		}
		return "index";
	}

///activateのgetを受ける。アクセス許可しているのでアクセスされた際の挙動制御用。signup画面に遷移
	@RequestMapping(value = "/activate", method = RequestMethod.GET)
	public String activateGet(@ModelAttribute SignUpForm signUpForm) {
		return "signup/signup";
	}

}
