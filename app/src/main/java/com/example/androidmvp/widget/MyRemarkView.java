package com.example.androidmvp.widget;

import android.content.Context;
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
import com.example.androidmvp.mvp.entity.db.Remark;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

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
