package com.example.androidmvp.mvp.login.presenter;

import android.util.Log;

import com.example.androidmvp.common.Constant;
import com.example.androidmvp.data.httpdata.HttpData;
import com.example.androidmvp.mvp.base.OnLoadDataListener;
import com.example.androidmvp.mvp.entity.UserResult;
import com.example.androidmvp.mvp.login.model.Model;
import com.example.androidmvp.mvp.login.view.BaseRegisterView;

import okhttp3.MultipartBody;

import static android.support.constraint.Constraints.TAG;

public class RegisterPresenter implements OnLoadDataListener<UserResult> {

    private Model model;
    private BaseRegisterView view;

    public RegisterPresenter(BaseRegisterView view) {
        this.view = view;
        model = new Model();
    }

    public void register(MultipartBody multipartBody) {
        model.register(this, multipartBody);
    }

    @Override
    public void onSuccess(UserResult data) {
        String username = data.getUsername();
        if (username  == null){
            view.register_login(Constant.Types.FAIL,"用户名或邮箱已存在");
        }else {
            view.clear();
            view.register_login(Constant.Types.SUCCESS,null);

        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError: "+e.getMessage());
        view.register_login(Constant.Types.FAIL,"出错：用户名或邮箱已存在");
    }
}
