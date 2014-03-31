package com.example.asamles.app.upNavigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asamles.app.R;

public class UpFragmentSecond extends Fragment {

    private Button btn;
    private TextView label;

    public static UpFragmentSecond newInstance() {
        UpFragmentSecond fragment = new UpFragmentSecond();

        return fragment;
    }

    public UpFragmentSecond() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.up_navigation_text, container, false);
        btn = (Button) rootView.findViewById(R.id.button);
        btn.setEnabled(false);
        label = (TextView) rootView.findViewById(R.id.textView);
        label.setText("Second Fragment");

        return rootView;
    }

}