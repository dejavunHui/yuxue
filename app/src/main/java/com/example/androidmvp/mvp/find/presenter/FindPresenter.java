package com.example.androidmvp.mvp.find.presenter;

import com.example.androidmvp.mvp.find.model.FindModel;
import com.example.androidmvp.mvp.find.view.BaseFindView;

public class FindPresenter {

    private BaseFindView view;
    private FindModel model;

    public FindPresenter(BaseFindView view) {
        this.view = view;
        model = new FindModel();
    }
}
