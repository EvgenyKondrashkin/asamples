package com.example.asamles.app.striptab;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.constants.Constants;
import com.example.asamles.app.R;

public class StripTabMain extends Fragment {

	private String name;
	
	public static StripTabMain newInstance(String name) {
            StripTabMain fragment = new StripTabMain();
            Bundle args = new Bundle();
            args.putString(Constants.NAME, name);
            fragment.setArguments(args);
            return fragment;
    }
		
    public StripTabMain() {}
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.striptab_fragment, container, false);
		name = getArguments().getString(Constants.NAME);
		((MainActivity) getActivity()).getSupportActionBar().setTitle(name);
		
		ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
		pager.setAdapter(new StripPagerAdapter(getActivity().getSupportFragmentManager()));
		pager.setCurrentItem(1);
        return rootView;
    }
	
}