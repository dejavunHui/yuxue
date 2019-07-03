package com.example.androidmvp.mvp.residemenu.model;

import android.text.Html;

import com.example.androidmvp.data.httpdata.HttpData;
import com.example.androidmvp.mvp.base.OnLoadDataListener;
import com.example.androidmvp.mvp.entity.UserResult;
import com.example.androidmvp.mvp.entity.db.ShowPage;
import com.example.androidmvp.mvp.entity.show.ShowPageResult;

import java.util.List;

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

    public void loadOnesShowpage(final OnLoadDataListener<List<ShowPage>> listener,String user){
        HttpData.getInstance().getOnesShowpages(new Observer<List<ShowPage>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<ShowPage> value) {
                listener.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }

            @Override
            public void onComplete() {

            }
        }, user);
    }

    public void deleteShowpage(final OnLoadDataListener<ShowPageResult> listener, String pageid){
        HttpData.getInstance().deleteShowpage(new Observer<ShowPageResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ShowPageResult value) {
                listener.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }

            @Override
            public void onComplete() {

            }
        }, pageid);
    }
}
