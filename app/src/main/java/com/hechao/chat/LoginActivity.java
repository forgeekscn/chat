package com.hechao.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.bugly.crashreport.CrashReport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
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
    @InjectView(R.id.gotoRegister)
    Button gotologin;

    private Handler mHandler;
    public static final int RECEIVE_CODE = 1;
    private SmsObserver observer;
    private AlertDialog progressDialog;


//    @InjectView(R.id.gifView2)
//    GifView gifView2;

//    @InjectView(R.id.sensor)
//    TextView sensor;


//    @InjectView(R.id.baidumap)
//    Button baidumap;

    SensorManager sensorManager;
    StationData stationData = new StationData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login1);

        bugly();
        ButterKnife.inject(this);

        if (App.sharedPreferences.getBoolean("isloged", false)) {
            App.isLogin = true;
            App.token = App.sharedPreferences.getString("token", "");
            App.username = App.sharedPreferences.getString("username", "");

            Intent intent = new Intent(LoginActivity.this, main.class);
            startActivity(intent);
            finish();
        }
    }


//    @OnClick(R.id.leftright)
//    void leftrightPage(){
//        LeftRightActivity leftRightActivity= new LeftRightActivity(this);
//        setContentView(leftRightActivity);
//    }

//    @OnClick(R.id.baidumap)
//    void  getBaidumap(){
//
//        Intent intent= new Intent(LoginActivity.this , BaiduMapActivity.class) ;
//        startActivity(intent);
//
//    }


    @OnClick(R.id.gotoRegister)
    void gotologinPage() {
        Intent intent = new Intent(LoginActivity.this, Register1.class);
        startActivity(intent);
    }


    //传感器测试
//    private void sensorTest() {
//        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
//        for (Sensor sensor1 : sensorList) {
//
//            sensor.append(sensor1.getName() + "\n");
//        }
//    }
//

    private void bugly() {
        CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(getApplicationContext());
        userStrategy.setAppChannel("何超");
        userStrategy.setAppVersion("Chat V1.0");
        userStrategy.setAppReportDelay(5000);

        CrashReport.initCrashReport(getApplicationContext(), BuildConfig.API_KEY_BUGLY, true, userStrategy);
        CrashReport.setUserId("hechao");
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // Fix memory leak: dismiss dialog
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        super.onDestroy();
    }

    /**
     * 试用
     */
    @OnClick(R.id.tryacount)
    void tryAcount() {
        Intent intent = new Intent(LoginActivity.this, main.class);
        startActivity(intent);
        finish();
    }

    /**
     * 输入验证
     */
    private boolean validateInput(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password == null || password.trim().isEmpty()) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (username.length() < 3 || username.length() > 20) {
            Toast.makeText(this, "用户名长度必须在 3-20 之间", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "密码长度必须至少 6 位", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Filter SQL injection characters
        if (username.matches(".*[;'\"\\\\].*")) {
            Toast.makeText(this, "用户名包含非法字符", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 登陆处理函数
     */
    @OnClick(R.id.login)
    public void login() {
        String usernameStr = username.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();

        if (!validateInput(usernameStr, passwordStr)) {
            return;
        }

        progressDialog = new AlertDialog.Builder(LoginActivity.this)
            .setIcon(R.drawable.rc_progress_sending_style)
            .setTitle("正在登陆")
            .create();
        progressDialog.show();

        App.sharedPreferences.edit().putBoolean("isloged", true).commit();
        SharedPreferences.Editor editor = App.sharedPreferences.edit();
        editor.putString("username", usernameStr);
        editor.commit();

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://" + App.getServerIp() + "/chat/login.php";
        
        // Fix SQL injection: use POST with RequestParams
        RequestParams params = new RequestParams();
        params.put("username", usernameStr);
        params.put("password", passwordStr);
        
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                String response = new String(bytes);

                if (response.equals("error")) {
                    Log.e("hechao", "Login failed");
                    Toast.makeText(LoginActivity.this, "登录失败，用户名或密码错误", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("hechao", "response:" + response);
                    App.token = response;
                    App.sharedPreferences.edit().putString("token", response).commit();
                    App.username = usernameStr;
                    App.isLogin = true;

                    Intent intent = new Intent(LoginActivity.this, main.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("hechao", "Network error", throwable);
                Toast.makeText(LoginActivity.this, "网络错误，请检查网络连接", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
