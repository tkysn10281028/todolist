package com.example.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.form.TweetForm;
import com.example.demo.model.Usertable;
import com.example.demo.service.TweetUserService;
import com.example.demo.service.UserService;

@Controller
public class TweetController {

	@Autowired
	TweetUserService tweetuserservice;

	@Autowired
	UserService userservice;

	@Autowired
	HttpSession session;

	@RequestMapping(value = "/tweet", method = RequestMethod.GET)
	public String tweetGet(@ModelAttribute TweetForm tweetForm, Model model, Authentication auth) {
		Usertable user = userservice.findByEmailAddress(auth.getName());

		model.addAttribute("username", user.getUsername());

		List<TweetForm> tweetlist = tweetuserservice.showTweetList(user);
		model.addAttribute("tweetList", tweetlist);

		return "main/main";
	}

///tweetのpost要求を受け取る。
	@RequestMapping(value = "/tweet", method = RequestMethod.POST)
	public String tweetPost(@ModelAttribute TweetForm tweetForm, Model model, Authentication auth) throws IOException {

		Usertable user = userservice.findByEmailAddress(auth.getName());
		tweetForm.setUser(user);

//tweetuserserviceのtweetregisterサービス読んで呟きの登録処理
		int tweetid = tweetuserservice.tweetRegister(tweetForm);
		model.addAttribute("tweet_id", tweetid);
// /tweetのget要求を飛ばして再度リストを表示させる（リダイレクト）
		return tweetGet(tweetForm, model, auth);

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editPost(@ModelAttribute TweetForm tweetForm, Model model, Authentication auth,
			@RequestParam Map<String, String> map) {

		Usertable user = userservice.findByEmailAddress(auth.getName());
		tweetForm.setUser(user);

		Optional<String> delete = Optional.ofNullable(map.get("delete"));
		Optional<String> update = Optional.ofNullable(map.get("update"));
		Optional<String> updatedate = Optional.ofNullable(map.get("updatedate"));
//@requestparamをmap<string,string>で受け取ることで全パラメータ取得。
//このmapオブジェクトでname属性に入っている値を元に分岐を制御する
//空の場合はeditが押された、値が入っている場合はdeleteが押された
		if (delete.isPresent()) {

			tweetuserservice.deleteTweet(tweetForm);
			model.addAttribute("isEdit", false);

		} else if (update.isPresent()) {

			tweetuserservice.updateTweet(tweetForm, map);
			model.addAttribute("isEdit", false);

		} else if (updatedate.isPresent()) {
			tweetuserservice.updateDate(tweetForm, map);
			model.addAttribute("isEdit", false);

		}

		else {
			model.addAttribute("isEdit", true);

		}

		return tweetGet(tweetForm, model, auth);
	}

}
