package com.example.androidmvp.mvp.entity.localdb;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

public class Token extends DataSupport {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private String data;
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setData(String data) {
        this.data = data;
    }
    public String getData() {
        return data;
    }

}
