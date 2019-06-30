package com.example.androidmvp.mvp.wealth.presenter;

import com.example.androidmvp.mvp.base.OnLoadDataListener;
import com.example.androidmvp.mvp.entity.localdb.City;
import com.example.androidmvp.mvp.entity.localdb.Country;
import com.example.androidmvp.mvp.entity.localdb.Province;
import com.example.androidmvp.mvp.wealth.model.OnLoadCityListener;
import com.example.androidmvp.mvp.wealth.model.WealthModel;
import com.example.androidmvp.mvp.wealth.view.BaseCityView;

import java.util.List;

public class StartPresenter {

    private WealthModel model;
    private BaseCityView view;

    public StartPresenter(BaseCityView view) {
        this.view = view;
        model = new WealthModel();
    }

    public void queryProvinces(){

//        view.showProgressDialog();
        model.loadProvince(new OnLoadCityListener<List<Province>>() {
            @Override
            public void onSuccess(List<Province> data) {
                view.setProvince(data);
            }

            @Override
            public void onError(Throwable e) {
                view.showMessage(e.getMessage());
            }
        });

    }

    public void queryCities(int p){
        view.showProgressDialog();
        model.loadCity(new OnLoadCityListener<List<City>>() {
            @Override
            public void onSuccess(List<City> data) {
                view.setCity(data);
            }

            @Override
            public void onError(Throwable e) {
                view.showMessage(e.getMessage());
            }
        }, p);
    }

    public void queryCounties(int p,int c){
        view.showProgressDialog();
        model.loadCountry(new OnLoadCityListener<List<Country>>() {
            @Override
            public void onSuccess(List<Country> data) {
                view.setCountry(data);
            }

            @Override
            public void onError(Throwable e) {
                view.showMessage(e.getMessage());
            }
        }, p, c);
    }

}
