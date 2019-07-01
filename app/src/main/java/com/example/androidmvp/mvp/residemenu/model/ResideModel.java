package com.example.androidmvp.mvp.residemenu.model;

import com.example.androidmvp.data.httpdata.HttpData;
import com.example.androidmvp.mvp.base.OnLoadDataListener;
import com.example.androidmvp.mvp.entity.UserResult;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ResideModel {

    public void changeInfo(final OnLoadDataListener<UserResult> listener,UserResult userResult){
        HttpData.getInstance().changeInfo(new Observer<UserResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UserResult value) {
                listener.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }

            @Override
            public void onComplete() {

            }
        }, userResult);
    }
}
