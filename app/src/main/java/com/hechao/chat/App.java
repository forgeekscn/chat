package com.hechao.chat;

import android.app.Application;

import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2016/3/25.
 */
public class App extends Application {

    public static String token=null;
    public static String username=null;
    public static boolean isLogin=false;

    @Override
    public void onCreate() {
        super.onCreate();

        RongIM.init(this);

    }


}

