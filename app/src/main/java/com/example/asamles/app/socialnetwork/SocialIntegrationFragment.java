package com.example.asamles.app.socialnetwork;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asamles.app.dialog.ADialogs;
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
    private FacebookIntegration facebookCallback;
    private ADialogs alertDialog;
    private Session facebookSession;
    private GraphUser facebookMe;

    public interface FacebookIntegration {
        public void onFacebookCall();
        public void onFacebookLogout();
    }
    public interface TwitterIntegration {
        public void onTwitterCall();
        public void onTwitterLogout();
    }
    public void setOnFacebookCall(FacebookIntegration facebookCallback){
        this.facebookCallback = facebookCallback;
    }
	public SocialIntegrationFragment() {
    }

	private Session.StatusCallback statusCallback = new SessionStatusCallback();
    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session callSession, SessionState state, Exception exception) {
            facebookSession = callSession;
            onFacebookSessionStateChange(facebookSession);
        }
    }
	    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
            Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
            alertDialog = new ADialogs(getActivity());
            facebookSession = Session.getActiveSession();
            if (facebookSession == null) {
                if (savedInstanceState != null) {
                    facebookSession = Session.restoreSession(getActivity(), null, statusCallback, savedInstanceState);
                }
                if (facebookSession == null) {
                    facebookSession = new Session(getActivity());
                }
                Session.setActiveSession(facebookSession);
                if (facebookSession.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
                    facebookSession.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
                }
            }
            onFacebookSessionStateChange(facebookSession);
            return null;
    }
    public Session setFacebookSession(){
        return facebookSession;
    }
    public GraphUser setFacebookMe(){
        return facebookMe;
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
        facebookCallback.onFacebookLogout();
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
    private void onFacebookSessionStateChange(final Session session) {
        if(session.isOpened()){
            Request request = Request.newMeRequest(session,
                    new Request.GraphUserCallback() {
                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                            if (session == Session.getActiveSession()) {
                                if (user != null) {
                                    facebookMe =  user;
                                    facebookCallback.onFacebookCall();
                                }
                            }
                            if (response.getError() != null) {
                                alertDialog.alert(true, "Error", "There are problem to connect to your facebook accaunt", "Cancel", null);
                            }
                        }
                    });
            request.executeAsync();
        }
//        } else {

//        }
    }
}
