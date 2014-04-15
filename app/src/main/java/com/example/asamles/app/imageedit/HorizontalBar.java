package com.example.asamles.app.imageedit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.asamles.app.R;
import com.example.asamles.app.imageedit.utils.SeekbarFragment;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

public class HorizontalBar extends Fragment{
    private String[] name;
    private int value = 50;
    private ImageButton brightnessButton;
    private ImageButton contrastButton;
    private ImageButton rotateButton;
    private ImageButton opacityButton;
    private ImageButton blurButton;
    private ImageButton cropButton;
    private ImageButton filterButton;
    private ImageButton stickerButton;
    private ImageButton photoButton;
    private ImageButton loadButton;
    private ImageButton saveButton;
    private ViewGroup container;
    public static HorizontalBar newInstance() {
        HorizontalBar fragment = new HorizontalBar();
        return fragment;
    }

    public HorizontalBar() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container = container;
        name = getActivity().getResources().getStringArray(R.array.seek_list);
        View rootView = inflater.inflate(R.layout.imageedit_horizontal_scroll, container, false);
        setButtons(rootView);

        return rootView;
    }
    private void setButtons(View view){
        brightnessButton = (ImageButton) view.findViewById(R.id.brightness);
        brightnessButton.setImageDrawable(new IconDrawable(getActivity(), Iconify.IconValue.fa_sun_o)
                .colorRes(R.color.grey_light).sizeDp(32));
        brightnessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSeekbar(0,value);
            }
        });
        contrastButton = (ImageButton) view.findViewById(R.id.contrast);
        contrastButton.setImageDrawable(new IconDrawable(getActivity(), Iconify.IconValue.fa_adjust)
                .colorRes(R.color.grey_light).sizeDp(32));
        contrastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSeekbar(1,value);
            }
        });
        rotateButton = (ImageButton) view.findViewById(R.id.rotate);
        rotateButton.setImageDrawable(new IconDrawable(getActivity(), Iconify.IconValue.fa_undo)
                .colorRes(R.color.grey_light).sizeDp(32));
        opacityButton = (ImageButton) view.findViewById(R.id.opacity);
        opacityButton.setImageDrawable(new IconDrawable(getActivity(), Iconify.IconValue.fa_eye_slash)
                .colorRes(R.color.grey_light).sizeDp(32));
        opacityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSeekbar(2,value);
            }
        });
        blurButton = (ImageButton) view.findViewById(R.id.blur);
        blurButton.setImageDrawable(new IconDrawable(getActivity(), Iconify.IconValue.fa_tint)
                .colorRes(R.color.grey_light).sizeDp(32));
        cropButton = (ImageButton) view.findViewById(R.id.crop);
        cropButton.setImageDrawable(new IconDrawable(getActivity(), Iconify.IconValue.fa_crop)
                .colorRes(R.color.grey_light).sizeDp(32));
        filterButton = (ImageButton) view.findViewById(R.id.filter);
        filterButton.setImageDrawable(new IconDrawable(getActivity(), Iconify.IconValue.fa_picture_o)
                .colorRes(R.color.grey_light).sizeDp(32));
        stickerButton = (ImageButton) view.findViewById(R.id.sticker);
        stickerButton.setImageDrawable(new IconDrawable(getActivity(), Iconify.IconValue.fa_tag)
                .colorRes(R.color.grey_light).sizeDp(32));
        photoButton = (ImageButton) view.findViewById(R.id.photo);
        photoButton.setImageDrawable(new IconDrawable(getActivity(), Iconify.IconValue.fa_camera)
                .colorRes(R.color.grey_light).sizeDp(32));
        loadButton = (ImageButton) view.findViewById(R.id.load);
        loadButton.setImageDrawable(new IconDrawable(getActivity(), Iconify.IconValue.fa_arrow_circle_o_down)
                .colorRes(R.color.grey_light).sizeDp(32));
        saveButton = (ImageButton) view.findViewById(R.id.save);
        saveButton.setImageDrawable(new IconDrawable(getActivity(), Iconify.IconValue.fa_floppy_o)
                .colorRes(R.color.grey_light).sizeDp(32));
    }
    public void openSeekbar(int type, int value){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.bottomLayout, SeekbarFragment.newInstance(type, value));
        ft.addToBackStack("seekbar");
        ft.commit();
    }
}
