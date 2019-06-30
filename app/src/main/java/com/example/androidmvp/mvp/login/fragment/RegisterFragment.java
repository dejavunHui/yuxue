package com.example.androidmvp.mvp.login.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.androidmvp.R;
import com.example.androidmvp.common.Constant;
import com.example.androidmvp.mvp.base.BaseFragment;
import com.example.androidmvp.mvp.entity.UserResult;
import com.example.androidmvp.mvp.login.activity.DoActivity;
import com.example.androidmvp.mvp.login.presenter.RegisterPresenter;
import com.example.androidmvp.mvp.login.view.BaseRegisterView;

import static android.support.constraint.Constraints.TAG;

public class RegisterFragment extends BaseFragment implements BaseRegisterView {

    private static RegisterFragment instance = null;
    private RegisterPresenter presenter;
    private DoActivity activity;

    private EditText username;
    private EditText email;
    private EditText password;
    private EditText age;
    private RadioButton male;
    private RadioButton female;
    private ActionProcessButton register;
    private TextView toLogin;


    public void setActivity(DoActivity activity) {
        this.activity = activity;
    }

    public static RegisterFragment getInstance() {

        if (instance == null) {
            instance = new RegisterFragment();
        }
        return instance;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    private void initCompenent() {
        presenter = new RegisterPresenter(this);
        username = rootView.findViewById(R.id.rg_name_et);
        email = rootView.findViewById(R.id.rg_email_et);
        password = rootView.findViewById(R.id.rg_password_et);
        age = rootView.findViewById(R.id.rg_age);
        male = rootView.findViewById(R.id.male);
        female = rootView.findViewById(R.id.female);
        register = rootView.findViewById(R.id.register);
        toLogin = rootView.findViewById(R.id.rg_gotologin_tv);

    }

    @Override
    protected void initListener() {
        initCompenent();
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showFragement(0);
            }
        });
        register.setOnClickListener(new ClickListener());
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void register_login(int type, String message) {
        if (type == Constant.Types.SUCCESS) {
            activity.showFragement(0);
            register.setProgress(100);
            register.setMode(ActionProcessButton.Mode.ENDLESS);
        } else {
//            activity.showToast(message);
            register.setProgress(-1);
            register.setMode(ActionProcessButton.Mode.ENDLESS);
            register.setProgress(0);
        }
    }

    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            register.setMode(ActionProcessButton.Mode.PROGRESS);
            register.setProgress(10);
            String nameStr = username.getText().toString().trim();
            String emailStr = email.getText().toString().trim();
            String passwordStr = password.getText().toString();
            if(nameStr.length()==0 || emailStr.length() == 0 || passwordStr.length() == 0){
                activity.showToast("请填写完整信息");
            }
            int ageInt = Integer.parseInt(age.getText().toString());
            String genderStr = male.isSelected() == true? "F" : "M";
            Log.d(TAG, "onClick: "+passwordStr+genderStr);
            register.setProgress(40);

            UserResult rs = new UserResult();
            rs.setUsername(nameStr);
            rs.setEmail(emailStr);
            rs.setPassword(passwordStr);
            rs.setGender(genderStr);
            rs.setAge(ageInt);
            register.setProgress(70);
            presenter.register(rs.createMultipartBody(getContext(),null));


        }
    }

    @Override
    public void clear() {
        username.setText("");
        email.setText("");
        password.setText("");
        age.setText("");
        male.setSelected(false);
        female.setSelected(false);
    }
}
