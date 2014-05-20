package com.androidsocialnetworks.lib.listener;

import com.androidsocialnetworks.lib.SocialPerson;
import com.androidsocialnetworks.lib.listener.base.SocialNetworkListener;

public interface OnRequestSocialPersonCompleteListener1<T extends SocialPerson> extends SocialNetworkListener {
    public void onRequestSocialPersonSuccess1(int socialNetworkID, T socialPerson);
}