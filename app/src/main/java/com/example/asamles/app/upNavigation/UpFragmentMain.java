package com.example.asamles.app.upNavigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.R;
import com.example.asamles.app.constants.Constants;

public class UpFragmentMain extends Fragment {

    private Button btn;
    private TextView label;
    private String name;

    public static UpFragmentMain newInstance(String name) {
        UpFragmentMain fragment = new UpFragmentMain();
        Bundle args = new Bundle();
        args.putString(Constants.NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    public UpFragmentMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.up_navigation_text, container, false);
        name = getArguments().getString(Constants.NAME);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(name);
        btn = (Button) rootView.findViewById(R.id.button);
        label = (TextView) rootView.findViewById(R.id.textView);
        label.setText("Main");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, UpFragmentFirst.newInstance());
                ft.addToBackStack("UpNav");
                ft.commit();
            }
        });
        return rootView;
    }

}