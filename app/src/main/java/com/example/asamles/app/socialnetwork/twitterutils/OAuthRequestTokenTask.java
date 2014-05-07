//package com.example.asamles.app.socialnetwork.twitterutils;
//
//import oauth.signpost.OAuthConsumer;
//import oauth.signpost.OAuthProvider;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.os.Handler;
//import android.os.Message;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.example.asamles.app.socialnetwork.SocialNetworkMain;
//
//public class OAuthRequestTokenTask extends AsyncTask<Void, Void, Void> {
//
//	final String TAG = getClass().getName();
//    private SocialNetworkMain context;
//	private OAuthProvider provider;
//	private OAuthConsumer consumer;
//	private Handler errorHandler;
//	private String url;
//
//	public OAuthRequestTokenTask(SocialNetworkMain context, OAuthConsumer consumer, OAuthProvider provider, Handler errorHandler) {
//		this.context =  (SocialNetworkMain)context;
//		this.consumer = consumer;
//		this.provider = provider;
//		this.errorHandler = errorHandler;
//	}
//
//	@Override
//	protected Void doInBackground(Void... params) {
//		try {
//			Log.i(TAG, "Retrieving request token");
//			url = provider.retrieveRequestToken(consumer, TwitterConstants.OAUTH_CALLBACK_URL);
//
//		} catch (Exception e) {
//			if (errorHandler != null) errorHandler.sendMessage(new Message());
//			Log.e(TAG, "Error during OAUth retrieve request token", e);
//		}
//		return null;
//	}
//
//	@Override
//	protected void onPostExecute(Void result) {
//		if (!TextUtils.isEmpty(url)) {
//            context.showTwitterDialog(url);
//		}
//	}
//
//
//}