package com.example.androidmvp.mvp.login.view;

import com.example.androidmvp.mvp.entity.UserResult;

public interface BaseLoginView {

    public void login(int type);
    public void setUser(UserResult user);
}
