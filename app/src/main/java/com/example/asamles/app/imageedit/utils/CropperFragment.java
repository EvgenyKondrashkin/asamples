package com.example.asamles.app.imageedit.utils;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.edmodo.cropper.CropImageView;
import com.example.asamles.app.R;
import com.example.asamles.app.imageedit.ImageEditMain;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

public class CropperFragment extends Fragment {
    private Bitmap finalBitmap;
	private CropImageView cropImageView;
	private OkFragmentListener doneListener = null;
	
    public static CropperFragment newInstance() {
        CropperFragment fragment = new CropperFragment();
        return fragment;
    }

    public CropperFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.imageedit_cropper, container, false);
        setHasOptionsMenu(true);
		cropImageView = (CropImageView) rootView.findViewById(R.id.cropper);
        return rootView;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.done_menu, menu);
        menu.findItem(R.id.action_done).setIcon(new IconDrawable(getActivity(), Iconify.IconValue.fa_check)
                .colorRes(R.color.grey_light)
                .actionBarSize());
        menu.setGroupVisible(R.id.menu_group_imageedit, false);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                finalBitmap = cropImageView.getCroppedImage();
                if(doneListener != null){
                    doneListener.onDone(getTag());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	private void updateImage(Bitmap bitmap){
        ImageEditMain.imageView.setImageBitmap(bitmap);
        ImageEditMain.mAttacher.update();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        updateImage(finalBitmap);
    }
}