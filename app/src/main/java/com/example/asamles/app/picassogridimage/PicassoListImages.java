package com.example.asamles.app.picassogridimage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.R;
import com.example.asamles.app.constants.Constants;
import com.example.asamles.app.dialog.ADialogs;
import com.example.asamles.app.json.JsonFromAssets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PicassoListImages extends Fragment implements AdapterView.OnItemClickListener {

    public static final String ASSETS_FILE = "images.json";
    public static final String STACK_NAME = "image";
	public static final String IMAGES = "images";
    private ArrayList<String> imgs = new ArrayList<String>();
	private ArrayList<String> list = new ArrayList<String>();
    private ListView listMenu;
    private ImageListAdapter adapter;
    public static PicassoListImages newInstance(ArrayList<String> imgs){
        PicassoListImages fragment = new PicassoListImages();
		Bundle args = new Bundle();
        args.putStringArrayList(IMAGES, imgs);
        fragment.setArguments(args);
        return fragment;
    }

    public PicassoListImages() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_images_fragment, container, false);
        imgs = getArguments().getStringArrayList(IMAGES);
		for(int i = 1; i<imgs.size()+1; i++) {
			list.add("Image "+ i);
		}
		listMenu = (ListView)rootView.findViewById(R.id.list);
		listMenu.setOnItemClickListener(this);
		adapter = new ImageListAdapter(getActivity(), list, imgs);
        listMenu.setAdapter(adapter);
        return rootView;
    }
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
		getActivity().getSupportFragmentManager().beginTransaction()
			.replace(R.id.container, ImagePager.newInstance(position, imgs))
			.addToBackStack(STACK_NAME)
			.commit();
	}
}