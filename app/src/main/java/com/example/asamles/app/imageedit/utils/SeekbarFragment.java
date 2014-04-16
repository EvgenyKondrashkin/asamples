package com.example.asamles.app.imageedit.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.asamles.app.R;

public class SeekbarFragment extends Fragment  {
    private SeekBar seekbar;
    private TextView nameView;
    private int value;
    private String name;
    public static final String TYPE = "TYPE";
    public static final String VALUE = "VALUE";

    private SeekbarFragmentListener seekbarListener = null;
    public interface SeekbarFragmentListener {
        public void onSeekbarFragmentStopTrackingTouch(SeekBar seekBar);
        public void onSeekbarFragmentStartTrackingTouch(SeekBar seekBar);
        public void onSeekbarFragmentProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser);
    }
    public void setSeekbarFragmentListener(SeekbarFragmentListener seekbarListener) {
        this.seekbarListener = seekbarListener;
    }
    public static SeekbarFragment newInstance(String name, int value) {
        SeekbarFragment fragment = new SeekbarFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, name);
        args.putInt(VALUE, value);
        fragment.setArguments(args);
        return fragment;
    }
    public SeekbarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        name = getArguments() != null ? getArguments().getString(TYPE) : null;
        value = getArguments() != null ? getArguments().getInt(VALUE) : 0;

        View rootView = inflater.inflate(R.layout.imageedit_seekbar, container, false);

        seekbar = (SeekBar) rootView.findViewById(R.id.seekBar);
        seekbar.setProgress(value);
        nameView = (TextView) rootView.findViewById(R.id.textView);
        nameView.setText(name);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekbarListener != null) {
                    seekbarListener.onSeekbarFragmentStopTrackingTouch(seekBar);
                } else { seekbarListener.onSeekbarFragmentStopTrackingTouch(null); }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (seekbarListener != null) {
                    seekbarListener.onSeekbarFragmentStartTrackingTouch(seekBar);
                } else { seekbarListener.onSeekbarFragmentStartTrackingTouch(null); }
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (seekbarListener != null) {
                    seekbarListener.onSeekbarFragmentProgressChanged(seekBar, progress, fromUser);
                } else { seekbarListener.onSeekbarFragmentProgressChanged(null, 0, false); }
            }
        });
        return rootView;
    }
}
