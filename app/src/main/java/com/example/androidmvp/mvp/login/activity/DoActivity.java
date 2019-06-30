package com.example.androidmvp.mvp.login.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.androidmvp.R;
import com.example.androidmvp.mvp.MainActivity;
import com.example.androidmvp.mvp.base.BaseActivity;
import com.example.androidmvp.mvp.login.fragment.LoginFragment;
import com.example.androidmvp.mvp.login.fragment.RegisterFragment;
import com.example.androidmvp.mvp.login.presenter.LoginPresenter;

public class DoActivity extends BaseActivity {

    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;


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
        Intent intent = new Intent(DoActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
