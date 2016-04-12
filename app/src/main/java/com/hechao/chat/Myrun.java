package com.hechao.chat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

        initData();


    }

    private void initData() {

        AsyncHttpClient client= new AsyncHttpClient();
        String url="http://"+App.ip+"/chat/getAllUserInfor.php?username="+App.username;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                String response= new String(bytes);
                try {
                    JSONObject json=new JSONObject(response);
                    ((Button)(findViewById(R.id.a1)) ).setText(json.getDouble("TOTALDISTANCE")+"");


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                ((Button)(findViewById(R.id.a2)) ).setText(response);
                ((Button)(findViewById(R.id.a3)) ).setText("");
                ((Button)(findViewById(R.id.a4)) ).setText("");
                ((Button)(findViewById(R.id.a5)) ).setText("");
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
                
        });

//
//        ((Button)(findViewById(R.id.a1)) ).setText("");
//        ((Button)(findViewById(R.id.a2)) ).setText("");
//        ((Button)(findViewById(R.id.a3)) ).setText("");
//        ((Button)(findViewById(R.id.a4)) ).setText("");
//        ((Button)(findViewById(R.id.a5)) ).setText("");


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
