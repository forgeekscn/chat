package com.hechao.chat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/4/9.
 */
public class EditProfile extends Activity {


    @InjectView(R.id.sexual)
    EditText sexual;
    @InjectView(R.id.name)
    EditText name;
    @InjectView(R.id.height)
    EditText height;
    @InjectView(R.id.weight)
    EditText weight;
    @InjectView(R.id.classname)
    EditText classname;
    @InjectView(R.id.mywords)
    EditText mywords;

    @InjectView(R.id.photo123)
    ImageView photo123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        ButterKnife.inject(this);

        try {
            setImage("http://"+App.ip+"/chat/pic/"+App.username+".png");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @OnClick(R.id.photo123)
    void setPhoto123(){

        Intent intent = new Intent(EditProfile.this,UploadPicActivity.class);
        startActivity(intent);
    }


    void setImage(String address) throws Exception{
        //通过代码 模拟器浏览器访问图片的流程

        AsyncHttpClient client=new AsyncHttpClient();

        client.get(address, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                photo123.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }




    @OnClick(R.id.finish)
     void setMyProfile() {
        AsyncHttpClient client= new AsyncHttpClient();
        String url="http://"+App.ip+"/chat/setData.php";
        RequestParams params= new RequestParams();
        params.add("sexual",sexual.getText().toString());
        params.add("username",App.username);
        params.add("name",name.getText().toString());
        params.add("mywords",mywords.getText().toString());
        params.add("height",height.getText().toString());
        params.add("weight",weight.getText().toString());
        params.add("classname",classname.getText().toString());

        client.post(url,params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                String response=new String(bytes) ;
                Log.e("hechao",response);

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }


}
