package com.example.androidmvp.mvp.residemenu.presenter;

import com.example.androidmvp.mvp.base.OnLoadDataListener;
import com.example.androidmvp.mvp.entity.UserResult;
import com.example.androidmvp.mvp.residemenu.model.ResideModel;
import com.example.androidmvp.mvp.residemenu.view.BaseResideView;

public class ResidePresenter {
    BaseResideView view;
    ResideModel model;

    public ResidePresenter(BaseResideView view) {
        this.view = view;
        model = new ResideModel();
    }

    public void changeInfo(UserResult userResult){

        model.changeInfo(new OnLoadDataListener<UserResult>() {
            @Override
            public void onSuccess(UserResult data) {
                view.showMessage("更新成功");
            }

            @Override
            public void onError(Throwable e) {
                view.showMessage(e.getMessage());
            }
        }, userResult);
    }
}
