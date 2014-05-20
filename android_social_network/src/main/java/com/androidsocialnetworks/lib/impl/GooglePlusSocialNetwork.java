package com.androidsocialnetworks.lib.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.androidsocialnetworks.lib.GooglePlusPerson;
import com.androidsocialnetworks.lib.MomentUtil;
import com.androidsocialnetworks.lib.SocialNetwork;
import com.androidsocialnetworks.lib.SocialNetworkException;
import com.androidsocialnetworks.lib.SocialPerson;
import com.androidsocialnetworks.lib.listener.OnCheckIsFriendCompleteListener;
import com.androidsocialnetworks.lib.listener.OnLoginCompleteListener;
import com.androidsocialnetworks.lib.listener.OnPostingCompleteListener;
import com.androidsocialnetworks.lib.listener.OnRequestAddFriendCompleteListener;
import com.androidsocialnetworks.lib.listener.OnRequestRemoveFriendCompleteListener;
import com.androidsocialnetworks.lib.listener.OnRequestSocialPersonCompleteListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class GooglePlusSocialNetwork extends SocialNetwork
        implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {

    public static final int ID = 3;

    private static final String TAG = GooglePlusSocialNetwork.class.getSimpleName();
    // max 16 bit to use in startActivityForResult
    private static final int REQUEST_AUTH = UUID.randomUUID().hashCode() & 0xFFFF;

    private PlusClient mPlusClient;
    private ConnectionResult mConnectionResult;

    private boolean mConnectRequested;

    private Handler mHandler = new Handler();

    public GooglePlusSocialNetwork(Fragment fragment) {
        super(fragment);
    }

    @Override
    public boolean isConnected() {
        return mPlusClient.isConnected();
    }

    @Override
    public void requestLogin(OnLoginCompleteListener onLoginCompleteListener) {
        super.requestLogin(onLoginCompleteListener);

        mConnectRequested = true;

        try {
            mConnectionResult.startResolutionForResult(mSocialNetworkManager.getActivity(), REQUEST_AUTH);
        } catch (Exception e) {
            Log.e(TAG, "ERROR", e);
            if (!mPlusClient.isConnecting()) {
                mPlusClient.connect();
            }
        }
    }

    @Override
    public void logout() {
        mConnectRequested = false;

        if (mPlusClient.isConnected()) {
            mPlusClient.clearDefaultAccount();
            mPlusClient.disconnect();
            mPlusClient.connect();
        }
    }

    @Override
    public int getID() {
        return ID;
    }
	
//	@Override
    public void requestGooglePlusPerson(OnRequestSocialPersonCompleteListener1 onRequestSocialPersonCompleteListener1) {
        // super.requestGooglePlusPerson(onRequestSocialPersonCompleteListener1);

        Person person = mPlusClient.getCurrentPerson();

        if (person == null) {
            if (mLocalListeners.get(REQUEST_GET_DETAIL_PERSON) != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mLocalListeners.get(REQUEST_GET_DETAIL_PERSON)
                                .onError(getID(), REQUEST_GET_DETAIL_PERSON, "Can't get person", null);
                    }
                });
            }

            return;
        }

        final GooglePlusPerson googlePlusPerson = new GooglePlusPerson();
        googlePlusPerson.id = person.getId();
        googlePlusPerson.name = person.getDisplayName();
        Person.Image image = person.getImage();
        if (image != null) {
            String imageURL = image.getUrl();

            if (imageURL != null) {
                googlePlusPerson.avatarURL = imageURL;
            }
        }
		googlePlusPerson.aboutMe = person.getAboutMe();
		googlePlusPerson.birthday = person.getBirthday();
		googlePlusPerson.braggingRights = person.getBraggingRights();
		Person.Cover cover = person.getCover();
        if (cover != null) {
            Person.Cover.CoverPhoto coverPhoto = cover.getCoverPhoto();

            if (coverPhoto != null) {
                String coverPhotoURL = coverPhoto.getUrl();
				if(coverPhotoURL != null){
					googlePlusPerson.coverURL = coverPhotoURL;
				}
            }
        }
		googlePlusPerson.currentLocation = person.getCurrentLocation();
		googlePlusPerson.gender = person.getGender();
		googlePlusPerson.lang = person.getLanguage();
		googlePlusPerson.nickname = person.getNickname();
		googlePlusPerson.objectType = person.getObjectType();
		List<Person.Organizations> organizations = person.getOrganizations();
		if(organizations.size() > 0) {
			String organizationsName = organizations.get(organizations.size()-1).getName();
			if (organizationsName != null) {
				googlePlusPerson.company = organizationsName;
			}
			String organizationsTitle = organizations.get(organizations.size()-1).getTitle();
			if (organizationsTitle != null) {
				googlePlusPerson.position = organizationsTitle;
			}
		}
		List<Person.PlacesLived> placesLived = person.getPlacesLived();
		if(placesLived.size() > 0) {
			String placeLivedValue = placesLived.get(placesLived.size()-1).getValue();
			if (placeLivedValue != null) {
				googlePlusPerson.placeLivedValue = placeLivedValue;
			}
		}
		googlePlusPerson.relationshipStatus = person.getRelationshipStatus();
		googlePlusPerson.tagline = person.getTagline();
		googlePlusPerson.url = person.getUrl();
		
		
        if (mLocalListeners.get(REQUEST_GET_DETAIL_PERSON) != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                   ((OnRequestSocialPersonCompleteListener1)
                           mLocalListeners.get(REQUEST_GET_DETAIL_PERSON))//REQUEST_GET_GOOGLEPLUS_PERSON))
                           .onRequestSocialPersonSuccess(getID(), googlePlusPerson);
                }
            });
        }
    }
	
    @Override
    public void requestCurrentPerson(OnRequestSocialPersonCompleteListener onRequestSocialPersonCompleteListener) {
        super.requestCurrentPerson(onRequestSocialPersonCompleteListener);

        Person person = mPlusClient.getCurrentPerson();

        if (person == null) {
            if (mLocalListeners.get(REQUEST_GET_CURRENT_PERSON) != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mLocalListeners.get(REQUEST_GET_CURRENT_PERSON)
                                .onError(getID(), REQUEST_GET_CURRENT_PERSON, "Can't get person", null);
                    }
                });
            }

            return;
        }

        final SocialPerson socialPerson = new SocialPerson();
        socialPerson.id = person.getId();
        socialPerson.name = person.getDisplayName();

        Person.Image image = person.getImage();
        if (image != null) {
            String imageURL = image.getUrl();

            if (imageURL != null) {
                socialPerson.avatarURL = imageURL;
            }
        }

        if (mLocalListeners.get(REQUEST_GET_CURRENT_PERSON) != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    ((OnRequestSocialPersonCompleteListener)
                            mLocalListeners.get(REQUEST_GET_CURRENT_PERSON))
                            .onRequestSocialPersonSuccess(getID(), socialPerson);
                }
            });
        }
    }

    @Override
    public void requestSocialPerson(String userID, OnRequestSocialPersonCompleteListener onRequestSocialPersonCompleteListener) {
        throw new SocialNetworkException("requestSocialPerson isn't allowed for GooglePlusSocialNetwork");
    }

    @Override
    public void requestPostMessage(String message, OnPostingCompleteListener onPostingCompleteListener) {
        throw new SocialNetworkException("requestPostMessage isn't allowed for GooglePlusSocialNetwork");
    }

    @Override
    public void requestPostPhoto(File photo, String message, OnPostingCompleteListener onPostingCompleteListener) {
        throw new SocialNetworkException("requestPostPhoto isn't allowed for GooglePlusSocialNetwork");
    }

    @Override
    public void requestCheckIsFriend(String userID, OnCheckIsFriendCompleteListener onCheckIsFriendCompleteListener) {
        throw new SocialNetworkException("requestCheckIsFriend isn't allowed for GooglePlusSocialNetwork");
    }

    @Override
    public void requestAddFriend(String userID, OnRequestAddFriendCompleteListener onRequestAddFriendCompleteListener) {
        throw new SocialNetworkException("requestAddFriend isn't allowed for GooglePlusSocialNetwork");
    }

    @Override
    public void requestRemoveFriend(String userID, OnRequestRemoveFriendCompleteListener onRequestRemoveFriendCompleteListener) {
        throw new SocialNetworkException("requestRemoveFriend isn't allowed for GooglePlusSocialNetwork");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlusClient = new PlusClient.Builder(mSocialNetworkManager.getActivity(), this, this)
                .setActions(MomentUtil.ACTIONS).build();
    }

    @Override
    public void onStart() {
        mPlusClient.connect();
    }

    @Override
    public void onStop() {
        if (mPlusClient.isConnected()) {
            mPlusClient.disconnect();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_AUTH) {
            if (resultCode == Activity.RESULT_OK && !mPlusClient.isConnected() && !mPlusClient.isConnecting()) {
                // This time, connect should succeed.
                mPlusClient.connect();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (mLocalListeners.get(REQUEST_LOGIN) != null) {
                    mLocalListeners.get(REQUEST_LOGIN).onError(getID(), REQUEST_LOGIN,
                            "canceled", null);
                }
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (mConnectRequested) {
            if (mPlusClient.getCurrentPerson() != null) {
                if (mLocalListeners.get(REQUEST_LOGIN) != null) {
                    ((OnLoginCompleteListener) mLocalListeners.get(REQUEST_LOGIN)).onLoginSuccess(getID());
                }

                return;
            }

            if (mLocalListeners.get(REQUEST_LOGIN) != null) {
                mLocalListeners.get(REQUEST_LOGIN).onError(getID(), REQUEST_LOGIN,
                        "get person == null", null);
            }
        }

        mConnectRequested = false;
    }

    @Override
    public void onDisconnected() {
        mConnectRequested = false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mConnectionResult = connectionResult;

        if (mConnectRequested && mLocalListeners.get(REQUEST_LOGIN) != null) {
            mLocalListeners.get(REQUEST_LOGIN).onError(getID(), REQUEST_LOGIN,
                    "error: " + connectionResult.getErrorCode(), null);
        }

        mConnectRequested = false;
    }
}
