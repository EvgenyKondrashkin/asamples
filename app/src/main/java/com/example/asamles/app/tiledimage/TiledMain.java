package com.example.asamles.app.tiledimage;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.asamles.app.R;
import com.qozix.tileview.TileView;

public class TiledMain extends Fragment {

    private int[][] mInfoXY = {{2292, 3528}, {538, 957}};
    private int[][] mParkXY = {{1044, 1230, 1188, 2526, 3810, 4080}, {616, 395, 850, 760, 850, 927}};
    private int sizeX = 4800;
    private int sizeY = 1700;
    private int mMuseumX = 2583;
    private int mMuseumY = 400;
    private float center = -0.5f;
    private TileView tileView;
    private ImageView mMuseum;
    private ImageView[] mInfo = new ImageView[mInfoXY.length];
    private ImageView[] mPark = new ImageView[mParkXY.length];
    private boolean marker = false;
    private int activityOrientation;

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
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getMarkers();

        tileView = new TileView(getActivity());
        tileView = getTileView();
        tileView.setSize(sizeX, sizeY);

        tileView.addDetailLevel(1f, "tiles/central_park/1000/2xbig_map-%col%_%row%.png", "tiles/central_park/small_map.png", 256, 256);
        tileView.addDetailLevel(0.5f, "tiles/central_park/500/big_map_%col%_%row%.jpg", "tiles/central_park/small_map.png", 128, 128);
        tileView.addDetailLevel(0.25f, "tiles/central_park/250/not_big_map-%col%_%row%.png", "tiles/central_park/small_map.png", 256, 256);
        tileView.setScaleToFit(true);
        tileView.setScale(0);

        tileView.defineRelativeBounds(0, 0, sizeX, sizeY);
        tileView.moveToAndCenter(sizeX / 2, sizeY / 2);
        tileView.slideToAndCenter(sizeX / 2, sizeY / 2);

        return tileView;
    }

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
        for (int i = 0; i < mInfo.length; i++) {
            mInfo[i] = new ImageView(getActivity());
            mInfo[i].setImageResource(R.drawable.m_info);
            mInfo[i].setTag("Info");
        }
        for (int i = 0; i < mPark.length; i++) {
            mPark[i] = new ImageView(getActivity());
            mPark[i].setImageResource(R.drawable.m_park);
            mPark[i].setTag("Park");
        }
    }

    public void setMarkers() {

        if (!marker) {
            marker = true;
            tileView.addMarker(mMuseum, mMuseumX, mMuseumY, center, center);
            for (int i = 0; i < mInfoXY.length; i++) {
                tileView.addMarker(mInfo[i], mInfoXY[i][0], mInfoXY[0][i], center, center);
            }
            for (int i = 0; i < mParkXY.length; i++) {
                tileView.addMarker(mPark[i], mParkXY[i][0], mParkXY[0][i], center, center);
            }
        } else {
            marker = false;
            tileView.removeMarker(mMuseum);
            for (int i = 0; i < mInfoXY.length; i++) {
                tileView.removeMarker(mInfo[i]);
            }
            for (int i = 0; i < mParkXY.length; i++) {
                tileView.removeMarker(mPark[i]);
            }
        }
    }

}
