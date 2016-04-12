package com.hechao.chat;

import android.app.Application;

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
    public static String ip="10.176.174.185";
    public static double x=0;
    public static double y=0;

    @Override
    public void onCreate() {

        super.onCreate();
        //    融云sdk初始化
        RongIM.init(this);
        //   百度地图sdk初始化
        SDKInitializer.initialize(this);
        // 聚合数据sdk初始化
    }


}

