package com.example.androidmvp.data.dataapi;

import com.example.androidmvp.mvp.entity.weather.PeomResult;
import com.example.androidmvp.mvp.entity.weather.WealthResult;
import com.example.androidmvp.mvp.entity.weather.WealthResultReal;
import com.example.androidmvp.mvp.entity.localdb.Token;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WealthService {

    @GET("weather")
    Observable<WealthResult> getWeatherInfo(@Query("cityid")String cityId, @Query("key")String key);

    @GET("{citycode}/")
    Observable<WealthResultReal> getWealtherinfoReal(@Path("citycode") String code);

    @GET("token/")
    Observable<Token> getToken();

    @GET("sentence/")
    Observable<PeomResult> getPeom(@Header("X-User-Token") String token);
}
