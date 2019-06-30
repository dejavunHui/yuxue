package com.example.androidmvp.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    public View rootView;

    private Unbinder binder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
    }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(rootView == null) {
            rootView = initView(inflater, container);
            initListener();
        }
//        binder = ButterKnife.bind(this, rootView);//绑定rootview和当前
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        binder.unbind();
    }

    //加载数据
    protected abstract void initData(Bundle savedInstanceState);

    protected abstract View initView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initListener();

    protected abstract void loadData();

}
