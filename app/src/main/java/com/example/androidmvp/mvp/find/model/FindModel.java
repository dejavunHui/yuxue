package com.example.androidmvp.mvp.find.model;

import com.example.androidmvp.data.httpdata.HttpData;
import com.example.androidmvp.mvp.base.OnLoadDataListener;
import com.example.androidmvp.mvp.entity.FindPageResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class FindModel {

    /**
     * 获取今天以及前十天的文章
     * @param listener
     */
    public void getDayPage(final OnLoadDataListener<FindPageResult> listener){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int moth = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = ""+year;
        date = date + (moth >= 10 ? moth:"0"+moth);
        date = date + (day >= 10 ? date:"0"+day);
        HttpData.getInstance().getDayPage(new Observer<FindPageResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(FindPageResult value) {
                listener.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }

            @Override
            public void onComplete() {

            }
        }, date);
    }
}
