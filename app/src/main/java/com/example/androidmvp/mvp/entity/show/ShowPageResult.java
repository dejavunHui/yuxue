package com.example.androidmvp.mvp.entity.show;

import java.util.List;

import okhttp3.MultipartBody;

public class ShowPageResult{
        private int id;
        private String autor;
        private String content;
        private String title;
        private String time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAutor() {
            return autor;
        }

        public void setAutor(String autor) {
            this.autor = autor;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public MultipartBody createMultipartBody(){
            MultipartBody.Builder builder = new MultipartBody.Builder();
            //添加文本
            builder.addFormDataPart("title", title);
            builder.addFormDataPart("content", content);
            builder.addFormDataPart("autor", autor);

            builder.setType(MultipartBody.FORM);
            MultipartBody multipartBody = builder.build();
            return multipartBody;
        }


}
