package com.example.androidmvp.mvp.find.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidmvp.R;
import com.example.androidmvp.mvp.base.BaseFragment;
import com.example.androidmvp.mvp.entity.FindPageResult;
import com.example.androidmvp.mvp.find.presenter.FindPresenter;
import com.example.androidmvp.mvp.find.view.BaseFindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends BaseFragment implements BaseFindView {

    private String tag;
    private static final String TAG = "FindFragment";
    private FindPresenter presenter;

    TextView title;
    TextView content;
    TextView author;
    SwipeRefreshLayout swipeRefreshLayout;

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
        title = rootView.findViewById(R.id.find_page_title);
        content = rootView.findViewById(R.id.find_page_content);
        author = rootView.findViewById(R.id.find_page_author);
        swipeRefreshLayout = rootView.findViewById(R.id.find_page_swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadPage();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void loadData() {
        presenter.loadPage();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadPage(FindPageResult pageResult) {
        title.setText(pageResult.data.title);
        author.setText(pageResult.data.author);
        content.setText("        "+Html.fromHtml(pageResult.data.content));
    }
}
