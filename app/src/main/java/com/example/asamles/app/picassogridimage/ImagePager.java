package com.example.asamles.app.picassogridimage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asamles.app.R;

import java.util.ArrayList;


public class ImagePager extends Fragment {

    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String POS = "position";
    public static final String URLS = "urls";
    ViewPager pager;

    public static ImagePager newInstance(int pagerPosition, ArrayList<String> imageUrls) {
        ImagePager fragment = new ImagePager();
        Bundle args = new Bundle();
        args.putStringArrayList(URLS, imageUrls);
        args.putInt(POS, pagerPosition);
        fragment.setArguments(args);
        return fragment;
    }

    public ImagePager() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.image_pager, container, false);
        int pagerPosition;
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        } else {
            pagerPosition = getArguments().getInt(POS);
        }
        ArrayList<String> imageUrls = getArguments().getStringArrayList(URLS);

        if (rootView != null) {
            pager = (ViewPager) rootView.findViewById(R.id.pager);
        }
        String[] ims = new String[0];
        if (imageUrls != null) {
            ims = imageUrls.toArray(new String[imageUrls.size()]);
        }
        pager.setAdapter(new ImagePagerAdapter(ims, inflater, getActivity()));
        pager.setCurrentItem(pagerPosition);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, pager.getCurrentItem());
    }


}