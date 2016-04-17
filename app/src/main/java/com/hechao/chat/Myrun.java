package com.hechao.chat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

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

//        initData();

        try {
            setImage("http://" + App.ip + "/chat/pic/" + App.username + ".png");
        } catch (Exception e) {
            e.printStackTrace();
        }






    }


    void setImage(String address) throws Exception {
        //通过代码 模拟器浏览器访问图片的流程

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(address, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                profilePic.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });


    }


//
//    @OnClick(R.id.profilePic)
//    void profilePic(){
//
////        Bitmap bitmap = getLoacalBitmap("/mnt/sdcard/aaa/hhh/temp.png"); //从本地取图片(在cdcard中获取)  //
////        profilePic.setImageBitmap(bitmap); //设置Bitmap
//
//        Intent intent = new Intent(Myrun.this,UploadPicActivity.class);
//        startActivity(intent);
//
//    }


    @OnClick(R.id.seemydata)
    void setMyData() {

        Intent intent = new Intent(Myrun.this, Myprofile.class);
        startActivity(intent);

    }


    /**
     * 加载本地图片
     *
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
    void editProfile() {
        Intent intent = new Intent(Myrun.this, EditProfile.class);
        startActivity(intent);

    }


}
