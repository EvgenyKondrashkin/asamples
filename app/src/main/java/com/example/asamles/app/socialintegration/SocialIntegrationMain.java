package com.example.asamles.app.socialintegration;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidsocialnetworks.lib.FacebookPerson;
import com.androidsocialnetworks.lib.SocialNetwork;
import com.androidsocialnetworks.lib.SocialNetworkManager;
import com.androidsocialnetworks.lib.SocialPerson;
import com.androidsocialnetworks.lib.impl.FacebookSocialNetwork;
import com.androidsocialnetworks.lib.listener.OnLoginCompleteListener;
import com.androidsocialnetworks.lib.listener.OnRequestFacebookPersonCompleteListener;
import com.androidsocialnetworks.lib.listener.OnRequestSocialPersonCompleteListener;
import com.example.asamles.app.R;
import com.example.asamles.app.card.SocialCard;
import com.example.asamles.app.dialog.ADialogs;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCaptchaDialog;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKWallPostResult;
import com.vk.sdk.util.VKUtil;

public class SocialIntegrationMain extends Fragment implements SocialNetworkManager.OnInitializationCompleteListener, OnLoginCompleteListener, OnRequestSocialPersonCompleteListener, OnRequestFacebookPersonCompleteListener {

    private SocialCard fbCard;
	private SocialCard twCard;
	private SocialCard gpCard;
	private SocialCard inCard;
	private SocialCard vkCard;
	private SocialCard okCard;
    public static final String SOCIAL_NETWORK_TAG = "SocialIntegrationMain.SOCIAL_NETWORK_TAG";
    private SocialNetworkManager mSocialNetworkManager;
    private ADialogs progressDialog;
	private boolean[] isConnected = new boolean [6];

    //    private SocialNetworkID socialNetworkID;
    private enum SocialNetworkID {
        TWITTER,
        LINKEDIN,
        GOOGLEPLUS,
        FACEBOOK,
		VK,
		OK

    }
	// vk rules
	private static final String[] sMyScope = new String[] {
            VKScope.WALL,
            VKScope.PHOTOS
            // VKScope.NOHTTPS
    };

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
        
        View rootView = inflater.inflate(R.layout.fragment_social_network, container, false);
		
		String[] fingerprints = VKUtil.getCertificateFingerprint(getActivity(), getActivity().getPackageName());
		Log.d("TAG via vk ", "Fingerprint: " + fingerprints[0]);
        
		fbCard = (SocialCard) rootView.findViewById(R.id.fb_card);
		twCard = (SocialCard) rootView.findViewById(R.id.tw_card);
		gpCard = (SocialCard) rootView.findViewById(R.id.gp_card);
        inCard = (SocialCard) rootView.findViewById(R.id.in_card);
		vkCard = (SocialCard) rootView.findViewById(R.id.vk_card);
		okCard = (SocialCard) rootView.findViewById(R.id.ok_card);
		
        //===============================================================
        mSocialNetworkManager = (SocialNetworkManager) getFragmentManager().findFragmentByTag(SOCIAL_NETWORK_TAG);

        if (mSocialNetworkManager == null) {
            mSocialNetworkManager = SocialNetworkManager.Builder.from(getActivity())
                    .twitter("BBQAUAVKYzmYtvEcNhUEvGiKd", "byZzHPxE1tkGmnPEj5zUyc7MG464Q1LgNRcwbBJV1Ap86575os")
                    .facebook()
                    .googlePlus()
                    .linkedIn("75l0oioxdhf2h5", "HqXZg2A14PWBwWEB", "r_basicprofile+rw_nus+w_messages")
                    .build();
            getFragmentManager().beginTransaction().add(mSocialNetworkManager, SOCIAL_NETWORK_TAG).commit();
        }
        mSocialNetworkManager.setOnInitializationCompleteListener(this);
		
        //===============================================================
		VKUIHelper.onCreate(getActivity());
		VKSdk.initialize(vkSdkListener, "4357233");
        updateVKCard(vkCard);
		//===============================================================
        return rootView;
    }
    private void defaultSocialCardData(SocialCard socialCard, SocialNetworkID enumId) {
        socialCard.setName("NoName");
        socialCard.setBirthday("Birthday: unknown");
        socialCard.setContact("id: unknown");
        switch (enumId){
            case TWITTER:
                socialCard.setImageResource(R.drawable.twitter_user);
                break;
            case LINKEDIN:
                socialCard.setImageResource(R.drawable.linkedin_user);
                break;
            case GOOGLEPLUS:
                socialCard.setImageResource(R.drawable.g_plus_user);
                break;
            case FACEBOOK:
                socialCard.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
                break;
			case VK:
                socialCard.setImageResource(R.drawable.vk_user);
                break;
			case OK:
                socialCard.setImageResource(R.drawable.ok_user);
                break;
        }
    }
