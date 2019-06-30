package com.example.androidmvp.mvp.show.presenter;

import com.example.androidmvp.mvp.show.fragment.ShowFragment;
import com.example.androidmvp.mvp.show.model.ShowModel;
import com.example.androidmvp.mvp.show.view.BaseShowView;

public class ShowPresenter {
    private BaseShowView view;
    private ShowModel model;

    public ShowPresenter(BaseShowView view) {
        this.view = view;
        model = new ShowModel();
    }
}
