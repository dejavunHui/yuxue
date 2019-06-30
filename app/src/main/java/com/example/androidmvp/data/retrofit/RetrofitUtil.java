package com.example.androidmvp.data.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 封装一个retrofit集成OkHttp3的抽象基类
 */
public abstract class RetrofitUtil {

    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;

    /**
     * 获取一个Retrofit对象
     */

    protected static Retrofit getRetrofit(String baseUrl){

        if(null == okHttpClient){
            okHttpClient = OKHttpUtils.getOkHttpClient();
        }
        retrofit = new Retrofit.Builder()
                //设置服务器路径
                .baseUrl(baseUrl)
                //添加转化库，默认是Gson
                .addConverterFactory(GsonConverterFactory.create())
                //添加回调库，采用RxJava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //设置使用okhttp网络请求
                .client(okHttpClient)
                .build();

        return retrofit;
    }

}
