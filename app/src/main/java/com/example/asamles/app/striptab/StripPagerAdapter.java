package com.example.asamles.app.striptab;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.asamles.app.R;

public class StripPagerAdapter extends FragmentPagerAdapter {
    private int PAGE_COUNT;
    private String[] list;

    public StripPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        list = context.getResources().getStringArray(R.array.strip_list);
        PAGE_COUNT = list.length;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1);
    }

    public CharSequence getPageTitle(int position) {

        return list[position];
    }
}