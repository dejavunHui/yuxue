package com.example.androidmvp.mvp.wealth.view;

import com.example.androidmvp.mvp.entity.localdb.City;
import com.example.androidmvp.mvp.entity.localdb.Country;
import com.example.androidmvp.mvp.entity.localdb.Province;

import java.util.List;

public interface BaseCityView {

    public void setProvince(List<Province> provinces);

    public void setCity(List<City> cities);

    public void setCountry(List<Country> countries);

    public void showProgressDialog();
    public void closeProgressDialog();

    public void showMessage(String message);


}
