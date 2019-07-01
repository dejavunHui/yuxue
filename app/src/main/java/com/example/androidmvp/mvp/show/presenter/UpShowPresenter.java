package com.example.androidmvp.mvp.show.presenter;

import com.example.androidmvp.mvp.base.OnLoadDataListener;
import com.example.androidmvp.mvp.entity.show.ImageResult;
import com.example.androidmvp.mvp.entity.show.RemarkResult;
import com.example.androidmvp.mvp.entity.show.ShowPageResult;
import com.example.androidmvp.mvp.show.model.ShowModel;
import com.example.androidmvp.mvp.show.view.BaseUpShowView;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.List;

public class UpShowPresenter {

    private ShowModel model;
    private BaseUpShowView view;

    public UpShowPresenter(BaseUpShowView view) {
        this.view = view;
        model = new ShowModel();
    }

    public void upShowpage(String autor, String title, String content) {
        model.upShowpage(new OnLoadDataListener<ShowPageResult>() {
            @Override
            public void onSuccess(ShowPageResult data) {
                view.upImage(String.valueOf(data.getId()), null);
            }

            @Override
            public void onError(Throwable e) {
                view.showMessage(e.getMessage());
            }
        }, title, autor, content);
    }

    public void upImage(String showpage, String remark, List<ImageItem> images) {

        if (images!=null && images.size() > 0)
            for (ImageItem item : images) {
                model.upImage(new OnLoadDataListener<ImageResult>() {
                    @Override
                    public void onSuccess(ImageResult data) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }, item.path, showpage, remark);
            }
    }

    public void upRemark(String from, String to, String page, String content) {
        model.upRemark(new OnLoadDataListener<RemarkResult>() {
            @Override
            public void onSuccess(RemarkResult data) {
                view.upImage(null, String.valueOf(data.getId())) ;
            }

            @Override
            public void onError(Throwable e) {
                view.showMessage(e.getMessage());
            }
        }, from, to, page, content);
    }


}
