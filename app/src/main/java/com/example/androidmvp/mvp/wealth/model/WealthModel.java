package com.example.androidmvp.mvp.wealth.model;

import com.example.androidmvp.common.Constant;
import com.example.androidmvp.data.httpdata.HttpData;
import com.example.androidmvp.mvp.entity.weather.PeomResult;
import com.example.androidmvp.mvp.entity.weather.WealthResult;
import com.example.androidmvp.mvp.entity.weather.WealthResultReal;
import com.example.androidmvp.mvp.entity.localdb.City;
import com.example.androidmvp.mvp.entity.localdb.Country;
import com.example.androidmvp.mvp.entity.localdb.Province;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class WealthModel {

    /**
     * 查询省份信息
     *
     * @param listener
     */
    public void loadProvince(final OnLoadCityListener<List<Province>> listener) {

        HttpData.getInstance().getProvince(new Observer<List<Province>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Province> value) {

                listener.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }

            @Override
            public void onComplete() {

            }
        });

    }


    public void loadCity(final OnLoadCityListener<List<City>> listener, final int provinceId) {

        HttpData.getInstance().getCity(new Observer<List<City>>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(List<City> value) {
                List<City> cities = new ArrayList<>();
                for (City city : value) {
                    city.setProvinceId(provinceId);
                    cities.add(city);
                }
                listener.onSuccess(cities);
            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }

            @Override
            public void onComplete() {

            }
        }, provinceId);

    }

    public void loadCountry(final OnLoadCityListener<List<Country>> listener, int provinceId, final int cityId) {

        HttpData.getInstance().getCountry(new Observer<List<Country>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Country> value) {
                List<Country> countries = new ArrayList<>();
                for (Country country : value) {
                    country.setCityId(cityId);
                    countries.add(country);
                }
                listener.onSuccess(countries);
            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }

            @Override
            public void onComplete() {

            }
        }, provinceId, cityId);
    }


    public void loadWealth(final OnLoadCityListener<WealthResult> listener,String weatherid){
        HttpData.getInstance().getWeatherInfo(new Observer<WealthResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WealthResult value) {
                listener.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }

            @Override
            public void onComplete() {

            }
        }, weatherid, Constant.Key.KEY);
    }

    public void getPeom(final OnLoadCityListener<PeomResult> listener){
        HttpData.getInstance().getPeom(new Observer<PeomResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(PeomResult value) {
                listener.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void loadWeatherReal(final OnLoadCityListener<WealthResultReal> listener,String code){
        HttpData.getInstance().getWeatherInfoReal(new Observer<WealthResultReal>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WealthResultReal value) {
                listener.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }

            @Override
            public void onComplete() {

            }
        }, code);
    }
}
