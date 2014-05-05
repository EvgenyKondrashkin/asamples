package com.example.asamles.app.socialnetwork.twitterutils;

import twitter4j.TwitterException;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.example.asamles.app.socialnetwork.SocialNetworkMain;


public class SendTweetTask extends AsyncTask<Void, Void, Void> {

	private String message;
	private Handler handler;

	public SendTweetTask(String message, Handler handler) {
		this.message = message;
		this.handler = handler;
	}

	@Override
	protected Void doInBackground(Void... params) {
		String result;
		if (SocialNetworkMain.twitter != null) {
			try {
                SocialNetworkMain.twitter.updateStatus(message);
				result = "Your message has been sent";
			} catch (TwitterException e) {
				result = "Error sending message";
			}
			if (handler != null) {
				Message msg = new Message();
				msg.obj = result;
				handler.sendMessage(msg);
			}
		}

		return null;
	}
}
