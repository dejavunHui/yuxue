package com.example.androidmvp.mvp.entity.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WealthResultReal {

    public String message;

    @SerializedName("data")
    public Data data;

    public static class Data {
        @SerializedName("wendu")
        public String wendu;

        @SerializedName("forecast")
        public List<ForeCast> foreCasts;

    }
    public static class ForeCast {
        @SerializedName("high")
        public String highTemperature;

        @SerializedName("low")
        public String lowTemp;
        @SerializedName("fx")
        public String fx;
        @SerializedName("fl")
        public String fl;
        @SerializedName("type")
        public String weather;
        @SerializedName("week")
        public String week;

        @SerializedName("ymd")
        public String date;
    }


}
