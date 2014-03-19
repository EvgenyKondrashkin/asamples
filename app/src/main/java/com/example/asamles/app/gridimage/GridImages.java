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
    public static final String JSON_ARRAY = "img";
    private ArrayList<String> imgs = new ArrayList<String>();
    DisplayImageOptions options;
    private String name;
    private Context context;

    public static GridImages newInstance(String name) {
        GridImages fragment = new GridImages();
        Bundle args = new Bundle();
        args.putString(Constants.NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    public GridImages() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grid_images_fragment, container, false);
        name = getArguments().getString(Constants.NAME);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(name);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
        try {
            String jsonString = loadJSONFromAsset();
            imgs = jsonParse(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
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

    public String loadJSONFromAsset() throws IOException {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open(ASSETS_FILE);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public ArrayList<String> jsonParse(String res) throws JSONException {
        JSONObject jsonResponse = new JSONObject(res);
        JSONArray jsonImgs = jsonResponse.getJSONArray(JSON_ARRAY);
        ArrayList<String> imgs = new ArrayList<String>();
        for (int i = 0; i < jsonImgs.length(); i++) {
            try {
                String item = jsonImgs.getString(i);
                imgs.add(item);
            } catch (JSONException e) {
                alert();
                e.printStackTrace();
            }
        }
        return imgs;
    }

    public void alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(this.getString(R.string.error));
        builder.setCancelable(true);
        builder.setPositiveButton(this.getString(R.string.close),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create().show();
    }
}