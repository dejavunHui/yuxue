package com.example.androidmvp.mvp.login.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.androidmvp.R;
import com.example.androidmvp.common.Constant;
import com.example.androidmvp.mvp.base.BaseFragment;
import com.example.androidmvp.mvp.login.activity.DoActivity;
import com.example.androidmvp.mvp.login.presenter.LoginPresenter;
import com.example.androidmvp.mvp.login.view.BaseLoginView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment implements BaseLoginView {

    private static final String TAG = "LoginFragment";
    private static LoginFragment instance = null;

    private EditText username;
    private EditText password;
    private ActionProcessButton login;
    private CheckBox remember;
    private TextView toRegister;
    private DoActivity activity;
    private LoginPresenter presenter;

    public void setActivity(DoActivity activity) {
        this.activity = activity;
    }

    public static LoginFragment getInstance() {
        if (instance == null) {
            instance = new LoginFragment();
        }
        return instance;
    }


    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    protected void initListener() {
        presenter = new LoginPresenter(this);
        username = rootView.findViewById(R.id.username);
        password = rootView.findViewById(R.id.password);
        login = rootView.findViewById(R.id.login);
        remember = rootView.findViewById(R.id.remeberpassword);
        toRegister = rootView.findViewById(R.id.login_noid_tv);
        login.setOnClickListener(new ClickListener());
        String name = activity.preferences.getString("username","error");
        if(name != "error"){
            username.setText(name);
            password.setText(activity.preferences.getString("password","1234"));
            remember.setChecked(true);
        }

        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showFragement(1);
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void login(int type) {
        if (type == Constant.Types.SUCCESS) {
            login.setProgress(100);
            login.setMode(ActionProcessButton.Mode.ENDLESS);
            activity.login();
        } else {
//            activity.showToast("用户名或者密码错误");
            login.setProgress(-1);
            login.setMode(ActionProcessButton.Mode.ENDLESS);
        }
    }

    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            login.setMode(ActionProcessButton.Mode.PROGRESS);
            login.setProgress(1);
            String name = username.getText().toString().trim();
            login.setProgress(10);
            String p = password.getText().toString().trim();
            login.setProgress(40);

            if (name.length() == 0 || p.length() == 0) {
                activity.showToast("用户名或密码不能为空");
                login.setMode(ActionProcessButton.Mode.ENDLESS);
            } else {
                boolean rem = remember.isChecked();
                if (rem) {
//                    Log.d(TAG, "onClick: "+"记住密码");
                    //保存用户名密码
                    activity.editor.putString("username",name);
                    activity.editor.putString("password",p);
                    activity.editor.commit();
                }else {
//                    Log.d(TAG, "onClick: "+"记住密码sadad");
                    activity.editor.clear();
                    activity.editor.commit();
                }
                login.setProgress(70);
                presenter.login(name, p);

            }
        }
    }


}
