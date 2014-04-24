package com.example.asamles.app.imageedit.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.edmodo.cropper.CropImageView;
import com.example.asamles.app.R;
import com.example.asamles.app.imageedit.ImageEditMain;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

public class CropperFragment extends Fragment {
    private Bitmap finalBitmap;
    private Bitmap bitmap;
    public static final String TYPE = "TYPE";
    public static final String VALUE = "VALUE";
    private CropImageView cropImageView;
    private OkFragmentListener doneListener = null;

    public void setOkFragmentListener(OkFragmentListener doneListener) {
        this.doneListener = doneListener;
    }

    public static CropperFragment newInstance(String name, int value) {
        CropperFragment fragment = new CropperFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, name);
        args.putInt(VALUE, value);
        fragment.setArguments(args);
        return fragment;
    }

    public CropperFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String name = getArguments() != null ? getArguments().getString(TYPE) : null;
        int value = getArguments() != null ? getArguments().getInt(VALUE) : 0;

        View rootView = inflater.inflate(R.layout.imageedit_cropper, container, false);
        setHasOptionsMenu(true);
        cropImageView = (CropImageView) rootView.findViewById(R.id.cropper);
        cropImageView.setGuidelines(1);
        finalBitmap = ((BitmapDrawable) ImageEditMain.imageView.getDrawable()).getBitmap();
        bitmap = finalBitmap;
        cropImageView.setImageBitmap(finalBitmap);

        TextView nameView = (TextView) rootView.findViewById(R.id.textView);
        nameView.setText(name);

        final ImageEditor imageEditor = new ImageEditor(bitmap);
        SeekBar seekbar = (SeekBar) rootView.findViewById(R.id.seekBar);
        seekbar.setProgress(value);
        final float index = 90 / 50;
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                bitmap = imageEditor.doInvert(bitmap);
                bitmap = imageEditor.doRotate(bitmap, (float) ((progress - 50) * index));
                updateCropImage(bitmap);
                // cropImageView.setRotation((progress - 50) * index);
//				cropImageView.rotateImage((progress-50)*index);
            }
        });

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
                if (doneListener != null) {
                    doneListener.onDone(getTag());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void updateCropImage(Bitmap bitmap) {
        cropImageView.setImageBitmap(bitmap);
    }
    private void updateImage(Bitmap bitmap) {
        ImageEditMain.imageView.setImageBitmap(bitmap);
        ImageEditMain.mAttacher.update();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        updateImage(finalBitmap);
        if (finalBitmap != null) {
            finalBitmap = null;
        }

    }
}