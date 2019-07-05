package com.example.androidmvp.mvp;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.androidmvp.MyApplication;
import com.example.androidmvp.R;
import com.example.androidmvp.common.Constant;
import com.example.androidmvp.mvp.base.BaseActivity;
import com.example.androidmvp.mvp.entity.UserResult;
import com.example.androidmvp.mvp.residemenu.activity.UserInfoActivity;
import com.example.androidmvp.mvp.residemenu.activity.UserShowActivity;
import com.example.androidmvp.mvp.show.fragment.ShowFragment;
import com.example.androidmvp.mvp.show.service.JWebSocketClientService;
import com.example.androidmvp.util.JWebSocketClient;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.wakehao.bar.BottomNavigationBar;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.NotificationCompat.VISIBILITY_PUBLIC;

public class MainActivity extends BaseActivity {

    public static final String CURRENTUSERINFO = "com.example.currentuser.user";
    public static final String WEBSOCKETNOTIFY = "com.example.websocket.notify";
    private static final String TAG = "MainActivity";

    public static Context context;
    static float x_o = 0;
    static float x_n = 0;
    private ResideMenu resideMenu;

    private List<ResideMenuItem> items = new ArrayList<>();
    private BottomNavigationBar bar;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private UserResult result;
    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver receiver;
    private MessageReceiver messageReceiver;

//    public static JWebSocketClient client = new JWebSocketClient(URI.create(Constant.Urls.WEBSOCKET)){
//        @Override
//        public void onMessage(String message) {
//            Log.e(TAG, "onMessage: "+message);
//            try {
//                JSONObject object = new JSONObject(message);
//                if(object.isNull("type")){
//
//                }else {
//                    sendBroadCast(object);
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            super.onMessage(message);
//        }

//        @Override
//        public void onOpen(ServerHandshake handshakedata) {
//            Log.e(TAG, "onOpen: "+"websocket连接成功" );
//            super.onOpen(handshakedata);
//        }
//    };

