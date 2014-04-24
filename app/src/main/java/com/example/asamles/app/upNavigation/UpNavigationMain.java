package com.example.asamles.app.upNavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.asamles.app.R;
import com.example.asamles.app.constants.Constants;

public class UpNavigationMain extends Fragment {

    public static UpNavigationMain newInstance() {
        return new UpNavigationMain();
    }

    public UpNavigationMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_up_navigation_main, container, false);
        Button btn = (Button) rootView.findViewById(R.id.button);
        Button btn2 = (Button) rootView.findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClassName(getActivity(), "com.example.asamles.app.upNavigation.UpMain");
                intent.putExtra(Constants.NAME, "Activity Way");
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, UpFragmentMain.newInstance("Fragment Way"));
                ft.addToBackStack("UpNav");
                ft.commit();
            }
        });
        return rootView;
    }
}