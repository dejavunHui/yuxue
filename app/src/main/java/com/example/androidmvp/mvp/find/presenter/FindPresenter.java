package com.example.androidmvp.mvp.find.presenter;

import com.example.androidmvp.mvp.base.OnLoadDataListener;
import com.example.androidmvp.mvp.entity.FindPageResult;
import com.example.androidmvp.mvp.find.model.FindModel;
import com.example.androidmvp.mvp.find.view.BaseFindView;

public class FindPresenter {

    private BaseFindView view;
    private FindModel model;

    public FindPresenter(BaseFindView view) {
        this.view = view;
        model = new FindModel();
    }

    public void loadPage(){
        model.getDayPage(new OnLoadDataListener<FindPageResult>() {
            @Override
            public void onSuccess(FindPageResult data) {
                view.loadPage(data);
            }

            @Override
            public void onError(Throwable e) {
                view.showMessage(e.getMessage());
            }
        });
    }
}
