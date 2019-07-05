package com.example.androidmvp.data.test;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.androidmvp.R;
import com.example.androidmvp.util.JWebSocketClient;

public class Test2Activity extends AppCompatActivity {

    private static final String TAG = "Test2Activity";
    private Context mContext;
    private JWebSocketClient client;


    private ChatMessageReceiver chatMessageReceiver;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e("MainActivity", "服务与活动成功绑定");

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("MainActivity", "服务与活动成功断开");
        }
    };

    private class ChatMessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String message=intent.getStringExtra("message");
            Log.d(TAG, "onReceive: "+message);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        mContext=Test2Activity.this;
        //启动服务

        Log.d(TAG, "onCreate: "+"dasdas");

        Log.d(TAG, "onCreate: "+"dasdas");
        //注册广播
        doRegisterReceiver();
        //检测通知是否开启
        Log.d(TAG, "onCreate: "+"dasdas");

    }


    private void doRegisterReceiver() {
        chatMessageReceiver = new ChatMessageReceiver();
        IntentFilter filter = new IntentFilter("com.xch.servicecallback.content");
        registerReceiver(chatMessageReceiver, filter);
    }


//    URI uri = URI.create(Constant.Urls.WEBSOCKET);
//    client = new MyWebSocketClient(uri){
//        public void onMessage(String message) {
//            try {
//                JSONObject aa = new JSONObject(message);
//                Log.e(TAG, "onMessage: "+aa.getString("user") );
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            Log.e("JWebSocketClientService", "收到的消息：" + message);
//
//        }
//
//        @Override
//        public void onOpen(ServerHandshake handshakedata) {
//            super.onOpen(handshakedata);
//            Log.e("JWebSocketClientService", "websocket连接成功");
//        }
//    };
//
//        try {
//        client.connectBlocking();
//    } catch (InterruptedException e) {
//        e.printStackTrace();
//    }
//
//    JSONObject message = new JSONObject();
//        try {
//        message.put("type",1);
//        message.put("user","dejavun");
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }
//        client.send(message.toString());
}
