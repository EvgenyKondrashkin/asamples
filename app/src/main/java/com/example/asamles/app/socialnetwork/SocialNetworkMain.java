package com.example.asamles.app.socialnetwork;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asamles.app.R;
import com.example.asamles.app.card.SocialCard;
import com.example.asamles.app.dialog.ADialogs;
import com.facebook.Session;
import com.facebook.model.GraphUser;

public class SocialNetworkMain extends SocialIntegrationFragment implements SocialIntegrationFragment.FacebookIntegration {

    private ADialogs progressDialog;
    private SocialCard fbCard;
    private Session facebookSession;
    private GraphUser facebookMe;
	private SocialCard twCard;
	private SocialCard gpCard;
	private SocialCard vkCard;
    public static SocialNetworkMain newInstance() {
        return new SocialNetworkMain();
    }
    
	public SocialNetworkMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        progressDialog = new ADialogs(getActivity());
        progressDialog.progress(false, "Fetching data...");
        View rootView = inflater.inflate(R.layout.fragment_social_network, container, false);

        fbCard = (SocialCard) rootView.findViewById(R.id.fb_card);
        setOnFacebookCall(this);
        facebookSession = setFacebookSession();
        updateFacebookCard(fbCard, facebookSession);

		twCard = (SocialCard) rootView.findViewById(R.id.tw_card);
		gpCard = (SocialCard) rootView.findViewById(R.id.gp_card);
		vkCard = (SocialCard) rootView.findViewById(R.id.vk_card);
		
        return rootView;
    }
    private void defaultSocialCardData(SocialCard socialCard) {
        socialCard.setName("NoName");
        socialCard.setBirthday("Birthday: unknown");
        socialCard.setContact("id: unknown");
    }

    private void updateFacebookCard(final SocialCard socialCard, final Session session) {
		socialCard.setShareButtonText("{fa-share}  Share");
        if (session.isOpened()) {
            socialCard.setConnectButtonText("{fa-facebook}   Disconnect");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onFacebookClickLogout();
                }
            });
			setFacebookCardFromUser(socialCard);
        } else {
            socialCard.setConnectButtonText("{fa-facebook} Connect");
            socialCard.connect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onFacebookClickLogin();
                }
            });
            defaultSocialCardData(socialCard);
        }
    }

	public void setFacebookCardFromUser(SocialCard socialCard){
        facebookMe = setFacebookMe();
        if (facebookMe == null){
            progressDialog.showProgress();
        } else {
            progressDialog.cancelProgress();
            socialCard.setName(facebookMe.getName());
            socialCard.setBirthday("Birthday: "+ facebookMe.getBirthday());
            socialCard.setContact("Token: " + facebookMe.getId());
            socialCard.setImage("https://graph.facebook.com/" + facebookMe.getId() + "/picture?type=large", R.drawable.com_facebook_profile_picture_blank_square, R.drawable.error);
        }
    }

    @Override
    public void onFacebookCall() {
        facebookSession = setFacebookSession();
        updateFacebookCard(fbCard, facebookSession);
    }

    @Override
    public void onFacebookLogout() {
        facebookSession = setFacebookSession();
        updateFacebookCard(fbCard, facebookSession);
    }
//=========================================================================================
    // public void printHashKey() {
        // try {
            // PackageInfo info = getActivity().getPackageManager().getPackageInfo("com.example.asamles.app",
                    // PackageManager.GET_SIGNATURES);
            // for (Signature signature : info.signatures) {
                // MessageDigest md = MessageDigest.getInstance("SHA");
                // md.update(signature.toByteArray());
                // Log.d("TEMPTAGHASH KEY:",
                        // Base64.encodeToString(md.digest(), Base64.DEFAULT));
            // }
        // } catch (PackageManager.NameNotFoundException e) {

        // } catch (NoSuchAlgorithmException e) {

        // }

    // }
//========================================================================================>

}