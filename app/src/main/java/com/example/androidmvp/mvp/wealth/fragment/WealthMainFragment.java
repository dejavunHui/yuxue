package com.example.androidmvp.mvp.wealth.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.sacot41.scviewpager.DotsView;
import com.dev.sacot41.scviewpager.SCViewPager;
import com.example.androidmvp.R;
import com.example.androidmvp.mvp.base.BaseFragment;
import com.example.androidmvp.mvp.entity.localdb.Country;
import com.example.androidmvp.mvp.wealth.adapter.WealthViewPageAdapter;

import java.util.ArrayList;
import java.util.List;

public class WealthMainFragment extends BaseFragment {

    private static final String TAG = "WealthMainFragment";
    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver receiver;

    DayFragment dayFragment;
    WeekFragment weekFragment;
    ChooseCityFragment citymanage;

    //    @BindView(R.id.wealth_viewPager)
    private SCViewPager viewPager;

    //    @BindView(R.id.wealth_dotsview)
    private DotsView dotsView;

    private WealthViewPageAdapter adapter;

    private FragmentManager manager;

    private List<Fragment> fragments = new ArrayList<>();

    private Country country;



    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_wealth, container, false);
    }

    @Override
    protected void initListener() {
        viewPager = rootView.findViewById(R.id.wealth_viewPager);
        manager = getChildFragmentManager();
        adapter = new WealthViewPageAdapter(manager);
        fragments.clear();
        dayFragment = DayFragment.getInstance("d1");
        weekFragment = WeekFragment.getInstance("w1");
        citymanage = ChooseCityFragment.getInstance();
        fragments.add(dayFragment);
        fragments.add(weekFragment);
        fragments.add(citymanage);
        dotsView = rootView.findViewById(R.id.wealth_dotsview);
        dotsView.setDotRessource(R.drawable.dot_selected, R.drawable.dot_unselected);
        dotsView.setNumberOfPage(fragments.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                dotsView.selectDot(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        adapter.setFragments(fragments);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        //注册广播接收
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Country country = (Country)intent.getSerializableExtra("country");
                Log.d(TAG, "onReceive: "+"处理广播");
                dayFragment.changeUI(country);
                weekFragment.changeUI(country);

            }
        };
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ChooseCityFragment.CITYSELECTED);
        broadcastManager.registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void loadData() {

    }


    @Override
    public void onDestroy() {
        broadcastManager.unregisterReceiver(receiver);
        super.onDestroy();
    }
}
