package com.example.androidmvp.mvp.show.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.example.androidmvp.R;
import com.example.androidmvp.mvp.base.BaseFragment;
import com.example.androidmvp.mvp.entity.show.ImageResult;
import com.example.androidmvp.mvp.entity.db.ShowPage;
import com.example.androidmvp.mvp.entity.UserResult;
import com.example.androidmvp.mvp.show.adapter.ReCyclerAdapter;
import com.example.androidmvp.mvp.show.presenter.ShowPresenter;
import com.example.androidmvp.mvp.show.view.BaseShowView;
import com.jaeger.ninegridimageview.NineGridImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowFragment extends BaseFragment implements BaseShowView {

    private static final String TAG = "ShowFragment";
    private String tag;
    private static ShowFragment instance = null;
    private ShowPresenter presenter;

//    @BindView(R.id.showimages)
    NineGridImageView<String> nineGridImageView;
//    @BindView(R.id.recycleview)
    RecyclerView recyclerView;

    PullRefreshLayout pullRefreshLayout;
    RecyclerView.LayoutManager layoutManager;
    ReCyclerAdapter adapter;


    public static ShowFragment getInstance(String tag) {
        if (instance == null) {
            instance = new ShowFragment();
            Bundle bundle = new Bundle();
            bundle.putString("tag", tag);
            instance.setArguments(bundle);
        }
        return instance;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
//        if (savedInstanceState == null) {
//            Bundle bundle = getArguments();
//            tag = bundle.getString("tag");
//            Log.d(TAG, "initData: " + tag);
//        }
        presenter = new ShowPresenter(this);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_show, container, false);
    }

    @Override
    protected void initListener() {
        nineGridImageView = rootView.findViewById(R.id.showimages);
        recyclerView = rootView.findViewById(R.id.recycleview);
        pullRefreshLayout = rootView.findViewById(R.id.swipeRefreshlayout);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new ReCyclerAdapter(getContext(),new ArrayList<ShowPage>());
        //添加子item点击事件
        adapter.setOnItemClickListener(new ReCyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, List<ShowPage> showPageResults, int position) {
//                Log.d(TAG, "onItemClick: "+position+ showPageResults.get(position).getAutor().getUsername());
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新

                loadData();
                //数据刷新完成
                pullRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void loadData() {

        //加载显示数据
//        test();
    }

    void test(){
        List<ShowPage> data = new ArrayList<>();
        String icon = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2275306855,4098056377&fm=27&gp=0.jpg";
        List<ImageResult> imageResults = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            ImageResult imageResult = new ImageResult();
//            imageResult.setId(i);
//            imageResult.setUrl(Uri.parse(icon));
            imageResults.add(imageResult);
        }

        ShowPage page = new ShowPage();
        UserResult userResult = new UserResult();
        userResult.setUsername("消耗发");
        userResult.setIcon(icon);
//        page.setAutor(userResult);
//        page.setImages(imageResults);
        for (int i = 0; i < 11; i++)
            data.add(page);

        adapter.setShowPageResults(data);
        adapter.notifyDataSetChanged();
    }
}
