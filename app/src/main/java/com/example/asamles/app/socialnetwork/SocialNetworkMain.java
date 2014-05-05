package com.example.asamles.app.socialnetwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.R;
import com.example.asamles.app.card.SocialCard;
import com.example.asamles.app.dialog.ADialogs;
import com.example.asamles.app.socialnetwork.twitterutils.OAuthRequestTokenTask;
import com.example.asamles.app.socialnetwork.twitterutils.SendTweetTask;
import com.example.asamles.app.socialnetwork.twitterutils.TwitterConstants;
import com.example.asamles.app.socialnetwork.twitterutils.TwitterDialog;
import com.example.asamles.app.socialnetwork.twitterutils.TwitterUtils;
import com.facebook.Session;
import com.facebook.model.GraphUser;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class SocialNetworkMain extends SocialIntegrationFragment implements SocialIntegrationFragment.FacebookIntegration {

    private ADialogs progressDialog;
    private SocialCard fbCard;
    private Session facebookSession;
    private GraphUser facebookMe;
	private SocialCard twCard;
	private SocialCard gpCard;
	private SocialCard vkCard;
    public static SocialNetworkMain newInstance() {
        return new SocialNetworkMain();
    }
    
	public SocialNetworkMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        progressDialog = new ADialogs(getActivity());
        progressDialog.progress(false, "Fetching data...");
        View rootView = inflater.inflate(R.layout.fragment_social_network, container, false);

        fbCard = (SocialCard) rootView.findViewById(R.id.fb_card);
        setOnFacebookCall(this);
        facebookSession = setFacebookSession();
        updateFacebookCard(fbCard, facebookSession);
		fbCard.share.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    PublishToFeedInBackground();
                }
            });

		twCard = (SocialCard) rootView.findViewById(R.id.tw_card);
		updateTwitterCard(twCard);
		twCard.share.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    sendTweet();
                }
            });
		
		gpCard = (SocialCard) rootView.findViewById(R.id.gp_card);
		vkCard = (SocialCard) rootView.findViewById(R.id.vk_card);
		
        return rootView;
    }
    private void defaultSocialCardData(SocialCard socialCard) {
        socialCard.setName("NoName");
        socialCard.setBirthday("Birthday: unknown");
        socialCard.setContact("id: unknown");
    }

    private void updateFacebookCard(final SocialCard socialCard, final Session session) {
		socialCard.setShareButtonText("{fa-share}  Share");
        if (session.isOpened()) {
            socialCard.setConnectButtonText("{fa-facebook}   Disconnect");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onFacebookClickLogout();
                }
            });
			setFacebookCardFromUser(socialCard);
        } else {
            socialCard.setConnectButtonText("{fa-facebook} Connect");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onFacebookClickLogin();
                }
            });
            defaultSocialCardData(socialCard);
        }
    }

	public void setFacebookCardFromUser(SocialCard socialCard){
        facebookMe = setFacebookMe();
        if (facebookMe == null){
            progressDialog.showProgress();
        } else {
            progressDialog.cancelProgress();
            socialCard.setName(facebookMe.getName());
            socialCard.setBirthday("Birthday: "+ facebookMe.getBirthday());
            socialCard.setContact("Token: " + facebookMe.getId());
            socialCard.setImage("https://graph.facebook.com/" + facebookMe.getId() + "/picture?type=large", R.drawable.com_facebook_profile_picture_blank_square, R.drawable.error);
        }
    }

    @Override
    public void onFacebookCall() {
        facebookSession = setFacebookSession();
        updateFacebookCard(fbCard, facebookSession);
    }

    @Override
    public void onFacebookLogout() {
        facebookSession = setFacebookSession();
        updateFacebookCard(fbCard, facebookSession);
    }
//=========================================================================================
    // public void printHashKey() {
        // try {
            // PackageInfo info = getActivity().getPackageManager().getPackageInfo("com.example.asamles.app",
                    // PackageManager.GET_SIGNATURES);
            // for (Signature signature : info.signatures) {
                // MessageDigest md = MessageDigest.getInstance("SHA");
                // md.update(signature.toByteArray());
                // Log.d("TEMPTAGHASH KEY:",
                        // Base64.encodeToString(md.digest(), Base64.DEFAULT));
            // }
        // } catch (PackageManager.NameNotFoundException e) {

        // } catch (NoSuchAlgorithmException e) {

        // }

    // }
