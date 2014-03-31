package com.example.asamles.app.picassogridimage;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asamles.app.R;
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        setMenuVisibility(true);
        View rootView = inflater.inflate(R.layout.picasso_main, container, false);
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ed = sPref.edit();
        JsonFromAssets JFA = new JsonFromAssets(ASSETS_FILE, getActivity());
        ArrayList<String> imgs = JFA.getFromJson();
        if (imgs == null) {
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
    public void onConfigurationChanged(Configuration newConfig)
    {

        super.onConfigurationChanged(newConfig);
        getActivity().supportInvalidateOptionsMenu();





    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.grid_menu, menu);
        gridList = menu.findItem(R.id.action_list);
        if (isGrid) {
            gridList.setIcon(R.drawable.ic_grid);
        } else {
            gridList.setIcon(R.drawable.ic_list2);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list:
                if (isGrid) {
                    isGrid = false;
                    item.setIcon(R.drawable.ic_grid);
                } else {
                    isGrid = true;
                    item.setIcon(R.drawable.ic_list2);
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
        if (isGrid) {
            Toast.makeText(getActivity(), "Grid", Toast.LENGTH_LONG).show();
//            gridList.setIcon(R.drawable.ic_grid);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_content, grid)
                    .commit();
//            gridList.setIcon(R.drawable.ic_grid);
        } else {
            Toast.makeText(getActivity(), "List", Toast.LENGTH_LONG).show();

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_content, list)
                    .commit();
//            gridList.setIcon(R.drawable.ic_list2);
        }
    }
}