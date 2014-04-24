package com.example.asamles.app.picassogridimage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.asamles.app.R;
import com.example.asamles.app.dialog.ADialogs;
import com.example.asamles.app.json.JsonFromAssets;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;

public class PicassoMain extends Fragment {

    private Fragment grid, list;
    private boolean isGrid;
    private SharedPreferences sPref;
    private SharedPreferences.Editor ed;
    public final String IS_GRID = "isGrid";
    public static final String ASSETS_FILE = "images.json";
    private IconDrawable gridIcon;
    private IconDrawable listIcon;

    public static PicassoMain newInstance() {
        return new PicassoMain();
    }

    public PicassoMain() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        isGrid = sPref.getBoolean(IS_GRID, true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_picasso, container, false);
        ed = sPref.edit();
        JsonFromAssets JFA = new JsonFromAssets(ASSETS_FILE, getActivity());
        ArrayList<String> imgs = JFA.getFromJson();
        if (imgs == null) {
            ADialogs alertDialog = new ADialogs(getActivity());
            alertDialog.alert(false, this.getString(R.string.error), this.getString(R.string.json_error), this.getString(R.string.ok), null);
            return rootView;
        }
        grid = PicassoGridImages.newInstance(imgs);
        list = PicassoListImages.newInstance(imgs);

        showContent(isGrid);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.grid_menu, menu);
        gridIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_th)
                .colorRes(R.color.grey_light)
                .actionBarSize();
        listIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_th_list)
                .colorRes(R.color.grey_light)
                .actionBarSize();
        MenuItem gridList = menu.findItem(R.id.action_list);
        if (gridList != null) {
            if (isGrid) {
                    gridList.setIcon(gridIcon);
            } else {
                gridList.setIcon(listIcon);
            }
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list:
                if (isGrid) {
                    isGrid = false;
                    item.setIcon(listIcon);
                } else {
                    isGrid = true;
                    item.setIcon(gridIcon);
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
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_content, grid)
                    .commit();
        } else {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_content, list)
                    .commit();
        }
    }
}