package com.example.androidmvp.widget;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidmvp.R;
import com.example.androidmvp.mvp.entity.db.Remark;

public class MyRemarkView  extends RelativeLayout {

    TextView from;
    TextView to;
    TextView dao;
    TextView content;

    private Remark remark;

    public MyRemarkView(Context context,Remark remark) {
        super(context);

        View inflate = inflate(getContext(), R.layout.remark_item,this);
        from = inflate.findViewById(R.id.remark_from);
        dao = inflate.findViewById(R.id.dao);
        to = inflate.findViewById(R.id.remark_to);
        content = inflate.findViewById(R.id.remark_content);
        from.setText(remark.getFrom());
        content.setText(remark.getContent());
        
    }

    public MyRemarkView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRemarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setListener(OnClickListener listener){
        if(listener != null) {
            from.setOnClickListener(listener);
            to.setOnClickListener(listener);
            content.setOnClickListener(listener);
        }
    }
}
