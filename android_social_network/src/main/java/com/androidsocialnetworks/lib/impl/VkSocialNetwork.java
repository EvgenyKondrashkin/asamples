//package com.androidsocialnetworks.lib.impl;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//
//import com.androidsocialnetworks.lib.FacebookPerson;
//import com.androidsocialnetworks.lib.SocialNetwork;
//import com.androidsocialnetworks.lib.SocialNetworkException;
//import com.androidsocialnetworks.lib.SocialPerson;
//import com.androidsocialnetworks.lib.listener.OnCheckIsFriendCompleteListener;
//import com.androidsocialnetworks.lib.listener.OnLoginCompleteListener;
//import com.androidsocialnetworks.lib.listener.OnPostingCompleteListener;
//import com.androidsocialnetworks.lib.listener.OnRequestAddFriendCompleteListener;
//import com.androidsocialnetworks.lib.listener.OnRequestFacebookPersonCompleteListener;
//import com.androidsocialnetworks.lib.listener.OnRequestRemoveFriendCompleteListener;
//import com.androidsocialnetworks.lib.listener.OnRequestSocialPersonCompleteListener;
//import com.androidsocialnetworks.lib.listener.OnRequestSocialPersonCompleteListener1;
//
//import java.io.File;
//import java.util.Collections;
//import java.util.List;
//
///**
// * TODO: think about canceling requests
// */
//public class VkSocialNetwork extends SocialNetwork {
//    public static final int ID = 5;
//
//    private static final String TAG = VkSocialNetwork.class.getSimpleName();
//
//	// private static final String PERMISSION = "publish_actions";
//    // private SessionTracker mSessionTracker;
//    // private UiLifecycleHelper mUILifecycleHelper;
//    // private String mApplicationId;
//    // private SessionState mSessionState;
//    // private String mPhotoPath;
//    // private String mStatus;
//    // private PendingAction mPendingAction = PendingAction.NONE;
//    // private Session.StatusCallback mSessionStatusCallback = new Session.StatusCallback() {
//        // @Override
//        // public void call(Session session, SessionState state, Exception exception) {
//            // onSessionStateChange(session, state, exception);
//        // }
//    // };
//
//    public VkSocialNetwork(Fragment fragment) {
//        super(fragment);
//    }
//
//    @Override
//    public void requestLogin(OnLoginCompleteListener onLoginCompleteListener) {
//        super.requestLogin(onLoginCompleteListener);
//
//
//    }
//
//	@Override
//    public void requestLogin(OnLoginCompleteListener onLoginCompleteListener, List<String> permissions) {
//
//    }
//
//    @Override
//    public void logout() {
//
//    }
//
//    @Override
//    public int getID() {
//        return ID;
//    }
//
//    public void requestCurrentVkPerson(OnRequestSocialPersonCompleteListener1 onRequestSocialPersonCompleteListener1) {
//
//
//    }
//    @Override
//    public void requestCurrentPerson(OnRequestSocialPersonCompleteListener onRequestSocialPersonCompleteListener) {
//        super.requestCurrentPerson(onRequestSocialPersonCompleteListener);
//
//
//    }
//
//    @Override
//    public void requestSocialPerson(String userID, OnRequestSocialPersonCompleteListener onRequestSocialPersonCompleteListener) {
//        throw new SocialNetworkException("requestSocialPerson isn't allowed for FacebookSocialNetwork");
//    }
//
//    @Override
//    public void requestPostMessage(String message, OnPostingCompleteListener onPostingCompleteListener) {
//        super.requestPostMessage(message, onPostingCompleteListener);
//        mStatus = message;
//        performPublish(PendingAction.POST_STATUS_UPDATE);
//    }
//
//    @Override
//    public void requestPostPhoto(File photo, String message, OnPostingCompleteListener onPostingCompleteListener) {
//        super.requestPostPhoto(photo, message, onPostingCompleteListener);
//        mPhotoPath = photo.getAbsolutePath();
//        performPublish(PendingAction.POST_PHOTO);
//    }
//
//    private void performPublish(PendingAction action) {
//
//
//        if (action == PendingAction.POST_STATUS_UPDATE) {
//
//        }
//
//        if (action == PendingAction.POST_PHOTO) {
//
//        }
//    }
//
//    @Override
//    public void requestCheckIsFriend(String userID, OnCheckIsFriendCompleteListener onCheckIsFriendCompleteListener) {
//        throw new SocialNetworkException("requestCheckIsFriend isn't allowed for FacebookSocialNetwork");
//    }
//
//    @Override
//    public void requestAddFriend(String userID, OnRequestAddFriendCompleteListener onRequestAddFriendCompleteListener) {
//        throw new SocialNetworkException("requestAddFriend isn't allowed for FacebookSocialNetwork");
//    }
//
//    @Override
//    public void requestRemoveFriend(String userID, OnRequestRemoveFriendCompleteListener onRequestRemoveFriendCompleteListener) {
//        throw new SocialNetworkException("requestRemoveFriend isn't allowed for FacebookSocialNetwork");
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mUILifecycleHelper = new UiLifecycleHelper(mSocialNetworkManager.getActivity(), mSessionStatusCallback);
//        mUILifecycleHelper.onCreate(savedInstanceState);
//
//		VKUIHelper.onCreate(getActivity());
//		VKSdk.initialize(vkSdkListener, "4357233");
//        updateVKCard(vkCard);
//
//
//        initializeActiveSessionWithCachedToken(mSocialNetworkManager.getActivity());
//        finishInit();
//    }
//
//    private boolean initializeActiveSessionWithCachedToken(Context context) {
//        if (context == null) {
//            return false;
//        }
//
//        Session session = Session.getActiveSession();
//        if (session != null) {
//            return session.isOpened();
//        }
//
//        mApplicationId = Utility.getMetadataApplicationId(context);
//        if (mApplicationId == null) {
//            return false;
//        }
//
//        return Session.openActiveSessionFromCache(context) != null;
//    }
//
//    private void finishInit() {
//        mSessionTracker = new SessionTracker(
//                mSocialNetworkManager.getActivity(), mSessionStatusCallback, null, false);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mUILifecycleHelper.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mUILifecycleHelper.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mUILifecycleHelper.onDestroy();
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mUILifecycleHelper.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        mUILifecycleHelper.onActivityResult(requestCode, resultCode, data, null);
//
//        Session session = mSessionTracker.getOpenSession();
//        if (session != null) {
//            session.onActivityResult(mSocialNetworkManager.getActivity(), requestCode, resultCode, data);
//        }
//    }
//
//    private boolean hasPublishPermission() {
//        Session session = Session.getActiveSession();
//        return session != null && session.getPermissions().contains(PERMISSION);
//    }
//
//    @SuppressWarnings("incomplete-switch")
//    private void handlePendingAction() {
//        PendingAction previouslyPendingAction = mPendingAction;
//        // These actions may re-set pendingAction if they are still pending, but we assume they
//        // will succeed.
//        mPendingAction = PendingAction.NONE;
//
//        switch (previouslyPendingAction) {
//            case POST_PHOTO:
//                postPhoto(mPhotoPath);
//                break;
//            case POST_STATUS_UPDATE:
//                postStatusUpdate(mStatus);
//                break;
//        }
//    }
//
//    private void postStatusUpdate(String message) {
//        if (isConnected() && hasPublishPermission()) {
//            Request request = Request
//                    .newStatusUpdateRequest(Session.getActiveSession(), message, null, null, new Request.Callback() {
//                        @Override
//                        public void onCompleted(Response response) {
//                            publishSuccess(REQUEST_POST_MESSAGE,
//                                    response.getError() == null ? null : response.getError().getErrorMessage());
//                        }
//                    });
//            request.executeAsync();
//        } else {
//            mPendingAction = PendingAction.POST_STATUS_UPDATE;
//        }
//    }
//
//    private void postPhoto(final String path) {
//        if (hasPublishPermission()) {
//            Bitmap image = BitmapFactory.decodeFile(path);
//            Request request = Request.newUploadPhotoRequest(Session.getActiveSession(), image, new Request.Callback() {
//                @Override
//                public void onCompleted(Response response) {
//                    publishSuccess(REQUEST_POST_PHOTO,
//                            response.getError() == null ? null : response.getError().getErrorMessage());
//                }
//            });
//            request.executeAsync();
//        } else {
//            mPendingAction = PendingAction.POST_PHOTO;
//        }
//    }
//
//    private void publishSuccess(String requestID, String error) {
//        if (mLocalListeners.get(requestID) == null) return;
//
//        if (error != null) {
//            mLocalListeners.get(requestID).onError(getID(), requestID, error, null);
//            return;
//        }
//
//        ((OnPostingCompleteListener) mLocalListeners.get(requestID)).onPostSuccessfully(getID());
//        mLocalListeners.remove(requestID);
//    }
//
//    private enum PendingAction {
//        NONE,
//        POST_PHOTO,
//        POST_STATUS_UPDATE
//    }
//}
