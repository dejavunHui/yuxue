package com.example.androidmvp.mvp.wealth.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidmvp.R;
import com.example.androidmvp.mvp.base.BaseFragment;
import com.example.androidmvp.mvp.entity.weather.WealthResultReal;
import com.example.androidmvp.mvp.entity.localdb.Country;
import com.example.androidmvp.mvp.wealth.adapter.CityWealthAdapter;
import com.example.androidmvp.mvp.wealth.presenter.WealthPresenter;
import com.example.androidmvp.mvp.wealth.service.AutoUpdateService;
import com.example.androidmvp.mvp.wealth.view.BaseWealthView;
import com.example.androidmvp.util.TimebarUtil;
import com.example.androidmvp.widget.DayWeatherView;
import com.example.androidmvp.widget.SegmentedControlItem;
import com.example.androidmvp.widget.SegmentedControlView;
import com.example.androidmvp.widget.WeekWeatherView;

import java.util.ArrayList;
import java.util.List;

public class WeekFragment extends BaseFragment implements BaseWealthView {
    private static final String TAG = "WeekFragment";
    public static int flag = 0;
    private String tag;
    private Country country;
    private WealthPresenter presenter;
    private static WeekFragment instance = null;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    TimebarUtil timebarUtil;
    private RecyclerView weekWealth;
    private CityWealthAdapter adapter;
    private RecyclerView.LayoutManager manager;

    private TextView title;
    private TextView firstPeom;
    private TextView secondPeom;
    private SwipeRefreshLayout swipeRefreshLayout;
    private WeekWeatherView weekWeatherView;
    private SegmentedControlView segmentedControlView;
    private DayWeatherView dayWeatherView;

    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver receiver;


    //单例模式，weekwealth只有一个
    public static WeekFragment getInstance(String tag) {
        if (instance == null) {
            instance = new WeekFragment();
            Bundle bundle = new Bundle();
            bundle.putString("tag", tag);
            instance.setArguments(bundle);
        }
        return instance;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
//        if (savedInstanceState == null) {
//            Bundle bundle = getArguments();
//            tag = bundle.getString("tag");
//            Log.d(TAG, "initData: " + tag);
//        }
        presenter = new WealthPresenter(this);
        country = new Country(1, "尖草坪区", "101100106", 85);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_week, container, false);
    }

    @Override
    protected void initListener() {
        weekWealth = rootView.findViewById(R.id.week_wealth);
        manager = new LinearLayoutManager(getContext());
        adapter = new CityWealthAdapter(getContext(), new ArrayList<WealthResultReal.ForeCast>());
        weekWealth.setLayoutManager(manager);
        weekWealth.setAdapter(adapter);
        swipeRefreshLayout = rootView.findViewById(R.id.xiala);
        firstPeom = rootView.findViewById(R.id.lock_week_peom_1);
        secondPeom = rootView.findViewById(R.id.lock_week_peom_2);
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();
        title = rootView.findViewById(R.id.lock_week_day_city);
        weekWeatherView = rootView.findViewById(R.id.chart_week);
        segmentedControlView = rootView.findViewById(R.id.select);
        List<SegmentedControlItem> items = new ArrayList<>();
        items.add(new SegmentedControlItem("当前"));
        items.add(new SegmentedControlItem("本周"));
        items.add(new SegmentedControlItem("预览"));
        segmentedControlView.addItems(items);
        dayWeatherView = rootView.findViewById(R.id.day_weather);

        segmentedControlView.setOnSegItemClickListener(new SegmentedControlView.OnSegItemClickListener() {
            @Override
            public void onItemClick(SegmentedControlItem item, int position) {
                loadChart(position);
            }
        });


        firstPeom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadPeom();
            }
        });
        secondPeom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadPeom();
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                changeUI(country);
                swipeRefreshLayout.setRefreshing(false);
                showMessage("更新成功");
            }
        });

        //注册广播接收
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String signal = intent.getStringExtra("signal");
                if (signal == "update")
                    changeUI(country);
            }
        };
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AutoUpdateService.UPDATEACTION);
        broadcastManager.registerReceiver(receiver, intentFilter);


        timebarUtil = new TimebarUtil(rootView);
        timebarUtil.startClock();
    }

    @Override
    protected void loadData() {
        String city = preferences.getString("cityname", "error");
        String weather_id = preferences.getString("weatherId", "error");
        int id = preferences.getInt("id", -1);
        int cityid = preferences.getInt("cityid", -1);
        if (city != "error") {
            Country country = new Country();
            country.setCityId(id);
            country.setCountryName(city);
            country.setCityId(cityid);
            country.setWeatherId(weather_id);
            changeUI(country);
        }
    }

    public void changeUI(Country country) {
        this.country = country;
        Log.d(TAG, "changeUI: " + country.getCountryName());
        presenter.loadRealData(country);
        presenter.loadPeom();

    }


    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void newDatas1(WealthResultReal data) {

        int[] day = new int[15];
        int[] night = new int[15];

        for (int i = 0; i < 15; i++) {
            day[i] = Integer.parseInt(data.data.foreCasts.get(i).highTemperature.substring(3, data.data.foreCasts.get(i).highTemperature.length() - 3));
            night[i] = Integer.parseInt(data.data.foreCasts.get(i).lowTemp.substring(3, data.data.foreCasts.get(i).lowTemp.length() - 3));
        }

        if (flag % 10 == 0) {
            int[] temp = new int[30];
            int[] water = new int[30];

            int h = Integer.parseInt(data.data.foreCasts.get(0).highTemperature.substring(3, data.data.foreCasts.get(0).highTemperature.length() - 3));
            int l = Integer.parseInt(data.data.foreCasts.get(0).lowTemp.substring(3, data.data.foreCasts.get(0).lowTemp.length() - 3));

            for (int i = 0; i < 30; i++) {
                int a = (int) (Math.random() * h);
                temp[i] = a < l ? l : a;
                int b = (int) (Math.random() * 5);
                water[i] = a = b + (int) (Math.random() * 10);
            }

            dayWeatherView.setData(temp, water);
            flag = 0;
        }
        flag ++;

        weekWeatherView.setTempDay(day);
        weekWeatherView.setTempNight(night);
        weekWeatherView.invalidate();

        title.setText(country.getCountryName());
        List<WealthResultReal.ForeCast> foreCasts = data.data.foreCasts;
        adapter.setDatas(foreCasts);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setPeom(String f, String l) {
        firstPeom.setText(f);
        secondPeom.setText(l);
    }

    @Override
    public void newBackground(String url) {

    }


    public void loadChart(int position) {
        if (position == 0) {
            //当前
            dayWeatherView.setVisibility(View.VISIBLE);
            weekWealth.setVisibility(View.INVISIBLE);
            weekWeatherView.setVisibility(View.INVISIBLE);

        } else if (position == 1) {
            //本周
            dayWeatherView.setVisibility(View.INVISIBLE);
            weekWealth.setVisibility(View.INVISIBLE);
            weekWeatherView.setVisibility(View.VISIBLE);

        } else if (position == 2) {
            //下周

            dayWeatherView.setVisibility(View.INVISIBLE);
            weekWealth.setVisibility(View.VISIBLE);
            weekWeatherView.setVisibility(View.INVISIBLE);
        }
    }
}
