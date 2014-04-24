package com.example.asamles.app.upNavigation;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.R;

public class UpFragmentFirst extends Fragment {

    public static UpFragmentFirst newInstance() {
        return new UpFragmentFirst();
    }

    public UpFragmentFirst() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.up_navigation_text, container, false);

        Button btn = (Button) rootView.findViewById(R.id.button);
        TextView label = (TextView) rootView.findViewById(R.id.textView);
        label.setText("First Fragment");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, UpFragmentSecond.newInstance());
                ft.addToBackStack("UpNav");
                ft.commit();
            }
        });
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ((MainActivity) getActivity()).getSupportFragmentManager().popBackStack("UpNav", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}