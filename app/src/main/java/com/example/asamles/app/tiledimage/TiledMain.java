package com.example.asamles.app.tiledimage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.asamles.app.R;
import com.qozix.tileview.TileView;

public class TiledMain extends Fragment {
    private Button btn, btn2;

    private int[] mInfoX = {2292, 3528};
    private int[] mInfoY = {538, 957};
    private int[] mParkX = {1044, 1230, 1188, 2526, 3810, 4080};
    private int[] mParkY = {616, 395, 850, 760, 850, 927};
    private TileView tileView;
    private ImageView mMuseum;
    private ImageView[] mInfo = new ImageView[2];
    private ImageView[] mPark = new ImageView[6];
    private boolean marker = false;

    public static TiledMain newInstance() {
        TiledMain fragment = new TiledMain();
        return fragment;
    }

    public TiledMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        getMarkers();

        tileView = new TileView(getActivity());
        tileView = getTileView();
        tileView.setSize(4800, 1700);
		
        tileView.addDetailLevel(1f, "tiles/central_park/1000/2xbig_map-%col%_%row%.png", "tiles/central_park/small_map.png", 256, 256);
        tileView.addDetailLevel(0.5f, "tiles/central_park/500/big_map_%col%_%row%.jpg", "tiles/central_park/small_map.png", 128, 128);
        tileView.addDetailLevel(0.25f, "tiles/central_park/250/not_big_map-%col%_%row%.png", "tiles/central_park/small_map.png", 256, 256);
        tileView.setScale(0.25f);

        tileView.defineRelativeBounds(0, 0, 4800, 1700);
        tileView.moveToAndCenter(4800 / 2, 1700 / 2);
        tileView.slideToAndCenter(4800 / 2, 1700 / 2);

        return tileView;
    }

    private int activityOrientation;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tile_image, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_markers:
                setMarkers();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        tileView.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
        tileView.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tileView.destroy();
        tileView = null;
    }

    public TileView getTileView() {
        return tileView;
    }

    public void getMarkers() {

        mMuseum = new ImageView(getActivity());
        mMuseum.setImageResource(R.drawable.m_museum);
        mMuseum.setTag("Museum");
        for (int i = 0; i < 2; i++) {
            mInfo[i] = new ImageView(getActivity());
            mInfo[i].setImageResource(R.drawable.m_info);
            mInfo[i].setTag("Info");
        }
        for (int i = 0; i < 6; i++) {
            mPark[i] = new ImageView(getActivity());
            mPark[i].setImageResource(R.drawable.m_park);
            mPark[i].setTag("Park");
        }
    }

    public void setMarkers() {

        if (!marker) {
            marker = true;
            tileView.addMarker(mMuseum, 2583, 400, -0.5f, -0.5f);
            for (int i = 0; i < 2; i++) {
                tileView.addMarker(mInfo[i], mInfoX[i], mInfoY[i], -0.5f, -0.5f);
            }
            for (int i = 0; i < 6; i++) {
                tileView.addMarker(mPark[i], mParkX[i], mParkY[i], -0.5f, -0.5f);
            }
        } else {
            marker = false;
            tileView.removeMarker(mMuseum);
            for (int i = 0; i < 2; i++) {
                tileView.removeMarker(mInfo[i]);
            }
            for (int i = 0; i < 6; i++) {
                tileView.removeMarker(mPark[i]);
            }
        }
    }

}
