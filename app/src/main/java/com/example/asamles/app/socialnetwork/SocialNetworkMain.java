package com.example.asamles.app.socialnetwork;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class SocialNetworkMain extends Fragment {

    private Button fbButton, tButton, gButton, vkButton;
    private TextView name;

    public static SocialNetworkMain newInstance() {
        return new SocialNetworkMain();
    }

    private Session.StatusCallback statusCallback = new SessionStatusCallback();
    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            updateView();
        }
    }
    private void updateView() {
        Session session = Session.getActiveSession();
        if (session.isOpened()) {
            fbButton.setText("logout");
            fbButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) { onClickLogout(); }
            });
            makeMeRequest(session);
        } else {
            fbButton.setText("login");
            fbButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) { onClickLogin(); }
            });
            defaultData();
        }
    }
    private void onClickLogin() {
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
        } else {
            Session.openActiveSession(getActivity(), this, true, statusCallback);
        }
    }

    private void onClickLogout() {
        Session session = Session.getActiveSession();
        if (!session.isClosed()) {
            session.closeAndClearTokenInformation();
        }
    }
    public SocialNetworkMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_social_network, container, false);
        name = (TextView) rootView.findViewById(R.id.name);
        fbButton = (Button) rootView.findViewById(R.id.facebook_button);
        Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
//        fbButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                fbButton.setTextColor(getActivity().getResources().getColor(R.color.green));
//            }
//        });
        Session session = Session.getActiveSession();
        if (session == null) {
            if (savedInstanceState != null) {
                session = Session.restoreSession(getActivity(), null, statusCallback, savedInstanceState);
            }
            if (session == null) {
                session = new Session(getActivity());
            }
            Session.setActiveSession(session);
            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
                session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
            }
        }

        updateView();

        tButton = (Button) rootView.findViewById(R.id.twitter_button);
        gButton = (Button) rootView.findViewById(R.id.google_button);
        vkButton = (Button) rootView.findViewById(R.id.vk_button);
        tButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tButton.setTextColor(getActivity().getResources().getColor(R.color.green));
//                printHashKey();
            }
        });
        gButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gButton.setTextColor(getActivity().getResources().getColor(R.color.green));
                PublishToFeedInBackground();
            }
        });

        vkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vkButton.setTextColor(getActivity().getResources().getColor(R.color.green));
            }
		});
        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        Session.getActiveSession().addCallback(statusCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        Session.getActiveSession().removeCallback(statusCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Session session = Session.getActiveSession();
        Session.saveSession(session, outState);
    }
//    public void printHashKey() {
//
//        try {
//            PackageInfo info = getActivity().getPackageManager().getPackageInfo("com.example.asamles.app",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("TEMPTAGHASH KEY:",
//                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }
//
//    }
    private void defaultData(){
        name.setText("NoName");
    }
    private void makeMeRequest(final Session session) {
        // Make an API call to get user data and define a
        // new callback to handle the response.
        Request request = Request.newMeRequest(session,
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                        // If the response is successful
                        if (session == Session.getActiveSession()) {
                            if (user != null) {
                                // Set the id for the ProfilePictureView
                                // view that in turn displays the profile picture.
//                                profilePictureView.setProfileId(user.getId());
                                // Set the Textview's text to the user's name.
//                                userNameView.setText(user.getName());
                                name.setText(user.getName());
                            }
                        }
                        if (response.getError() != null) {
                            // Handle errors, will do so later.
                        }
                    }
                });
        request.executeAsync();
    }
    public void PublishToFeedInBackground()
    {
        Session session = Session.getActiveSession();
        Request.Callback checkincallback = new Request.Callback() {
            public void onCompleted(Response response) {
                FacebookRequestError error = response.getError();
                if (error != null) {
                    // error
                } else {
                    //succeeded
                }
            }
        };
        Bundle parameterss = new Bundle();
        parameterss.putString("message","Hello World");
        Request request = new Request(session, "me/feed", parameterss,
                HttpMethod.POST, checkincallback);
        RequestAsyncTask task = new RequestAsyncTask(request);
        task.execute();
    }
}