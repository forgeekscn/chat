package com.hechao.chat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/9.
 */
public class Myrun extends Activity {

    @InjectView(R.id.profilePic)
    ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myrun);

        ButterKnife.inject(Myrun.this);

    }


    @OnClick(R.id.profilePic)
    void profilePic(){

        Bitmap bitmap = getLoacalBitmap("/mnt/sdcard/aaa/hhh/temp.png"); //从本地取图片(在cdcard中获取)  //
        profilePic.setImageBitmap(bitmap); //设置Bitmap

        Intent intent = new Intent(Myrun.this,UploadPicActivity.class);
        startActivity(intent);

    }

    /**
     * 加载本地图片
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @OnClick(R.id.profile)
    void editProfile(){
        Intent intent= new Intent(Myrun.this,EditProfile.class);
        startActivity(intent);

    }


}
