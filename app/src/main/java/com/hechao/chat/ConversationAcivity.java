package com.hechao.chat;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import io.rong.imkit.RongIM;

/**
 * 私聊窗口
 * Created by Administrator on 2016/3/25.
 */
public class ConversationAcivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.conversation);


    }
}
