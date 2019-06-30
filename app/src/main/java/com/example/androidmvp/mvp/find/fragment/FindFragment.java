package com.example.androidmvp.mvp.find.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidmvp.R;
import com.example.androidmvp.mvp.base.BaseFragment;
import com.example.androidmvp.mvp.find.presenter.FindPresenter;
import com.example.androidmvp.mvp.find.view.BaseFindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends BaseFragment implements BaseFindView {

    private String tag;
    private static final String TAG = "FindFragment";
    private FindPresenter presenter;


    @Override
    protected void initData(Bundle savedInstanceState) {
//        if (savedInstanceState == null) {
//            Bundle bundle = getArguments();
//            tag = bundle.getString("tag");
//            Log.d(TAG, "initData: " + tag);
//        }
        presenter = new FindPresenter(this);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_find, container, false);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {

    }
}
