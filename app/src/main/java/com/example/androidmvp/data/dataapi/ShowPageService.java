package com.example.androidmvp.data.dataapi;


import com.example.androidmvp.mvp.entity.show.ImageResult;
import com.example.androidmvp.mvp.entity.show.RemarkResult;
import com.example.androidmvp.mvp.entity.show.ShowPageResult;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

//请求，上传接口
public interface ShowPageService {

    @POST("service/showpages/")
    Observable<ShowPageResult> postShowpage(@Body MultipartBody multipartBody);

    @POST("service/remarks/")
    Observable<RemarkResult> postRemark(@Body MultipartBody multipartBody);

    @POST("service/images/")
    Observable<ImageResult> postImages(@Body MultipartBody multipartBody);


    @PUT("service/showpages/{id}/")
    Observable<ShowPageResult> putShowpage(@Body MultipartBody multipartBody,@Path("id")String id);

    @POST("service/remarks/{id}/")
    Observable<RemarkResult> putRemark(@Body MultipartBody multipartBody,@Path("id") String id);

    @POST("service/images/{id}/")
    Observable<ImageResult> putImages(@Body MultipartBody multipartBody,@Path("id") String id);


    @DELETE("service/showpages/{id}/")
    Observable<ShowPageResult> deleteShowpage(@Path("id")String id);

    @DELETE("service/remarks/{id}/")
    Observable<RemarkResult> deleteRemark(@Path("id") String id);

    @DELETE("service/images/{id}/")
    Observable<ImageResult> deleteImages(@Path("id") String id);


    @GET("service/showpages/")
    Observable<List<ShowPageResult>> getShowpages();
    @GET("service/remarks/")
    Observable<List<RemarkResult>> getRemarks();
    @GET("service/images/")
    Observable<List<ImageResult>> getImages();


}