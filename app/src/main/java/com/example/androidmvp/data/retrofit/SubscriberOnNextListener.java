package com.example.androidmvp.data.retrofit;

public interface SubscriberOnNextListener<T> {
    void onNext(T t);
    void onError(Throwable e);
    void onCompleted();
}
