package com.example.androidmvp.mvp.entity.localdb;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

public class City extends DataSupport {
    private int id;
    @SerializedName("name")
    private String cityName;
    private int provinceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
