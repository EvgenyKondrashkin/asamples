package com.example.asamles.app.upNavigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.R;

public class UpFragmentSecond extends Fragment {

    public static UpFragmentSecond newInstance() {
        return new UpFragmentSecond();
    }

    public UpFragmentSecond() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.up_navigation_text, container, false);
        Button btn = (Button) rootView.findViewById(R.id.button);
        btn.setEnabled(false);
        TextView label = (TextView) rootView.findViewById(R.id.textView);
        label.setText("Second Fragment");

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