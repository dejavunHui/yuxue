package com.example.androidmvp.mvp.wealth.view;

import com.example.androidmvp.mvp.entity.weather.WealthResultReal;

public interface BaseWealthView {
    public void newDatas1(WealthResultReal data);
    public void setPeom(String f,String l);
    public void newBackground(String url);

    public void showMessage(String message);
}
