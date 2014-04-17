package com.example.asamles.app.imageedit;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.IconButton;
import android.widget.SeekBar;

import com.example.asamles.app.R;
import com.example.asamles.app.imageedit.utils.ImageEditior;
import com.example.asamles.app.imageedit.utils.SeekbarFragment;
import com.example.asamles.app.saveload.SaveLoadFile;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.concurrent.Callable;

public class HorizontalBar extends Fragment implements SeekbarFragment.SeekbarFragmentListener {
    private String[] name;
    private int value = 50;
    private int progressValue = 50;
    float index;
    private Bitmap bitmap;
    private IconButton brightnessButton;
    private IconButton contrastButton;
    private IconButton rotateButton;
    private IconButton saturationButton;
    private IconButton blurButton;
    private IconButton cropButton;
    private IconButton filterButton;
    private IconButton stickerButton;
    private IconButton photoButton;
    private IconButton loadButton;
    private IconButton saveButton;
//    private ViewGroup container;
//    private boolean mShowingBack;
    private int type;

    public static HorizontalBar newInstance() {
        HorizontalBar fragment = new HorizontalBar();
        return fragment;
    }

    public HorizontalBar() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        this.container = container;
        name = getActivity().getResources().getStringArray(R.array.seek_list);
        View rootView = inflater.inflate(R.layout.imageedit_horizontal_scroll, container, false);
        setButtons(rootView);
        return rootView;
    }

    private void setButtons(View view){
        brightnessButton = (IconButton) view.findViewById(R.id.brightness);
        brightnessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 0;
                index = 255/50;
                openSeekbar(name[type],value);
            }
        });
        contrastButton = (IconButton) view.findViewById(R.id.contrast);
        contrastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                index = (float)1/50;
                openSeekbar(name[type],value);
            }
        });
        saturationButton = (IconButton) view.findViewById(R.id.saturation);
        saturationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                index = (float)1/50;
                openSeekbar(name[type],value);
            }
        });
        rotateButton = (IconButton) view.findViewById(R.id.rotate);
        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        blurButton = (IconButton) view.findViewById(R.id.blur);
        blurButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cropButton = (IconButton) view.findViewById(R.id.crop);
        cropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        filterButton = (IconButton) view.findViewById(R.id.filter);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        stickerButton = (IconButton) view.findViewById(R.id.sticker);
        stickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        photoButton = (IconButton) view.findViewById(R.id.photo);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        loadButton = (IconButton) view.findViewById(R.id.load);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        saveButton = (IconButton) view.findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void openSeekbar(String name, int value){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.flip_enter, R.anim.flip_exit, R.anim.flip_enter, R.anim.flip_exit);
        SeekbarFragment fragment = SeekbarFragment.newInstance(name, value);
        fragment.setSeekbarFragmentListener(this);
        ft.replace(R.id.bottomLayout, fragment);
        ft.addToBackStack("seekbar");
        ft.commit();
    }


    Runnable save = new Runnable() {
        @Override
        public void run() {
            SaveLoadFile.saveToGallery(getActivity(), ImageEditMain.bitmap, null);
        }
    };

    @Override
    public void onSeekbarFragmentStopTrackingTouch(SeekBar seekBar) {
//        int index = 255/50;
//        progressValue = (seekBar.getProgress()-50)*index;
//        bitmap = ImageEditior.changeBitmapBrightness(ImageEditMain.bitmap, progressValue);
//        updateImage();
    }

    @Override
    public void onSeekbarFragmentStartTrackingTouch(SeekBar seekBar) {
    }
    @Override
    public void onSeekbarFragmentProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        bitmap = changeBitmap(seekBar.getProgress());

        updateImage(bitmap);
    }

    private Bitmap changeBitmap(int progress) {
        float changedValue = (float)progress;
        switch (type){
            case 0:
                changedValue = (float)(progress-50)*index;
                bitmap = ImageEditior.changeBitmapBrightness(ImageEditMain.bitmap, changedValue);
                break;
            case 1:
                changedValue = (float)(progress-50)*index+1;
                bitmap = ImageEditior.changeBitmapContrast(ImageEditMain.bitmap, changedValue);
                break;
            case 2:
                changedValue = (float)(progress-50)*index+1;
                bitmap = ImageEditior.changeBitmapSaturation(ImageEditMain.bitmap, changedValue);
                break;
        }
        return bitmap;
    }

    private void updateImage(Bitmap bitmap){
        ImageEditMain.imageView.setImageBitmap(bitmap);
        ImageEditMain.mAttacher.update();
    }
}
