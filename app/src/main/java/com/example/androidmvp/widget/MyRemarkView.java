package com.example.androidmvp.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.androidmvp.R;
import com.example.androidmvp.common.Constant;
import com.example.androidmvp.mvp.entity.ThumbViewInfo;
import com.example.androidmvp.mvp.entity.db.Remark;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.previewlibrary.GPreviewBuilder;

import java.util.ArrayList;
import java.util.List;

public class MyRemarkView extends FrameLayout {

    private static final String TAG = "MyRemarkView";
    TextView from;
    TextView to;
    TextView dao;
    TextView content;
    View inflate;
    NineGridImageView<String> imageView;
    NineGridImageViewAdapter<String> adapter;
    private Remark remark;
    LinearLayout layout;


    public interface ClickListener {
        public void onClickItem(View view,Remark mark);
    }

    public MyRemarkView(Context context, final Remark remark) {
        super(context);
        this.remark = remark;
        inflate = inflate(context, R.layout.remark_item, this);
        layout = inflate.findViewById(R.id.remark_item_layout);
        from = inflate.findViewById(R.id.remark_from);
        dao = inflate.findViewById(R.id.dao);
        to = inflate.findViewById(R.id.remark_to);
        content = inflate.findViewById(R.id.remark_content);
        imageView = inflate.findViewById(R.id.remark_images);
        final ArrayList<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>();
        from.setText(remark.getFrom());
        content.setText(remark.getContent());
        if (remark.getTo() != null) {
            dao.setText("  回复  ");
            to.setText(remark.getTo() + " :");
        }
        else {
            dao.setText("");
            to.setText(" : ");
        }
        if (remark.getImages().size() > 0) {

            imageView.setVisibility(View.VISIBLE);
        }

        adapter = new NineGridImageViewAdapter<String>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String s) {
                Glide.with(context).load(Constant.Urls.IMAGEURLROOT + s).into(imageView);
            }

            @Override
            protected void onItemImageClick(Context context, int index, List<String> list) {
                super.onItemImageClick(context, index, list);

                ThumbViewInfo item;
                mThumbViewInfoList.clear();
                for (int i = 0;i < list.size(); i++) {
                    Rect bounds = new Rect();
                    //new ThumbViewInfo(图片地址);
                    item=new ThumbViewInfo(Constant.Urls.IMAGEURLROOT+list.get(i));
                    item.setBounds(bounds);
                    mThumbViewInfoList.add(item);
                }
                GPreviewBuilder.from((Activity)context)
//                            .to(xxActivity.class)//使用自定义界面
                        .setData(mThumbViewInfoList)
                        .setCurrentIndex(index)
                        .setSingleFling(true)
                        .setType(GPreviewBuilder.IndicatorType.Dot)
                        .start();
            }
        };


        imageView.setAdapter(adapter);
        imageView.setImagesData(remark.getImages());

    }

    public MyRemarkView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRemarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setListener(final ClickListener listener) {
        inflate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickItem(v,remark);
            }
        });
    }
}
