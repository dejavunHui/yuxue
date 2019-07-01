package com.example.androidmvp.mvp.show.view;

import com.example.androidmvp.mvp.entity.UserResult;
import com.example.androidmvp.mvp.entity.db.ShowPage;

import java.util.List;

public interface BaseShowView {
    public  void showMessage(String message);

    public void loadShowpages(List<ShowPage> data);

}
