//package com.example.asamles.app.socialnetwork.twitterutils;
//
//import oauth.signpost.OAuthConsumer;
//import oauth.signpost.OAuthProvider;
//import twitter4j.TwitterFactory;
//import twitter4j.auth.AccessToken;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Handler;
//import android.os.Message;
//import android.preference.PreferenceManager;
//import android.util.Log;
//
//import com.example.asamles.app.socialnetwork.SocialNetworkMain;
//
//public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void> {
//
//	final String TAG = getClass().getName();
//	public static final int RETRIEVAL_SUCCESS = 1;
//	public static final int RETRIEVAL_FAIL = 2;
//
//	private Context	context;
//	private OAuthProvider provider;
//	private OAuthConsumer consumer;
//	private String message;
//	private Handler handler;
//
//	public RetrieveAccessTokenTask(Context context, OAuthConsumer consumer, OAuthProvider provider, String message, Handler handler) {
//		this.context = context;
//		this.consumer = consumer;
//		this.provider = provider;
//		this.message = message;
//		this.handler = handler;
//	}
//
//	@Override
//	protected Void doInBackground(Uri...params) {
//		final Uri uri = params[0];
//
//			final String oauth_verifier = uri.getQueryParameter(oauth.signpost.OAuth.OAUTH_VERIFIER);
//
//			try {
//				provider.retrieveAccessToken(consumer, oauth_verifier);
//
//				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//				AccessToken a = new AccessToken(consumer.getToken(), consumer.getTokenSecret());
//				TwitterUtils.saveAccessToken(prefs, a);
//				Log.i(TAG, "OAuth - Access Token Retrieved");
//                SocialNetworkMain.twitter = new TwitterFactory().getInstance();
//                SocialNetworkMain.twitter.setOAuthConsumer(TwitterConstants.CONSUMER_KEY, TwitterConstants.CONSUMER_SECRET);
//                SocialNetworkMain.twitter.setOAuthAccessToken(a);
//				Log.d(TAG, "Twitter Initialised");
//
//				if (handler != null) {
//					Message msg = new Message();
//					msg.arg1 = RETRIEVAL_SUCCESS;
//					msg.obj = "OAuth - Access Token Retrieval Success";
//					handler.sendMessage(msg);
//				}
//
//				if (message != null) {
//					new SendTweetTask(message, handler).execute();
//				}
//			} catch (Exception e) {
//				Log.e(TAG, "OAuth - Access Token Retrieval Error", e);
//				if (handler != null) {
//					Message msg = new Message();
//					msg.arg1 = RETRIEVAL_FAIL;
//					msg.obj = "OAuth - Access Token Retrieval Error";
//					handler.sendMessage(msg);
//				}
//			}
//
//		return null;
//	}
//}