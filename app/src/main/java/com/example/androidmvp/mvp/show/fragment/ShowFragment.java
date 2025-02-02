package com.example.androidmvp.mvp.show.fragment;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.example.androidmvp.R;
import com.example.androidmvp.common.Constant;
import com.example.androidmvp.mvp.MainActivity;
import com.example.androidmvp.mvp.base.BaseFragment;
import com.example.androidmvp.mvp.entity.db.Remark;
import com.example.androidmvp.mvp.entity.db.ShowPage;
import com.example.androidmvp.mvp.entity.UserResult;
import com.example.androidmvp.mvp.residemenu.activity.UserInfoActivity;
import com.example.androidmvp.mvp.show.activity.CreateRemarkActivity;
import com.example.androidmvp.mvp.show.activity.CreateShowPageActivity;
import com.example.androidmvp.mvp.show.adapter.ReCyclerAdapter;
import com.example.androidmvp.mvp.show.presenter.ShowPresenter;
import com.example.androidmvp.mvp.show.view.BaseShowView;
import com.example.androidmvp.mvp.wealth.service.AutoUpdateService;
import com.example.androidmvp.widget.CircleImageView;
import com.example.androidmvp.widget.MyRemarkView;
import com.jaeger.ninegridimageview.NineGridImageView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowFragment extends BaseFragment implements BaseShowView {

    public static final int REQUEST_CODE = 2;

    private static final String TAG = "ShowFragment";
    private Boolean isHeart = false;
    private String tag;
    private static ShowFragment instance = null;
    private ShowPresenter presenter;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private TextView notify;

    UserResult user;
    //    @BindView(R.id.showimages)
    NineGridImageView<String> nineGridImageView;
    //    @BindView(R.id.recycleview)
    RecyclerView recyclerView;

    PullRefreshLayout pullRefreshLayout;
    RecyclerView.LayoutManager layoutManager;
    ReCyclerAdapter adapter;

    CircleImageView headerIcon;
    TextView headerName;
    Button edit;

    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver receiver;

    //增加websocket



    /******************************************************/

    public static ShowFragment getInstance(String tag) {
        if (instance == null) {
            instance = new ShowFragment();
            Bundle bundle = new Bundle();
            bundle.putString("tag", tag);
            instance.setArguments(bundle);
        }
        return instance;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
//        if (savedInstanceState == null) {
//            Bundle bundle = getArguments();
//            tag = bundle.getString("tag");
//            Log.d(TAG, "initData: " + tag);
//        }
        presenter = new ShowPresenter(this);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_show, container, false);
    }

    @Override
    protected void initListener() {

//        //注册广播接收
//        receiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                    int type = intent.getIntExtra("type",-1);
//                    if(type == 1){
//                        updateNotify(intent.getStringExtra("user"));
//                    }
//                    else  if(type == 2){
//                        notifyC(intent.getStringExtra("title"),intent.getStringExtra("content"));
//                        Log.e(TAG, "onReceive: "+ intent.getStringExtra("title"));
//                    }
//            }
//        };
//        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(MainActivity.WEBSOCKETNOTIFY);
//        broadcastManager.registerReceiver(receiver, intentFilter);


        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();

        nineGridImageView = rootView.findViewById(R.id.showimages);
        recyclerView = rootView.findViewById(R.id.recycleview);
        pullRefreshLayout = rootView.findViewById(R.id.swipeRefreshlayout);
        headerIcon = rootView.findViewById(R.id.show_head_user_image);
        headerName = rootView.findViewById(R.id.show_head_user_name);
        edit = rootView.findViewById(R.id.fashuoshuo);

        notify = rootView.findViewById(R.id.notifytv);
        headerName.setOnClickListener(new HeaderItemClickListener());
        headerIcon.setOnClickListener(new HeaderItemClickListener());
        edit.setOnClickListener(new HeaderItemClickListener());


        layoutManager = new LinearLayoutManager(getContext());
        adapter = new ReCyclerAdapter(getContext(), new ArrayList<ShowPage>());
        //添加子item点击事件
        adapter.setOnItemClickListener(new OnItemClickListener());

        adapter.setOnRemarkItemListener(new RemarkItemClickListener());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                loadData();
                //数据刷新完成
                pullRefreshLayout.setRefreshing(false);
                showMessage("更新成功");
            }
        });


    }

    @Override
    public void loadData() {
        loadHeader();
        notify.setVisibility(View.GONE);
        presenter.loadShowPages();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadHeader();
    }

    @Override
    public void loadShowpages(List<ShowPage> data) {
        adapter.setShowPageResults(data);
        adapter.notifyDataSetChanged();
    }

    public void loadHeader() {
        user = new UserResult();
        user.setUsername(preferences.getString("c_user", ""));
        user.setAge(preferences.getInt("c_age", 20));
        user.setEmail(preferences.getString("c_email", ""));
        user.setGender(preferences.getString("c_gender", ""));
        user.setIcon(preferences.getString("c_icon", ""));
        user.setPassword(preferences.getString("c_password", ""));

        headerName.setText(user.getUsername());
        Glide.with(headerIcon.getContext()).load(Constant.Urls.IMAGEURLROOT + user.getIcon()).error(R.drawable.ic_default_image).into(headerIcon);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void setUser(UserResult user) {
        this.user = user;
    }


    public void updateNotify(String user){
        notify.setText(user+"“更新了动态");
        notify.setVisibility(View.VISIBLE);
    }

    public void notifyC(String title,String content){
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);

        Notification notification = new NotificationCompat.Builder(getContext()).setContentTitle(title)
                .setContentText(content).setWhen(System.currentTimeMillis()).setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.laucher)).build();
        manager.notify(1, notification);
    }

    class RemarkItemClickListener implements MyRemarkView.ClickListener {
        @Override
        public void onClickItem(View view, Remark mark) {
            editRemark(mark);
        }

        public void editRemark(Remark remark){
            Bundle bundle = new Bundle();
            bundle.putString("from",user.getUsername());
            bundle.putString("to",remark.getFrom());
            bundle.putString("page",remark.getPage());
            Intent intent = new Intent(getContext(), CreateRemarkActivity.class);
            intent.putExtras(bundle);
            getActivity().startActivityForResult(intent, REQUEST_CODE);
        }
    }

    class OnItemClickListener implements ReCyclerAdapter.OnItemClickListener {
        @Override
        public void onItemClick(View view, List<ShowPage> showPageResults, int position) {
            //点击recycleview item 事件
            Log.d(TAG, "onItemClick: " + position);
            switch (view.getId()) {
                case R.id.dianzan:
                    clickHeart(view);
                    break;
                case R.id.show_remark:
                    editRemark(showPageResults.get(position));
                    break;
                case R.id.edit_remark:
                    editRemark(showPageResults.get(position));
                    break;
                case R.id.show_user_image:
                    //跳转到该用户个人信息
                    Toast.makeText(getContext(),"访问别人信息功能尚在开发",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.show_user_name:
                    //跳转别人的动态页

                    break;
            }
        }

        private void clickHeart(View view) {
            if (!isHeart) {
                ((ImageButton) view).setImageResource(R.drawable.hearclick);
                isHeart = true;
            } else {
                ((ImageButton) view).setImageResource(R.drawable.ic_heart_fill);
                isHeart = false;
            }
        }

        private void editRemark(ShowPage page) {
            Bundle bundle = new Bundle();
            bundle.putString("from",user.getUsername());
//            bundle.putString("to",null);
            bundle.putString("page",String.valueOf(page.getId()));
            Intent intent = new Intent(getContext(), CreateRemarkActivity.class);
            intent.putExtras(bundle);
            getActivity().startActivityForResult(intent, REQUEST_CODE);
        }

    }

    //显示头上的组件动作事件
    class HeaderItemClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fashuoshuo:
                    editShow();
                    break;
                case R.id.show_head_user_image:
                    //跳转到我的信息
                    toMysUser();
                    break;
                case R.id.show_head_user_name:
                    //跳转到我的信息
                    toMysUser();
                    break;
                default:
                    break;

            }
        }

        public void editShow() {
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);

            Intent intent = new Intent(getContext(), CreateShowPageActivity.class);
            intent.putExtras(bundle);
            getActivity().startActivityForResult(intent, REQUEST_CODE);
        }

        public void toMysUser(){
            Bundle bundle = new Bundle();
            bundle.putSerializable("user",user);
            bundle.putString("own","Y");
            Intent intent = new Intent(getContext(), UserInfoActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }


}
