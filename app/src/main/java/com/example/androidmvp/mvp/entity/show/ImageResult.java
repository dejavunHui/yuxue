package com.example.androidmvp.mvp.entity.show;

import android.net.Uri;

import java.util.List;

public class ImageResult {

    protected int id;
    protected String showpage;
    protected String remark;
    protected String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShowpage() {
        return showpage;
    }

    public void setShowpage(String showpage) {
        this.showpage = showpage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
