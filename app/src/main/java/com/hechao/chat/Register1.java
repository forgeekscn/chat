package com.hechao.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/4/9.
 */
public class Register1 extends Activity {

    String ip = "10.176.172.177";

    @InjectView(R.id.username)
    EditText username;

    @InjectView(R.id.password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register1);

        ButterKnife.inject(this);


    }


    /**
     * 注册处理函数
     */
    @OnClick(R.id.register)
    void regist() {


        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        AlertDialog.Builder builder = new AlertDialog.Builder(Register1.this);
        //    设置Title的图标
        builder.setIcon(R.drawable.rc_progress_sending_style);
        //    设置Title的内容
        builder.setTitle("正在提交");
        builder.show();



        Log.e("hechao", "register clicked");
        if (username.getText().toString() == "" || password.getText().toString() == "") {
            Toast.makeText(Register1.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            AsyncHttpClient client = new AsyncHttpClient();
//                    RequestParams params = new RequestParams();
//                    params.add("username", user_name);
//                    params.add("password", pass_word);
            String url = "http://"+ip+"/chat/reg.php?username=" + username.getText().toString() + "&password=" + password.getText().toString();
            Log.e("hechao", url);
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {

                    String response = new String(bytes);
                    Log.e("hechao", "response:" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("code");

                        if (!status.equals("200")) {
                            Log.e("hechao", "error");
                        } else {
                            Log.e("hechao", "register success");
                            String token = jsonObject.getString("token");
                            Log.e("hechao", "token: " + token);
                            App.token = token;
                            App.username = username.getText().toString();
                            App.isLogin = true;
                            Intent intent = new Intent(Register1.this, main.class);
                            startActivity(intent);
                            finish();

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Toast.makeText(Register1.this, "network error", Toast.LENGTH_SHORT).show();

                }
            });


        }
    }

}


