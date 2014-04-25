package com.example.asamles.app.socialnetwork;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asamles.app.R;

public class SocialNetworkMain extends Fragment {

    private Button fbButton, tButton, gButton, vkButton;

    public static SocialNetworkMain newInstance() {
        return new SocialNetworkMain();
    }

    public SocialNetworkMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_social_network, container, false);

        fbButton = (Button) rootView.findViewById(R.id.facebook_button);
        tButton = (Button) rootView.findViewById(R.id.twitter_button);
        gButton = (Button) rootView.findViewById(R.id.google_button);
        vkButton = (Button) rootView.findViewById(R.id.vk_button);

        fbButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                fbButton.setTextColor(getActivity().getResources().getColor(R.color.green));
            }
        });
        tButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tButton.setTextColor(getActivity().getResources().getColor(R.color.green));
            }
        });
        gButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gButton.setTextColor(getActivity().getResources().getColor(R.color.green));
            }
        });

        vkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vkButton.setTextColor(getActivity().getResources().getColor(R.color.green));
            }
		});
        return rootView;
    }
}