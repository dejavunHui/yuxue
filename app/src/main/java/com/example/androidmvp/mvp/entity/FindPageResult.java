package com.example.androidmvp.mvp.entity;

import com.google.gson.annotations.SerializedName;

public class FindPageResult{

    @SerializedName("data")
    public Data data;

    public class Data{
        @SerializedName("author")
        public String author;

        @SerializedName("title")
        public String title;

        @SerializedName("content")
        public String content;

    }

}
