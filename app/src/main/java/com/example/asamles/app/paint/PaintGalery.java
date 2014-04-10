package com.example.asamles.app.paint;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.asamles.app.R;
import com.example.asamles.app.picassogridimage.ImageAdapter;
import com.example.asamles.app.picassogridimage.ImagePager;

import java.util.ArrayList;

public class PaintGalery extends Fragment {

    public static final String STACK_NAME = "image";
    public static final String IMAGES = "images";
    private ArrayList<String> imgs = new ArrayList<String>();

    public static PaintGalery newInstance() {
        PaintGalery fragment = new PaintGalery();
        return fragment;
    }

    public PaintGalery() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grid_images_fragment, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(getActivity(), imgs));
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