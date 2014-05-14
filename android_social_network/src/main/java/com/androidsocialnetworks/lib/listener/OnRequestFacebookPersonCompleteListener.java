package com.androidsocialnetworks.lib.listener;

import com.androidsocialnetworks.lib.FacebookPerson;
import com.androidsocialnetworks.lib.listener.base.SocialNetworkListener;

public interface OnRequestFacebookPersonCompleteListener extends SocialNetworkListener {
    public void onRequestFacebookPersonSuccess(int socialNetworkID, FacebookPerson facebookPerson);
}
