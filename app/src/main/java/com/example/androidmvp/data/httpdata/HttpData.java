package com.example.androidmvp.data.httpdata;


import android.media.Image;
import android.util.Log;

import com.example.androidmvp.common.Constant;
import com.example.androidmvp.data.dataapi.CityService;
import com.example.androidmvp.data.dataapi.LoginService;
import com.example.androidmvp.data.dataapi.ShowPageService;
import com.example.androidmvp.data.dataapi.WealthService;
import com.example.androidmvp.data.retrofit.RetrofitUtil;
import com.example.androidmvp.mvp.entity.db.Remark;
import com.example.androidmvp.mvp.entity.db.ShowPage;
import com.example.androidmvp.mvp.entity.show.ImageResult;
import com.example.androidmvp.mvp.entity.show.RemarkResult;
import com.example.androidmvp.mvp.entity.show.ShowPageResult;
import com.example.androidmvp.mvp.entity.weather.PeomResult;
import com.example.androidmvp.mvp.entity.UserResult;
import com.example.androidmvp.mvp.entity.weather.WealthResult;
import com.example.androidmvp.mvp.entity.weather.WealthResultReal;
import com.example.androidmvp.mvp.entity.localdb.City;
import com.example.androidmvp.mvp.entity.localdb.Country;
import com.example.androidmvp.mvp.entity.localdb.Province;
import com.example.androidmvp.mvp.entity.localdb.Token;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache.Reply;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.functions.Func1;
import rx.functions.Func2;


/**
 * 处理http数据请求
 */
public class HttpData extends RetrofitUtil {

    private static final String TAG = "HttpData";
//    private static File cacheDirectory = FileUtil.getcacheDirectory();
//    private static final CacheProviders providers = new RxCache.Builder()
//            .persistence(cacheDirectory)
//            .using(CacheProviders.class);

    private static final String baseurl = Constant.Urls.SERVERIP;
    protected LoginService loginService = getRetrofit(baseurl).create(LoginService.class);
    protected ShowPageService showPageService = getRetrofit(baseurl).create(ShowPageService.class);
    protected CityService cityService = getRetrofit(Constant.Urls.PLACEJSON).create(CityService.class);
    protected WealthService wealthService = getRetrofit(Constant.Urls.WEATHERAPI).create(WealthService.class);
    protected WealthService peomService = getRetrofit(Constant.Urls.PEOM).create(WealthService.class);
    private WealthService tokenService = getRetrofit(Constant.Urls.TOKEN).create(WealthService.class);
    protected WealthService realWeather = getRetrofit(Constant.Urls.API).create(WealthService.class);

    private static class SingletonHolder {
        private static final HttpData INSTANCE = new HttpData();
    }

    public static HttpData getInstance() {
        return SingletonHolder.INSTANCE;
    }


    /******************************************************************************/

    //获取用户信息
    public void getLoginInfo(Observer<UserResult> observer, String username) {

//        Map<String,String> map = new HashMap<>();
//        map.put("username","hui");
        Observable observable = loginService.getUserInfo(username);
        setSubscribe(observable, observer);

    }

    //注册
    public void register(Observer<UserResult> observer, MultipartBody multipartBody) {
        Observable observable = loginService.register(multipartBody);
        setSubscribe(observable, observer);
    }


    public void login(Observer<UserResult> observer, String username, String password) {
        Observable observable = loginService.login(username, username, password);
        setSubscribe(observable, observer);
    }

    public void changeInfo(Observer<UserResult> observer, String username, MultipartBody multipartBody) {
        Observable observable = loginService.changeInfo(username, multipartBody);
        setSubscribe(observable, observer);
    }


    /******************************************************************************/

    public void getProvince(Observer<List<Province>> observer) {
        Observable observable = cityService.getProvince();
        setSubscribe(observable, observer);
    }


    public void getCity(Observer<List<City>> observer, int provinceid) {
        Observable observable = cityService.getCity(provinceid);
        setSubscribe(observable, observer);
    }

    public void getCountry(Observer<List<Country>> observer, int provinceid, int cityid) {
        Observable observable = cityService.getCountry(provinceid, cityid);
        setSubscribe(observable, observer);
    }

    /******************************************************************************/
    //获取模拟天气信息
    public void getWeatherInfo(Observer<WealthResult> observer, String cityId, String key) {
        Observable observable = wealthService.getWeatherInfo(cityId, key);
        setSubscribe(observable, observer);

    }

    //获取真时天气信息
    public void getWeatherInfoReal(Observer<WealthResultReal> observer, String code) {
        Observable observable = realWeather.getWealtherinfoReal(code);
        setSubscribe(observable, observer);
    }


    /******************************************************************************/

