package com.example.asamles.app.socialnetwork;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphUser;

import java.util.ArrayList;
import java.util.List;

public class SocialIntegrationFragment extends Fragment{
    private SocialIntegration callback;
    Session session;
    GraphUser me;

    public interface SocialIntegration{
        public void onFacebookCall(Session session, SessionState state);
    }
    public void setOnFacebookCall(SocialIntegration callback){
        this.callback = callback;
    }
	public SocialIntegrationFragment() {

    }
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session callSession, SessionState state, Exception exception) {
            session = callSession;
//            updateFacebookCard(fbCard);
            onSessionStateChange(session, state);

        }
    }
	    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
            Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
            // List<String> permissions = new ArrayList<String>();
            // permissions.add("email");
            // permissions.add("user_birthday");
            session = Session.getActiveSession();
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
//		return container;
            return null;
        }
	public void onFacebookClickLogin() {
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

    public void onFacebookClickLogout() {
        Session session = Session.getActiveSession();
        if (!session.isClosed()) {
            session.closeAndClearTokenInformation();
        }
    }
	public GraphUser setDataFromMe(final Session session) {
        final GraphUser[] me = new GraphUser[1];
        Request request = Request.newMeRequest(session,
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                        if (session == Session.getActiveSession()) {
                            if (user != null) {
								me[0] =  user;
                            }
                        }
                        if (response.getError() != null) {
                            // Error
                        }
                    }
                });
        request.executeAsync();
        return me[0];
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
    private void onSessionStateChange(final Session session, SessionState state) {
            Request request = Request.newMeRequest(session,
                    new Request.GraphUserCallback() {
                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                            if (session == Session.getActiveSession()) {
                                if (user != null) {
                                    me =  user;
                                }
                            }
                            if (response.getError() != null) {
                                // Error
                            }
                        }
                    });
            request.executeAsync();
//        } else {

//        }
    }
}
