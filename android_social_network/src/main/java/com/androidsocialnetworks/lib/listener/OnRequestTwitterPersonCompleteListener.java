package com.androidsocialnetworks.lib.listener;

import com.androidsocialnetworks.lib.TwitterPerson;
import com.androidsocialnetworks.lib.listener.base.SocialNetworkListener;

public interface OnRequestTwitterPersonCompleteListener extends SocialNetworkListener {
    public void onRequestTwitterPersonSuccess(int socialNetworkID, TwitterPerson twitterPerson);
}
