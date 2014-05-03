package com.example.asamles.app.socialnetwork;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asamles.app.constants.Constants;
import com.example.asamles.app.dialog.ADialogs;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SocialIntegrationFragment extends Fragment{
//    private static final String PERMISSIONS = "Permission";
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
    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
    private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
    private boolean pendingPublishReauthorization = false;
    public void PublishToFeedInBackground()
    {
        Session facebookSession = Session.getActiveSession();
        Request.Callback checkincallback = new Request.Callback() {
            public void onCompleted(Response response) {
                FacebookRequestError error = response.getError();
                if (error != null) {
                    //error
                } else {
                    //succeeded
                }
            }
        };
        List<String> permissions = facebookSession.getPermissions();
        if (!isSubsetOf(PERMISSIONS, permissions)) {
            pendingPublishReauthorization = true;
            Session.NewPermissionsRequest newPermissionsRequest = new Session
                    .NewPermissionsRequest(this, PERMISSIONS);
            facebookSession.requestNewPublishPermissions(newPermissionsRequest);
            return;
        }
        Bundle postParams = new Bundle();
        postParams.putString("name", "Facebook SDK for Android");
        postParams.putString("caption", "Build great social apps and get more installs.");
        postParams.putString("description", "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
        postParams.putString("link", "https://developers.facebook.com/android");
        postParams.putString("picture", "https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");

//        Request request = new Request(facebookSession, "facebookMe/feed", postParams,
//                HttpMethod.POST, checkincallback);
//        RequestAsyncTask task = new RequestAsyncTask(request);
//        task.execute();
        Request.Callback callback= new Request.Callback() {
            public void onCompleted(Response response) {
                JSONObject graphResponse = response
                        .getGraphObject()
                        .getInnerJSONObject();
                String postId = null;
                try {
                    postId = graphResponse.getString("id");
                } catch (JSONException e) {
                    Log.i(Constants.LOG_TAG,
                            "JSON error " + e.getMessage());
                }
                FacebookRequestError error = response.getError();
                if (error != null) {
                    Toast.makeText(getActivity()
                                    .getApplicationContext(),
                            error.getErrorMessage(),
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(getActivity()
                                    .getApplicationContext(),
                            postId,
                            Toast.LENGTH_LONG).show();
                }
            }
        };

        Request request = new Request(facebookSession, "me/feed", postParams,
                HttpMethod.POST, callback);

        RequestAsyncTask task = new RequestAsyncTask(request);
        task.execute();
    }
    private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
        for (String string : subset) {
            if (!superset.contains(string)) {
                return false;
            }
        }
        return true;
    }
}