// ================Facebook=========================================================================
    private void updateFacebookCard(final SocialCard socialCard) {
		socialCard.setShareButtonText("{icon-share}  Share");
        final FacebookSocialNetwork fb = mSocialNetworkManager.getFacebookSocialNetwork();
        if(fb.isConnected()) {
            socialCard.setConnectButtonText("{icon-facebook}   Disconnect");
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
            fb.requestCurrentFacebookPerson(this);
        } else {
            socialCard.setConnectButtonText("{icon-facebook}   Connect");
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
    public void setFacebookCardFromUser1(FacebookPerson socialPerson, SocialCard socialCard){
        socialCard.setName(socialPerson.name);
        socialCard.setBirthday("Birthday: "+ socialPerson.birthday);
        socialCard.setContact("ID: " + socialPerson.id);
        socialCard.setImage("https://graph.facebook.com/" + socialPerson.id + "/picture?type=large", R.drawable.com_facebook_profile_picture_blank_square, R.drawable.error);
    }
    public void onRequestFacebookPersonSuccess(int socialNetworkID, FacebookPerson facebookPerson){
        setFacebookCardFromUser1(facebookPerson, fbCard);
    }
// ================Twitter==========================================================================
    private void updateTwitterCard(final SocialCard socialCard) {
        socialCard.setShareButtonText("{icon-share}  Share tweet");
        final SocialNetwork tw = mSocialNetworkManager.getTwitterSocialNetwork();
        if(tw.isConnected()){
            socialCard.setConnectButtonText("{icon-twitter}   Logout");
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
            tw.requestCurrentPerson();
        } else {
            socialCard.setConnectButtonText("{icon-twitter} login");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
				progressDialog.progress(false, "login tw...");
				progressDialog.showProgress();
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
// ================GooglePlus==========================================================================
    private void updateGooglePlusCard(final SocialCard socialCard) {
        socialCard.setShareButtonText("{icon-share}  Share");
        final SocialNetwork gp = mSocialNetworkManager.getGooglePlusSocialNetwork();
        if(gp.isConnected()){
            socialCard.setConnectButtonText("{icon-gplus}   Logout");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    gp.logout();
                    updateGooglePlusCard(socialCard);
                }
            });
            socialCard.share.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    gp.requestPostMessage("Hello from ASample");
                }
            });
            gp.requestCurrentPerson();
        } else {
            socialCard.setConnectButtonText("{icon-gplus} login");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    gp.requestLogin();
                }
            });
            socialCard.share.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "You should login first!",Toast.LENGTH_LONG).show();
                }
            });
            defaultSocialCardData(socialCard, SocialNetworkID.GOOGLEPLUS);
        }
    }
    public void setGooglePlusCardFromUser(SocialPerson socialPerson, SocialCard socialCard){
        socialCard.setName(socialPerson.name);
        socialCard.setBirthday("Birthday: I can't!");
        socialCard.setContact("ID: " + socialPerson.id);
        socialCard.setImage(socialPerson.avatarURL, R.drawable.g_plus_user, R.drawable.error);
    }
    // ================LinkedIn==========================================================================
    private void updateLinkedInCard(final SocialCard socialCard) {
        socialCard.setShareButtonText("{icon-share}  Share");
        final SocialNetwork in = mSocialNetworkManager.getLinkedInSocialNetwork();
        if(in.isConnected()){
            socialCard.setConnectButtonText("{icon-linkedin}   Logout");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    in.logout();
                    updateLinkedInCard(socialCard);
                }
            });
            socialCard.share.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    in.requestPostMessage("Hello from ASample");
                }
            });
            in.requestCurrentPerson();
        } else {
            socialCard.setConnectButtonText("{icon-linkedin} login");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
					progressDialog.progress(false, "login In...");
					progressDialog.showProgress();
                    in.requestLogin();
                }
            });
            socialCard.share.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "You should login first!",Toast.LENGTH_LONG).show();
                }
            });
            defaultSocialCardData(socialCard, SocialNetworkID.LINKEDIN);
        }
    }
    public void setLinkedInCardFromUser(SocialPerson socialPerson, SocialCard socialCard){
        socialCard.setName(socialPerson.name);
        socialCard.setBirthday("Birthday: I can't!");
        socialCard.setContact("ID: " + socialPerson.id);
        socialCard.setImage(socialPerson.avatarURL, R.drawable.linkedin_user, R.drawable.error);
    }

//==================================================================================================
	@Override
    public void onSocialNetworkManagerInitialized() {
		boolean isAnyConnected = false;
		for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.setOnLoginCompleteListener(this);
            socialNetwork.setOnRequestCurrentPersonCompleteListener(this);
			if(socialNetwork.isConnected()){
				isAnyConnected = true;
			}
        }
		progressDialog.progress(false, "Fetching data...");

		// if(isAnyConnected){	
			progressDialog.showProgress();
		// }
		
        updateFacebookCard(fbCard);
        updateTwitterCard(twCard);
        updateGooglePlusCard(gpCard);
        updateLinkedInCard(inCard);
		
		progressDialog.cancelProgress();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mSocialNetworkManager.setOnInitializationCompleteListener(null);
        for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.setOnLoginCompleteListener(null);
            socialNetwork.setOnRequestCurrentPersonCompleteListener(this);
        }
    }
    @Override
    public void onLoginSuccess(int id) {
        switch (id){
            case 1:
                updateTwitterCard(twCard);
				progressDialog.cancelProgress();
                break;
            case 2:
                updateLinkedInCard(inCard);
				progressDialog.cancelProgress();
                break;
            case 3:
                updateGooglePlusCard(gpCard);
                break;
            case 4:
                updateFacebookCard(fbCard);
                break;
        }
    }

