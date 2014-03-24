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
import android.widget.Toast;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.R;
import com.example.asamles.app.constants.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PicassoGridImages extends Fragment {

    public static final String ASSETS_FILE = "images.json";
    public static final String STACK_NAME = "image";
    public static final String JSON_ARRAY = "img";
    private ArrayList<String> imgs = new ArrayList<String>();
    private String name;
    private Context context;

    public static PicassoGridImages newInstance(String name) {
        PicassoGridImages fragment = new PicassoGridImages();
        Bundle args = new Bundle();
        args.putString(Constants.NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    public PicassoGridImages() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grid_images_fragment, container, false);
        setHasOptionsMenu(true);
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
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.grid_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list:
                Toast.makeText(getActivity(), "List", Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, PicassoListImages.newInstance(name))
					.addToBackStack(null)
                    .commit();
				return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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