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

    public static SocialIntegrationMain newInstance() {
        return new SocialIntegrationMain();
    }
    
	public SocialIntegrationMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
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
		progressDialog = new ADialogs(getActivity());
        progressDialog.progress(false, "Fetching data...");
        View rootView = inflater.inflate(R.layout.fragment_social_network, container, false);

        fbCard = (SocialCard) rootView.findViewById(R.id.fb_card);
        updateFacebookCard(fbCard);
		fbCard.share.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

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

    private void updateFacebookCard(final SocialCard socialCard) {
		socialCard.setShareButtonText("{fa-share}  Share");

            socialCard.setConnectButtonText("{fa-facebook}   Disconnect");

//			setFacebookCardFromUser();


            defaultSocialCardData(socialCard);

    }

	public void setFacebookCardFromUser(SocialPerson socialPerson){

    }
//==============================================================================================
	@Override
    public void onSocialNetworkManagerInitialized() {
        for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.setOnLoginCompleteListener(this);
            socialNetwork.setOnRequestSocialPersonListener(this);
        }
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
//=================================================================================================
	public boolean isOnline() {
        return false;
	}
	private void updateTwitterCard(SocialCard socialCard) {
		socialCard.setShareButtonText("{fa-share}  Share tweet");
		if(isOnline()){
			socialCard.setConnectButtonText("{fa-twitter}   Logout");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    Toast.makeText(getActivity(), "Signed Off", Toast.LENGTH_SHORT).show();
                }
            });
//			setTwitterCardFromUser();
		} else {
			socialCard.setConnectButtonText("{fa-twitter} login");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    mSocialNetworkManager.getTwitterSocialNetwork().requestLogin();
                    Toast.makeText(getActivity(), "Signed in", Toast.LENGTH_SHORT).show();
                }
            });
            defaultSocialCardData(socialCard);
		}
	}
	public void setTwitterCardFromUser(SocialPerson socialPerson){

    }


	public void sendTweet() {
		String message = "Test tweet message from ASample";

	}

    @Override
    public void onLoginSuccess(int id) {
        mSocialNetworkManager.getSocialNetwork(id).requestPerson();
    }

    @Override
    public void onLoginFailed(int id, String errorReason) {
        Log.d("TAG Login failed: ", "onLoginFailed: " + id + " : " + errorReason);
        handleError(id, errorReason);
    }
    private void handleError(int id, String errorReason) {
        Toast.makeText(getActivity(), "ERROR: " + errorReason, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestSocialPersonSuccess(int i, SocialPerson socialPerson) {

    }

    @Override
    public void onRequestSocialPersonFailed(int i, String s) {

    }
    private static enum UserState {
        LOGIN,
        LOGOUT
    }
    UserState userState;
    private void switchUIState(UserState state, int id) {
        userState = state;

    }
}