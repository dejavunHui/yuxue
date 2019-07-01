package com.example.androidmvp.mvp.show.model;

import com.example.androidmvp.data.httpdata.HttpData;
import com.example.androidmvp.mvp.base.OnLoadDataListener;
import com.example.androidmvp.mvp.entity.UserResult;
import com.example.androidmvp.mvp.entity.db.ShowPage;
import com.example.androidmvp.mvp.entity.show.ImageResult;
import com.example.androidmvp.mvp.entity.show.RemarkResult;
import com.example.androidmvp.mvp.entity.show.ShowPageResult;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ShowModel {
    public void loadShowpages(final OnLoadDataListener<List<ShowPage>> listener) {
        HttpData.getInstance().getShowpages(new Observer<List<ShowPage>>() {
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
        });
    }

    public void loadHeaderInfo(final OnLoadDataListener<UserResult> listener, String user) {
        HttpData.getInstance().getLoginInfo(new Observer<UserResult>() {
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
        }, user);
    }

    public void upImage(final OnLoadDataListener<ImageResult> listener, String path, String showPage, String remark) {
        HttpData.getInstance().upImage(new Observer<ImageResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ImageResult value) {
                listener.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }

            @Override
            public void onComplete() {

            }
        }, showPage, remark, path);
    }

    public void upShowpage(final OnLoadDataListener<ShowPageResult> listener, String title, String autor, String content) {
        HttpData.getInstance().upShowPage(new Observer<ShowPageResult>() {
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
        }, title, autor, content);
    }

    public void upRemark(final OnLoadDataListener<RemarkResult> listener,String from,String to,String showpage,String content){
        HttpData.getInstance().upRemark(new Observer<RemarkResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(RemarkResult value) {
                listener.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }

            @Override
            public void onComplete() {

            }
        }, from, to, showpage, content);
    }
}
