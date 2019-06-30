package com.example.androidmvp.data.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.androidmvp.R;
import com.example.androidmvp.common.Constant;
import com.example.androidmvp.data.httpdata.HttpData;
import com.example.androidmvp.mvp.entity.db.ShowPage;
import com.example.androidmvp.mvp.entity.show.RemarkResult;
import com.example.androidmvp.mvp.entity.show.ShowPageResult;
import com.example.androidmvp.mvp.entity.weather.PeomResult;
import com.example.androidmvp.mvp.entity.UserResult;
import com.example.androidmvp.mvp.entity.weather.WealthResult;
import com.example.androidmvp.mvp.entity.weather.WealthResultReal;
import com.example.androidmvp.mvp.entity.localdb.City;
import com.example.androidmvp.mvp.entity.localdb.Country;
import com.example.androidmvp.mvp.entity.localdb.Province;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
//        RXjavaTest test = new RXjavaTest();
//        test.test();
//        Log.d(TAG, "onCreate: "+Thread.currentThread().getName());

        //默认同一线程工作
//        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                Log.d(TAG, "observable thread is: "+Thread.currentThread().getName());
//                Log.d(TAG, "emit  1");
//                e.onNext(1);
//            }
//        });
//
//        Consumer<Integer> consumer = new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Log.d(TAG, "Observable thread is:"+Thread.currentThread().getName());
//                Log.d(TAG, "onNext:"+integer);
//            }
//        };

//        observable.subscribe(consumer);
//

        // 使用线程调度器改变上下游工作线程
//        observable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(consumer);

//        register();
//        login();
//        getWeatherInfo("CN101240409");
//        testpeom();
//        testW("101240409");
//        postShowpage("消耗风吃屎","dejavun","消耗分是大傻逼爱吃屎");
//        postRemark("dejavun","dejavun","1","你说的没错");
        getShowpages();
    }

    void getShowpages(){
        HttpData.getInstance().getShowpages(new Observer<List<ShowPage>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<ShowPage> value) {
                for(ShowPage page:value){
                    Log.d(TAG, "onNext: "+page.getContent()+page.getRemarks().size()+" "+page.getImages().size());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: "+e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }


    void postRemark(String autor,String to,String showpage,String content){
        HttpData.getInstance().upRemark(new Observer<RemarkResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(RemarkResult value) {
                Log.d(TAG, "onNext: "+value.getContent());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: "+e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }, autor, to, showpage, content);
    }

    public void postShowpage(String title,String autor,String content){
        HttpData.getInstance().upShowPage(new Observer<ShowPageResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ShowPageResult value) {
                Log.d(TAG, "onNext: "+value.getContent());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: "+e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }, title, autor, content);
    }

    void testW(String code){
        HttpData.getInstance().getWeatherInfoReal(new Observer<WealthResultReal>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WealthResultReal value) {
                Log.d(TAG, "onNext: "+value.message+value.data.foreCasts.get(0).weather);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, code);
    }


    void testpeom(){
        HttpData.getInstance().getPeom(new Observer<PeomResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(PeomResult value) {
                Log.d(TAG, "onNext: "+value.getData().getContent());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: "+e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    void getWeatherInfo(String weatherId){
        HttpData.getInstance().getWeatherInfo(new Observer<WealthResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WealthResult value) {
                Log.d(TAG, "onNext: "+value.wealths.size() + " "+value.wealths.get(0).status+" "+
                        value.wealths.get(0).now.wind
                        );
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: "+e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }, weatherId, Constant.Key.KEY);
    }

    void getCountry(int province,int city){
        HttpData.getInstance().getCountry(new Observer<List<Country>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Country> value) {
                for(Country country:value){
                    Log.d(TAG, "onNext: "+country.getWeatherId()+country.getCountryName());
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, province, city);
    }

    void getCity(int province){
        HttpData.getInstance().getCity(new Observer<List<City>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<City> value) {
                for(City city:value){
                    Log.d(TAG, "onNext: "+city.getCityName());
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, province);
    }

    void getProvince(){
        HttpData.getInstance().getProvince(new Observer<List<Province>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Province> value) {
                for(Province province:value){
                    province.save();
                    Log.d(TAG, "onNext: "+province.getId()+province.getProvinceName()+"保存成功");
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: "+e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void get() {
        HttpData.getInstance().getLoginInfo(new Observer<UserResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UserResult value) {
                Log.d(TAG, "onNext: " + value.getUsername() + ":" + value.getEmail());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, "xiaohaofeng");
    }


    public void login() {
        HttpData.getInstance().login(new Observer<UserResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UserResult value) {
                Log.d(TAG, "onNext: " + value.getUsername()+"登录成功");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, "xiaoxin", "1234");
    }

//    public void register() {
//
//        MultipartBody.Builder builder = new MultipartBody.Builder();
////文本部分
//        builder.addFormDataPart("email", "22625672@qq.com");
//        builder.addFormDataPart("gender", "M");
//        builder.addFormDataPart("age", "18");
//        builder.addFormDataPart("username", "xiaoxin");
//        builder.addFormDataPart("password", "1234");
//
//        Resources res = getResources();
//        BitmapDrawable d = (BitmapDrawable) res.getDrawable(R.drawable.login_bk);
//        Bitmap img = d.getBitmap();
//
//        String fn = "image.png";
//        String path = getFilesDir() + File.separator + fn;
//        try {
//            OutputStream os = new FileOutputStream(path);
//            img.compress(Bitmap.CompressFormat.PNG, 100, os);
//            os.close();
//        } catch (Exception e) {
//            Log.e("TAG", "", e);
//        }
//
//        File file = new File(path);
//
//
////文件部分
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
//        builder.addFormDataPart("icon", file.getName(), requestBody); // “image”为文件参数的参数名（由服务器后台提供）
//
//        builder.setType(MultipartBody.FORM);
//        MultipartBody multipartBody = builder.build();
//
//        HttpData.getInstance().register(new Observer<UserResult>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(UserResult value) {
//                Log.i(TAG, "onNext: " + value + "注册成功");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        }, multipartBody);
//
//    }


}
