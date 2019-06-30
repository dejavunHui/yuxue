package com.example.androidmvp.mvp.wealth.model;

public interface OnLoadCityListener<T> {
    public void onSuccess(T data);
    public void onError(Throwable e);
}
