package com.example.asamles.app.imageedit;

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
import com.example.asamles.app.imageedit.utils.CropperFragment;
import com.example.asamles.app.imageedit.utils.FilterFragment;
import com.example.asamles.app.imageedit.utils.ImageEditor;
import com.example.asamles.app.imageedit.utils.OkFragmentListener;
import com.example.asamles.app.imageedit.utils.RotateFragment;
import com.example.asamles.app.imageedit.utils.SeekbarFragment;

public class HorizontalBar extends Fragment implements SeekbarFragment.SeekbarFragmentListener {
    private String[] name;
    private int value = 50;
    private float index;
    private Bitmap bitmap, mainBitmap;
    private IconButton brightnessButton;
    private IconButton contrastButton;
    private IconButton rotateButton;
    private IconButton saturationButton;
    private IconButton cropButton;
    private IconButton filterButton;
    private IconButton stickerButton;
    private int type;
    private float changedValue;
    private ImageEditor imageEditor;

    public static HorizontalBar newInstance() {
        HorizontalBar fragment = new HorizontalBar();
        return fragment;
    }

    public HorizontalBar() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        name = getActivity().getResources().getStringArray(R.array.seek_list);
//		mainBitmap = ((BitmapDrawable)ImageEditMain.imageView.getDrawable()).getBitmap();
//        mainBitmap = mainBitmap.copy(mainBitmap.getConfig(), true);
        View rootView = inflater.inflate(R.layout.imageedit_horizontal_scroll, container, false);
        setButtons(rootView);
        return rootView;
    }

    private void setButtons(View view) {
        brightnessButton = (IconButton) view.findViewById(R.id.brightness);
        brightnessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 0;
                index = 255 / 50;
                openSeekbar(name[type], value);
            }
        });
        contrastButton = (IconButton) view.findViewById(R.id.contrast);
        contrastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                index = (float) 1 / 50;
                openSeekbar(name[type], value);
            }
        });
        saturationButton = (IconButton) view.findViewById(R.id.saturation);
        saturationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                index = (float) 1 / 50;
                openSeekbar(name[type], value);
            }
        });
        rotateButton = (IconButton) view.findViewById(R.id.rotate);
        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRotate();
            }
        });
        cropButton = (IconButton) view.findViewById(R.id.crop);
        cropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCrop();
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
    }

    public void openSeekbar(String name, int value) {
        mainBitmap = ((BitmapDrawable) ImageEditMain.imageView.getDrawable()).getBitmap();
        imageEditor = new ImageEditor(mainBitmap);
//        mainBitmap = mainBitmap.copy(mainBitmap.getConfig(), true);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.flip_enter, R.anim.flip_exit, R.anim.flip_enter, R.anim.flip_exit);
        SeekbarFragment fragment = SeekbarFragment.newInstance(name, value);
        fragment.setOkFragmentListener(new OkFragmentListener() {
            @Override
            public void onDone(String tag) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        fragment.setSeekbarFragmentListener(this);
        ft.replace(R.id.bottomLayout, fragment);
        ft.addToBackStack("seekbar");
        ft.commit();
    }

    public void openFilter() {
//        mainBitmap = ((BitmapDrawable)ImageEditMain.imageView.getDrawable()).getBitmap();
//        mainBitmap = mainBitmap.copy(mainBitmap.getConfig(), true);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.flip_enter, R.anim.flip_exit, R.anim.flip_enter, R.anim.flip_exit);
        FilterFragment fragment = FilterFragment.newInstance();
        fragment.setOkFragmentListener(new OkFragmentListener() {
            @Override
            public void onDone(String tag) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        ft.replace(R.id.bottomLayout, fragment);
        ft.addToBackStack("filter");
        ft.commit();
    }

    public void openRotate() {
//        mainBitmap = ((BitmapDrawable)ImageEditMain.imageView.getDrawable()).getBitmap();
//        mainBitmap = mainBitmap.copy(mainBitmap.getConfig(), true);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.flip_enter, R.anim.flip_exit, R.anim.flip_enter, R.anim.flip_exit);
        RotateFragment fragment = RotateFragment.newInstance();
        fragment.setOkFragmentListener(new OkFragmentListener() {
            @Override
            public void onDone(String tag) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        ft.replace(R.id.bottomLayout, fragment);
        ft.addToBackStack("rotate");
        ft.commit();
    }

    public void openCrop() {
//        mainBitmap = ((BitmapDrawable)ImageEditMain.imageView.getDrawable()).getBitmap();
//        mainBitmap = mainBitmap.copy(mainBitmap.getConfig(), true);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//        ft.setCustomAnimations(R.anim.flip_enter, R.anim.flip_exit, R.anim.flip_enter, R.anim.flip_exit);
        CropperFragment fragment = CropperFragment.newInstance("Rotate", 50);
        fragment.setOkFragmentListener(new OkFragmentListener() {
            @Override
            public void onDone(String tag) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        ft.replace(R.id.container, fragment);
        ft.addToBackStack("cropper");
        ft.commit();
    }

    @Override
    public void onSeekbarFragmentStopTrackingTouch(SeekBar seekBar) {
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
        Bitmap bitmap = mainBitmap;
        switch (type) {
            case 0:
                changedValue = (float) (progress - 50) * index;
                bitmap = imageEditor.onBrightness(mainBitmap, changedValue);
                break;
            case 1:
                changedValue = (float) (progress - 50) * index + 1;
                bitmap = ImageEditor.onContrast(mainBitmap, changedValue);
                break;
            case 2:
                changedValue = (float) (progress - 50) * index + 1;
                bitmap = ImageEditor.onSaturation(mainBitmap, changedValue);
                break;
        }
        return bitmap;
    }

    private void updateImage(Bitmap bitmap) {
        ImageEditMain.imageView.setImageBitmap(bitmap);
        ImageEditMain.mAttacher.update();
    }
}
