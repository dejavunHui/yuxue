package com.example.androidmvp.mvp.login.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.androidmvp.common.Constant;
import com.example.androidmvp.mvp.base.OnLoadDataListener;
import com.example.androidmvp.mvp.entity.UserResult;
import com.example.androidmvp.mvp.login.model.Model;
import com.example.androidmvp.mvp.login.view.BaseLoginView;

import static android.support.constraint.Constraints.TAG;

public class LoginPresenter implements OnLoadDataListener<UserResult> {

    private BaseLoginView view;
    private Model model;

    public LoginPresenter(BaseLoginView view) {
        this.view = view;
        model = new Model();
    }

    public void login(String username, String password) {
        model.login(this, username, password);
    }

    @Override
    public void onSuccess(UserResult data) {
        String username = data.getUsername();
        if (username != null) {
            view.login(Constant.Types.SUCCESS);
        } else {
            view.login(Constant.Types.FAIL);
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError: "+"出错了");
        view.login(Constant.Types.FAIL);
    }

}
