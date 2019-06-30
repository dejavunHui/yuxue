package com.example.androidmvp.mvp.wealth.weatherutil;

import java.util.Calendar;
import java.util.TimeZone;

public class ConstantUtil {
        public static Calendar cal = Calendar.getInstance();
    public static String getTime(){
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int hour;
        if (cal.get(Calendar.AM_PM) == 0)
            hour = cal.get(Calendar.HOUR);
        else
            hour = cal.get(Calendar.HOUR) + 12;
        if(hour > 1 && hour <= 3) return "丑时";
        else if(hour > 3 && hour <= 5) return "寅时";
        else if(hour > 5 && hour <= 7) return "卯时";
        else if(hour > 7 && hour<=9)return "辰时";
        else if(hour > 9 && hour <= 11)return "巳时";
        else if(hour > 11 && hour <= 13) return "午时";
        else if(hour > 13 && hour <= 15) return "未时";
        else if(hour > 15 && hour <= 17) return "申时";
        else if(hour > 17 && hour <= 19) return "酉时";
        else if(hour > 19 && hour <= 21) return "戌时";
        else if(hour>21 && hour <= 23) return "亥时";
        else return "子时";
    }

    public static String getTimeInfo(){
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int hour;
        if (cal.get(Calendar.AM_PM) == 0)
            hour = cal.get(Calendar.HOUR);
        else
            hour = cal.get(Calendar.HOUR) + 12;
        if(hour > 1 && hour <= 3) return "鸡鸣娇娇";
        else if(hour > 3 && hour <= 5) return "黎明前夕";
        else if(hour > 5 && hour <= 7) return "旭日初生";
        else if(hour > 7 && hour<=9)return "晨曦微光";
        else if(hour > 9 && hour <= 11)return "日禺光暖";
        else if(hour > 11 && hour <= 13) return "日正午长";
        else if(hour > 13 && hour <= 15) return "盛日初斜";
        else if(hour > 15 && hour <= 17) return "日薄西山";
        else if(hour > 17 && hour <= 19) return "夕阳余辉";
        else if(hour > 19 && hour <= 21) return "夜幕初降";
        else if(hour>21 && hour <= 23) return "夜深人静";
        else return "万籁俱寂";
    }
}
