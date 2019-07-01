package com.example.androidmvp.mvp.show.presenter;

import com.example.androidmvp.mvp.base.OnLoadDataListener;
import com.example.androidmvp.mvp.entity.UserResult;
import com.example.androidmvp.mvp.entity.db.ShowPage;
import com.example.androidmvp.mvp.show.fragment.ShowFragment;
import com.example.androidmvp.mvp.show.model.ShowModel;
import com.example.androidmvp.mvp.show.view.BaseShowView;

import java.util.List;

public class ShowPresenter {
    private BaseShowView view;
    private ShowModel model;

    public ShowPresenter(BaseShowView view) {
        this.view = view;
        model = new ShowModel();
    }

    public void loadShowPages(){
        model.loadShowpages(new OnLoadDataListener<List<ShowPage>>() {
            @Override
            public void onSuccess(List<ShowPage> data) {
                view.loadShowpages(data);
            }

            @Override
            public void onError(Throwable e) {
                view.showMessage(e.getMessage());
            }
        });
    }

}
