package com.example.androidmvp.util;

import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.TextView;

import com.example.androidmvp.R;
import com.xenione.digit.TabDigit;

import java.util.Calendar;
import java.util.TimeZone;

public class TimebarUtil {

    private View rootView;
    TabDigit hour2;
    TabDigit hour1;
    TabDigit min2;
    TabDigit min1;
    TabDigit span1;


    public TimebarUtil(View view) {
        this.rootView = view;
    }

    private void initCompent(){
        hour2 = rootView.findViewById(R.id.hour2);
        hour1 = rootView.findViewById(R.id.hour1);

        min2 = rootView.findViewById(R.id.minu2);
        min1 = rootView.findViewById(R.id.minu1);
        span1 = rootView.findViewById(R.id.span1);
    }

    public void setTime(){
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int hour;
        int minute;
        if (cal.get(Calendar.AM_PM) == 0)
            hour = cal.get(Calendar.HOUR);
        else
            hour = cal.get(Calendar.HOUR) + 12;
        minute = cal.get(Calendar.MINUTE);

        int h2;
        int h1;
        int m2;
        int m1;
        if (hour >= 10) {
            h2 = hour / 10;
            h1 = hour % 10;
        } else {
            h2 = 0;
            h1 = hour;
        }

        if (minute >= 10) {
            m2 = minute / 10;
            m1 = minute % 10;
        } else {
            m2 = 0;
            m1 = minute;
        }
        hour2.setChar(h2);
        hour1.setChar(h1);
        min2.setChar(m2);
        min1.setChar(m1);
        span1.setChars(":".toCharArray());
    }
    public void startClock(){
        initCompent();
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        final int second = cal.get(Calendar.SECOND);
        new Thread(new Runnable() {
            @Override
            public void run() {
                setTime();
                ViewCompat.postOnAnimationDelayed(min1,this,second*1000-60000);
            }
        }).start();

    }
}
