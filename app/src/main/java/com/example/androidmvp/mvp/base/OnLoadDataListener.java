package com.example.androidmvp.mvp.base;

public interface OnLoadDataListener<T> {

    public void onSuccess(T data);
    public void onError(Throwable e);
}
