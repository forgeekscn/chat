package com.hechao.chat;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Administrator on 2016/3/29.
 */
public class BaiduMapActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.baidumap);
    }
}
