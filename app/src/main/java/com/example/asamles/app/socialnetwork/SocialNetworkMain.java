package com.example.asamles.app.socialnetwork;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asamles.app.R;
import com.example.asamles.app.card.SocialCard;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.android.Facebook;
import com.facebook.model.GraphUser;
import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SocialNetworkMain extends SocialIntegrationFragment {

    private SocialCard fbCard;
	private SocialCard twCard;
	private SocialCard gpCard;
	private SocialCard vkCard;

    public static SocialNetworkMain newInstance() {
        return new SocialNetworkMain();
    }
    
	public SocialNetworkMain() {
    }
    
	// private Session.StatusCallback statusCallback = new SessionStatusCallback();
    // private class SessionStatusCallback implements Session.StatusCallback {
        // @Override
        // public void call(Session session, SessionState state, Exception exception) {
            // updateFacebookCard(fbCard);
        // }
    // }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_social_network, container, false);
        fbCard = (SocialCard) rootView.findViewById(R.id.fb_card);
		twCard = (SocialCard) rootView.findViewById(R.id.tw_card);
		gpCard = (SocialCard) rootView.findViewById(R.id.gp_card);
		vkCard = (SocialCard) rootView.findViewById(R.id.vk_card);

        // Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        // //List<String> permissions = new ArrayList<String>();
        // //permissions.add("email");
        // //permissions.add("user_birthday");
        // Session session = Session.getActiveSession();
        // if (session == null) {
            // if (savedInstanceState != null) {
                // session = Session.restoreSession(getActivity(), null, statusCallback, savedInstanceState);
            // }
            // if (session == null) {
                // session = new Session(getActivity());
            // }
            // Session.setActiveSession(session);
            // if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
                // session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
            // }
        // }
        updateFacebookCard(fbCard);     
        // printHashKey();
		
        return rootView;
    }
	
	// @Override
    // public void onStart() {
        // super.onStart();
        // Session.getActiveSession().addCallback(statusCallback);
    // }

    // @Override
    // public void onStop() {
        // super.onStop();
        // Session.getActiveSession().removeCallback(statusCallback);
    // }

    // @Override
    // public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        // Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);
    // }

    // @Override
    // public void onSaveInstanceState(Bundle outState) {
        // super.onSaveInstanceState(outState);
        // Session session = Session.getActiveSession();
        // Session.saveSession(session, outState);
    // }

    private void updateFacebookCard(SocialCard socialCard) {
        Session session = Session.getActiveSession();
		socialCard.setShareButtonText("{fa-share}  Share");
        if (session.isOpened()) {
            socialCard.setConnectButtonText("{fa-facebook}   Disconnect");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onClickLogout();
                }
            });
			setCardFromUser(socialCard);
            // setDataFromMe(session, socialCard);
        } else {
            socialCard.setConnectButtonText("{fa-facebook} Connect");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onClickLogin();
                }
            });
            defaultSocialCardData(socialCard);
        }
    }
    // private void onClickLogin() {
        // Session session = Session.getActiveSession();
        // List<String> permissions = new ArrayList<String>();
        // permissions.add("email");
        // permissions.add("user_birthday");
        // if (!session.isOpened() && !session.isClosed()) {
            // session.openForRead(new Session.OpenRequest(this).setPermissions(permissions).setCallback(statusCallback));
        // } else {
            // Session.openActiveSession(getActivity(), this, true, statusCallback);
        // }
    // }

    // private void onClickLogout() {
        // Session session = Session.getActiveSession();
        // if (!session.isClosed()) {
            // session.closeAndClearTokenInformation();
        // }
    // }
    private void defaultSocialCardData(SocialCard socialCard) {
        socialCard.setName("NoName");
        socialCard.setBirthday("Birthday: unknown");
        socialCard.setContact("id: unknown");
        socialCard.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
    }

    // private void setDataFromMe(final Session session, final SocialCard socialCard) {
        // Request request = Request.newMeRequest(session,
                // new Request.GraphUserCallback() {
                    // @Override
                    // public void onCompleted(GraphUser user, Response response) {
                        // if (session == Session.getActiveSession()) {
                            // if (user != null) {
                                // socialCard.setName(user.getName());
                                // socialCard.setBirthday("Birthday: "+ user.getBirthday());
                                // socialCard.setContact("id: " + user.getId());
                                // socialCard.setImage("https://graph.facebook.com/" + user.getId() + "/picture?type=large", R.drawable.com_facebook_profile_picture_blank_square, R.drawable.error);
                            // }
                        // }
                        // if (response.getError() != null) {
                            // Error
                        // }
                    // }
                // });
        // request.executeAsync();
    // }
	public void setCardFromUser(SocialCard socialCard){
		GraphUser user = setDataFromMe(session);
		socialCard.setName(user.getName());
        socialCard.setBirthday("Birthday: "+ user.getBirthday());
        socialCard.setContact("id: " + user.getId());
        socialCard.setImage("https://graph.facebook.com/" + user.getId() + "/picture?type=large", R.drawable.com_facebook_profile_picture_blank_square, R.drawable.error);
	}
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
    // public void PublishToFeedInBackground()
    // {
        // Session session = Session.getActiveSession();
        // Request.Callback checkincallback = new Request.Callback() {
            // public void onCompleted(Response response) {
                // FacebookRequestError error = response.getError();
                // if (error != null) {
                    // //error
                // } else {
                    // //succeeded
                // }
            // }
        // };
        // Bundle parameterss = new Bundle();
        // parameterss.putString("message","Hello World");
        // Request request = new Request(session, "me/feed", parameterss,
                // HttpMethod.POST, checkincallback);
        // RequestAsyncTask task = new RequestAsyncTask(request);
        // task.execute();
    // }
}