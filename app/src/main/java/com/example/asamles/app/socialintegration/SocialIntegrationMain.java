package com.example.asamles.app.socialintegration;

import android.support.v4.app.Fragment;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.androidsocialnetworks.lib.SocialNetwork;
import com.androidsocialnetworks.lib.SocialNetworkManager;
import com.androidsocialnetworks.lib.SocialPerson;
import com.example.asamles.app.R;
import com.example.asamles.app.card.SocialCard;
import com.example.asamles.app.dialog.ADialogs;

public class SocialIntegrationMain extends Fragment implements SocialNetworkManager.OnInitializationCompleteListener, SocialNetwork.OnLoginCompleteListener, SocialNetwork.OnRequestSocialPersonListener {

    private SocialCard fbCard;
	private SocialCard twCard;
	private SocialCard gpCard;
	private SocialCard vkCard;
    public static final String SOCIAL_NETWORK_TAG = "SocialIntegrationMain";
    private SocialNetworkManager mSocialNetworkManager;
    private ADialogs progressDialog;
//    private SocialNetworkID socialNetworkID;
    private enum SocialNetworkID {
        TWITTER,
        LINKEDIN,
        GOOGLEPLUS,
        FACEBOOK;

    }

    public static SocialIntegrationMain newInstance() {
        return new SocialIntegrationMain();
    }
    
	public SocialIntegrationMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

		progressDialog = new ADialogs(getActivity());
        progressDialog.progress(false, "Fetching data...");
        View rootView = inflater.inflate(R.layout.fragment_social_network, container, false);

        fbCard = (SocialCard) rootView.findViewById(R.id.fb_card);
//        updateFacebookCard(fbCard);


		twCard = (SocialCard) rootView.findViewById(R.id.tw_card);
//		updateTwitterCard(twCard);


		
		gpCard = (SocialCard) rootView.findViewById(R.id.gp_card);
		vkCard = (SocialCard) rootView.findViewById(R.id.vk_card);

        //===============================================================
        mSocialNetworkManager = (SocialNetworkManager) getFragmentManager().findFragmentByTag(SOCIAL_NETWORK_TAG);

        if (mSocialNetworkManager == null) {
            mSocialNetworkManager = SocialNetworkManager.Builder.from(getActivity())
                    .twitter("BBQAUAVKYzmYtvEcNhUEvGiKd", "byZzHPxE1tkGmnPEj5zUyc7MG464Q1LgNRcwbBJV1Ap86575os")
                    .facebook()
                    .build();
            getFragmentManager().beginTransaction().add((Fragment)mSocialNetworkManager, SOCIAL_NETWORK_TAG).commit();
        }
        mSocialNetworkManager.setOnInitializationCompleteListener(this);
        //===============================================================
        return rootView;
    }
    private void defaultSocialCardData(SocialCard socialCard, SocialNetworkID enumId) {
        socialCard.setName("NoName");
        socialCard.setBirthday("Birthday: unknown");
        socialCard.setContact("id: unknown");
//        SocialNetworkID enumId = SocialNetworkID.values()[id];
        switch (enumId){
            case TWITTER:
                socialCard.setImageResource(R.drawable.twitter_user);
                break;
            case LINKEDIN:
                break;
            case GOOGLEPLUS:
                break;
            case FACEBOOK:
                socialCard.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
                break;
        }
    }
// ================Facebook=========================================================================
    private void updateFacebookCard(final SocialCard socialCard) {
		socialCard.setShareButtonText("{fa-share}  Share");
        final SocialNetwork fb = mSocialNetworkManager.getFacebookSocialNetwork();
        if(fb.isConnected()) {
            socialCard.setConnectButtonText("{fa-facebook}   Disconnect");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fb.logout();
                    updateFacebookCard(socialCard);
                }
            });
            socialCard.share.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    fb.requestPostMessage("Hello from ASample!");
                }
            });
            fb.requestPerson();
        } else {
            socialCard.setConnectButtonText("{fa-facebook}   Connect");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fb.requestLogin();
                }
            });
            socialCard.share.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "You should connect first!",Toast.LENGTH_LONG).show();
                }
            });
            defaultSocialCardData(socialCard, SocialNetworkID.FACEBOOK);
        }
    }

	public void setFacebookCardFromUser(SocialPerson socialPerson, SocialCard socialCard){
        socialCard.setName(socialPerson.name);
        socialCard.setBirthday("Birthday: I can't!");
        socialCard.setContact("ID: " + socialPerson.id);
        socialCard.setImage("https://graph.facebook.com/" + socialPerson.id + "/picture?type=large", R.drawable.com_facebook_profile_picture_blank_square, R.drawable.error);
    }
// ================Twitter==========================================================================
    private void updateTwitterCard(final SocialCard socialCard) {
        socialCard.setShareButtonText("{fa-share}  Share tweet");
        final SocialNetwork tw = mSocialNetworkManager.getTwitterSocialNetwork();
        if(tw.isConnected()){
            socialCard.setConnectButtonText("{fa-twitter}   Logout");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    tw.logout();
                    updateTwitterCard(socialCard);
                }
            });
            socialCard.share.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    tw.requestPostMessage("Hello from ASample");
                }
            });
            tw.requestPerson();
        } else {
            socialCard.setConnectButtonText("{fa-twitter} login");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    tw.requestLogin();
                }
            });
            socialCard.share.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "You should login first!",Toast.LENGTH_LONG).show();
                }
            });
            defaultSocialCardData(socialCard, SocialNetworkID.TWITTER);
        }
    }
    public void setTwitterCardFromUser(SocialPerson socialPerson, SocialCard socialCard){
        socialCard.setName(socialPerson.name);
        socialCard.setBirthday("Birthday: I can't!");
        socialCard.setContact("ID: " + socialPerson.id);
        socialCard.setImage(socialPerson.avatarURL, R.drawable.twitter_user, R.drawable.error);
    }


//==================================================================================================
	@Override
    public void onSocialNetworkManagerInitialized() {
        for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.setOnLoginCompleteListener(this);
            socialNetwork.setOnRequestSocialPersonListener(this);
        }
        updateFacebookCard(fbCard);
        updateTwitterCard(twCard);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mSocialNetworkManager.setOnInitializationCompleteListener(null);
        for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.setOnLoginCompleteListener(null);
            socialNetwork.setOnRequestSocialPersonListener(null);
        }
    }
    @Override
    public void onLoginSuccess(int id) {
        switch (id){
            case 1:
                updateTwitterCard(twCard);
                break;
            case 4:
                updateFacebookCard(fbCard);
                break;
        }
    }

    @Override
    public void onLoginFailed(int id, String errorReason) {
        Log.d("TAG Login failed: ", "onLoginFailed: " + id + " : " + errorReason);
        handleError(id, errorReason);
    }
    private void handleError(int id, String errorReason) {
        Toast.makeText(getActivity(), "ERROR: " + errorReason, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestSocialPersonSuccess(int id, SocialPerson socialPerson) {
        switch (id){
            case 1:
                setTwitterCardFromUser(socialPerson, twCard);
                break;
            case 4:
                setFacebookCardFromUser(socialPerson, fbCard);
                break;
        }
    }

    @Override
    public void onRequestSocialPersonFailed(int i, String s) {
        Toast.makeText(getActivity(), "Request user ERROR: " + s, Toast.LENGTH_LONG).show();
    }
//==================================================================================================

}