package com.example.androidmvp.mvp.residemenu.view;

import com.example.androidmvp.mvp.entity.db.ShowPage;

import java.util.List;

public interface BaseUserShowView extends BaseResideView{
    public void loadShowpages(List<ShowPage> data);
}
