package com.hechao.chat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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


    @InjectView(R.id.username)
    EditText username;

    @InjectView(R.id.password)
    EditText password;

    @InjectView(R.id.code)
    EditText code;

    @InjectView(R.id.getCode)
    Button getcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register1);

        ButterKnife.inject(this);


    }

    @OnClick(R.id.getCode)
    void setGetcode() {

        codenum = (int) Math.random() * 1000;
        SMSAPIDeal();
        getcode.setText("耐心等待10s");
        getcode.setClickable(false);

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

            if (code.getText().toString().equals("" + codenum)) {
            }
            AsyncHttpClient client = new AsyncHttpClient();
//                    RequestParams params = new RequestParams();
//                    params.add("username", user_name);
//                    params.add("password", pass_word);
//            http://localhost/chat/reg.php?username=ll&password=123
            String url = "http://" + App.ip + "/chat/reg.php?username=" + username.getText().toString() + "&password=" + password.getText().toString();
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


    int codenum = 0;


    /**
     * 短信验证码处理
     */
    private void SMSAPIDeal() {


        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("apikey", "34ca0f7732b7f0718ad418e3e7d6ed08");
        params.put("mobile", username.getText().toString());
        params.put("text", "【纺大阳光】你好，请保存此验证码" + codenum + "，作为入场的唯一凭证，请妥善保管！");
        String url = "https://sms.yunpian.com/v1/sms/send.json";

        Log.e("hechao", "begin sms...");
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                String response = new String(bytes);
                JSONObject jsonObject = new JSONObject();

                Log.e("hechao", "SMSresponse:" + new String(bytes));

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.e("hechao", "error");
            }
        });


    }


}


