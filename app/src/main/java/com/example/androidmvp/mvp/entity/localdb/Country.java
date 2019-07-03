package com.example.androidmvp.mvp.entity.localdb;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class Country extends DataSupport implements Serializable {
    private int id;
    @SerializedName("name")
    private String countryName;
    @SerializedName("weather_id")
    private String weatherId;

    private int cityId;

    public Country(int id, String countryName, String weatherId, int cityId) {
        this.id = id;
        this.countryName = countryName;
        this.weatherId = weatherId;
        this.cityId = cityId;
    }

    public Country() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