    public void getToken(Observer<Token> observer) {
        Observable observable = tokenService.getToken();
        setSubscribe(observable, observer);
    }

    public void getPeom(Observer<PeomResult> observer) {
        List<Token> token = Token.findAll(Token.class);
        if (token.size() > 0) {
            Log.d(TAG, "getPeom: " + token.get(0).getData());
            Observable observable = peomService.getPeom(token.get(0).getData());
            setSubscribe(observable, observer);
        } else {
            HttpData.getInstance().getToken(new Observer<Token>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Token value) {
                    Log.d(TAG, "onNext: " + value.getStatus() + value.getData());
                    value.save();
                }

                @Override
                public void onError(Throwable e) {
                    Log.d(TAG, "onError: " + e.getMessage());
                }

                @Override
                public void onComplete() {

                }
            });
            token = Token.findAll(Token.class);
            Log.d(TAG, "getPeom: " + token.size());
            Observable observable = peomService.getPeom(token.get(0).getData());
            setSubscribe(observable, observer);
        }
    }


    /***********************************show*************************************************/

    public void upShowPage(Observer<ShowPageResult> observer, String title, String autor, String content) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        //添加文本
        builder.addFormDataPart("title", title);
        builder.addFormDataPart("content", content);
        builder.addFormDataPart("autor", autor);

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();

        Observable observable = showPageService.postShowpage(multipartBody);
        setSubscribe(observable, observer);
    }

    public void upRemark(Observer<RemarkResult> observer, String autor,
                         String to, String page, String content) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("autor", autor);
        builder.addFormDataPart("to", to);
        builder.addFormDataPart("page", page);
        builder.addFormDataPart("content", content);


        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();

        Observable observable = showPageService.postRemark(multipartBody);
        setSubscribe(observable, observer);
    }

    /**
     * @param observer
     * @param showpage
     * @param remark
     * @param image    图片地址
     */
    public void upImage(Observer<ImageResult> observer, String showpage, String remark, String image) {

        MultipartBody.Builder builder = new MultipartBody.Builder();

        File file = new File(image);
        RequestBody body = RequestBody.create(MediaType.parse("image/png"), file);

        MultipartBody multipartBody = builder.build();

        Observable observable = showPageService.postImages(multipartBody);
        setSubscribe(observable, observer);
    }

    public void getShowpages(Observer<List<ShowPage>> observer) {
        Observable observable1 = showPageService.getShowpages();
        Observable observable2 = showPageService.getRemarks();
        Observable observable3 = showPageService.getImages();
        Observable observable = Observable.zip(observable1, observable2, observable3, new Function3<List<ShowPageResult>, List<RemarkResult>, List<ImageResult>, List<ShowPage>>() {
            @Override
            public List<ShowPage> apply(List<ShowPageResult> o, List<RemarkResult> o2, List<ImageResult> o3) throws Exception {
                List<ShowPage> showPages = new ArrayList<>();
                List<String> images = new ArrayList<>();
                List<Remark> remarks = new ArrayList<>();
                for (ShowPageResult showpageBean : o) {
                    ShowPage page = new ShowPage();
                    page.setId(showpageBean.getId());
                    page.setUser(showpageBean.getAutor());
                    page.setContent(showpageBean.getContent());
                    page.setTitle(showpageBean.getTitle());
                    String showpageid = String.valueOf(showpageBean.getId());
                    List<ImageResult> image_remark = new ArrayList<>();
                    for (ImageResult imageBean : o3){
                        if(imageBean.getShowpage().equals(showpageid)){
                            images.add(imageBean.getImage());
                        }else {
                            image_remark.add(imageBean);
                        }
                    }
                    for(RemarkResult remarkBean:o2){
                        if(remarkBean.getPage().equals(showpageid)){
                            Remark remark = new Remark();
                            remark.setId(remarkBean.getId());
                            remark.setFrom(remarkBean.getAuotr());
                            remark.setTo(remarkBean.getTo());
                            remark.setContent(remarkBean.getContent());
                            List<String> image = new ArrayList<>();

                            for(ImageResult bean:image_remark){
                                if(bean.getRemark().equals(bean.getId())){
                                    image.add(bean.getImage());
                                }
                            }
                            remarks.add(remark);
                        }
                    }
                    page.setImages(images);
                    page.setRemarks(remarks);
                    showPages.add(page);
                }

                return showPages;
            }
        }).subscribeOn(Schedulers.computation());
        setSubscribe(observable,observer);
    }

    /**
     * 插入观察者
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }

    /**
     * 用来统一处理RxCacha的结果
     */
    private class HttpResultFuncCcche<T> implements Func1<Reply<T>, T> {

        @Override
        public T call(Reply<T> httpResult) {
            return httpResult.getData();
        }
    }


}
