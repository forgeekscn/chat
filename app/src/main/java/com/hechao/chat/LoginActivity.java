package com.hechao.chat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Observer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * 登陆
 */
public class LoginActivity extends Activity {


    @InjectView(R.id.user_name)
    EditText username;
    @InjectView(R.id.password)
    EditText password;

    @InjectView(R.id.login)
    TextView login;
    @InjectView(R.id.register)
    TextView register;

    @InjectView(R.id.phone)
    EditText phone;
    @InjectView(R.id.code)
    EditText code;
    @InjectView(R.id.getCode)
    Button getCode;

    private Handler mHandler;
    public static final int RECEIVE_CODE = 1;

    private SmsObserver observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ButterKnife.inject(this);


        //短信验证码处理
        SMSAPIDeal();

        //验证码处理
        dealCode();


    }


    /**
     * 短信验证码处理
     */
    private void SMSAPIDeal() {


        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("apikey", "34ca0f7732b7f0718ad418e3e7d6ed08");
        params.put("mobile", "13720308660");
        params.put("code", "110");
        params.put("text", "code is :");
        String url = "https://sms.yunpian.com/v1/sms/send.json";

        Log.e("hechao", "begin sms...");
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                Log.e("hechao", "SMSresponse:" + new String(bytes));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.e("hechao", "error");
            }
        });


//
//        /**************** 查账户信息调用示例 *****************/
//        try {
//            String apikey = "34ca0f7732b7f0718ad418e3e7d6ed08";
//            //修改为您要发送的手机号
//            String mobile = URLEncoder.encode("13720308660", "UTF-8");
//            Log.e("hechao", "userinfo->" + YunPianAPI.getUserInfo(apikey));
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }


    }


    /**
     * 验证码处理
     */
    private void dealCode() {
        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //handler初始化
                mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {

                        if (msg.what == RECEIVE_CODE) {
                            String code1 = (String) msg.obj;
                            code.setText(code1);
                        }

                    }
                };

                //验证码处理
                observer = new SmsObserver(LoginActivity.this, mHandler);
                Uri uri = Uri.parse("content://sms");
                getContentResolver().registerContentObserver(uri, true, observer);

            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
//        getContentResolver().unregisterContentObserver(observer);
    }

    /**
     * 注册处理函数
     */
    @OnClick(R.id.register)
     void regist() {

        Log.e("hechao", "register clicked");
        if (username.getText().toString() == "" || password.getText().toString() == "") {
            Toast.makeText(LoginActivity.this, "invalid form", Toast.LENGTH_SHORT).show();
        } else {
            AsyncHttpClient client = new AsyncHttpClient();
//                    RequestParams params = new RequestParams();
//                    params.add("username", user_name);
//                    params.add("password", pass_word);
            String url = "http://10.176.137.250/chat/reg.php?username=" + username.getText().toString() + "&password=" + password.getText().toString();
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
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Toast.makeText(LoginActivity.this, "network error", Toast.LENGTH_SHORT).show();

                }
            });


        }

    }


    /**
     * 登陆处理函数
     */
    @OnClick(R.id.login)
    public void login() {

        if (username.getText().toString() == "" || password.getText().toString() == "") {
            Toast.makeText(LoginActivity.this, "invalid form", Toast.LENGTH_SHORT).show();
        } else {

            AsyncHttpClient client = new AsyncHttpClient();
            String url = "http://10.176.137.250/chat/login.php?username=" + username.getText().toString() + "&password=" + password.getText().toString();
            Log.e("hechao", url);
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {


                    String response = new String(bytes);


                    if (response.equals("error")) {

                    } else {
                        Log.e("hechao", "response:" + response);

                        App.token = response;
                        App.username = username.getText().toString();
                        App.isLogin = true;

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }


                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                }


            });

        }
    }


}
