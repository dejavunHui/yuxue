package com.example.androidmvp.mvp.entity.base;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Wealth {
    public String status;
    public Basic basic;
    public Aqi aqi;
    public Now now;

    @SerializedName("daily_forecast")
    public List<ForeCast> foreCasts;

}
