package com.example.androidmvp.mvp.entity;

import android.content.Context;
import android.net.Uri;

import com.example.androidmvp.common.Constant;
import com.example.androidmvp.util.PhotoUtil;

import java.io.File;
import java.io.Serializable;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public final class UserResult implements Serializable {

    private String email;
    private String gender;
    private int age;
    private String icon;
    private String username;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MultipartBody createMultipartBody(Context context, Uri[] url) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
//文本部分
        builder.addFormDataPart("email", email);
        builder.addFormDataPart("gender", gender);
        builder.addFormDataPart("age", "" + age);
        builder.addFormDataPart("username", username);
        builder.addFormDataPart("password", password);


//文件部分
        if (url != null) {
            for (Uri uri : url) {

                String path = PhotoUtil.getFilePathByUri(context, uri);
                File file = new File(path);

                RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
                builder.addFormDataPart("icon", file.getName(), requestBody); // “image”为文件参数的参数名（由服务器后台提供）
            }
        }

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }

}
