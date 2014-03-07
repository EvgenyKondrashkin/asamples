package com.example.asamles.app.striptab;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class StripPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 5;
 
        public StripPagerAdapter(FragmentManager fm) {
            super(fm);
        }
 
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
 
        @Override
        public Fragment getItem(int position) {
            return PageFragment.create(position + 1);
        }
        public CharSequence getPageTitle(int position) {
            return "Page " + (position + 1);
        }
    }