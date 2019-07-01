package com.example.androidmvp.mvp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.androidmvp.R;
import com.example.androidmvp.mvp.base.BaseActivity;
import com.example.androidmvp.mvp.entity.UserResult;
import com.example.androidmvp.mvp.residemenu.activity.UserInfoActivity;
import com.example.androidmvp.mvp.show.activity.CreateRemarkActivity;
import com.example.androidmvp.mvp.show.activity.CreateShowPageActivity;
import com.example.androidmvp.mvp.show.fragment.ShowFragment;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.wakehao.bar.BottomNavigationBar;
import com.wakehao.bar.BottomNavigationItemWithDot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    public static final String CURRENTUSERINFO = "com.example.currentuser.user";

    private static final String TAG = "MainActivity";

    static float x_o = 0;
    static float x_n = 0;
    private ResideMenu resideMenu;

    private List<ResideMenuItem> items = new ArrayList<>();
    private BottomNavigationBar bar;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private UserResult result;

    private void initResideMenu() {
        resideMenu = new ResideMenu(this);
        resideMenu.attachToActivity(this);
        resideMenu.setBackground(R.drawable.menubak);
        ItemClickListener listener = new ItemClickListener();

        String[] titles = {"千机阁", "听雨轩", "退出登录", "关于我们"};
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
        preferences = getPreferences(MODE_PRIVATE);
        editor = preferences.edit();
    }

    @Override
    protected void setListener() {

        initResideMenu();
    }

    @Override
    protected void processLogic() {
        Bundle bundle = getIntent().getExtras();
        result = (UserResult) bundle.getSerializable("user");

        Log.d(TAG, "processLogic: "+result.getUsername());
        editor.putString("c_user",result.getUsername());
        editor.putString("c_email",result.getEmail());
        editor.putString("c_gender",result.getGender());
        editor.putString("c_icon",result.getIcon());
        editor.putInt("c_age",result.getAge());
        editor.putString("c_password",result.getPassword());
        editor.commit();
    }

    @Override
    protected void anything() {
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ShowFragment.REQUEST_CODE ){
            bar.setItemSelected(1,false);
        }
    }


    //点击菜单逻辑
    class ItemClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            if(v == items.get(0)){
                //跳转个人信息
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",result);
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        }
    }
}
