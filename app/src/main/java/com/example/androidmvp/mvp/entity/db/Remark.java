package com.example.androidmvp.mvp.entity.db;

import com.example.androidmvp.mvp.entity.show.ImageResult;
import com.example.androidmvp.mvp.entity.UserResult;

import java.util.List;

public class Remark {

    int id;
    String from;
    String to;
    String content;
    List<String> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
