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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PicassoListImages extends Fragment implements AdapterView.OnItemClickListener {

    public static final String ASSETS_FILE = "images.json";
    public static final String STACK_NAME = "image";
    public static final String JSON_ARRAY = "img";
    private ArrayList<String> imgs = new ArrayList<String>();
	private ArrayList<String> list = new ArrayList<String>();
    private String name;
    private Context context;
    private ListView listMenu;
    private ImageListAdapter adapter;
    public static PicassoListImages newInstance(String name) {
        PicassoListImages fragment = new PicassoListImages();
        Bundle args = new Bundle();
        args.putString(Constants.NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    public PicassoListImages() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.list_images_fragment, container, false);
		
		name = getArguments().getString(Constants.NAME);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(name);
        
		try {
            String jsonString = loadJSONFromAsset();
            imgs = jsonParse(jsonString);
			for(int i = 0; i<imgs.size();i++)
			{
				list.add("Image "+ i);
			}
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
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
					.replace(R.id.container, PicassoGridImages.newInstance(name))
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