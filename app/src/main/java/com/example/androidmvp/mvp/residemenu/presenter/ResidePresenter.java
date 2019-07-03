package com.example.androidmvp.mvp.residemenu.presenter;

import android.util.Log;

import com.example.androidmvp.mvp.base.OnLoadDataListener;
import com.example.androidmvp.mvp.entity.UserResult;
import com.example.androidmvp.mvp.entity.db.ShowPage;
import com.example.androidmvp.mvp.entity.show.ShowPageResult;
import com.example.androidmvp.mvp.login.model.Model;
import com.example.androidmvp.mvp.residemenu.activity.UserInfoActivity;
import com.example.androidmvp.mvp.residemenu.model.ResideModel;
import com.example.androidmvp.mvp.residemenu.view.BaseResideView;
import com.example.androidmvp.mvp.residemenu.view.BaseUserShowView;

import java.util.List;

public class ResidePresenter {
    BaseResideView view;
    ResideModel model;
    private static final String TAG = "ResidePresenter";

    public ResidePresenter(BaseResideView view) {
        this.view = view;
        model = new ResideModel();
    }

    public void changeInfo(final UserResult userResult){

        model.changeInfo(new OnLoadDataListener<UserResult>() {
            @Override
            public void onSuccess(UserResult data) {

                new Model().login(new OnLoadDataListener<UserResult>() {
                    @Override
                    public void onSuccess(UserResult data) {
                        view.showMessage("更新成功");
                        view.setUser(data);
                        ((UserInfoActivity)view).sendBroadCast();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }, userResult.getUsername(), userResult.getPassword());
            }

            @Override
            public void onError(Throwable e) {
                view.showMessage(e.getMessage());
            }
        }, userResult);

    }

    public void loadOnesShowPages(String user){
        model.loadOnesShowpage(new OnLoadDataListener<List<ShowPage>>() {
            @Override
            public void onSuccess(List<ShowPage> data) {
                ((BaseUserShowView)view).loadShowpages(data);
            }

            @Override
            public void onError(Throwable e) {
                view.showMessage(e.getMessage());
            }
        }, user);
    }

    public void deleteShowpage(String pageid){
        model.deleteShowpage(new OnLoadDataListener<ShowPageResult>() {
            @Override
            public void onSuccess(ShowPageResult data) {
                view.showMessage("删除成功");
            }

            @Override
            public void onError(Throwable e) {
                view.showMessage("删除成功");
            }
        }, pageid);
    }
}
