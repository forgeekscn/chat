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
 * 添加好友列表
 * Created by Administrator on 2016/3/27.
 */
public class AddFriendActivity extends Activity {

    Button addFriendBtn;
    EditText friendName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfriend);

        addFriendBtn= (Button) findViewById(R.id.addFriendBtn);
        friendName= (EditText) findViewById(R.id.addFriendName);

        addFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!friendName.getText().toString().equals("")){
                    AsyncHttpClient client= new AsyncHttpClient();
                    String url="http://"+App.ip+"/chat/addFriend1.php?username="+App.username+"&target="+friendName.getText().toString();
                    Log.e("hechao",url);
                    client.get(url,new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {

                            String response=new String(bytes);
                            Log.e("hechao","response->"+response);
                            if (response.contains("success")){
                                Log.e("hechao","added success");
                                Toast.makeText(AddFriendActivity.this,friendName.getText().toString()+" 已经成为你的好友",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Log.e("hechao","error added");
                                Toast.makeText(AddFriendActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                            }




                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                            Log.e("hechao","error  net ");
                        }
                    });

                }



            }
        });



    }



}
