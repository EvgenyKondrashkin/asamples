package com.androidsocialnetworks.lib.listener;

import com.androidsocialnetworks.lib.LinkedInPerson;
import com.androidsocialnetworks.lib.listener.base.SocialNetworkListener;

public interface OnRequestLinkedInPersonCompleteListener extends SocialNetworkListener {
    public void onRequestLinkedInPersonSuccess(int socialNetworkID, LinkedInPerson linkedinPerson);
}
