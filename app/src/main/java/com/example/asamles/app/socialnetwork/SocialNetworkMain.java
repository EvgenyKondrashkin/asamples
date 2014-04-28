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

public class SocialNetworkMain extends Fragment {

    private Button fbButton, tButton, gButton, vkButton;
    private TextView name;
    boolean open;
    SocialCard fbCard;

    public static SocialNetworkMain newInstance() {
        return new SocialNetworkMain();
    }

    private Session.StatusCallback statusCallback = new SessionStatusCallback();
    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            updateView();
            updateSocialCard(fbCard);
        }
    }
    private void updateView() {
        Session session = Session.getActiveSession();
        if (session.isOpened()) {
            open = true;
            fbButton.setText("logout");
            fbButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) { onClickLogout(); }
            });
            makeMeRequest(session);
        } else {
            open = false;
            fbButton.setText("login");
            fbButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) { onClickLogin(); }
            });
            defaultData();
        }
    }
    private void onClickLogin() {
        Session session = Session.getActiveSession();
        List<String> permissions = new ArrayList<String>();
        permissions.add("email");
        permissions.add("user_birthday");
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this).setPermissions(permissions).setCallback(statusCallback));
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

        View rootView = inflater.inflate(R.layout.fragment_social_network, container, false);
        fbCard = (SocialCard) rootView.findViewById(R.id.fb_card);



        name = (TextView) rootView.findViewById(R.id.login);
        fbButton = (Button) rootView.findViewById(R.id.facebook_button);
        Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
//        fbButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                fbButton.setTextColor(getActivity().getResources().getColor(R.color.green));
//            }
//        });
        List<String> permissions = new ArrayList<String>();
        permissions.add("email");
        permissions.add("user_birthday");
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
                session.openForRead(new Session.OpenRequest(this).setPermissions(permissions).setCallback(statusCallback));
            }
        }
        updateSocialCard(fbCard);
        updateView();
        TextView birth = (TextView)rootView.findViewById(R.id.birthday);
        birth.setText("WAT");
        tButton = (Button) rootView.findViewById(R.id.twitter_button);
        gButton = (Button) rootView.findViewById(R.id.google_button);
        vkButton = (Button) rootView.findViewById(R.id.vk_button);
        tButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tButton.setTextColor(getActivity().getResources().getColor(R.color.green));
                printHashKey();
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

    private void updateSocialCard(SocialCard socialCard) {
        Session session = Session.getActiveSession();
        socialCard.button.setTextColor(Color.WHITE);
        socialCard.button.setBackgroundColor(getResources().getColor(R.color.facebook));
        if (session.isOpened()) {
            open = true;

            socialCard.button.setText("{fa-facebook}   Disconnect");
            socialCard.button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onClickLogout();
                }
            });
            setDataFromMe(session, socialCard);
            makeMeRequest(session);
        } else {
            open = false;
            socialCard.button.setText("{fa-facebook} Connect");
            socialCard.button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onClickLogin();
                }
            });
            defaultSocialCardData(socialCard);
            defaultData();
        }
    }

    private void defaultSocialCardData(SocialCard socialCard) {
        socialCard.name.setText("NoName");
        socialCard.birthday.setText("Birthday: unknown");
        socialCard.contact.setText("id: unknown");
        socialCard.image.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
    }

    private void setDataFromMe(final Session session, final SocialCard socialCard) {
        Request request = Request.newMeRequest(session,
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                        if (session == Session.getActiveSession()) {
                            if (user != null) {
                                socialCard.name.setText(user.getName());
                                socialCard.birthday.setText("Birthday: "+ user.getBirthday());
                                socialCard.contact.setText("id: " + user.getId());
                                Picasso.with(getActivity())
                                        .load("https://graph.facebook.com/" + user.getId() + "/picture?type=large")
                                        .placeholder(R.drawable.com_facebook_profile_picture_blank_square)
                                        .error(R.drawable.error)
                                        .into(socialCard.image);
//                                socialCard.image.setImageBitmap(user.getId());
                            }
                        }
                        if (response.getError() != null) {
                            // Handle errors, will do so later.
                        }
                    }
                });
        request.executeAsync();
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
    public void printHashKey() {

        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo("com.example.asamles.app",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("TEMPTAGHASH KEY:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }
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