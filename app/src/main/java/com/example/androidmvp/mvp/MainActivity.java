package com.example.androidmvp.mvp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.androidmvp.R;
import com.example.androidmvp.mvp.base.BaseActivity;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.wakehao.bar.BottomNavigationBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    private static final String TAG = "MainActivity";

    static float x_o = 0;
    static float x_n = 0;
    private ResideMenu resideMenu;

    private List<ResideMenuItem> items = new ArrayList<>();


    private BottomNavigationBar bar;

    private void initResideMenu() {
        resideMenu = new ResideMenu(this);
        resideMenu.attachToActivity(this);
        resideMenu.setBackground(R.drawable.menubak);
        ItemClickListener listener = new ItemClickListener();

        String[] titles = {"我的信息", "我的动态", "退出登录", "关于我们"};
        int[] icons = {R.drawable.dot_selected, R.drawable.dot_selected,
                R.drawable.dot_selected, R.drawable.dot_selected};
        for(int i = 0;i < titles.length;i ++){
            ResideMenuItem item = new ResideMenuItem(this,icons[i],titles[i]);
            item.setOnClickListener(listener);
            items.add(item);
            resideMenu.addMenuItem(item,ResideMenu.DIRECTION_LEFT);
        }

        //禁用右边菜单
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        //添加Fragment忽略

        FrameLayout ignore = findViewById(R.id.fragment_container);
        resideMenu.addIgnoredView(ignore);
    }

    private void fullScreen(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return resideMenu.dispatchKeyEvent(event);
    }

    public ResideMenu getResideMenu() {
        return resideMenu;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {


        if(ev.getAction() == MotionEvent.ACTION_DOWN)
            x_o = ev.getX();
        if(ev.getAction() == MotionEvent.ACTION_UP){
            x_n = ev.getX();
            if(x_o < 100 && ev.getX() > x_o){
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        }

        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    protected void loadViewLayout() {
        fullScreen();
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void findViewById() {
        bar = findViewById(R.id.bottombar);
    }

    @Override
    protected void setListener() {
        initResideMenu();
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected void anything() {
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    class ItemClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

        }
    }
}
