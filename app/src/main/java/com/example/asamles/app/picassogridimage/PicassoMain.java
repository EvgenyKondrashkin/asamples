package com.example.asamles.app.picassogridimage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.R;
import com.example.asamles.app.constants.Constants;
import com.example.asamles.app.dialog.ADialogs;
import com.example.asamles.app.json.JsonFromAssets;

import java.util.ArrayList;

public class PicassoMain extends Fragment {

    private Fragment grid, list;
	private boolean isGrid;
	private SharedPreferences sPref;
    private SharedPreferences.Editor ed;
	public final String IS_GRID = "isGrid";
    public static final String ASSETS_FILE = "images.json";
    private MenuItem gridList;

    public static PicassoMain newInstance() {
        PicassoMain fragment = new PicassoMain();
        return fragment;
    }

    public PicassoMain() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.picasso_main, container, false);
		sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ed = sPref.edit();
		JsonFromAssets JFA = new JsonFromAssets(ASSETS_FILE, getActivity());
		ArrayList<String> imgs = JFA.getFromJson();
		if(imgs == null) {
			ADialogs.alert(getActivity(), this.getString(R.string.json_error));
			return rootView;
		} 
		grid = PicassoGridImages.newInstance(imgs);
		list = PicassoListImages.newInstance(imgs);
		isGrid = sPref.getBoolean(IS_GRID, true);
		showContent(isGrid);
        return rootView;
    }

	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.grid_menu, menu);
		gridList = menu.findItem(R.id.action_list);
        super.onCreateOptionsMenu(menu, inflater);
    }
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list:
				if(isGrid) {
					isGrid = false;
				} else {
					isGrid = true;
				}
				ed.putBoolean(IS_GRID, isGrid);
				ed.commit();
				showContent(isGrid);
				return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
	private void showContent(boolean isGrid) {
		if(isGrid) {
			Toast.makeText(getActivity(), "Grid", Toast.LENGTH_LONG).show();
//			gridList.setIcon(R.drawable.ic_grid);
			getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.frag_content, grid)
				.commit();
		} else {
			Toast.makeText(getActivity(), "List", Toast.LENGTH_LONG).show();
//            gridList.setIcon(R.drawable.ic_list2);
			getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.frag_content, list)
				.commit();
		}
	}
}