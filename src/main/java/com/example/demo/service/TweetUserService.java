package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.form.TweetForm;
import com.example.demo.model.Tweet;
import com.example.demo.model.Usertable;
import com.example.demo.repository.TweetRepository;

@Service
public class TweetUserService {
	@Autowired
	TweetRepository tweetrepository;

	public List<TweetForm> showTweetList(Usertable user) {
		List<Tweet> tweets = tweetrepository.findAll();

		List<TweetForm> tweetforms = new ArrayList<TweetForm>();

		for (int i = 0; i < tweets.size(); i++) {
			if (user.getEmailaddress().equals(tweets.get(i).getUsertable().getEmailaddress())) {
				TweetForm tweetform = new TweetForm();
				tweetform.setRealtweetId(tweets.get(i).getTweetid());
				tweetform.setDate(tweets.get(i).getCreatedate());
				tweetform.setTweet(tweets.get(i).getTweet());
				tweetform.setWhentodo(tweets.get(i).getWhentodone());
				tweetforms.add(tweetform);
			}

		}

		for (int i = 0; i < tweetforms.size(); i++) {
			tweetforms.get(i).setId(i + 1);
		}

		return tweetforms;
	}

	public int tweetRegister(TweetForm tweetForm) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowstr = sdf.format(new Date());

		Tweet tweet = new Tweet();
		tweet.setUsertable(tweetForm.getUser());
		tweet.setCreatedate(nowstr);
		tweet.setTweet(tweetForm.getTweet());
		tweet.setTweetdisableflag(false);
		tweetrepository.save(tweet);

		return tweet.getTweetid();
	}

	public void updateTweet(TweetForm tweetForm, Map<String, String> map) {

		Tweet tweet = new Tweet();
		tweet.setTweetid(Integer.parseInt(map.get("realtweetId")));
		tweet.setUsertable(tweetForm.getUser());
		tweet.setCreatedate(map.get("date"));
		tweet.setTweet(map.get("updatetweet"));
		tweet.setTweetdisableflag(false);
		tweet.setWhentodone(map.get("whentodo"));

		tweetrepository.save(tweet);

	}

	public void deleteTweet(TweetForm tweetForm) {

		Optional<Tweet> opttweet = tweetrepository.findByTweetid(tweetForm.getRealtweetId());
		if (opttweet.isPresent()) {
			tweetrepository.delete(opttweet.get());
		}

	}

	public void updateDate(TweetForm tweetForm, Map<String, String> map) {

		Tweet tweet = new Tweet();
		tweet.setTweetid(Integer.parseInt(map.get("realtweetId")));
		tweet.setUsertable(tweetForm.getUser());
		tweet.setCreatedate(map.get("date"));
		tweet.setTweet(map.get("updatetweet"));
		tweet.setTweetdisableflag(false);
		tweet.setWhentodone(map.get("trip-start"));
		tweetrepository.save(tweet);

	}

}
