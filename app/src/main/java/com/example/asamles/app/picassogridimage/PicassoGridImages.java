package com.example.asamles.app.picassogridimage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.asamles.app.R;

import java.util.ArrayList;

public class PicassoGridImages extends Fragment {

    public static final String ASSETS_FILE = "images.json";
    public static final String STACK_NAME = "image";
    public static final String JSON_ARRAY = "img";
    public static final String IMAGES = "images";
    private ArrayList<String> imgs = new ArrayList<String>();
    private String name;
    private Context context;

    public static PicassoGridImages newInstance(ArrayList<String> imgs) {
        PicassoGridImages fragment = new PicassoGridImages();
        Bundle args = new Bundle();
        args.putStringArrayList(IMAGES, imgs);
        fragment.setArguments(args);
        return fragment;
    }

    public PicassoGridImages() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grid_images_fragment, container, false);
        imgs = getArguments().getStringArrayList(IMAGES);
        // ((MainActivity) getActivity()).getSupportActionBar().setTitle(name);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);

        // JsonFromAssets JFA = new JsonFromAssets(ASSETS_FILE, getActivity());
        // imgs = JFA.getFromJson();

        // if(imgs == null) {
        // ADialogs.alert(getActivity(), this.getString(R.string.json_error));
        // return rootView;
        // }

        gridView.setAdapter(new ImageAdapter(getActivity(), imgs));
        Context context = getActivity();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, ImagePager.newInstance(position, imgs)).addToBackStack(STACK_NAME)
                        .commit();
            }
        });
        return rootView;
    }

}