//========================================================================================>
	public static Twitter twitter;
	RequestToken requestToken;
	String verifier;
	public static OAuthConsumer consumer;
	public static OAuthProvider provider;
    public static ProgressDialog progressDialog1;
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {

			return true;
		}
		return false;
	}
	private void updateTwitterCard(final SocialCard socialCard) {
		socialCard.setShareButtonText("{fa-share}  Share tweet");
		if(isOnline()){
			socialCard.setConnectButtonText("{fa-twitter}   Logout");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    twitterLogout();
                }
            });
			setTwitterCardFromUser(socialCard, twitter, requestToken, verifier);
		} else {
			socialCard.setConnectButtonText("{fa-twitter} login");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    twitterLogin();
                }
            });
            defaultSocialCardData(socialCard);
		}
	}
	public void setTwitterCardFromUser(SocialCard socialCard, Twitter twitter, RequestToken requestToken, String verifier){
        AccessToken accessToken = null;
        try {
            accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
            long userID = accessToken.getUserId();
            User user = twitter.showUser(userID);
            socialCard.setName(user.getName());
            socialCard.setBirthday(user.getDescription());
            socialCard.setContact("Token: " + accessToken.toString());
            socialCard.setImage(user.getBiggerProfileImageURL(), R.drawable.twitter_user, R.drawable.error);

        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }
	public void twitterLogin(){
		if (isOnline()) {
			if (twitter == null) {
					signOnTwitter();
				}
			} else {
			   Toast.makeText(getActivity(), "network unavaliable",
			   Toast.LENGTH_SHORT).show();
			}
	}
	public void twitterLogout(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		SharedPreferences.Editor e = prefs.edit();
		e.remove(OAuth.OAUTH_TOKEN);
		e.remove(OAuth.OAUTH_TOKEN_SECRET);
		e.commit();
		twitter = null;
	
		Toast.makeText(getActivity(), "Signed Off", Toast.LENGTH_SHORT).show();
	}
	public void signOnTwitter() {

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());

		if (twitter == null) {
			twitter = TwitterUtils.isAuthenticated(prefs);
		}

		if (twitter == null) {
			progressDialog1 = ProgressDialog.show(getActivity(), "", "Please wait");
			Toast.makeText(getActivity(), "Please authorize this app!",
					Toast.LENGTH_LONG).show();
			consumer = new CommonsHttpOAuthConsumer(
					TwitterConstants.CONSUMER_KEY,
					TwitterConstants.CONSUMER_SECRET);
			provider = new CommonsHttpOAuthProvider(
					TwitterConstants.REQUEST_URL, TwitterConstants.ACCESS_URL,
					TwitterConstants.AUTHORIZE_URL);
			new OAuthRequestTokenTask(this, consumer, provider, new Handler() {

				@Override
				public void handleMessage(Message msg) {
					if (progressDialog1 != null) {
						progressDialog1.dismiss();
						progressDialog1 = null;
					}
					Toast.makeText(
							getActivity(),
							"Error during Twitter Authorization: "
									+ "OAUth retrieve request token failed",
							Toast.LENGTH_LONG).show();
				}

			}).execute();
		} else {
			// ((ImageView)findViewById(R.id.twitter_sign_on_btn)).setImageResource(R.drawable.twitter_icon);
		}
	}
	public void sendTweet() {
		String message = "Test tweet message from ASample";
		if (message != null && !("".equals(message))) {
			sendTweetit(message);
		}
	}
	public void sendTweetit(String message) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());

		if (twitter == null) {
			twitter = TwitterUtils.isAuthenticated(prefs);
		}

		if (twitter != null) {
			new SendTweetTask(message, new Handler() {

				public void handleMessage(Message msg) {
					if (msg != null && msg.obj != null)
						Toast.makeText(getActivity(),
								(String) msg.obj, Toast.LENGTH_LONG).show();
					//
					if (progressDialog1 != null) {
						progressDialog1.dismiss();
						progressDialog1 = null;
					}
				}

			}).execute();
		} else {
			progressDialog1 = ProgressDialog.show(getActivity(), "", "Please wait");
			Toast.makeText(getActivity(), "Please authorize this app!",
					Toast.LENGTH_LONG).show();
			consumer = new CommonsHttpOAuthConsumer(
					TwitterConstants.CONSUMER_KEY,
					TwitterConstants.CONSUMER_SECRET);
			provider = new CommonsHttpOAuthProvider(
					TwitterConstants.REQUEST_URL, TwitterConstants.ACCESS_URL,
					TwitterConstants.AUTHORIZE_URL);
			// store message for future use
			new OAuthRequestTokenTask(this, consumer, provider, new Handler() {

				@Override
				public void handleMessage(Message msg) {
					if (progressDialog1 != null) {
						progressDialog1.dismiss();
						progressDialog1 = null;
					}
					Toast.makeText(
							getActivity(),
							"Error during Twitter Authorization: "
									+ "OAUth retrieve request token",
							Toast.LENGTH_LONG).show();
				}

			}).execute();
		}
	}
    public void showTwitterDialog(String url) {
        new TwitterDialog(getActivity(), url).show();
    }
}