//    @Override
//    public void onLoginFailed(int id, String errorReason) {
//        Log.d("TAG Login failed: ", "onLoginFailed: " + id + " : " + errorReason);
//        handleError(id, errorReason);
//    }
    @Override
    public void onError(int id, String errorReason, String s2, Object o) {
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
            case 2:
                setLinkedInCardFromUser(socialPerson, inCard);
                break;
            case 3:
                setGooglePlusCardFromUser(socialPerson, gpCard);
                break;
//            case 4:
//                setFacebookCardFromUser(socialPerson, fbCard);
//                break;
        }
    }

//    @Override
//    public void onRequestSocialPersonFailed(int i, String s) {
//        Toast.makeText(getActivity(), "Request user ERROR: " + s, Toast.LENGTH_LONG).show();
//    }
//==================================================================================================
//==+VK+============================================================================================	
	@Override
    public void onResume() {
		super.onResume(); 
		VKUIHelper.onResume(getActivity());
	} 

	@Override
    public void onDestroy() {
		super.onDestroy(); 
		VKUIHelper.onDestroy(getActivity());
	} 

	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data); 
		VKUIHelper.onActivityResult(requestCode, resultCode, data); 
	} 
	
	private final VKSdkListener vkSdkListener = new VKSdkListener() {
        @Override
        public void onCaptchaError(VKError captchaError) {
            new VKCaptchaDialog(captchaError).show();
        }

        @Override
        public void onTokenExpired(VKAccessToken expiredToken) {
            VKSdk.authorize(sMyScope);
        }

        @Override
        public void onAccessDenied(VKError authorizationError) {
            new AlertDialog.Builder(getActivity())
                    .setMessage(authorizationError.errorMessage)
                    .show();
        }

        @Override
        public void onReceiveNewToken(VKAccessToken newToken) {
            updateVKCard(vkCard);
           ///
        }

        @Override
        public void onAcceptUserToken(VKAccessToken token) {
          ///
            updateVKCard(vkCard);
        }
    };
	private void updateVKCard(final SocialCard socialCard) {
		socialCard.setShareButtonText("{icon-share}  Share");
		
        if(VKSdk.isLoggedIn()) {
            socialCard.setConnectButtonText("{icon-vk}   Disconnect");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    VKSdk.logout();
                    updateFacebookCard(socialCard);
                }
            });
            socialCard.share.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    // fb.requestPostMessage("Hello from ASample!");
					makePost(null, "hello from asample");
                }
            });
//                    VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,
//                            "id,first_name,last_name,sex,bdate,city,country,photo_50,photo_100," +
//                            "photo_200_orig,photo_200,photo_400_orig,photo_max,photo_max_orig,online," +
//                            "online_mobile,lists,domain,has_mobile,contacts,connections,site,education," +
//                            "universities,schools,can_post,can_see_all_posts,can_see_audio,can_write_private_message," +
//                            "status,last_seen,common_count,relation,relatives,counters"));
			VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.USER_IDS, "id,first_name,last_name,bdate,photo_200"));
            request.executeWithListener(new VKRequest.VKRequestListener() {
			@Override 
			public void onComplete(VKResponse response) {
			//Do complete stuff 
			 Toast.makeText(getActivity(), response.json.toString(),Toast.LENGTH_LONG).show();
             Log.d("TAG via vk ", "Response: " + response.json.toString());
				
			} 
			@Override 
			public void onError(VKError error) { 
			//Do error stuff 
			} 
			@Override 
			public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) { 
			//I don't really believe in progress 
			} 
			}); 
			
        } else {
            socialCard.setConnectButtonText("{icon-vk}   Connect");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
					VKSdk.authorize(sMyScope);
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

	// public void setVKCardFromUser(){
        // socialCard.setName(socialPerson.name);
        // socialCard.setBirthday("Birthday: I can't!");
        // socialCard.setContact("ID: " + socialPerson.id);
        // socialCard.setImage("https://graph.facebook.com/" + socialPerson.id + "/picture?type=large", R.drawable.com_facebook_profile_picture_blank_square, R.drawable.error);
    // }
	
	private void makePost(VKAttachments attachments, String message) {
        VKRequest post = VKApi.wall().post(VKParameters.from(VKApiConst.OWNER_ID, "-60479154", VKApiConst.ATTACHMENTS, attachments, VKApiConst.MESSAGE, message));
        post.setModelClass(VKWallPostResult.class);
        post.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("https://vk.com/wall-60479154_%s", ((VKWallPostResult) response.parsedModel).post_id)) );
                startActivity(i);
            }
            @Override
            public void onError(VKError error) {
//                showError(error.apiError != null ? error.apiError : error);
            }
        });
    }
}