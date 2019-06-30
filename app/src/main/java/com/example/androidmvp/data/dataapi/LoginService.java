package com.example.androidmvp.data.dataapi;

import com.example.androidmvp.mvp.entity.UserResult;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LoginService {

    @GET("/users/{username}/oneuser/")
    Observable<UserResult> getUserInfo(
            @Path("username") String username
    );//请求用户信息


    @FormUrlEncoded
    @POST("/users/{username}/login/")
    Observable<UserResult> login(
            @Path("username") String username,
      @Field("username") String name,
      @Field("password") String password
    );

    @POST("/users/")
    Observable<UserResult> register(
            @Body MultipartBody multipartBody
            );


    /**
     * 更改用户信息
     * @param username
     * @param multipartBody
     * @return
     */
    @PUT("/users/{username}/")
    Observable<UserResult> changeInfo(
      @Path("username") String username,
      @Body MultipartBody multipartBody
    );

    /**
     *
     * 使用例子
     *
     * MultipartBody.Builder builder = new MultipartBody.Builder();
     * //文本部分
     * builder.addFormDataPart("fromType", "1");
     * builder.addFormDataPart("content", "意见反馈内容");
     * builder.addFormDataPart("phone", "17700000066");
     *
     * //文件部分
     * RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
     * builder.addFormDataPart("image", file.getName(), requestBody); // “image”为文件参数的参数名（由服务器后台提供）
     *
     * builder.setType(MultipartBody.FORM);
     * MultipartBody multipartBody = builder.build();
     */

}
