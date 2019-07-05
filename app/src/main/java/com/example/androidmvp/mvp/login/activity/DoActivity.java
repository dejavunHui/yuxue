package com.example.androidmvp.mvp.login.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;

import com.example.androidmvp.R;
import com.example.androidmvp.mvp.MainActivity;
import com.example.androidmvp.mvp.base.BaseActivity;
import com.example.androidmvp.mvp.login.fragment.LoginFragment;
import com.example.androidmvp.mvp.login.fragment.RegisterFragment;
import com.example.androidmvp.mvp.login.presenter.LoginPresenter;
import com.example.androidmvp.widget.CustomVideoView;

public class DoActivity extends BaseActivity {


    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;


    private CustomVideoView backView;


    @Override
    protected void anything() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_do);
    }

    @Override
    protected void findViewById() {
        loginFragment = LoginFragment.getInstance();
        registerFragment = RegisterFragment.getInstance();
        backView = findViewById(R.id.back_view);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.login_register,loginFragment);
        transaction.add(R.id.login_register,registerFragment);
        transaction.hide(registerFragment);
        transaction.show(loginFragment);
        transaction.commit();

        preferences = getPreferences(MODE_PRIVATE);
        editor = preferences.edit();


        loginFragment.setActivity(this);
        registerFragment.setActivity(this);

    }

    @Override
    protected void setListener() {
        initBackView();
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected Context getActivityContext() {
        return null;
    }

    public FragmentTransaction getTransaction() {
        return transaction;
    }

    public void showFragement(int i){
        transaction = manager.beginTransaction();
        transaction.hide(registerFragment);
        transaction.hide(loginFragment);

        if(i == 0){
            transaction.show(loginFragment);
        }else {

            transaction.show(registerFragment);
        }
        transaction.commit();
    }

    public void login(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",loginFragment.user);

        Intent intent = new Intent(DoActivity.this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void initBackView(){
        backView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.mox));
        backView.start();
        backView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                backView.start();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initBackView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        backView.stopPlayback();
    }
}
