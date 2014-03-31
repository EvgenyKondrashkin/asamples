package com.example.asamles.app.upNavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.R;
import com.example.asamles.app.constants.Constants;

public class UpNavigationMain extends Fragment {

    private Button btn, btn2;

    public static UpNavigationMain newInstance() {
        UpNavigationMain fragment = new UpNavigationMain();
        return fragment;
    }

    public UpNavigationMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.up_navigation_main, container, false);
        btn = (Button) rootView.findViewById(R.id.button);
        btn2 = (Button) rootView.findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClassName("com.example.asamles.app", "com.example.asamles.app.upNavigation.UpMain");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ((MainActivity) getActivity()).getSupportFragmentManager().popBackStack("UpNav", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}