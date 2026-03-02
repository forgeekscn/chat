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

    // User session data (consider using ViewModel in future refactoring)
    public static String token = null;
    public static String username = null;
    public static boolean isLogin = false;
    public static double x = 0;
    public static double y = 0;
    public static boolean islogout = false;
    public static SharedPreferences sharedPreferences = null;

    // Server IP moved to BuildConfig for security
    public static String getServerIp() {
        return BuildConfig.SERVER_IP;
    }

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

