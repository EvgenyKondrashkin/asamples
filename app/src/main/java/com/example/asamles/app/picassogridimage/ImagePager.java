package com.example.asamles.app.picassogridimage;


import android.graphics.Bitmap;
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

        pager = (ViewPager) rootView.findViewById(R.id.pager);
        String[] ims = imageUrls.toArray(new String[imageUrls.size()]);
        pager.setAdapter(new ImagePagerAdapter(ims, inflater, getActivity()));
        pager.setCurrentItem(pagerPosition);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, pager.getCurrentItem());
    }
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.image_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_blur:
                Toast.makeText(getActivity(), "Blur", Toast.LENGTH_LONG).show();
                
				return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}