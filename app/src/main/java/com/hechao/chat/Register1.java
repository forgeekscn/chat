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
 * 注册
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

    private AlertDialog progressDialog;
    private int codenum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register1);
        ButterKnife.inject(this);
    }

    @Override
    protected void onDestroy() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        super.onDestroy();
    }

    @OnClick(R.id.getCode)
    void setGetcode() {
        codenum = (int) (Math.random() * 1000);
        SMSAPIDeal();
        getcode.setText("耐心等待10s");
        getcode.setClickable(false);
    }

    /**
     * 输入验证
     */
    private boolean validateInput(String username, String password, String code) {
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
        if (code == null || code.isEmpty()) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (username.matches(".*[;'\"\\\\].*")) {
            Toast.makeText(this, "用户名包含非法字符", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 注册处理函数
     */
    @OnClick(R.id.register)
    void regist() {
        String usernameStr = username.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();
        String codeStr = code.getText().toString().trim();

        if (!validateInput(usernameStr, passwordStr, codeStr)) {
            return;
        }

        progressDialog = new AlertDialog.Builder(Register1.this)
            .setIcon(R.drawable.rc_progress_sending_style)
            .setTitle("正在提交")
            .create();
        progressDialog.show();

        if (!codeStr.equals("" + codenum)) {
            progressDialog.dismiss();
            Toast.makeText(Register1.this, "验证码错误", Toast.LENGTH_SHORT).show();
            return;
        }

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://" + App.getServerIp() + "/chat/reg.php";
        
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
                Log.e("hechao", "response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("code");

                    if (!status.equals("200")) {
                        Log.e("hechao", "Register failed");
                        Toast.makeText(Register1.this, "注册失败，用户名可能已存在", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("hechao", "Register success");
                        String token = jsonObject.getString("token");
                        Log.e("hechao", "token: " + token);
                        App.token = token;
                        App.username = usernameStr;
                        App.isLogin = true;
                        Intent intent = new Intent(Register1.this, main.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    Log.e("hechao", "JSON parsing error", e);
                    Toast.makeText(Register1.this, "服务器响应异常", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(Register1.this, "网络错误，请检查网络连接", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 短信验证码处理
     */
    private void SMSAPIDeal() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("apikey", BuildConfig.API_KEY_YUNPIAN);
        params.put("mobile", username.getText().toString().trim());
        params.put("text", "【纺大阳光】你好，请保存此验证码" + codenum + "，作为入场的唯一凭证，请妥善保管！");
        String url = "https://sms.yunpian.com/v1/sms/send.json";

        Log.e("hechao", "Sending SMS code...");
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                Log.e("hechao", "SMS response:" + response);
                Toast.makeText(Register1.this, "验证码已发送", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.e("hechao", "SMS sending failed", throwable);
                Toast.makeText(Register1.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


