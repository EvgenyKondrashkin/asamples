package com.example.asamles.app.paint;

import android.content.Context;
import android.content.ContextWrapper;
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

import java.io.File;
import java.util.ArrayList;

public class PaintGalery extends Fragment {

    public static final String STACK_NAME = "image";
    public static final String IMAGES = "images";
    private String[] imgs;

    public static PaintGalery newInstance(String[] imgs) {
        PaintGalery fragment = new PaintGalery();
		Bundle args = new Bundle();
        args.putStringArray(IMAGES, imgs);
        fragment.setArguments(args);
        return fragment;
    }

    public PaintGalery() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		imgs = getArguments().getStringArray(IMAGES);
		ArrayList<String> imgsList= new ArrayList<String>();//(Arrays.asList(imgs))
		ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
		File directory = cw.getDir("PaintGallery", Context.MODE_PRIVATE);
		for(int i = 0; i < imgs.length; i++) {
			imgsList.add("file:"+directory.getAbsolutePath()+"/"+imgs[i]);
		}
		
        View rootView = inflater.inflate(R.layout.grid_images_fragment, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(getActivity(), imgsList));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, PaintMain.newInstance(imgs[position]))
                        .commit();
            }
        });
        return rootView;
    }

}