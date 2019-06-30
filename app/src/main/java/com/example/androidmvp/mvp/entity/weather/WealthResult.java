/**
  * Copyright 2019 bejson.com 
  */
package com.example.androidmvp.mvp.entity.weather;

import com.example.androidmvp.mvp.entity.base.Aqi;
import com.example.androidmvp.mvp.entity.base.Basic;
import com.example.androidmvp.mvp.entity.base.ForeCast;
import com.example.androidmvp.mvp.entity.base.Now;
import com.example.androidmvp.mvp.entity.base.Wealth;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Auto-generated: 2019-06-28 10:23:7
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class WealthResult {

    @SerializedName("HeWeather")
    public List<Wealth> wealths;
}