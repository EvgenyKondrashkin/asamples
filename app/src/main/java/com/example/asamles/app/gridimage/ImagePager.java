package com.example.asamles.app.gridimage;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asamles.app.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;


public class ImagePager extends Fragment {

    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String POS = "position";
    public static final String URLS = "urls";
    DisplayImageOptions options;
    ImageLoader imageLoader;
    private ArrayList<String> imageUrls = new ArrayList<String>();
    private int pagerPosition;
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
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        } else {
            pagerPosition = getArguments().getInt(POS);
        }
        imageUrls = getArguments().getStringArrayList(URLS);
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.empty)
                .showImageOnFail(R.drawable.error)
                .resetViewBeforeLoading(true)
                .cacheOnDisc(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity().getApplicationContext()));
        pager = (ViewPager) rootView.findViewById(R.id.pager);
        String[] ims = imageUrls.toArray(new String[imageUrls.size()]);
        pager.setAdapter(new ImagePagerAdapter(ims, inflater, imageLoader, options, getActivity()));
        pager.setCurrentItem(pagerPosition);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, pager.getCurrentItem());
    }

}