package com.hechao.chat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * Created by Administrator on 2016/3/26.
 */
public class LoginActivity extends Activity {


    private EditText username;
    private EditText password;
    private TextView login;
    private TextView register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);

        login = (TextView) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


                            if(response.equals("error")){

                            }else {
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


        });








        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });








    }









}
