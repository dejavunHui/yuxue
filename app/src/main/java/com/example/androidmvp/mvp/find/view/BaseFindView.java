package com.example.androidmvp.mvp.find.view;

import com.example.androidmvp.mvp.entity.FindPageResult;

public interface BaseFindView {

    public void showMessage(String message);
    public void loadPage(FindPageResult pageResult);
}
