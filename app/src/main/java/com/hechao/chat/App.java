package com.hechao.chat;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.baidu.mapapi.SDKInitializer;
import com.tencent.bugly.crashreport.CrashReport;

import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2016/3/25.
 */
public class App extends Application {

    public static String token = null;
    public static String username = null;
    public static boolean isLogin = false;
    public static String ip="51052e61.nat123.net";
//    public static String ip="10.176.152.24";
    public static double x=0;
    public static double y=0;
    public static boolean islogout=false;
    public static SharedPreferences sharedPreferences=null;

    @Override
    public void onCreate() {

        super.onCreate();
        //    融云sdk初始化
        RongIM.init(this);
        //   百度地图sdk初始化
        SDKInitializer.initialize(this);
        // 聚合数据sdk初始化
        App.sharedPreferences=this.getSharedPreferences("sp", Context.MODE_PRIVATE);
    }


}

