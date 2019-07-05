package com.example.androidmvp;

import android.app.Activity;
import android.app.Application;

import com.example.androidmvp.util.PreviewImageLoader;
import com.previewlibrary.ZoomMediaLoader;

import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.List;


public class MyApplication extends LitePalApplication {

    //记录当前栈中的所有Activity
    private List<Activity> activities = new ArrayList<>();
    //需要一次性关闭的页面
    private List<Activity> activitys = new ArrayList<>();


    /**
     * 应用实例
     */
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        ZoomMediaLoader.getInstance().init(new PreviewImageLoader());
        instance = this;
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static MyApplication getInstance() {
        return instance;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 结束指定activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            this.activities.remove(activity);
            activity.finish();
            activity = null;
        }
    }


    /*
     *给临时Activitys
     * 添加activity
     * */
    public void addTemActivity(Activity activity) {
        activitys.add(activity);
    }


    public void finishTemActivity(Activity activity) {
        if (activity != null) {
            this.activitys.remove(activity);
            activity.finish();
            activity = null;
        }
    }


    /*
     * 退出指定的Activity
     * */
    public void exitActivitys() {
        for (Activity activity : activitys) {
            if (activity != null) {
                activity.finish();
            }
        }
    }


    /**
     * 退出应用
     */

    public void exit() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
    }
}
