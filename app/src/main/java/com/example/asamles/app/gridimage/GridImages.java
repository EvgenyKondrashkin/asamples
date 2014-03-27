package com.example.asamles.app.gridimage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.R;
import com.example.asamles.app.constants.Constants;
import com.example.asamles.app.dialog.ADialogs;
import com.example.asamles.app.json.JsonFromAssets;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GridImages extends Fragment {

    public static final String ASSETS_FILE = "images.json";
    public static final String STACK_NAME = "image";
    private ArrayList<String> imgs = new ArrayList<String>();
    DisplayImageOptions options;
    private Context context;

    public static GridImages newInstance() {
        GridImages fragment = new GridImages();
        return fragment;
    }

    public GridImages() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grid_images_fragment, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);

        JsonFromAssets JFA = new JsonFromAssets(ASSETS_FILE, getActivity());
        imgs = JFA.getFromJson();
        if(imgs == null) {
            ADialogs.alert(getActivity(), this.getString(R.string.json_error));
            return rootView;
        }

        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.empty)
                .showImageOnLoading(R.drawable.loading)
                .showImageOnFail(R.drawable.error)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity().getApplicationContext()));
        boolean pauseOnScroll = false;
        boolean pauseOnFling = true;
        PauseOnScrollListener listener = new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling);
        gridView.setOnScrollListener(listener);
        gridView.setAdapter(new ImageAdapter(getActivity(), imgs, options, imageLoader));
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