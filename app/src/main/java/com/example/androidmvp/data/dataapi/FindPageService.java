package com.example.androidmvp.data.dataapi;

import com.example.androidmvp.mvp.entity.FindPageResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FindPageService {

    @GET("day")
    Observable<FindPageResult> getFindPage(@Query("dev") int dev, @Query("date") String date);
}
