package com.example.androidmvp.mvp.residemenu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidmvp.R;
import com.example.androidmvp.common.Constant;
import com.example.androidmvp.mvp.base.BaseActivity;
import com.example.androidmvp.mvp.entity.UserResult;
import com.example.androidmvp.mvp.residemenu.presenter.ResidePresenter;
import com.example.androidmvp.mvp.residemenu.view.BaseResideView;
import com.example.androidmvp.mvp.show.adapter.ImagePickerAdapter;
import com.example.androidmvp.util.GlideImageLoader;
import com.example.androidmvp.widget.SelectDialog;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;
import java.util.List;

public class UserInfoActivity extends BaseActivity implements BaseResideView {

    public static final String USER_INFO_UPDATE_BROADCAST = "com.userinfo.update.notify";

    private ImageView userIcon;
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText gender;
    private EditText age;
    private Button cancel;
    private Button change;

    private String userIconPath;
    private UserResult user;
    private String own = null;
    private ResidePresenter presenter;


    public static final int REQUEST_CODE_SELECT = 100;

    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 1;//允许选择图片最大数


    /**
     * 初始化组件
     */

    @Override
    protected void anything() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_user_info);
    }

    @Override
    protected void findViewById() {
        userIcon = findViewById(R.id.uf_touxiang);
        username = findViewById(R.id.uf_username_et);
        email = findViewById(R.id.uf_email_et);
        password = findViewById(R.id.uf_password_et);
        gender = findViewById(R.id.uf_gender_et);
        age = findViewById(R.id.uf_age_et);
        change = findViewById(R.id.uf_change);
        cancel = findViewById(R.id.uf_back);
        presenter = new ResidePresenter(this);
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    private void initWidget() {
        selImageList = new ArrayList<>();
    }


    public SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style
                .ImagePickerTheme,
                listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }


    @Override
    protected void setListener() {
        initImagePicker();
        initWidget();
        Bundle bundle = getIntent().getExtras();
        user = (UserResult) bundle.getSerializable("user");
        own = bundle.getString("own");
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelect();
            }
        });
        MyOnClickListener listener = new MyOnClickListener();
        change.setOnClickListener(listener);
        cancel.setOnClickListener(listener);
    }

    public void openSelect(){
        List<String> names = new ArrayList<>();
        names.add("拍照");
        names.add("相册");
        showDialog(new SelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // 直接调起相机
                        /**
                         * 0.4.7 目前直接调起相机不支持裁剪，如果开启裁剪后不会返回图片，请注意，后续版本会解决
                         *
                         * 但是当前直接依赖的版本已经解决，考虑到版本改动很少，所以这次没有上传到远程仓库
                         *
                         * 如果实在有所需要，请直接下载源码引用。
                         */
                        //打开选择,本次允许选择的数量
                        ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                        Intent intent = new Intent(UserInfoActivity.this, ImageGridActivity.class);
                        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                        startActivityForResult(intent, REQUEST_CODE_SELECT);
                        break;
                    case 1:
                        //打开选择,本次允许选择的数量
                        ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                        Intent intent1 = new Intent(UserInfoActivity.this, ImageGridActivity.class);
                        /* 如果需要进入选择的时候显示已经选中的图片，
                         * 详情请查看ImagePickerActivity
                         * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                        startActivityForResult(intent1, REQUEST_CODE_SELECT);
                }
            }
        },names);
    }

    @Override
    protected void processLogic() {
        initUserInfo();
    }

    public void initUserInfo(){
        Glide.with(this).load(Constant.Urls.IMAGEURLROOT+user.getIcon()).error(R.drawable.ic_launcher).into(userIcon);
        username.setText(user.getUsername());
        email.setText(user.getEmail());
        age.setText(String.valueOf(user.getAge()));
        gender.setText(user.getGender());
        password.setText(user.getPassword());
        if(own == "N"){
            change.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    ArrayList<ImageItem> images = null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    userIconPath = images.get(0).path;
                    Glide.with(this).load(userIconPath).into(userIcon);
                }
            }
        }
    }


    class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.uf_change:
                    //更改信息
                    changeInfo();
                    break;
                case R.id.uf_back:
                    finish();
                    break;
            }
        }

        public void changeInfo(){
            user.setIcon(userIconPath);
            presenter.changeInfo(user);
        }

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUser(UserResult user) {
        this.user = user;
    }

    //用户信息更新，广播出去
    public void sendBroadCast(){
        Intent intent = new Intent(USER_INFO_UPDATE_BROADCAST);
        intent.putExtra("user",user);
        LocalBroadcastManager.getInstance(getActivityContext()).sendBroadcast(intent);
    }
}
