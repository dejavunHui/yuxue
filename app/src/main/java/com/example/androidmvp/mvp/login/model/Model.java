package com.example.androidmvp.mvp.login.model;

import com.example.androidmvp.data.httpdata.HttpData;
import com.example.androidmvp.mvp.base.OnLoadDataListener;
import com.example.androidmvp.mvp.entity.DataResults;
import com.example.androidmvp.mvp.entity.UserResult;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MultipartBody;

public class Model {


    /**
     * 登录
     * @param loadDataListener
     * @param username
     * @param password
     */
    public void login(final OnLoadDataListener<UserResult> loadDataListener, String username, String password){

        HttpData.getInstance().login(new Observer<UserResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UserResult value) {
                loadDataListener.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                loadDataListener.onError(e);
            }

            @Override
            public void onComplete() {

            }
        }, username, password);
    }


    /**
     * 注册
     * @param loadDataListener
     * @param multipartBody
     */
    public void register(final OnLoadDataListener<UserResult> loadDataListener, MultipartBody multipartBody){
        HttpData.getInstance().register(new Observer<UserResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UserResult value) {
                loadDataListener.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                loadDataListener.onError(e);
            }

            @Override
            public void onComplete() {

            }
        }, multipartBody);
    }


}
