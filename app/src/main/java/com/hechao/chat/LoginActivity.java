package com.hechao.chat;

import android.app.Activity;
import android.content.Intent;
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
//    @InjectView(R.id.register)
//    TextView register;
//
//    @InjectView(R.id.phone)
//    EditText phone;
//    @InjectView(R.id.code)
//    EditText code;
//    @InjectView(R.id.getCode)
//    Button getCode;

    @InjectView(R.id.gotoRegister)
    Button gotologin;

    private Handler mHandler;
    public static final int RECEIVE_CODE = 1;

    private SmsObserver observer;


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

        //crash处理
//        bugly();

        //view注解
        ButterKnife.inject(this);


        //传感器测试
//        sensorTest();


        //gif图片显示
//        drawGIF();

        //短信处理
//        SMSAPIDeal();


//
//        city	string	是	城市名urlencode utf8;
//        keywords	string	否	关键字urlencode utf8;
//        page	int	否	页数，默认1
//        format	int	否	格式选择1或2，默认1
//                key

        //加油站
//        stationData.juheOil();

//        stationData .getStationData(100,100,1000);

        //百度map init


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


    //juhe加油站
    private void juheOil() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("city", "黄冈");
        params.add("key", "10eaa1e59ceeb2cbcd1ac446da43e3a9");
//        params.add("keywords","");
//        params.add("page","");
//        params.add("format","");
        String url = "http://apis.juhe.cn/oil/region";
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
//                Log.e("hechao", "" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

//                    double baiduX=jsonObject.getDouble("lay");
//                    double baiduY=jsonObject.getDouble("lon");
//                    Double X= jsonArray.getDouble();
//                    Double Y= jsonArray.getDouble();

//                    Log.e("hechao","");


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.e("hechao", "聚合加油站post->response" + response);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
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

    //crash处理
    private void bugly() {

        //用户策略
        CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(getApplicationContext());
        userStrategy.setAppChannel("何超");
        userStrategy.setAppVersion("Chat V1.0");
        userStrategy.setAppReportDelay(5000);


        CrashReport.initCrashReport(getApplicationContext(), "900023713", true, userStrategy);
        CrashReport.setUserId("hechao");

//        int i=1/0;


//        System.loadLibrary("BuglyNativeDemo");


    }


    /**
     * gif图片显示
     */
//    private void drawGIF() {
//        Log.e("hechao","begin draw gif...");
//        try {
//            InputStream inputStream = getAssets().open("xinhengjieyi.gif");
//            gifView2.setGifStream(inputStream);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * 短信验证码处理
     */
//    private void SMSAPIDeal() {
//
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.put("apikey", "34ca0f7732b7f0718ad418e3e7d6ed08");
//        params.put("mobile", phone.getText().toString());
//        params.put("text", "【纺大阳光】你好，请保存此验证码123456，作为入场的唯一凭证，请妥善保管！");
//        String url = "https://sms.yunpian.com/v1/sms/send.json";
//
//        Log.e("hechao", "begin sms...");
//        client.post(url, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//
//                String response = new String(bytes);
//                JSONObject jsonObject = new JSONObject();
//
//                Log.e("hechao", "SMSresponse:" + new String(bytes));
//
//            }
//
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                Log.e("hechao", "error");
//            }
//        });


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
//
//
//    }


    /**
     * 验证码处理
     */
//    @OnClick(R.id.getCode)
//    public void dealCode() {
//
//        SMSAPIDeal();
//        Log.e("hechao", "begin deal with code...");
//        //handler初始化
//        mHandler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//
//                if (msg.what == RECEIVE_CODE) {
//                    String code1 = (String) msg.obj;
//                    code.setText(code1);
//                }
//
//            }
//        };
//
//        //验证码处理
//        observer = new SmsObserver(LoginActivity.this, mHandler);
//        Uri uri = Uri.parse("content://sms");
//        getContentResolver().registerContentObserver(uri, true, observer);
//
//    }
    @Override
    protected void onPause() {
        super.onPause();
//        getContentResolver().unregisterContentObserver(observer);
    }

    /**
     * 注册处理函数
     */
//    @OnClick(R.id.register)
//    void regist() {
//
//        Log.e("hechao", "register clicked");
//        if (username.getText().toString() == "" || password.getText().toString() == "") {
//            Toast.makeText(LoginActivity.this, "invalid form", Toast.LENGTH_SHORT).show();
//        } else {
//            AsyncHttpClient client = new AsyncHttpClient();
////                    RequestParams params = new RequestParams();
////                    params.add("username", user_name);
////                    params.add("password", pass_word);
//            String url = "http://192.168.56.1/chat/reg.php?username=" + username.getText().toString() + "&password=" + password.getText().toString();
//            Log.e("hechao", url);
//            client.get(url, new AsyncHttpResponseHandler() {
//                @Override
//                public void onSuccess(int i, Header[] headers, byte[] bytes) {
//
//                    String response = new String(bytes);
//                    Log.e("hechao", "response:" + response);
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        String status = jsonObject.getString("code");
//
//                        if (!status.equals("200")) {
//                            Log.e("hechao", "error");
//                        } else {
//                            Log.e("hechao", "register success");
//                            String token = jsonObject.getString("token");
//                            Log.e("hechao", "token: " + token);
//                            App.token = token;
//                            App.username = username.getText().toString();
//                            App.isLogin = true;
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();
//
//                        }
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//                @Override
//                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                    Toast.makeText(LoginActivity.this, "network error", Toast.LENGTH_SHORT).show();
//
//                }
//            });
//
//
//        }
//
//    }


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
     * 登陆处理函数
     */
    @OnClick(R.id.login)
    public void login() {


        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        //    设置Title的图标
        builder.setIcon(R.drawable.rc_progress_sending_style);
        //    设置Title的内容
        builder.setTitle("正在登陆");
        builder.show();



//        Log.e("hechao", ip);
        if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
            Intent intent = new Intent(LoginActivity.this, main.class);
            startActivity(intent);
        } else {


//            String ip = (new NetworkInterface()).getInterfaceAddresses();


            AsyncHttpClient client = new AsyncHttpClient();
            String url = "http://" + App.ip + "/chat/login.php?username=" + username.getText().toString() + "&password=" + password.getText().toString();
            Log.e("hechao", url);
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {

                    String response = new String(bytes);

                    if (response.equals("error")) {
                        Log.e("hechao", "error");
                    } else {
                        Log.e("hechao", "response:" + response);
                        App.token = response;
                        App.username = username.getText().toString();
                        App.isLogin = true;

                        Intent intent = new Intent(LoginActivity.this, main.class);
                        startActivity(intent);
                        finish();
                    }

                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Log.e("hechao", "fail");
                }


            });

        }
    }


}