    private Context mContext;
    public static JWebSocketClient client;
    private JWebSocketClientService.JWebSocketClientBinder binder;
    public static JWebSocketClientService jWebSClientService;


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e("MainActivity", "服务与活动成功绑定");
            binder = (JWebSocketClientService.JWebSocketClientBinder) iBinder;
            jWebSClientService = binder.getService();
            client = jWebSClientService.client;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("MainActivity", "服务与活动成功断开");
        }
    };


    private void sendNotification(String title,String content) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                // 设置该通知优先级
                .setPriority(Notification.PRIORITY_MAX)
                .setSmallIcon(R.mipmap.laucher)
                .setContentTitle(title)
                .setContentText(content)
                .setVisibility(VISIBILITY_PUBLIC)
                .setWhen(System.currentTimeMillis())
                // 向通知添加声音、闪灯和振动效果
                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_ALL | Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent)
                .build();
        notifyManager.notify(11, notification);//id要保证唯一
    }

    private class MessageReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String message=intent.getStringExtra("message");
            Log.e(TAG, "onReceive: "+message );
            try {
                JSONObject object = new JSONObject(message);
                int type = object.getInt("type");
                if(type == 2){
                    sendNotification(object.getString("title"),object.getString("content"));
                }
                if(type == 1){
                    ((ShowFragment)bar.getFragment(1)).updateNotify(object.getString("user"));
                    sendNotification("新动态",object.getString("user")+"发表了新动态");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 绑定服务
     */
    private void bindService() {
        Intent bindIntent = new Intent(mContext, JWebSocketClientService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
    }
    /**
     * 启动服务（websocket客户端服务）
     */
    private void startJWebSClientService() {
        Intent intent = new Intent(mContext, JWebSocketClientService.class);
        startService(intent);
    }
    /**
     * 动态注册广播
     */
    private void doRegisterReceiver() {
        messageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter("com.xch.servicecallback.content");
        registerReceiver(messageReceiver, filter);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private boolean isNotificationEnabled(Context context) {

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 如果没有开启通知，跳转至设置界面
     *
     * @param context
     */
    private void setNotification(Context context) {
        Intent localIntent = new Intent();
        //直接跳转到应用通知设置的代码：
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            localIntent.putExtra("app_package", context.getPackageName());
            localIntent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            localIntent.addCategory(Intent.CATEGORY_DEFAULT);
            localIntent.setData(Uri.parse("package:" + context.getPackageName()));
        } else {
            //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.setAction(Intent.ACTION_VIEW);
                localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
                localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
            }
        }
        context.startActivity(localIntent);
    }


    private void checkNotification(final Context context) {
        if (!isNotificationEnabled(context)) {
            new AlertDialog.Builder(context).setTitle("温馨提示")
                    .setMessage("你还未开启系统通知，将影响消息的接收，要去开启吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setNotification(context);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        }
    }



    private void initResideMenu() {
        resideMenu = new ResideMenu(this);
        resideMenu.attachToActivity(this);
        resideMenu.setBackground(R.drawable.menubak);
        ItemClickListener listener = new ItemClickListener();

        String[] titles = {"千机阁", "听雨轩", "退出登录", "关于我们"};
        int[] icons = {R.drawable.dot_selected, R.drawable.dot_selected,
                R.drawable.dot_selected, R.drawable.dot_selected};
        for (int i = 0; i < titles.length; i++) {
            ResideMenuItem item = new ResideMenuItem(this, icons[i], titles[i]);
            item.setOnClickListener(listener);
            items.add(item);
            resideMenu.addMenuItem(item, ResideMenu.DIRECTION_LEFT);
        }

        //禁用右边菜单
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        //添加Fragment忽略

        FrameLayout ignore = findViewById(R.id.fragment_container);
        resideMenu.addIgnoredView(ignore);
    }

    private void fullScreen() {
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


        if (ev.getAction() == MotionEvent.ACTION_DOWN)
            x_o = ev.getX();
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            x_n = ev.getX();
            if (x_o < 100 && ev.getX() > x_o) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        }

        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    protected void loadViewLayout() {
        fullScreen();
        setContentView(R.layout.activity_main);
        mContext=MainActivity.this;

        startJWebSClientService();
        //绑定服务
        bindService();
        //注册广播
        doRegisterReceiver();
        //检测通知是否开启
        checkNotification(mContext);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(client != null && client.isOpen()) {
                        JSONObject object = new JSONObject();
                        try {
                            object.put("type",2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        jWebSClientService.sendMsg(object.toString());
                    }

                    try {
                        Thread.sleep(1000*60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    @Override
    protected void findViewById() {
        bar = findViewById(R.id.bottombar);
        preferences = getPreferences(MODE_PRIVATE);
        editor = preferences.edit();

        //信息更新接受广播
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                result = (UserResult) intent.getSerializableExtra("user");
                cacheUser();
            }
        };
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(UserInfoActivity.USER_INFO_UPDATE_BROADCAST);
        broadcastManager.registerReceiver(receiver, filter);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    client.connectBlocking();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();


    }

    @Override
    protected void setListener() {

        initResideMenu();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if(client.isClosed()) {
//                    try {
//                        client.connectBlocking();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                client.send("{\"type\":2}");
//                ViewCompat.postOnAnimationDelayed(bar,this,1000*60);
//            }
//        }).start();
    }

    @Override
    protected void processLogic() {
        Bundle bundle = getIntent().getExtras();
        result = (UserResult) bundle.getSerializable("user");

        cacheUser();
    }

    //缓存用户信息
    public void cacheUser() {
        editor.putString("c_user", result.getUsername());
        editor.putString("c_email", result.getEmail());
        editor.putString("c_gender", result.getGender());
        editor.putString("c_icon", result.getIcon());
        editor.putInt("c_age", result.getAge());
        editor.putString("c_password", result.getPassword());
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

        if (requestCode == ShowFragment.REQUEST_CODE) {
            bar.setItemSelected(1, false);
            ((ShowFragment) bar.getFragment(1)).loadData();


        }
    }

   public static void sendBroadCast(JSONObject object) throws JSONException {
        Intent intent = new Intent(WEBSOCKETNOTIFY);
        int type = object.getInt("type");
        intent.putExtra("type",type);
        if(type == 1){
            intent.putExtra("user",object.getString("user"));
        }else if(type == 2){
            intent.putExtra("title",object.getString("title"));
            intent.putExtra("content",object.getString("content"));
        }

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

   }

    @Override
    protected void onResume() {
        super.onResume();
//        if(client.isClosed()){
//            try {
//                client.connectBlocking();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    //点击菜单逻辑
    class ItemClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if (v == items.get(0)) {
                //跳转个人信息
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", result);
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            } else if (v == items.get(1)) {
                //跳转个人动态
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", result);
                Intent intent = new Intent(MainActivity.this, UserShowActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            } else if (v == items.get(2)) {
                //退出
                MyApplication.getInstance().exit();
            }

        }
    }
}
