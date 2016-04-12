package com.hechao.chat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2016/4/12.
 */
public class FriendProfile extends Activity{


    String username=null;
    TextView friendname=null;
    Button startPrivate =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendprofile);

        ButterKnife.inject(FriendProfile.this);
        Bundle info=getIntent().getExtras();
        username=info.getString("username");

        friendname = (TextView) findViewById(R.id.friendname);
        friendname.setText("欢迎来到 "+username+" 的主页");

        startPrivate= (Button) findViewById(R.id.startprivate);

        startPrivate.setClickable(false);
        startPrivate.setTextColor(getResources().getColor(R.color.highlighted_text_material_dark));

    }


    @OnClick(R.id.startprivate)
    void setStartPrivateF(){

        RongIM.getInstance().startPrivateChat(FriendProfile.this, username, null);

    }



    @OnClick(R.id.addfriend1)
    void addfriend(){
        AsyncHttpClient client= new AsyncHttpClient();
        String url="http://"+App.ip+"/chat/addFriend.php?username="+App.username+"&target="+username;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response= new String(bytes);

                Log.e("hechao",response);
                friendname.setText("欢迎来到 "+username+" 的主页 "+"你们已经是好友");

                friendname.setTextColor(getResources().getColor(R.color.abc_primary_text_disable_only_material_light));

                startPrivate.setClickable(true);
                startPrivate.setTextSize(15);
                startPrivate.setTextColor(getResources().getColor(R.color.rc_normal_bg));

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });



    }

}
