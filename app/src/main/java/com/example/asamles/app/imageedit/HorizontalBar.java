package com.example.asamles.app.imageedit;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.IconButton;
import android.widget.SeekBar;

import com.example.asamles.app.R;
import com.example.asamles.app.imageedit.utils.FilterFragment;
import com.example.asamles.app.imageedit.utils.ImageEditior;
import com.example.asamles.app.imageedit.utils.SeekbarFragment;
import com.example.asamles.app.saveload.SaveLoadFile;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.concurrent.Callable;

public class HorizontalBar extends Fragment implements SeekbarFragment.SeekbarFragmentListener {
    private static final int SELECT_PICTURE = 1;
    private String[] name;
    private int[] value = {50,50,50};
    private int progressValue = 50;
    private float index;
	private int angle = 0;
    private Bitmap bitmap, mainBitmap;
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
		mainBitmap = ((BitmapDrawable)ImageEditMain.imageView.getDrawable()).getBitmap();
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
                openSeekbar(name[type],value[type]);
            }
        });
        contrastButton = (IconButton) view.findViewById(R.id.contrast);
        contrastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                index = (float)1/50;
                openSeekbar(name[type],value[type]);
            }
        });
        saturationButton = (IconButton) view.findViewById(R.id.saturation);
        saturationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                index = (float)1/50;
                openSeekbar(name[type],value[type]);
            }
        });
        rotateButton = (IconButton) view.findViewById(R.id.rotate);
        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				bitmap = ImageEditior.doRotate(mainBitmap, getActivity());
                updateImage(bitmap);
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
                openFilter();
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
				loadFromGallery();
            }
        });
        saveButton = (IconButton) view.findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				SaveLoadFile.saveToGallery(getActivity(), mainBitmap, null);
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
    public void openFilter(){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.flip_enter, R.anim.flip_exit, R.anim.flip_enter, R.anim.flip_exit);
        FilterFragment fragment = FilterFragment.newInstance();
        ft.replace(R.id.bottomLayout, fragment);
//        ft.addToBackStack("filter");
        ft.commit();
    }

    Runnable save = new Runnable() {
        @Override
        public void run() {
            SaveLoadFile.saveToGallery(getActivity(), mainBitmap, null);
        }
    };

    @Override
    public void onSeekbarFragmentStopTrackingTouch(SeekBar seekBar) {    }
    @Override
    public void onSeekbarFragmentStartTrackingTouch(SeekBar seekBar) {    }
    @Override
    public void onSeekbarFragmentProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        bitmap = changeBitmap(seekBar.getProgress());
        value[type] = seekBar.getProgress();
        updateImage(bitmap);
    }

    private Bitmap changeBitmap(int progress) {
        float changedValue;
        Bitmap bitmap = mainBitmap;
        switch (type){
            case 0:
                changedValue = (float)(progress-50)*index;
                bitmap = ImageEditior.changeBitmapBrightness(mainBitmap, changedValue);
                break;
            case 1:
                changedValue = (float)(progress-50)*index+1;
                bitmap = ImageEditior.changeBitmapContrast(mainBitmap, changedValue);
                break;
            case 2:
                changedValue = (float)(progress-50)*index+1;
                bitmap = ImageEditior.changeBitmapSaturation(mainBitmap, changedValue);
                break;
        }
        return bitmap;
    }

    private void updateImage(Bitmap bitmap){
        ImageEditMain.imageView.setImageBitmap(bitmap);
        ImageEditMain.mAttacher.update();
    }
	private void loadFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getActivity().getString(R.string.load_intent_title)), SELECT_PICTURE);
    }
}
