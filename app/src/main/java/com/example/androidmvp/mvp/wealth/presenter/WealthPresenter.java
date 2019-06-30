package com.example.androidmvp.mvp.wealth.presenter;

import com.example.androidmvp.mvp.entity.weather.PeomResult;
import com.example.androidmvp.mvp.entity.weather.WealthResultReal;
import com.example.androidmvp.mvp.entity.localdb.Country;
import com.example.androidmvp.mvp.wealth.model.OnLoadCityListener;
import com.example.androidmvp.mvp.wealth.model.WealthModel;
import com.example.androidmvp.mvp.wealth.view.BaseWealthView;

public class WealthPresenter {

    private static final String TAG = "WealthPresenter";
    private WealthModel model;
    private BaseWealthView view;

    public WealthPresenter(BaseWealthView view) {
        this.view = view;
        model = new WealthModel();
    }


    public void loadRealData(Country country){
        String code = country.getWeatherId().substring(2,country.getWeatherId().length());
        model.loadWeatherReal(new OnLoadCityListener<WealthResultReal>() {
            @Override
            public void onSuccess(WealthResultReal data) {
                view.newDatas1(data);
            }

            @Override
            public void onError(Throwable e) {
                view.showMessage(e.getMessage());
            }
        }, code);
    }

    public void loadPeom() {
        model.getPeom(new OnLoadCityListener<PeomResult>() {
            @Override
            public void onSuccess(PeomResult data) {
//                Log.d(TAG, "onSuccess: "+data.getData().getContent());
                String[] peom = data.getData().getContent().split("，");
                String f = peom[0];
                String l;
                if(peom[1].endsWith("。"))
                     l = peom[1].substring(0,peom[1].length()-1);
                else
                    l = peom[1];
//                Log.d(TAG, "onSuccess: "+f+":"+l);
                view.setPeom(f, l);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }


    public void loadBackground(String type){

    }
}
