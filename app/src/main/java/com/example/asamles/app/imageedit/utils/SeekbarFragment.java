package com.example.asamles.app.imageedit.utils;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.asamles.app.R;
import com.example.asamles.app.imageedit.ImageEditMain;

/**
 * Created by evgeny.kondrashkin on 15.04.14.
 */
public class SeekbarFragment extends Fragment {
    private SeekBar seekbar;
    private TextView nameView;
    private int type;
    private int value;
    private String[] name;
    private int brightnessIndex;
    private int progressValue;
    private Bitmap bitmap;
    public static final String TYPE = "TYPE";
    public static final String VALUE = "VALUE";

    public static SeekbarFragment newInstance(int type, int value) {
        SeekbarFragment fragment = new SeekbarFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        args.putInt(VALUE, value);
        fragment.setArguments(args);
        return fragment;
    }
    public SeekbarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        brightnessIndex = 255/50;
        type = getArguments() != null ? getArguments().getInt(TYPE) : 0;
        value = getArguments() != null ? getArguments().getInt(VALUE) : 50;
        name = getActivity().getResources().getStringArray(R.array.seek_list);
        View rootView = inflater.inflate(R.layout.imageedit_seekbar, container, false);

        seekbar = (SeekBar) rootView.findViewById(R.id.seekBar);
        seekbar.setProgress(value);
        nameView = (TextView) rootView.findViewById(R.id.textView);
        nameView.setText(name[type]);

       seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                progressValue = (progress-50)*brightnessIndex;
//                bitmap=ImageEditior.doBrightness(ImageEditMain.bitmap, progressValue);
////                imageView.setImageBitmap(bitmap);
////                ImageEditMain.value = progressValue;
////                ImageEditMain.bitmap = ImageEditior.doBrightness(ImageEditMain.bitmap, progressValue);
//                ImageEditMain.imageView.setImageBitmap(bitmap);
//                ImageEditMain.mAttacher.update();
                bitmap = ImageEditior.changeBitmapBrightness(ImageEditMain.bitmap,progressValue);
                ImageEditMain.imageView.setImageBitmap(bitmap);
                ImageEditMain.mAttacher.update();
            }
        });

        return rootView;
    }
}
