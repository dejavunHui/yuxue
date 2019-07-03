package com.example.androidmvp.mvp.wealth.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.example.androidmvp.R;
import com.example.androidmvp.mvp.base.BaseFragment;
import com.example.androidmvp.mvp.entity.weather.WealthResultReal;
import com.example.androidmvp.mvp.entity.localdb.Country;
import com.example.androidmvp.mvp.wealth.presenter.WealthPresenter;
import com.example.androidmvp.mvp.wealth.service.AutoUpdateService;
import com.example.androidmvp.mvp.wealth.view.BaseWealthView;
import com.example.androidmvp.mvp.wealth.weatherutil.ConstantUtil;
import com.example.androidmvp.util.TimebarUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends BaseFragment implements BaseWealthView {

    private static final String TAG = "DayFragment";
    private String tag;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;

    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver receiver;

    private WealthPresenter presenter;
    private TimebarUtil timebarUtil;
    private RelativeLayout background;
    private Country country;
    private TextView title;
    private TextView time;//晨时
    private TextView timeInfo;//晨曦为光
    private TextView firstPeom;
    private TextView secondPeom;
    private TextView currentTmp;//0°
    private TextView wealth;
    private TextView wealthInfo;
    private TextView temperature;//2~5度
    private TextView temperatureHigh;
    private TextView wind;
    private TextView windSpeed;
    private SwipeRefreshLayout refreshLayout;//下拉缓存


    public static DayFragment getInstance(String tag) {
        DayFragment fragment = new DayFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
//        if (savedInstanceState == null) {
//            Bundle bundle = getArguments();
//            tag = bundle.getString("tag");
//            Log.d(TAG, "initData: " + tag);
//        }
        presenter = new WealthPresenter(this);
        country = new Country(1,"尖草坪区","101100106",85);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {

        return inflater.inflate(R.layout.fragment_day, container, false);
    }

    @Override
    protected void initListener() {

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
        background = rootView.findViewById(R.id.day_layout);
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();

        Drawable bk = getContext().getResources().getDrawable(R.drawable.qing);
        background.setBackground(bk);

        presenter = new WealthPresenter(this);
        title = rootView.findViewById(R.id.lock_day_city);
        time = rootView.findViewById(R.id.lock_day_time);
        timeInfo = rootView.findViewById(R.id.lock_day_time_describe);
        firstPeom = rootView.findViewById(R.id.lock_peom_first);
        secondPeom = rootView.findViewById(R.id.lock_peom_last);
        currentTmp = rootView.findViewById(R.id.lock_temperature);
        wealth = rootView.findViewById(R.id.lock_wealth);
        wealthInfo = rootView.findViewById(R.id.lock_wealth_info);
        temperature = rootView.findViewById(R.id.lock_temperature_info);
        wind = rootView.findViewById(R.id.lock_wind_direct);
        windSpeed = rootView.findViewById(R.id.lock_wind_speed);
        temperatureHigh = rootView.findViewById(R.id.lock_temperature_info_high);
        refreshLayout = rootView.findViewById(R.id.day_swiperefresh);

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
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                changeUI(country);
                showMessage("更新成功");
                refreshLayout.setRefreshing(false);
            }
        });
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


    //更新界面
    public void changeUI(Country country) {
        this.country = country;
        Log.d(TAG, "changeUI: " + country.getCountryName());
//        presenter.loadData(country);
        presenter.loadRealData(country);
        presenter.loadPeom();

        Drawable bk;
        if(wealth.getText().toString().endsWith("雪"))
            bk = getContext().getResources().getDrawable(R.mipmap.lock_xue);
        else
            bk = getContext().getResources().getDrawable(R.drawable.qing);
        background.setBackground(bk);

    }

    /**
     * 今日天气信息
     *
     * @param data
     */
    @Override
    public void newDatas1(WealthResultReal data) {
        time.setText(ConstantUtil.getTime());
        timeInfo.setText(ConstantUtil.getTimeInfo());
        title.setText(country.getCountryName());
        currentTmp.setText(data.data.wendu + "°");
        temperature.setText(data.data.foreCasts.get(0).lowTemp);
        temperatureHigh.setText(data.data.foreCasts.get(0).highTemperature);
        wealth.setText(data.data.foreCasts.get(0).weather);
        wealthInfo.setText(data.data.foreCasts.get(0).weather);
        wind.setText(data.data.foreCasts.get(0).fx);
        windSpeed.setText(data.data.foreCasts.get(0).fl);
    }

    @Override
    public void setPeom(String f, String l) {
        firstPeom.setText(f);
        secondPeom.setText(l);
    }

    public void newBackground(String url) {
        if (url != null) {
            Glide.with(this).load(url)
                    .into(new ViewTarget<View, GlideDrawable>(background) {
                        //括号里为需要加载的控件
                        @Override
                        public void onResourceReady(GlideDrawable resource,
                                                    GlideAnimation<? super GlideDrawable> glideAnimation) {
                            this.view.setBackground(resource.getCurrent());
                        }
                    });
        }

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
