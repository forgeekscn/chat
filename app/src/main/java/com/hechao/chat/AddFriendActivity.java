package com.hechao.chat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * 添加好友
 * Created by Administrator on 2016/3/27.
 */
public class AddFriendActivity extends Activity {

    Button addFriendBtn;
    EditText friendName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfriend);

        addFriendBtn = (Button) findViewById(R.id.addFriendBtn);
        friendName = (EditText) findViewById(R.id.addFriendName);

        addFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String friendNameStr = friendName.getText().toString().trim();

                if (friendNameStr.isEmpty()) {
                    Toast.makeText(AddFriendActivity.this, "请输入好友用户名", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (friendNameStr.matches(".*[;'\"\\\\].*")) {
                    Toast.makeText(AddFriendActivity.this, "用户名包含非法字符", Toast.LENGTH_SHORT).show();
                    return;
                }

                AsyncHttpClient client = new AsyncHttpClient();
                String url = "http://" + App.getServerIp() + "/chat/addFriend1.php";
                
                // Fix SQL injection: use POST with RequestParams
                RequestParams params = new RequestParams();
                params.put("username", App.username);
                params.put("target", friendNameStr);
                
                Log.e("hechao", "Adding friend: " + friendNameStr);
                client.post(url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        String response = new String(bytes);
                        Log.e("hechao", "response->" + response);
                        if (response.contains("success")) {
                            Log.e("hechao", "added success");
                            Toast.makeText(AddFriendActivity.this, 
                                friendNameStr + " 已经成为你的好友", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("hechao", "error added");
                            Toast.makeText(AddFriendActivity.this, 
                                "添加失败，用户可能不存在", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        Log.e("hechao", "Network error", throwable);
                        Toast.makeText(AddFriendActivity.this, 
                            "网络错误，请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
