package com.example.asamles.app.striptab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asamles.app.R;

public class StripTabMain extends Fragment {


    public static StripTabMain newInstance() {
        StripTabMain fragment = new StripTabMain();
        return fragment;
    }

    public StripTabMain() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_striptab, container, false);
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
        pager.setAdapter(new StripPagerAdapter(getActivity().getSupportFragmentManager(), getActivity()));
        pager.setCurrentItem(0);
        return rootView;
    }

}