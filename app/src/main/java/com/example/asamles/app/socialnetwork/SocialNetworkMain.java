package com.example.asamles.app.socialnetwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.R;
import com.example.asamles.app.card.SocialCard;
import com.example.asamles.app.dialog.ADialogs;
import com.example.asamles.app.socialnetwork.twitter.ConstantValues;
import com.example.asamles.app.socialnetwork.twitter.TwitterUtil;
//import com.example.asamles.app.socialnetwork.twitterutils.OAuthRequestTokenTask;
//import com.example.asamles.app.socialnetwork.twitterutils.SendTweetTask;
//import com.example.asamles.app.socialnetwork.twitterutils.TwitterConstants;
////import com.example.asamles.app.socialnetwork.twitterutils.TwitterDialog;
//import com.example.asamles.app.socialnetwork.twitterutils.TwitterUtils;
import com.example.asamles.app.socialnetwork.twitterutils.TwitterDialog;
import com.facebook.Session;
import com.facebook.model.GraphUser;

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

	public boolean isOnline() {
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
//			setTwitterCardFromUser(socialCard, twitter, requestToken, verifier);
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
        new TwitterAuthenticateTask().execute();
	}
	public void twitterLogout(){

		Toast.makeText(getActivity(), "Signed Off", Toast.LENGTH_SHORT).show();
	}

	public void sendTweet() {
		String message = "Test tweet message from ASample";

	}


    class TwitterAuthenticateTask extends AsyncTask<String, String, RequestToken> {

        @Override
        protected void onPostExecute(RequestToken requestToken) {
            if (requestToken!=null)
            {
                new TwitterDialog(getActivity(), requestToken.getAuthenticationURL()).show();
            }
        }

        @Override
        protected RequestToken doInBackground(String... params) {
            return TwitterUtil.getInstance().getRequestToken();
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "New Intent Arrived");
        dealWithTwitterResponse(intent);
    }
}