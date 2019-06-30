package com.example.androidmvp.mvp.entity.base;

import com.google.gson.annotations.SerializedName;

public class Now {
    @SerializedName("tmp")
    public String temperature;

    @SerializedName("wind_dir")
    public String wind;

    @SerializedName("wind_spd")
    public String windSpeed;

    @SerializedName("cond")
    public More more;



    public class More{

        @SerializedName("txt")
        public String info;
    }
}
