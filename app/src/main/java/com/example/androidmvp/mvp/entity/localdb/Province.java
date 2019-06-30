package com.example.androidmvp.mvp.entity.localdb;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

public class Province extends DataSupport {
    private int id;
    @SerializedName("name")
    private String provinceName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

}
