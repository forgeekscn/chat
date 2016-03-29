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

    @Override
    public void onCreate() {
        super.onCreate();

        //    融云sdk初始化
        RongIM.init(this);

        //   百度地图sdk初始化
        SDKInitializer.initialize(this);

        // 聚合数据sdk初始化
//        com.thinkland.sdk.android.JuheSDKInitializer.initialize(this);

    }


}

