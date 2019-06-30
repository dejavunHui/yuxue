package com.example.androidmvp.mvp.wealth.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class WealthViewPageAdapter extends FragmentPagerAdapter {

    private static final String TAG = "WealthViewPageAdapter";
    private List<Fragment> fragments;

    public WealthViewPageAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public Parcelable saveState() {
        return null;
    }
}
