/**
  * Copyright 2019 bejson.com 
  */
package com.example.androidmvp.mvp.entity.base;

import com.google.gson.annotations.SerializedName;

/**
 * Auto-generated: 2019-06-28 10:23:7
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Basic {
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update{

        @SerializedName("loc")
        public String updateTime;
    }

}