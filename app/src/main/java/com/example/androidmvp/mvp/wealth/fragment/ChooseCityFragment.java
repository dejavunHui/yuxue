package com.example.androidmvp.mvp.wealth.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidmvp.R;
import com.example.androidmvp.mvp.base.BaseFragment;
import com.example.androidmvp.mvp.entity.localdb.City;
import com.example.androidmvp.mvp.entity.localdb.Country;
import com.example.androidmvp.mvp.entity.localdb.Province;
import com.example.androidmvp.mvp.wealth.presenter.StartPresenter;
import com.example.androidmvp.mvp.wealth.view.BaseCityView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseCityFragment extends BaseFragment implements BaseCityView {


    public static final String CITYSELECTED="com.city.selected";
    private StartPresenter presenter;


    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTRY = 2;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;;

    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> datas = new ArrayList<>();
    private List<Province> provinces;
    private List<City> cities;
    private List<Country> countries;


    private Province selectedProvince;
    private City selectedCity;
    private Country selectedCountry;
    private int currentLevel;

    private static ChooseCityFragment instance = null;

    public static ChooseCityFragment getInstance() {
        if (instance == null) {
            instance = new ChooseCityFragment();
        }
        return instance;
    }


    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.start_fragment, container, false);
    }

    @Override
    protected void initListener() {

        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();

        titleText = rootView.findViewById(R.id.title_text);
        backButton = rootView.findViewById(R.id.back_button);
        listView = rootView.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1
        ,datas);
        listView.setAdapter(adapter);
        presenter = new StartPresenter(this);

    }

    @Override
    protected void loadData() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentLevel == LEVEL_PROVINCE){
                    selectedProvince = provinces.get(position);
                    presenter.queryCities(selectedProvince.getId());
                }else if(currentLevel == LEVEL_CITY){
                    selectedCity = cities.get(position);
                    presenter.queryCounties(selectedProvince.getId(),selectedCity.getId());
                }else if(currentLevel == LEVEL_COUNTRY){
                    selectedCountry = countries.get(position);
                    cache(selectedCountry);
                    sendBrocast(selectedCountry);
                    presenter.queryProvinces();
                    Toast.makeText(getContext(),"城市选择成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLevel == LEVEL_COUNTRY){
                    presenter.queryCities(selectedProvince.getId());
                }else if(currentLevel == LEVEL_CITY){
                    presenter.queryProvinces();
                }
            }
        });

        presenter.queryProvinces();
    }

    @Override
    public void setProvince(List<Province> provinces) {
        this.provinces = provinces;
        titleText.setText("中国");
        backButton.setVisibility(View.INVISIBLE);
        datas.clear();
        for(Province province:provinces){
            datas.add(province.getProvinceName());
        }
        adapter.notifyDataSetChanged();
        listView.setSelection(0);
        currentLevel = LEVEL_PROVINCE;
        closeProgressDialog();
    }

    @Override
    public void setCity(List<City> cities) {
        this.cities = cities;
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        datas.clear();
        for(City city:cities){
            datas.add(city.getCityName());
        }
        adapter.notifyDataSetChanged();
        listView.setSelection(0);
        currentLevel = LEVEL_CITY;
        closeProgressDialog();
    }

    @Override
    public void setCountry(List<Country> countries) {
        this.countries = countries;
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        datas.clear();
        for(Country country:countries){
            datas.add(country.getCountryName());
        }
        adapter.notifyDataSetChanged();
        listView.setSelection(0);
        currentLevel = LEVEL_COUNTRY;
        closeProgressDialog();
    }

    @Override
    public void showProgressDialog() {
        if(progressDialog == null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    @Override
    public void closeProgressDialog() {
        if (progressDialog != null) {

            progressDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }


    public void cache(Country selectedCountry){
        editor.putInt("id",selectedCountry.getId());
        editor.putInt("cityid",selectedCountry.getCityId());
        editor.putString("cityname",selectedCountry.getCountryName());
        editor.putString("weatherId",selectedCountry.getWeatherId());
        editor.commit();
    }

    public void sendBrocast(Country selectedCountry){
        Intent intent = new Intent(CITYSELECTED);
//        intent.putExtra("id",selectedCountry);
//        intent.putExtra("cityid",selectedCountry.getCityId());
//        intent.putExtra("cityname",selectedCountry.getCountryName());
//        intent.putExtra("weatherId",selectedCountry.getWeatherId());
        intent.putExtra("country",selectedCountry);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }
}
