package com.example.androidmvp.data.dataapi;

import com.example.androidmvp.mvp.entity.localdb.City;
import com.example.androidmvp.mvp.entity.localdb.Country;
import com.example.androidmvp.mvp.entity.localdb.Province;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CityService {

    /**
     * 获取省份
     * @return
     */
    @GET("china/")
    public Observable<List<Province>> getProvince();


    @GET("china/{province}/")
    public Observable<List<City>> getCity(@Path("province") int province);

    @GET("china/{province}/{city}/")
    public Observable<List<Country>> getCountry(
            @Path("province") int province,
            @Path("city") int city
    );

}
